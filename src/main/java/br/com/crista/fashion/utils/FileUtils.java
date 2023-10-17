package br.com.crista.fashion.utils;

import static java.util.Objects.nonNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static List readLines(File filename) throws IOException {

        return FileUtils
                .readLines(filename, StandardCharsets.UTF_8);
    }

    public static byte[] getBytesFromFile(String path) {

        try {

            return Files.readAllBytes(Paths.get(path));

        } catch (IOException e) {

            throw new RuntimeException("ERRO AO LER O ARQUIVO" + path);
        }
    }

    public static void deleteFile(File file) {

        if (file.exists()) {

            boolean result = file.delete();

            if (!result) {

                throw new RuntimeException("Erro ao deletar arquivo: "+ file.getName());
            }
        }
    }

    public static ResponseEntity<Resource> getFile(String absolutePath, boolean isPdf) throws FileNotFoundException {

        File file = new File(absolutePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=" + file.getName());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String type = isPdf ? "application/pdf" : "application/octet-stream";

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(type))
                .body(resource);
    }

    public static InputStreamResource getFile(String absolutePath) throws FileNotFoundException {

        return new InputStreamResource(new FileInputStream(new File(absolutePath)));
    }

    public static ResponseEntity<Resource> getFile(byte[] bytes, String fileName, boolean isPdf) throws FileNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        ByteArrayResource resource = new ByteArrayResource(bytes);

        String type = isPdf ? "application/pdf" : "application/octet-stream";

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(type))
                .body(resource);
    }

    public static String addSeparatorFolder(String path) {

        if (nonNull(path)) {

            char c = path.charAt(path.length()-1);

            if (c != File.separatorChar) {

                path += File.separatorChar;
            }
        }

        return path;
    }

    private static String generateFileName(String name) {

        return generateCode() + "-" + name;
    }

    public static String generateCode() {

        return UUID.randomUUID().toString();
        // return (new BigInteger(130, new SecureRandom())).toString(32) + Calendar.getInstance().getTimeInMillis();
    }

    private static String processFileName(String fileName, MultipartFile file) {

        if (nonNull(fileName) && !fileName.isEmpty()) {

            fileName = generateFileName(fileName);
            String type = getFileType(file);

            if (!fileName.contains(type)) {

                fileName = fileName + type;
            }

            return fileName;

        } else {

            return generateFileName(file.getOriginalFilename());
        }
    }

    private static String processFileName(String fileName, String strBase64) {

        fileName = generateFileName(fileName);
        String type = getFileType(strBase64);

        if (!fileName.contains(type)) {

            fileName = fileName + "." +type;
        }

        return fileName;
    }

    private static String getFileType(MultipartFile file) {

        String multipartFileName = file.getOriginalFilename();
        return multipartFileName.substring(multipartFileName.indexOf("."), multipartFileName.length());
    }

    public static String getFileType(String strBase64) {

        return strBase64.substring(strBase64.indexOf("/")+1,strBase64.indexOf(";"));
        /*String tipo = strBase64.substring(strBase64.indexOf("/")+1,strBase64.indexOf(";"));
        if(new String()[{"jpeg"},"jpg","png"])*/
    }

    public static String getMimeType(String strBase64) {

        return strBase64.split(",")[0];
    }

    public static String getBase64SemMimeType(String strBase64) {

        return strBase64.split(",")[1];
    }

    public static String getFileInBase64(String path) {

        try {

            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));

            return Base64.encodeBase64String(fileContent);

        } catch (IOException e) {

            log.error("Erro ao tentar ler o arquivo, arquivo não encontrado", e.getMessage());
        }

        return "";
    }

    private static void validar(String name, MultipartFile file) {

        if (name.contains("/")) {

            throw new RuntimeException("Não é permitido utilizar / no nome do arquivo");

        } else if (file.isEmpty()) {

            throw new RuntimeException("Arquivo vázio não é permitido");
        }
    }

    public static ResponseEntity<Resource> getFilePdfHttp(String urlAcesso, boolean isPdf) throws IOException {

        byte [] data = getFileExterno(urlAcesso);

        return getFile(data, UUID.randomUUID() + ".pdf", isPdf);
    }

    public static byte[] getFileExterno(String urlAcesso)  throws IOException {

        URL url = new URL(urlAcesso);
        URLConnection uc = url.openConnection();
        int len = uc.getContentLength();
        InputStream is = new BufferedInputStream(uc.getInputStream());

        try {

            byte[] data = new byte[len];
            int offset = 0;

            while (offset < len) {

                int read = is.read(data, offset, data.length - offset);

                if (read < 0) {
                    break;
                }

                offset += read;
            }

            if (offset < len) {

                throw new IOException(
                        String.format("Read %d bytes; expected %d", offset, len));
            }

            return data;

        } finally {

            is.close();
        }
    }

    public static File convertMultipartToFile(MultipartFile file) throws IOException {

        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        return convertedFile;
    }

    public static void copyURLToFile(URL source, File destination, int connectionTimeoutMillis, int readTimeoutMillis) throws IOException {

        URLConnection connection = source.openConnection();
        connection.setConnectTimeout(connectionTimeoutMillis);
        connection.setReadTimeout(readTimeoutMillis);
        InputStream stream = connection.getInputStream();
        Throwable var6 = null;

        try {

            copyInputStreamToFile(stream, destination);

        } catch (Throwable var15) {

            var6 = var15;
            throw var15;

        } finally {

            if (nonNull(stream)) {

                if (nonNull(var6)) {

                    try {

                        stream.close();

                    } catch (Throwable var14) {

                        var6.addSuppressed(var14);
                    }

                } else {

                    stream.close();
                }
            }

        }

    }
}
