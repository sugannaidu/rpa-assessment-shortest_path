package za.co.javadeveloper.assessment.solution;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ShortestPathSolution {

    private static final String ARG_TEST = "test";
    private static final String ARG_SHOW_STEPS = "steps";
    private static final String PATH_TEST_FILES = "map";
    private boolean showSteps;

    public static void main(String[] args) throws Exception {
        ShortestPathSolution solve = new ShortestPathSolution();
        solve.showSteps = includeSteps(args);
        if (isInteractiveMode(args))
            solve.interactiveMode();
        else
            solve.preloadedTestCases();
    }

    private static boolean isInteractiveMode(String[] args) {
        return args.length <= 0 || !(ARG_TEST.equalsIgnoreCase(args[0]));
    }

    private static boolean includeSteps(String[] args) {
        return ((args.length == 1)&&(ARG_SHOW_STEPS.equalsIgnoreCase(args[0])) ||
        ((args.length == 2)&&(ARG_SHOW_STEPS.equalsIgnoreCase(args[1]) || ARG_SHOW_STEPS.equalsIgnoreCase(args[0]))));
    }

    private void interactiveMode() {
        Scanner keyboardScanner = new Scanner(System.in);
        System.out.print("Enter file name: ");
        System.out.print(" ---> ");
        String fileName = keyboardScanner.next();
        File map1 = new File(fileName);
        Grid grid;
        try {
            grid = new Grid(map1);
            grid.setShowSteps(showSteps);
            solveWithBreadthFirstSearch(grid);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    private static void solveWithBreadthFirstSearch(Grid grid) {
        ShortestPathAlgorithm bfs = new ShortestPathAlgorithm();
        grid.printInterimMap();
        List<GridCell> path = bfs.solve(grid);
        grid.printPath(path);
        grid.reset();
    }

    private void preloadedTestCases() throws IOException {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile()) {
            runTestWithJARFile(jarFile);
        } else {
            runTestWithIDE();
        }

    }

    private void runTestWithJARFile(File jarFile) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final String testFile = entries.nextElement().getName();
                if (testFile.startsWith(PATH_TEST_FILES + "/") && !(testFile.endsWith(PATH_TEST_FILES + "/"))) { //filter according to the path
                    System.out.println();
                    System.out.println(" --------------- Test cases: " + testFile);
                    InputStream inputStream = ClassLoader.getSystemResourceAsStream(testFile);
                    List<String> lines = IOUtils.readLines(inputStream);
                    try {
                        Grid grid1 = new Grid(lines);
                        grid1.setShowSteps(showSteps);
                        solveWithBreadthFirstSearch(grid1);
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                        System.out.println(e.getCause());
                    }

                }
            }
        }
    }

    private void runTestWithIDE() throws FileNotFoundException {
        final URL url = ShortestPathSolution.class.getResource("/" + PATH_TEST_FILES);
        if (url != null) {
            try {
                final File apps = new File(url.toURI());
                for (File app : apps.listFiles()) {
                    System.out.println();
                    System.out.println(" --------------- Test cases: " + app.getName());
                    try {
                        Grid grid1 = new Grid(app);
                        grid1.setShowSteps(showSteps);
                        solveWithBreadthFirstSearch(grid1);
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                        System.out.println(e.getCause());
                    }
                }
            } catch (URISyntaxException ex) {
                // never happens
            }
        }
    }
}
///home/sugann/workspace-play/RPA-Assessment/rpa-assessment-shortest_path/src/main/resources/map/map.txt