package br.com.crista.fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import static java.util.Objects.isNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationFilterDTO<T extends GenericDTO> {

    // variáveis utilizadas na paginação
    private Integer pageNo;
    private Integer pageSize;
    private String  sortBy;
    private boolean direction;

    private T filtros;

    public Sort.Direction getDirection() {
        return direction ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public Integer getPageSize() {

        if (isNull(pageSize) || pageSize < 0) {

            return 25;
        }

        return pageSize;
    }
}
