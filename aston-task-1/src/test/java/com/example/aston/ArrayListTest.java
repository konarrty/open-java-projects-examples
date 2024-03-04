package com.example.aston;

import com.example.aston.list.List;
import com.example.aston.list.impl.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ArrayListTest {

    private List<String> list;

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add("string1");
        list.add("string2");
        list.add("string3");
    }

    @Test
    public void sizeTest() {
        list.remove(1);

        int size = list.size();

        assertEquals(2, size);
    }

    @Test
    public void addTest() {
        list.add("string4");

        boolean isContains = list.contains("string4");

        assertTrue(isContains);
    }

    @Test
    public void removeTest() {
        String removedElement = list.remove(1);

        assertEquals("string2", removedElement);
    }

    @Test
    public void indexOfTest() {
        list.add("string4");

        int index = list.indexOf("string4");

        assertEquals(3, index);
    }

    @Test
    public void resizeTest() {
        addManyElements();

        int size = list.size();

        assertEquals(11, size);
    }

    private void addManyElements() {
        list.add("string4");
        list.add("string5");
        list.add("string6");
        list.add("string7");
        list.add("string8");
        list.add("string9");
        list.add("string10");
        list.add("string11");
    }
}
