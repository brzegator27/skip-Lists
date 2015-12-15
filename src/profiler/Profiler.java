package profiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
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
    static protected File classFile;
    static protected ProfilerClass profilerClass;

    public static void main(String[] args) {
        classFileDirectoryPath = "C:\\Users\\Jakub\\Documents\\Java projects\\skipLists\\src\\skiplist\\map";
        className = "SkipListMap";
        classPackage = "skiplist.map";
        createProfiler();
    }

    private static void createProfiler() {
        classFile = new File(classFileDirectoryPath + "\\" + className + ".java");

        try {
            URL url = classFile.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);
            Class ourClass = classLoader.loadClass(classPackage + "." + className);

            manageClass(ourClass);
            manageAllMethods(ourClass);

            saveClassToFile();
            System.out.println(profilerClass.toString());
        } catch (MalformedURLException | UnsupportedEncodingException | FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void manageClass(Class baseClass) {
        String extendsClassFullName = baseClass.getName(),
                classType = getType(baseClass.getModifiers());

        profilerClass = new ProfilerClass(extendsClassFullName, classType);
        profilerClass.addImport("java.util.logging.*");
        profilerClass.addImport("java.io.IOException");
        profilerClass.setClassPackage(classPackage);
    }

    private static void manageAllMethods(Class baseClass) {
        ProfilerMethod newMethod;

        for(Method method : baseClass.getMethods()) {
            if(Modifier.isFinal(method.getModifiers())) {
                continue;
            }

            String[] methodParametersArr;
            String methodName = method.getName(),
                    methodType = getType(method.getModifiers()),
                    methodFullReturnType = method.getReturnType().getName(),
                    methodReturnType = processType(methodFullReturnType, true);
            Parameter[] methodParameters = method.getParameters();

            methodParametersArr = new String[methodParameters.length];
            for(int i = 0; i < methodParameters.length; i++) {
                methodParametersArr[i] = processType(methodParameters[i].getType().toString(), true);
            }

            newMethod = new ProfilerMethod(
                    methodName,
                    methodType,
                    methodReturnType,
                    Modifier.isStatic(method.getModifiers()),
                    methodParametersArr);

            profilerClass.addFunction(newMethod);
        }
    }

    private static String processType(String fullType, boolean addToImport) {
        if(fullType == null) {
            return null;
        }
        String[] fullTypeWithJunkInArr = fullType.split("\\s+"),
                fullTypeInArr = fullTypeWithJunkInArr[fullTypeWithJunkInArr.length - 1].split("\\.");
        String type = fullTypeInArr[fullTypeInArr.length - 1];

        if(fullTypeInArr.length > 1 && addToImport) {
            profilerClass.addImport(fullTypeWithJunkInArr[fullTypeWithJunkInArr.length - 1]);
        }

        return type;
    }

    private static String getType(int modifiers) {
        String type = "";

        if (Modifier.isPublic(modifiers)) {
            type += "public ";
        }
        if(Modifier.isProtected(modifiers)) {
            type += "protected ";
        }
        if (Modifier.isPrivate(modifiers)) {
            type += "private ";
        }
        if(Modifier.isAbstract(modifiers)) {
            type += "abstract ";
        }

        return type;
    }

    protected static void saveClassToFile() throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = className + "Profiler.java";
        PrintWriter writer = new PrintWriter(classFileDirectoryPath + "\\" + fileName, "UTF-8");
        writer.println(profilerClass.toString());
        writer.close();
    }

    private static class ProfilerClass {
        private String classType,
                extendsClassName,
                classPackage;

        private ArrayList<String> imports = new ArrayList<>();

        public void addImport(String singleImport) {
            imports.add(singleImport);
        }

        public void setClassPackage(String classPackage) {
            this.classPackage = classPackage;
        }

        ProfilerClass(String extendsClassFullName, String classType) {
            this.classType = classType;
            this.extendsClassName = processType(extendsClassFullName, false);
            addImport(extendsClassFullName);
        }

        private List<ProfilerMethod> methods = new ArrayList<>();

        public void addFunction(ProfilerMethod function) {
            methods.add(function);
        }

        public String classTopAsString() {
            return classType + " class " + extendsClassName + "Profiler extends " + extendsClassName;
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
                    "        PerformanceComparisonTester() {\n" +
                    "            try {\n" +
                    "                FileHandler fileHandler = new FileHandler(\"" + classFileDirectoryPath.replace("\\", "\\\\") + "\\\\" + className + "Profiler.log\");\n" +
                    "                SimpleFormatter formatter = new SimpleFormatter();\n" +
                    "                fileHandler.setFormatter(formatter);\n" +
                    "                logger.addHandler(fileHandler);\n" +
                    "                logger.setUseParentHandlers(false);\n" +
                    "            } catch (IOException e) {\n" +
                    "                e.printStackTrace();\n" +
                    "            }\n" +
                    "        }\n" +
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

        private String importsAsString() {
            String importsAsString = "";

            for(String singleImport : imports) {
                importsAsString += "import " + singleImport + ";\n";
            }

            return importsAsString;
        }

        private String classPackageAsString() {
            return "package " + classPackage + ";\n";
        }

        @Override
        public String toString() {
            return classPackageAsString()
                    + importsAsString()
                    + classTopAsString()
                    + "{\n"
                    + testerInnerClassAsString()
                    + classMethodsAsString()
                    + "\n}";
        }
    }

    private static class ProfilerMethod {
        private String functionName,
            functionType,
            functionReturnType;

        private String[] parameters;

        private boolean isStatic;

        ProfilerMethod(String functionName, String functionType, String functionReturnType, boolean isStatic, String[] parameters) {
            this.functionName = functionName;
            this.functionType = functionType;
            this.functionReturnType = functionReturnType;
            this.isStatic = isStatic;
            this.parameters = parameters;
        }

        protected String parametersAsString(boolean withTypes) {
            String parametersAsStringRough = "",
                    parametersAsString;

            for(int i = 0; i < parameters.length; i++) {
                parametersAsStringRough += withTypes ? parameters[i] : "";
                parametersAsStringRough += " p" + i + ", ";
            }

            parametersAsString = parametersAsStringRough.length() != 0 ?
                    parametersAsStringRough.substring(0, parametersAsStringRough.length() - 2) :
                    "";

            return parametersAsString;
        }

        protected String testStartAsString() {
            return "tester.testStart(\"" + functionName + "\");";
        }

        protected String testEndAsString() {
            return "tester.testEnd();";
        }

        protected String returnAsString() {
            return functionReturnType != "void" ? "return ret;" : "";
        }

        protected String superCallAsString() {
            String superCall = "";

            superCall += functionReturnType !=  "void" ? functionReturnType + " ret = " : "";
            superCall += "super." + functionName + "(" + parametersAsString(false) + ");";

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
