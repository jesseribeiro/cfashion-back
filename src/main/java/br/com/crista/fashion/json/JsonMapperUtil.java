/*******************************************************************************
 * ===========================================================
 * Ankush : Big Data Cluster Management Solution
 * ===========================================================
 * 
 * (C) Copyright 2014, by Impetus Technologies
 * 
 * This is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License (LGPL v3) as
 * published by the Free Software Foundation;
 * 
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this software; if not, write to the Free Software Foundation, 
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/
/**
 * 
 */
package br.com.crista.fashion.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static java.util.Objects.isNull;

/**
 * The Class JsonMapperUtil.
 * 
 * @author hokam chauhan Utility class to convert json string into the object
 *         and vice versa.
 */
public class JsonMapperUtil {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Method to convert a object into JSON string.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String jsonFromObject(Object object) {

		try {

			/* Generating the JSON string for the object. */
			return mapper.writeValueAsString(object);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to Convert the given json string into the specified class object.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param jsonString
	 *            the json string
	 * @param targetClass
	 *            the target class
	 * @return Object of the class formed using the JSON string.
	 */
	public static <S> S objectFromString(String jsonString, Class<S> targetClass) {

		S object = null;

		try {

			/* Creating the target class object */
			object = targetClass.newInstance();

		} catch (InstantiationException e) {

			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {

			throw new RuntimeException(e);
		}

		try {

			/* Populating the object with json string values. */
			object = mapper.readValue(jsonString, targetClass);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}

		return object;
	}

	/**
	 * Object from json file.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param filePath
	 *            the file path
	 * @param targetClass
	 *            the target class
	 * @return the s
	 */
	public static <S> S objectFromJsonFile(String filePath, Class<S> targetClass) {

		S object = null;

		try {

			File jsonFile = new File(filePath);
			object = objectFromString(FileUtils.readFileToString(jsonFile),
					targetClass);

		} catch (IOException e) {

			throw new RuntimeException(e);
		}

		return object;
	}

	/**
	 * Object from map.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param values
	 *            the values
	 * @param targetClass
	 *            the target class
	 * @return the s
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws Exception
	 *             the exception
	 */
	public static <S> S objectFromMap(Map<String, Object> values,
			Class<S> targetClass) throws IllegalArgumentException,
			IllegalAccessException, InstantiationException,
			InvocationTargetException, Exception {

		// Creating target class object.
		S mainObject = targetClass.newInstance();

		// Getting fields of the class.
		Field[] fields = targetClass.getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<String, Field>();

		for (Field field : fields) {

			// Putting fields in fieldMap
			fieldMap.put(field.getName(), field);
		}

		// Iterating over the key set in value map.
		for (String mainKey : values.keySet()) {

			if (values.get(mainKey) instanceof LinkedHashMap) {

				// Creating target object type.

				if (isNull(fieldMap.get(mainKey))) {

					continue;
				}

				Object subObject = fieldMap.get(mainKey).getType()
						.newInstance();

				// Casting to map.
				Map subValues = (Map) values.get(mainKey);

				// Iterating over the map keys.
				for (Object subKey : subValues.keySet()) {

					BeanUtils.setProperty(subObject, (String) subKey,
							subValues.get(subKey));
				}

				// setting the sub object in bean main object.
				BeanUtils.setProperty(mainObject, mainKey, subObject);

			} else {

				// setting the value in bean main object.
				BeanUtils.setProperty(mainObject, mainKey, values.get(mainKey));
			}
		}

		return mainObject;
	}

	/**
	 * Method to get Map from the object.
	 * 
	 * @param obj
	 *            the obj
	 * @return the map
	 * @throws Exception
	 *             the exception
	 */
	public static Map<String, Object> mapFromObject(Object obj) {

		ObjectMapper mapper = new ObjectMapper();

		return mapper.convertValue(obj, HashMap.class);
	}

	/**
	 * Map from object.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param obj
	 *            the obj
	 * @param className
	 *            the class name
	 * @return the s
	 * @throws Exception
	 *             the exception
	 */
	public static <S> S objectFromObject(Object obj, Class<S> className)
			throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		return mapper.convertValue(obj, className);
	}

	/**
	 * Gets the node info list.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param dataMap
	 *            the monitoring info
	 * @param key
	 *            the key
	 * @param targetClass
	 *            the target class
	 * @return the node info list
	 * @throws Exception
	 *             the exception
	 */
	public static <S> List<S> getListObject(List<Map> listMapData,
			Class<S> targetClass) throws Exception {

		// checking if map is null.
		if (isNull(listMapData)) {

			return null;
		}

		// Creating the resultant list object.
		List<S> result = new ArrayList<S>(listMapData.size());

		// populating values in the list object from map.
		for (Map<String, Object> info : listMapData) {

			// creating target class object.
			S status = targetClass.newInstance();
			// populating object with map values.
			BeanUtils.populate(status, info);
			// adding object in result list.
			result.add(status);
		}

		return result;
	}
}