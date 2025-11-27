import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DotHandler {
    public void writeDot(Graph e) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("DOTGraph.dot")))) {
            out.write("Directed Graph");
            out.newLine();
            int maxV = e.getMaxVertices();
            for (int i = 0; i < maxV; i++) {
                if (e.vertexExists(i)) {
                    for (int j = 0; j < maxV; j++) {
                        if (e.getEdgeWeight(i, j) != -1) {
                            if (e.isWeighted()) {
                                System.out.println("edge (" + i + " -> " + j + ") = " + (int) e.getEdgeWeight(i, j));
                                out.write(i + " -> " + j);
                                out.newLine();
                            } else {
                                System.out.println("edge (" + i + " -> " + j + ")");
                                out.write(i + " -> " + j);
                                out.newLine();
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
