package ru.spbstu.telematics.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyHashMapTest {

    private MyHashMap<String, Integer> customHashMap;
    private HashMap<String, Integer> standardHashMap;

    @BeforeEach
    void setUp() {
        customHashMap = new MyHashMap<>();
        standardHashMap = new HashMap<>();
    }

    @Test
    void testPutAndGet() {
        customHashMap.put("one", 1);
        standardHashMap.put("one", 1);

        assertEquals(1, customHashMap.get("one"));
        assertEquals(1, standardHashMap.get("one"));
    }

    @Test
    void testContainsKey() {
        customHashMap.put("one", 1);
        standardHashMap.put("one", 1);

        assertTrue(customHashMap.containsKey("one"));
        assertTrue(standardHashMap.containsKey("one"));

        assertFalse(customHashMap.containsKey("two"));
        assertFalse(standardHashMap.containsKey("two"));
    }

    @Test
    void testContainsValue() {
        customHashMap.put("one", 1);
        standardHashMap.put("one", 1);

        assertTrue(customHashMap.containsValue(1));
        assertTrue(standardHashMap.containsValue(1));

        assertFalse(customHashMap.containsValue(2));
        assertFalse(standardHashMap.containsValue(2));
    }

    @Test
    void testRemove() {
        customHashMap.put("one", 1);
        standardHashMap.put("one", 1);

        assertEquals(1, customHashMap.remove("one"));
        assertEquals(1, standardHashMap.remove("one"));

        assertFalse(customHashMap.containsKey("one"));
        assertFalse(standardHashMap.containsKey("one"));
    }

    @Test
    void testSize() {
        customHashMap.put("one", 1);
        customHashMap.put("two", 2);
        customHashMap.put("three", 3);

        assertEquals(3, customHashMap.size());

        standardHashMap.put("one", 1);
        standardHashMap.put("two", 2);
        standardHashMap.put("three", 3);

        assertEquals(3, standardHashMap.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(customHashMap.isEmpty());
        assertTrue(standardHashMap.isEmpty());

        customHashMap.put("one", 1);
        standardHashMap.put("one", 1);

        assertFalse(customHashMap.isEmpty());
        assertFalse(standardHashMap.isEmpty());
    }

    @Test
    void testClear() {
        customHashMap.put("one", 1);
        customHashMap.put("two", 2);
        customHashMap.put("three", 3);

        customHashMap.clear();
        assertEquals(0, customHashMap.size());
        assertTrue(customHashMap.isEmpty());

        standardHashMap.put("one", 1);
        standardHashMap.put("two", 2);
        standardHashMap.put("three", 3);

        standardHashMap.clear();
        assertEquals(0, standardHashMap.size());
        assertTrue(standardHashMap.isEmpty());
    }

    @Test
    void testClone() {
        customHashMap.put("one", 1);
        customHashMap.put("two", 2);
        customHashMap.put("three", 3);

        MyHashMap<String, Integer> clonedCustomHashMap = customHashMap.clone();
        assertEquals(customHashMap.size(), clonedCustomHashMap.size());

        Iterator<MyHashMap.Entry<String, Integer>> customIterator = clonedCustomHashMap.iterator();
        while (customIterator.hasNext()) {
            MyHashMap.Entry<String, Integer> entry = customIterator.next();
            assertTrue(customHashMap.containsKey(entry.getKey()));
            assertEquals(customHashMap.get(entry.getKey()), entry.getValue());
        }

        standardHashMap.put("one", 1);
        standardHashMap.put("two", 2);
        standardHashMap.put("three", 3);

        HashMap<String, Integer> clonedStandardHashMap = (HashMap<String, Integer>) standardHashMap.clone();
        assertEquals(standardHashMap.size(), clonedStandardHashMap.size());

        Iterator<Map.Entry<String, Integer>> standardIterator = clonedStandardHashMap.entrySet().iterator();
        while (standardIterator.hasNext()) {
            Map.Entry<String, Integer> entry = standardIterator.next();
            assertTrue(standardHashMap.containsKey(entry.getKey()));
            assertEquals(standardHashMap.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    void testIterable() {
        customHashMap.put("one", 1);
        customHashMap.put("two", 2);
        customHashMap.put("three", 3);

        int customMapCount = 0;
        for (MyHashMap.Entry<String, Integer> entry : customHashMap) {
            customMapCount++;
        }
        assertEquals(3, customMapCount);

        standardHashMap.put("one", 1);
        standardHashMap.put("two", 2);
        standardHashMap.put("three", 3);

        int standardMapCount = 0;
        for (Map.Entry<String, Integer> entry : standardHashMap.entrySet()) {
            standardMapCount++;
        }
        assertEquals(3, standardMapCount);
    }
}
