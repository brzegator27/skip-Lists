import skiplist.SkipList;
import skiplist.map.SkipListMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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
        testHigherKey();
        testRemove();
    }

    static SkipListMap skipListMap = new SkipListMap();
    static ConcurrentSkipListMap<Integer, Integer> concurrentSkipListMap = new ConcurrentSkipListMap();
    static long startTime,
            endTime,
            totalTimeConcurrent, totalTimeMyList;
    static double percentage;
    static ArrayList<Integer> valuesToPut = new ArrayList<>();

    private static void testPut() {
        System.out.println("Testing put method performance:");
        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            concurrentSkipListMap.put(value, value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeConcurrent = endTime - startTime;

        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            skipListMap.put(value, value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeMyList = endTime - startTime;

        percentage = totalTimeMyList - totalTimeConcurrent;
        percentage /= totalTimeMyList;
        percentage *= 100;
        System.out.println(totalTimeConcurrent + ", " + totalTimeMyList + " -> " + percentage + "% \n");
    }

    private static void testContainsKey() {
        System.out.println("Testing containsKey method performance:");
        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            concurrentSkipListMap.put(value, value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeConcurrent = endTime - startTime;

        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            skipListMap.put(value, value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeMyList = endTime - startTime;

        percentage = totalTimeMyList - totalTimeConcurrent;
        percentage /= totalTimeMyList;
        percentage *= 100;
        System.out.println(totalTimeConcurrent + ", " + totalTimeMyList + " -> " + percentage + "% \n");
    }

    private static void testGet() {
        System.out.println("Testing get method performance:");
        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            concurrentSkipListMap.get(value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeConcurrent = endTime - startTime;

        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            skipListMap.get(value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeMyList = endTime - startTime;

        percentage = totalTimeMyList - totalTimeConcurrent;
        percentage /= totalTimeMyList;
        percentage *= 100;
        System.out.println(totalTimeConcurrent + ", " + totalTimeMyList + " -> " + percentage + "% \n");
    }

    private static void testRemove() {
        System.out.println("Testing remove method performance:");
        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            concurrentSkipListMap.remove(value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeConcurrent = endTime - startTime;

        startTime = System.currentTimeMillis();
        for(Integer value : valuesToPut) {
            skipListMap.remove(value);
        }
        endTime   = System.currentTimeMillis();
        totalTimeMyList = endTime - startTime;

        percentage = totalTimeMyList - totalTimeConcurrent;
        percentage /= totalTimeMyList;
        percentage *= 100;
        System.out.println(totalTimeConcurrent + ", " + totalTimeMyList + " -> " + percentage + "% \n");
    }

    private static void testHigherKey() {
        System.out.println("Testing higherKey");
        for(int i = 0; i < 99999; i++) {
            if(skipListMap.higherKey(i) != i + 1) {
                System.out.println("HigherKey test not passed! \n");
                return;
            }
        }
        System.out.println("HigherKey test passed! \n");
    }

    private static void test() {
        Random randGenerator = new Random();
//        System.out.println(randGenerator.nextInt(2));
//        System.out.println(randGenerator.nextInt(2));
//        System.out.println(randGenerator.nextInt(2));
//        System.out.println(randGenerator.nextInt(2));
//        System.out.println(randGenerator.nextInt(2));

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
