import skiplist.SkipList;

import java.util.Random;

/**
 * Created by Jakub on 2015-11-13.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("asdfasdf");

        test();
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
