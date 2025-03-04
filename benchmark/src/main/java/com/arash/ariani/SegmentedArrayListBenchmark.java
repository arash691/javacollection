package com.arash.ariani;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SegmentedArrayListBenchmark {

    @Param({"1000", "10000", "100000"})
    public int size;

    List<Integer> arrayList;
    List<Integer> segmentedList;

    @Setup(Level.Trial)
    public void setUp() {
        arrayList = new ArrayList<>();
        segmentedList = new SegmentedArrayList<>();
    }

    @Benchmark
    public void addArrayList() {
        arrayList.clear();
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }
    }

    @Benchmark
    public void addSegmentedList() {
        segmentedList.clear();
        for (int i = 0; i < size; i++) {
            segmentedList.add(i);
        }
    }

    @Setup(Level.Invocation)
    public void fillLists() {
        // Fill lists so that get() benchmarks run on a pre-populated collection.
        arrayList.clear();
        segmentedList.clear();
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            segmentedList.add(i);
        }
    }

    @Benchmark
    public int getArrayList() {
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
        }
        return sum;
    }

    @Benchmark
    public int getSegmentedList() {
        int sum = 0;
        for (int i = 0; i < segmentedList.size(); i++) {
            sum += segmentedList.get(i);
        }
        return sum;
    }
}
