package com.arash.ariani;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentedArrayListTest {

    private SegmentedArrayList<String> list;

    @BeforeEach
    void setUp() {
        list = new SegmentedArrayList<>();
    }

    @Test
    void givenEmptyList_whenAddElements_thenElementsAreAccessible() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        assertEquals(3, list.size(), "Size should be 3");
        assertEquals("Apple", list.get(0), "First element should be Apple");
        assertEquals("Banana", list.get(1), "Second element should be Banana");
        assertEquals("Cherry", list.get(2), "Third element should be Cherry");
    }

    @Test
    void givenNonEmptyList_whenAddAtSpecificIndex_thenElementsAreShiftedCorrectly() {
        list.add("Apple");
        list.add("Cherry");

        list.add(1, "Banana");

        assertEquals(3, list.size(), "Size should be 3");
        assertEquals("Apple", list.get(0), "First element should be Apple");
        assertEquals("Banana", list.get(1), "Second element should be Banana");
        assertEquals("Cherry", list.get(2), "Third element should be Cherry");
    }

    @Test
    void givenListWithElements_whenRemoveElementAtIndex_thenElementIsRemovedAndSizeDecreases() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        assertEquals("Banana", list.remove(1), "Removed element should be Banana");
        assertEquals(2, list.size(), "Size should be 2 after removal");
        assertEquals("Apple", list.get(0), "First element should be Apple");
        assertEquals("Cherry", list.get(1), "Second element should be Cherry");
    }

    @Test
    void givenListWithElements_whenRemoveElementFromEnd_thenLastElementIsRemoved() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        assertEquals("Cherry", list.remove(2), "Removed element should be Cherry");
        assertEquals(2, list.size(), "Size should be 2 after removal");
        assertEquals("Apple", list.get(0), "First element should be Apple");
        assertEquals("Banana", list.get(1), "Second element should be Banana");
    }

    @Test
    void givenListWithElements_whenClear_thenSizeIsZero() {
        list.add("Apple");
        list.add("Banana");

        list.clear();

        assertEquals(0, list.size(), "Size should be 0 after clear");
    }

    @Test
    void givenListWithFewElements_whenGetWithInvalidIndex_thenThrowIndexOutOfBoundsException() {
        list.add("Apple");
        list.add("Banana");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2), "IndexOutOfBoundsException expected");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), "IndexOutOfBoundsException expected");
    }

    @Test
    void givenListWithFewElements_whenSetWithInvalidIndex_thenThrowIndexOutOfBoundsException() {
        list.add("Apple");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "Banana"), "IndexOutOfBoundsException expected");
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "Banana"), "IndexOutOfBoundsException expected");
    }

    @Test
    void givenListWithElements_whenSetElement_thenElementIsUpdated() {
        list.add("Apple");
        list.add("Banana");

        list.set(1, "Cherry");

        assertEquals("Apple", list.get(0), "First element should still be Apple");
        assertEquals("Cherry", list.get(1), "Second element should now be Cherry");
    }

    @Test
    void givenList_whenAddNullElement_thenListContainsNull() {
        list.add("Apple");
        list.add(null);

        assertEquals(2, list.size(), "Size should be 2 after adding null");
        assertNull(list.get(1), "Second element should be null");
    }

    @Test
    void givenEmptyList_whenAddMultipleElements_thenSizeAndAccessAreCorrect() {
        for (int i = 0; i < 1500; i++) {
            list.add("Item " + i);
        }

        assertEquals(1500, list.size(), "Size should be 1500");
        assertEquals("Item 0", list.get(0), "First element should be Item 0");
        assertEquals("Item 1499", list.get(1499), "Last element should be Item 1499");
    }

    @Test
    void givenListWithElements_whenRemoveAllElements_thenListIsEmpty() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        list.remove(0);
        list.remove(0);
        list.remove(0);

        assertTrue(list.isEmpty(), "List should be empty after removing all elements");
    }

    @Test
    void givenListWithElements_whenAddAtInvalidIndex_thenThrowIndexOutOfBoundsException() {
        list.add("Apple");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "Banana"), "IndexOutOfBoundsException expected");
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, "Banana"), "IndexOutOfBoundsException expected");
    }

    @Test
    void givenListWithMultipleElements_whenInsertionsAndDeletions_thenSizeIsManagedCorrectly() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");

        assertEquals(4, list.size(), "Size should be 4 initially");

        list.remove(2);
        assertEquals(3, list.size(), "Size should be 3 after removal");

        list.add("Elderberry");
        assertEquals(4, list.size(), "Size should be 4 after adding");

        list.clear();
        assertEquals(0, list.size(), "Size should be 0 after clear");
    }

    @Test
    void givenEmptyList_whenAddLargeNumberOfElements_thenListHandlesLargeDataSet() {
        for (int i = 0; i < 10000; i++) {
            list.add("Element " + i);
        }

        assertEquals(10000, list.size(), "Size should be 10000 after adding");
        assertEquals("Element 0", list.get(0), "First element should be Element 0");
        assertEquals("Element 9999", list.get(9999), "Last element should be Element 9999");
    }
}
