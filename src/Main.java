import skiplist.SkipList;
import skiplist.map.SkipListMap;
import skiplist.map.SkipListMapProfilerTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Jakub on 2015-11-13.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Testing SkipListMap");

//        test();
        for(int i = 0; i < 100000; i++) {
            valuesToPut.add(i);
        }
        Collections.shuffle(valuesToPut);
        testPut();
        testGet();
        testContainsKey();
        testHigherKey();
        testRemove();
    }

    static SkipListMapProfilerTest skipListMap = new SkipListMapProfilerTest();
    static ConcurrentSkipListMap<Integer, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();
    static ArrayList<Integer> valuesToPut = new ArrayList<>();
    static PerformanceComparisonTester tester = new PerformanceComparisonTester();

    private static void testPut() {
        System.out.print("Testing put method performance: ");

        tester.testOneStart();
        for(Integer value : valuesToPut) {
            concurrentSkipListMap.put(value, value);
        }
        tester.testOneEnd();

        tester.testTwoStart();
        for(Integer value : valuesToPut) {
            skipListMap.put(value, value);
        }
        tester.testTwoEnd();

        System.out.println(tester);
    }

    private static void testContainsKey() {
        System.out.print("Testing containsKey method performance: ");

        tester.testOneStart();
        valuesToPut.forEach(concurrentSkipListMap::containsKey);
        tester.testOneEnd();

        tester.testTwoStart();
        valuesToPut.forEach(skipListMap::containsKey);
        tester.testTwoEnd();

        System.out.println(tester);
    }

    private static void testGet() {
        System.out.print("Testing get method performance: ");

        tester.testOneStart();
        valuesToPut.forEach(concurrentSkipListMap::get);
        tester.testOneEnd();

        tester.testTwoStart();
        valuesToPut.forEach(skipListMap::get);
        tester.testTwoEnd();

        System.out.println(tester);
    }

    private static void testRemove() {
        System.out.print("Testing remove method performance: ");

        tester.testOneStart();
        valuesToPut.forEach(concurrentSkipListMap::remove);
        tester.testOneEnd();

        tester.testTwoStart();
        valuesToPut.forEach(skipListMap::remove);
        tester.testTwoEnd();

        System.out.println(tester);
    }

    private static void testHigherKey() {
        System.out.print("Testing higherKey: ");
        for(int i = 0; i < 99999; i++) {
            if(skipListMap.higherKey(i) != i + 1) {
                System.out.println("HigherKey test not passed!");
                return;
            }
        }
        System.out.println("HigherKey test passed!");
    }

    private static class PerformanceComparisonTester {
        private long startTime1,
                startTime2,
                endTime1,
                endTime2;

        public void testOneStart() {
            startTime1 = System.currentTimeMillis();
        }

        public void testOneEnd() {
            endTime1 = System.currentTimeMillis();
        }

        public void testTwoStart() {
            startTime2 = System.currentTimeMillis();
        }

        public void testTwoEnd() {
            endTime2 = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            long totalTime1 = endTime1 - startTime1,
                    totalTime2 = endTime2 - startTime2;

            double percentage;

            percentage = totalTime2 - totalTime1;
            percentage /= totalTime2;
            percentage *= 100;

            return totalTime1 + ", " + totalTime2 + " -> " + percentage + "%";
        }
    }

    private static void test() {
        SkipList skipList1 = new SkipList();
//        System.out.println(skipList1);
        skipList1.add(1);
//        System.out.println(skipList1);
        skipList1.add(2);
//        System.out.println(skipList1);
        skipList1.add(4);
//        System.out.println(skipList1);
        skipList1.add(3);
//        System.out.println(skipList1);
        skipList1.add(10);
        System.out.println(skipList1);

        System.out.println(skipList1.lowerKey(1));
        System.out.println(skipList1.lowerKey(2));
        System.out.println(skipList1.lowerKey(3));
        System.out.println(skipList1.lowerKey(4));
        System.out.println(skipList1.lowerKey(10));
        System.out.println(skipList1.higherKey(1));
        System.out.println(skipList1.higherKey(2));
        System.out.println(skipList1.higherKey(3));
        System.out.println(skipList1.higherKey(4));
        System.out.println(skipList1.higherKey(10));


        for(Integer nodeKey : skipList1) {
            System.out.println(nodeKey + ", ");
        }
    }
}
