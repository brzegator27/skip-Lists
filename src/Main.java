import skiplist.SkipList;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by Jakub on 2015-11-13.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("asdfasdf");

//        test();
        performanceComparison();
    }

    private static void performanceComparison() {
        Random randomGenerator = new Random();
        SkipList skipList = new SkipList();
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet();
        long startTime,
                endTime,
                totalTimeConcurrent, totalTimeMyList;
        double percentage;

        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            concurrentSkipListSet.add(randomGenerator.nextInt());
        }
        endTime   = System.currentTimeMillis();
        totalTimeConcurrent = endTime - startTime;
        System.out.println(totalTimeConcurrent);

        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            skipList.add(randomGenerator.nextInt());
        }
        endTime   = System.currentTimeMillis();
        totalTimeMyList = endTime - startTime;
        System.out.println(totalTimeMyList);

        percentage = totalTimeMyList - totalTimeConcurrent;
        percentage /= totalTimeMyList;
        percentage *= 100;
        System.out.println(percentage + "%");
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
