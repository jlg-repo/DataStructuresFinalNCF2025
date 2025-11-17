public class GraphTest {
    
    public static void main(String[] args) {
        
        // Test 1: Directed Weighted Graph (like the PDF example)
        System.out.println("TEST 1: Directed Weighted Graph");
        System.out.println("================================");
        
        Graph directedGraph = new Graph(5, true, true);
        
        // Add vertices
        for (int i = 0; i < 5; i++) {
            directedGraph.addVertex(i);
        }
        
        // Add edges with weights
        directedGraph.addEdge(0, 0, 1997);
        directedGraph.addEdge(1, 0, 175);
        directedGraph.addEdge(1, 1, 469);
        directedGraph.addEdge(2, 0, 474);
        directedGraph.addEdge(2, 1, 584);
        directedGraph.addEdge(2, 2, 1932);
        directedGraph.addEdge(2, 3, 993);
        directedGraph.addEdge(4, 0, 201);
        directedGraph.addEdge(4, 1, 1411);
        directedGraph.addEdge(4, 2, 423);
        directedGraph.addEdge(4, 3, 211);
        
        // Print the graph
        directedGraph.printGraph();
        
        // Print edges from a specific vertex
        directedGraph.printEdges(2);
        
        // Remove an edge
        System.out.println("\nRemoving edge (2, 3)...");
        directedGraph.removeEdge(2, 3);
        directedGraph.printEdges(2);
        
        // Remove a vertex
        System.out.println("\nRemoving vertex 1...");
        directedGraph.removeVertex(1);
        directedGraph.printGraph();
        
        
        // Test 2: Undirected Unweighted Graph
        System.out.println("\n\nTEST 2: Undirected Unweighted Graph");
        System.out.println("====================================");
        
        Graph undirectedGraph = new Graph(5, false, false);
        
        // Add vertices
        for (int i = 0; i < 5; i++) {
            undirectedGraph.addVertex(i);
        }
        
        // Add unweighted edges
        undirectedGraph.addEdge(0, 1, 1);
        undirectedGraph.addEdge(1, 2, 1);
        undirectedGraph.addEdge(2, 3, 1);
        undirectedGraph.addEdge(3, 4, 1);
        undirectedGraph.addEdge(4, 0, 1);
        
        // Print graph
        undirectedGraph.printGraph();
        
        System.out.println("\nNote: Undirected edges appear in both directions");

        Graph undirectedUnweightedGraph = new Graph(5, false, false);
        for (int i = 0; i < 5; i++) {
            undirectedUnweightedGraph.addVertex(i);

        }

        undirectedUnweightedGraph.addEdge(0, 1, 1);
        undirectedUnweightedGraph.addEdge(1, 2, 1);
        undirectedUnweightedGraph.addEdge(2, 3, 1);

        undirectedUnweightedGraph.printGraph();
        undirectedUnweightedGraph.printEdges(2);

    }
}
