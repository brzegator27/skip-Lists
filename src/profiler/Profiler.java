package profiler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 2015-12-04.
 */
public class Profiler {
    static protected String className,
            classPackage,
            classFileDirectoryPath;

    static protected File classFile,
            classFileJava;

    public static void main(String[] args) {
        classFileDirectoryPath = "C:\\Users\\Jakub\\Documents\\Java projects\\skipLists\\src\\skiplist\\map";
        className = "SkipListMap";
        classPackage = "skiplist.map";
        createClassProfiler();
    }

    private static void createClassProfiler() {
        classFile = new File(classFileDirectoryPath + "\\" + className + ".java");

        try {
            URL url = classFile.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            Class ourClass = classLoader.loadClass(classPackage + "." + className);
            System.out.println(ourClass.getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class ProfilerClass {
        private String className,
                classType,
                extendsClassName;

        ProfilerClass(String className, String classType, String extendsClassName) {
            this.className = className;
            this.classType = classType;
            this.extendsClassName = extendsClassName;
        }

        private List<ProfilerMethod> methods = new ArrayList<>();

        public void addFunction(ProfilerMethod function) {
            methods.add(function);
        }

        public String classTopAsString() {
            return classType + " class " + className + " extends " + extendsClassName;
        }

        protected void saveClassToFile() {

        }

        public String testerInnerClassAsString() {
            return "static PerformanceComparisonTester tester = new PerformanceComparisonTester();\n" +
                    "\n" +
                    "    private static class PerformanceComparisonTester {\n" +
                    "        private long startTime,\n" +
                    "                endTime;\n" +
                    "\n" +
                    "        private String testName;\n" +
                    "\n" +
                    "        private final static Logger logger = Logger.getLogger(PerformanceComparisonTester.class.getName());\n" +
                    "\n" +
                    "        public void testStart(String testName) {\n" +
                    "            startTime = System.nanoTime();\n" +
                    "            this.testName = testName;\n" +
                    "        }\n" +
                    "\n" +
                    "        public void testEnd() {\n" +
                    "            endTime = System.nanoTime();\n" +
                    "            doLog();\n" +
                    "        }\n" +
                    "\n" +
                    "        protected void doLog() {\n" +
                    "            logger.log(Level.INFO, toString());\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public String toString() {\n" +
                    "            long totalTime = endTime - startTime;\n" +
                    "\n" +
                    "            return this.testName + \" \" + totalTime + \" ns\";\n" +
                    "        }\n" +
                    "    }";
        }

        public String classMethodsAsString() {
            String methodsAsString = "";

            for(ProfilerMethod method : methods) {
                methodsAsString += "\n";
                methodsAsString += method.toString();
                methodsAsString += "\n";
            }

            return methodsAsString;
        }

        @Override
        public String toString() {
            String classAsString = classTopAsString();
            classAsString += "{\n";
            classAsString += testerInnerClassAsString();
            classAsString += classMethodsAsString();
            classAsString += "\n}";
            return classAsString;
        }
    }

    private static class ProfilerMethod {
        private String functionName,
            functionType,
            functionReturnType;

        private String[] parameters;

        private boolean isStatic;

        ProfilerMethod(String functionName, String functionType, String functionReturnType, boolean isStatic) {
            this.functionName = functionName;
            this.functionType = functionType;
            this.functionReturnType = functionReturnType;
            this.isStatic = isStatic;
        }

        protected String parametersAsString(boolean withTypes) {
            String parametersAsStringRough = "",
                    parametersAsString;

            for(int i = 0; i < parameters.length; i++) {
                parametersAsStringRough += withTypes ? parameters[i] : "";
                parametersAsStringRough += " p" + i + ", ";
            }

            parametersAsString = parametersAsStringRough.substring(0, parametersAsStringRough.length() - 2);

            return parametersAsString;
        }

        protected String testStartAsString() {
            return "tester.testStart(\"" + functionName + "\");";
        }

        protected String testEndAsString() {
            return "tester.testEnd(\"\");";
        }

        protected String returnAsString() {
            return functionReturnType != null ? "return ret;" : "";
        }

        protected String superCallAsString() {
            String superCall = "";

            superCall += functionReturnType !=  null ? functionReturnType + " ret = " : "";
            superCall += "super.put(" + parametersAsString(false) + ");";

            return superCall;
        }

        @Override
        public String toString() {
            String method = functionType
                    + " " + functionReturnType
                    + " " + functionName
                    + "(" + parametersAsString(true) + ")"
                    + "{\n";

            method += testStartAsString();
            method += superCallAsString();
            method += testEndAsString();
            method += returnAsString();
            method += "\n}";

            return method;
        }
    }
}
