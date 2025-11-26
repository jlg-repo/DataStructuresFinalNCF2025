import java.io.IOException;

public class GraphVisualizer {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "dot",
                    "-Tpng",
                    "DOTGraph.dot", //File name line, may change
                    "-o",
                    "Graph.png" // Output file name
            );

            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("PNG 'Graph.png' created successfully"); // Success!
            } else {
                System.out.println("Exit code: " + exitCode); // Error exit code
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
