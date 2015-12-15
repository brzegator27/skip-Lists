package skiplist.map;
import skiplist.map.SkipListMap;
import java.util.logging.*;
import java.io.IOException;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.String;
import java.util.Iterator;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Integer;
import java.lang.Object;
import java.util.Spliterator;
import java.util.function.Consumer;
public  class SkipListMapProfiler extends SkipListMap{
static PerformanceComparisonTester tester = new PerformanceComparisonTester();

    private static class PerformanceComparisonTester {
        private long startTime,
                endTime;

        private String testName;

        private final static Logger logger = Logger.getLogger(PerformanceComparisonTester.class.getName());

        PerformanceComparisonTester() {
            try {
                FileHandler fileHandler = new FileHandler("C:\\Users\\Jakub\\Documents\\Java projects\\skipLists\\src\\skiplist\\map\\SkipListMapProfiler.log");
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
public  void remove(Integer p0){
tester.testStart("remove");super.remove( p0);tester.testEnd();
}

public  Integer get(Integer p0){
tester.testStart("get");Integer ret = super.get( p0);tester.testEnd();return ret;
}

public  void put(Integer p0, Integer p1){
tester.testStart("put");super.put( p0,  p1);tester.testEnd();
}

public  String toString(){
tester.testStart("toString");String ret = super.toString();tester.testEnd();return ret;
}

public  Iterator iterator(){
tester.testStart("iterator");Iterator ret = super.iterator();tester.testEnd();return ret;
}

public  boolean containsKey(Integer p0){
tester.testStart("containsKey");boolean ret = super.containsKey( p0);tester.testEnd();return ret;
}

public  Integer lowerKey(Integer p0){
tester.testStart("lowerKey");Integer ret = super.lowerKey( p0);tester.testEnd();return ret;
}

public  Integer higherKey(Integer p0){
tester.testStart("higherKey");Integer ret = super.higherKey( p0);tester.testEnd();return ret;
}

public  boolean equals(Object p0){
tester.testStart("equals");boolean ret = super.equals( p0);tester.testEnd();return ret;
}

public  int hashCode(){
tester.testStart("hashCode");int ret = super.hashCode();tester.testEnd();return ret;
}

public  Spliterator spliterator(){
tester.testStart("spliterator");Spliterator ret = super.spliterator();tester.testEnd();return ret;
}

public  void forEach(Consumer p0){
tester.testStart("forEach");super.forEach( p0);tester.testEnd();
}

}
