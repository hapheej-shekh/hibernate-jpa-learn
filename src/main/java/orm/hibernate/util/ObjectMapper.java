package orm.hibernate.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectMapper {

	public static <T> T map(Object source, Class<T> destinationClass) {
	    try {
	        T target = destinationClass.getDeclaredConstructor().newInstance();
	        Field[] sourceFields = source.getClass().getDeclaredFields();

	        for (Field sourceField : sourceFields) {
	            sourceField.setAccessible(true);
	            Object value = sourceField.get(source);

	            try {
	                Field targetField = destinationClass.getDeclaredField(sourceField.getName());
	                targetField.setAccessible(true);

	                Class<?> targetFieldType = targetField.getType();

	                if (value == null) continue;

	                if (isPrimitiveOrWrapper(value.getClass())) {
	                    targetField.set(target, value);
	                } else if (Collection.class.isAssignableFrom(targetFieldType)) {
	                    // Handle collections
	                    Collection<?> sourceCollection = (Collection<?>) value;
	                    Collection<Object> targetCollection;

	                    if (Set.class.isAssignableFrom(targetFieldType)) {
	                        targetCollection = new HashSet<>();
	                    } else if (List.class.isAssignableFrom(targetFieldType)) {
	                        targetCollection = new ArrayList<>();
	                    } else {
	                        throw new UnsupportedOperationException("Unsupported collection type: " + targetFieldType);
	                    }

	                    // Try to get generic type (best-effort; may need more advanced logic for full support)
	                    ParameterizedType parameterizedType = (ParameterizedType) targetField.getGenericType();
	                    Class<?> targetGenericType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

	                    for (Object item : sourceCollection) {
	                        targetCollection.add(map(item, targetGenericType));
	                    }

	                    targetField.set(target, targetCollection);
	                } else {
	                    // Nested object mapping
	                    Object mappedValue = map(value, targetFieldType);
	                    targetField.set(target, mappedValue);
	                }

	            } catch (NoSuchFieldException ignored) {
	                System.err.println("Field not found: " + ignored.getMessage());
	            }
	        }

	        return target;
	    } catch (Exception e) {
	        throw new RuntimeException("Mapping failed: " + e.getMessage(), e);
	    }
	}

	private static boolean isPrimitiveOrWrapper(Class<?> type) {
	    return type.isPrimitive()
	            || type == String.class
	            || Number.class.isAssignableFrom(type)
	            || Boolean.class == type
	            || Character.class == type
	            || java.util.Date.class.isAssignableFrom(type);
	}
}
