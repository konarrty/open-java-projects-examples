package com.example.aston;

import com.example.aston.map.Map;
import com.example.aston.map.impl.HashMap;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HashMapTest {

    private Map<Integer, String> map;

    @Before
    public void init() {
        map = new HashMap<>();
        map.put(0, "string1");
        map.put(1, "string2");
        map.put(2, "string3");
    }

    @Test
    public void sizeTest() {
        map.remove(1);

        int size = map.size();

        assertEquals(2, size);
    }

    @Test
    public void addNewTest() {
        String newString = "string4";
        map.put(3, newString);

        String element = map.get(3);

        assertEquals(newString, element);
    }

    @Test
    public void putTest() {
        String newString = "string4";
        map.put(0, newString);

        String element = map.get(0);

        assertEquals(newString, element);
    }

    @Test
    public void removeTest() {
        String removedElement = map.remove(1);

        assertEquals("string2", removedElement);
    }

    @Test
    public void getTest() {
        String element = map.get(0);

        assertEquals("string1", element);
    }

    @Test
    public void resizeTest() {
        addManyElements();

        int size = map.size();

        assertEquals(17, size);
    }

    private void addManyElements() {
        map.put(3, "string4");
        map.put(4, "string5");
        map.put(5, "string6");
        map.put(6, "string7");
        map.put(7, "string8");
        map.put(8, "string9");
        map.put(9, "string10");
        map.put(10, "string11");
        map.put(11, "string12");
        map.put(12, "string13");
        map.put(13, "string14");
        map.put(14, "string15");
        map.put(15, "string16");
        map.put(16, "string17");
    }

}
