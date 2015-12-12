package skiplist.map;

import java.util.Iterator;
import java.util.logging.*;

/**
 * Created by Jakub on 2015-11-21.
 */
public class SkipListMapProfilerTest extends SkipListMap {

    public SkipListMapProfilerTest() {
        super();
    }

    static PerformanceComparisonTester tester = new PerformanceComparisonTester();

    private static class PerformanceComparisonTester {
        private long startTime,
                endTime;

        private String testName;

        private final static Logger logger = Logger.getLogger(PerformanceComparisonTester.class.getName());

        public void testStart(String testName) {
            startTime = System.nanoTime();
            this.testName = testName;
        }

        public void testEnd() {
            endTime = System.nanoTime();
            doLog();
        }

        protected void doLog() {
            logger.log(Level.INFO, toString());
        }

        @Override
        public String toString() {
            long totalTime = endTime - startTime;

            return this.testName + " " + totalTime + " ns";
        }
    }

    public void put(Integer a, Integer b) {
        tester.testStart("put");
        super.put(a, b);
        tester.testEnd();
    }

//    void manageNewMandrel(MapNode a) {
//        tester.testStart("manageNewMandrel");
//        super.manageNewMandrel(a);
//        tester.testEnd();
//    }
//
//    void insertMandrel(MapNode a, MapNode b) {
//        tester.testStart("insertMandrel");
//        super.insertMandrel(a, b);
//        tester.testEnd();
//    }
//
//    void insertNode(MapNode a, MapNode b, MapNode c, MapNode d) {
//        tester.testStart("insertNode");
//        super.insertNode(a, b, c, d);
//        tester.testEnd();
//    }

    public void remove(Integer a) {
        tester.testStart("remove");
        super.remove(a);
        tester.testEnd();
    }

//    void removeNode(MapNode a) {
//        tester.testStart("removeNode");
//        super.removeNode(a);
//        tester.testEnd();
//    }
//
//
//    int _getNodeMaxLevel() {
//        tester.testStart("_getNodeMaxLevel");
//        int ret = super._getNodeMaxLevel();
//        tester.testEnd();
//        return ret;
//    }

    public Integer get(Integer a) {
        tester.testStart("get");
        Integer ret = super.get(a);
        tester.testEnd();
        return ret;
    }

    public MapNode getNodeByKey(Integer a) {
        tester.testStart("getNodeByKey");
        MapNode ret = super.getNodeByKey(a);
        tester.testEnd();
        return ret;
    }

    public MapNode lowerNode(Integer a) {
        tester.testStart("lowerNode");
        MapNode ret = super.lowerNode(a);
        tester.testEnd();
        return ret;
    }

    public Integer higherKey(Integer a) {
        tester.testStart("higherKey");
        Integer ret = super.higherKey(a);
        tester.testEnd();
        return ret;
    }

//    MapNode higherNode(Integer a) {
//        tester.testStart("higherNode");
//        MapNode ret = super.higherNode(a);
//        tester.testEnd();
//        return ret;
//    }

    public Integer lowerKey(Integer a) {
        tester.testStart("lowerKey");
        Integer ret = super.lowerKey(a);
        tester.testEnd();
        return ret;
    }

//    MapNode getLowestNodeByKey(Integer a) {
//        tester.testStart("getLowestNodeByKey");
//        MapNode ret = super.getLowestNodeByKey(a);
//        tester.testEnd();
//        return ret;
//    }

    public boolean containsKey(Integer a) {
        tester.testStart("containsKey");
        boolean ret = super.containsKey(a);
        tester.testEnd();
        return ret;
    }

    @Override
    public String toString() {
        tester.testStart("toString");
        String ret = super.toString();
        tester.testEnd();
        return ret;
    }

//    String singleRowToString(MapNode a) {
//        tester.testStart("singleRowToString");
//        String ret = super.singleRowToString(a);
//        tester.testEnd();
//        return ret;
//    }

    @Override
    public Iterator<Integer> iterator() {
        tester.testStart("iterator");
        Iterator ret = super.iterator();
        tester.testEnd();
        return ret;
    }
}
