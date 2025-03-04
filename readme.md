# SegmentedArrayList

SegmentedArrayList is a custom Java collection designed as an alternative to the traditional `ArrayList`. It avoids the overhead of copying a large array when the list grows by using multiple fixed-size segments (or chunks) rather than one contiguous array.

## Overview

In a standard `ArrayList`, when the capacity is exceeded, a new, larger array is allocated, and all elements are copied over. This process can be inefficient for large datasets. In contrast, the **SegmentedArrayList** allocates a new segment (a fixed-size array) when needed, eliminating the need for mass copying of elements. Each element is stored in a segment, and its global index is calculated using the segment index and the offset within the segment.

## Key Concepts

- **Segment**: A fixed-size array that holds a portion of the overall data.
- **Dynamic Growth**: When the current segment is full, a new segment is created without copying the existing data.
- **Index Calculation**:
    - **Segment Index**: `index / segmentSize`
    - **Offset in Segment**: `index % segmentSize`

## Features

- **Avoids Large Array Copying**: New segments are added dynamically, which can lead to performance improvements in scenarios with frequent resizing.
- **Constant-Time Random Access**: Direct calculation of the segment and offset allows for O(1) access.
- **Seamless Integration**: Implements standard Java collection interfaces by extending `AbstractList` and implementing `List`, `RandomAccess`, `Cloneable`, and `Serializable`.
- **Scalable Memory Allocation**: Ideal for applications that deal with large and dynamically changing datasets.

## Usage Example

Here’s a simple example demonstrating how to use the SegmentedArrayList:

```java
import com.arash.ariani.SegmentedArrayList;

public class Example {
    public static void main(String[] args) {
        SegmentedArrayList<String> list = new SegmentedArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        
        System.out.println("Element at index 1: " + list.get(1)); // Outputs: Banana
        
        list.add(1, "Blueberry");
        System.out.println("Element at index 1 after insertion: " + list.get(1)); // Outputs: Blueberry
        
        list.remove(2);
        System.out.println("List size after removal: " + list.size());
    }
}
