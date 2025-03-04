package com.arash.ariani;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

/**
 * A segmented ArrayList implementation.
 * Instead of one contiguous array that is resized by copying, this list uses fixed-size segments.
 *
 * @param <E> the type of elements in this list
 */
public class SegmentedArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SEGMENT_SIZE = 1024;

    // Fixed size of each segment.
    private final int segmentSize;

    // List of segments; each segment is an array of type E.
    private final List<E[]> segments;

    // Total number of elements stored.
    private int size;

    /**
     * Creates a new SegmentedArrayList with the default segment size.
     */
    @SuppressWarnings("unchecked")
    public SegmentedArrayList() {
        this.segmentSize = DEFAULT_SEGMENT_SIZE;
        this.segments = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Creates a new SegmentedArrayList with a specified segment size.
     *
     * @param segmentSize the fixed size for each segment; must be positive
     * @throws IllegalArgumentException if segmentSize is non-positive
     */
    @SuppressWarnings("unchecked")
    public SegmentedArrayList(int segmentSize) {
        if (segmentSize <= 0) {
            throw new IllegalArgumentException("Segment size must be positive");
        }
        this.segmentSize = segmentSize;
        this.segments = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E get(int index) {
        rangeCheck(index);
        int segIndex = index / segmentSize;
        int offset = index % segmentSize;
        E[] segment = segments.get(segIndex);
        return segment[offset];
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        int segIndex = index / segmentSize;
        int offset = index % segmentSize;
        E[] segment = segments.get(segIndex);
        E old = segment[offset];
        segment[offset] = element;
        return old;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(E element) {
        ensureCapacity(size + 1);
        int segIndex = size / segmentSize;
        int offset = size % segmentSize;
        segments.get(segIndex)[offset] = element;
        size++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent elements
     * to the right.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        // Special case: appending at the end.
        if (index == size) {
            add(element);
            return;
        }
        // Increase capacity by one.
        add(null); // This adds a slot at the end.
        // Shift elements right starting from the end down to the index.
        for (int i = size - 1; i > index; i--) {
            E prev = get(i - 1);
            set(i, prev);
        }
        set(index, element);
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E remove(int index) {
        rangeCheck(index);
        E removed = get(index);
        // Shift elements left.
        for (int i = index; i < size - 1; i++) {
            set(i, get(i + 1));
        }
        // Clear the last element.
        int lastIndex = size - 1;
        int segIndex = lastIndex / segmentSize;
        int offset = lastIndex % segmentSize;
        segments.get(segIndex)[offset] = null;
        size--;
        // Remove any completely unused segments.
        int expectedSegments = (size + segmentSize - 1) / segmentSize;
        while (segments.size() > expectedSegments) {
            segments.remove(segments.size() - 1);
        }
        return removed;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all of the elements from this list.
     */
    @Override
    public void clear() {
        segments.clear();
        size = 0;
    }

    /**
     * Ensures that there is enough capacity for the given minimum capacity.
     * Allocates new segments if needed.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void ensureCapacity(int minCapacity) {
        int currentCapacity = segments.size() * segmentSize;
        while (currentCapacity < minCapacity) {
            @SuppressWarnings("unchecked")
            E[] newSegment = (E[]) new Object[segmentSize];
            segments.add(newSegment);
            currentCapacity += segmentSize;
        }
    }

    /**
     * Checks if the index is within the bounds of the list.
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if index is out of range
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * Returns a shallow copy of this SegmentedArrayList instance.
     *
     * @return a clone of this instance
     */
    @Override
    public Object clone() {
        try {
            @SuppressWarnings("unchecked")
            SegmentedArrayList<E> clone = (SegmentedArrayList<E>) super.clone();
            // Deep copy of segments.
            clone.segments.clear();
            for (int i = 0; i < segments.size(); i++) {
                E[] segment = segments.get(i);
                @SuppressWarnings("unchecked")
                E[] newSegment = (E[]) new Object[segmentSize];
                System.arraycopy(segment, 0, newSegment, 0, segment.length);
                clone.segments.add(newSegment);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
