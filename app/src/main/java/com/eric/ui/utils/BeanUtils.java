package com.eric.ui.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Bean反射类型转换
 */

public class BeanUtils {

    /**
     * @param object    目标对象
     * @param fieldName 目标域名称
     * @param value     目标域值
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     *
     * @param object    目标对象
     * @param fieldName 目标域名称
     * @return 类的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     *
     * @param clazz     目标对象Class字节码
     * @param fieldName 目标域名称
     * @return 类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Class clazz, final String fieldName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     *
     * @param field 域
     */
    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

}
