package com.scirisk.google.persistence;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class DynamicBean<T> {

	private T wrappedBean;
	private Class<? extends Object> wrappedBeanClass;
	private BeanInfo wrappedBeanInfo;

	public DynamicBean(T wrappedBean) {
		try {
			this.wrappedBean = wrappedBean;
			this.wrappedBeanClass = wrappedBean.getClass();
			this.wrappedBeanInfo = Introspector.getBeanInfo(wrappedBeanClass);
		} catch (IntrospectionException e) {
			throw new IllegalArgumentException(
					"Error introspecting wrapped bean", e);
		}
	}

	public Object getProperty(String name) throws Exception {
		for (PropertyDescriptor pd : wrappedBeanInfo.getPropertyDescriptors()) {
			if (name.equals(pd.getName())) {
				return pd.getReadMethod().invoke(wrappedBean);
			}
		}
		throw new IllegalArgumentException("No such property: " + name);
	}

	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		for (PropertyDescriptor pd : wrappedBeanInfo.getPropertyDescriptors()) {
			if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
				try {
					properties.put(pd.getName(),
							pd.getReadMethod().invoke(wrappedBean));
				} catch (Exception e) {
					throw new IllegalStateException("Error getting property: "
							+ pd.getName(), e);
				}
			}
		}
		return properties;
	}

	public DynamicBean<T> setProperty(String name, Object value) {
		for (PropertyDescriptor pd : wrappedBeanInfo.getPropertyDescriptors()) {
			if (name.equals(pd.getName())) {
				try {
					System.out.printf("Setting property %s value %s%n", name,
							value);

					Class<?> propertyClass = pd.getWriteMethod()
							.getParameterTypes()[0];
					pd.getWriteMethod().invoke(wrappedBean,
							convertType(propertyClass, value));
					break;
				} catch (Exception e) {
					throw new IllegalArgumentException(String.format(
							"Error setting property %s value %s", name, value),
							e);
				}
			}
		}
		return this;
	}

	private Object convertType(Class<?> targetClass, Object value) {
		if (value == null) {
			return value;
		}
		if (targetClass.isEnum()) {
			return Enum.valueOf((Class<? extends Enum>) targetClass,
					String.valueOf(value));
		} else {
			return value;
		}
	}

	public DynamicBean<T> setProperties(Map<String, Object> properties) {
		for (PropertyDescriptor pd : wrappedBeanInfo.getPropertyDescriptors()) {
			if (!"class".equals(pd.getName())) {
				try {
					System.out.printf("Setting property %s value %s%n",
							pd.getName(), properties.get(pd.getName()));
					Class<?> propertyClass = pd.getWriteMethod()
							.getParameterTypes()[0];

					pd.getWriteMethod().invoke(
							wrappedBean,
							convertType(propertyClass,
									properties.get(pd.getName())));
				} catch (Exception e) {
					throw new IllegalArgumentException(String.format(
							"Error setting property %s value %s", pd.getName(),
							properties.get(pd.getName())), e);
				}
			}
		}
		return this;
	}

	public T getBean() {
		return wrappedBean;
	}

}
