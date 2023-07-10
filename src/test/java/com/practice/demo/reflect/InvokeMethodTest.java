package com.practice.demo.reflect;

import com.practice.demo.util.ReflectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class InvokeMethodTest {
    @Test
    public void testInvokeMethod()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TestObject testObject = new TestObject();

        // Test 1: Using Object... parameters
        String result1 = (String) ReflectionUtils.invokeMethod(testObject,
                                                    "testReflection", "Alice");
        Assertions.assertEquals("Hello, Alice", result1);

        // Test 2: Using List<Class<?>> parameterTypes
        String result2 = (String) ReflectionUtils.invokeMethod(testObject,
                                                "testReflection", List.of(String.class), "Bob");
        Assertions.assertEquals("Hello, Bob", result2);

        // Test 3: Directly Using Class<?>[] classes
        String result3 = (String) ReflectionUtils.invokeMethod(testObject, "testReflection",
                                                                new Class<?>[]{String.class}, "Charlie");
        Assertions.assertEquals("Hello, Charlie", result3);

        // Test 4: Using Object... parameters
        String result4 = (String) ReflectionUtils.invokeMethod(testObject,
                                                    "testReflection", "Alice");
        Assertions.assertNotEquals("Hello, Alice1", result4);
    }
}







