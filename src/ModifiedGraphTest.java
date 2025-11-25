import java.util.ArrayList;
import java.util.List;

public class ModifiedGraphTest {

    public static void main(String[] args) {


        Graph undirectedGraph = new Graph(6, false,false);

        // Add vertices
        for (int i = 1; i < 6; i++) {
            undirectedGraph.addVertex(i);
        }

        // Add edges

        // unweighted, but for now give all a weight of 0
        undirectedGraph.addEdge(1, 2, 0);
        undirectedGraph.addEdge(1, 4, 0);
        undirectedGraph.addEdge(1, 5, 0);
        undirectedGraph.addEdge(2, 3, 0);
        undirectedGraph.addEdge(4, 2, 0);
        undirectedGraph.addEdge(4, 5, 0);


        // Print the graph
        undirectedGraph.printGraph();

        // Print edges from a specific vertex, 4
        undirectedGraph.printEdges(4);

        // Print the subgraph containing vertices {1, 2, 4}
        //List<Integer> vertices = new ArrayList<Integer>(3);
        //vertices.add(1);
        //vertices.add(2);
        //vertices.add(4);
        //vertices.add(5);
        //vertices.add(3);
        //undirectedGraph.createAndPrintSubgraph(vertices);


        // Delete vertex 5

        undirectedGraph.removeVertex(5);

        undirectedGraph.printGraph();




    }

}
