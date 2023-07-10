package com.practice.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class ReflectionUtils {

    public static Object invokeMethod(Object object, String methodName,
                                      Class<?>[] classes, Object... parameters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = object.getClass().getMethod(methodName, classes);
        return method.invoke(object, parameters);
    }

    public static Object invokeMethod(Object object, String methodName,
                                      Object... parameters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] classes = Arrays.stream(parameters)
                                   .map(Object::getClass)
                                   .toArray(Class<?>[]::new);
        return invokeMethod(object, methodName, classes, parameters);
    }

    public static Object invokeMethod(Object object, String methodName,
                                      List<Class<?>> parameterTypes,
                                      Object... parameters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] classes = parameterTypes.toArray(new Class<?>[0]);
        return invokeMethod(object, methodName, classes, parameters);
    }
}
