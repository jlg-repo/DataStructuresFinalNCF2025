import java.util.List;
import java.util.Optional;

public class Graph {
    private int maxVertices;
    private double[][] adjacencyMatrix;
    private boolean[] vertexExists;
    private boolean directed;
    private boolean weighted;

    // Constructor
    public Graph(int maxVertices, boolean directed, boolean weighted) {
        this.maxVertices = maxVertices;
        this.directed = directed;
        this.weighted = weighted;
        this.adjacencyMatrix = new double[maxVertices][maxVertices];
        this.vertexExists = new boolean[maxVertices];

        // Initialize with -1 (meaning no edge)
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                adjacencyMatrix[i][j] = -1;
            }
        }
    }

    // Add a vertex
    public void addVertex(int v) {
        if (v >= 0 && v < maxVertices) {
            vertexExists[v] = true;
        }
    }

    // Remove a vertex
    public void removeVertex(int v) {
        if (v >= 0 && v < maxVertices) {
            vertexExists[v] = false;
            // Remove all edges connected to this vertex
            for (int i = 0; i < maxVertices; i++) {
                adjacencyMatrix[v][i] = -1;
                adjacencyMatrix[i][v] = -1;
            }
        }
    }

    // Add an edge
    public void addEdge(int from, int to, double weight) {
        if (from >= 0 && from < maxVertices && to >= 0 && to < maxVertices) {
            if (vertexExists[from] && vertexExists[to]) {
                adjacencyMatrix[from][to] = weight;
                // If undirected, add the reverse edge too
                if (!directed) {
                    adjacencyMatrix[to][from] = weight;
                }

                // Note: fixed bug re: handling weighted or unweighted graphs. additional test
                // for unweighted undirected graph is in GraphTest
            }
        }
    }

    // Remove an edge
    public void removeEdge(int from, int to) {
        if (from >= 0 && from < maxVertices && to >= 0 && to < maxVertices) {
            adjacencyMatrix[from][to] = -1;
            if (!directed) {
                adjacencyMatrix[to][from] = -1;
            }
        }
    }

    // Print all vertices
    public void printVertices() {
        System.out.println("\nPrinting the vertices of the graph:");
        for (int i = 0; i < maxVertices; i++) {
            if (vertexExists[i]) {
                System.out.println("vertex: " + i);
            }
        }
    }

    // Print all edges
    public void printEdges() {
        System.out.println("\nPrinting the edges of the graph:");
        for (int i = 0; i < maxVertices; i++) {
            if (vertexExists[i]) {
                for (int j = 0; j < maxVertices; j++) {
                    if (adjacencyMatrix[i][j] != -1) {
                        if (weighted) {
                            System.out.println("edge (" + i + ", " + j + ") = " + (int)adjacencyMatrix[i][j]);

                        } else {
                            System.out.println("edge (" + i + ", " + j + ")");
                        }
                    }
                }
            }
        }
    }

    // Print edges from a specific vertex
    public void printEdges(int v) {
        if (v >= 0 && v < maxVertices && vertexExists[v]) {
            System.out.println("\nEdges from vertex " + v + ":");
            for (int i = 0; i < maxVertices; i++) {
                if (adjacencyMatrix[v][i] != -1) {
                    if (weighted) {
                        System.out.println("edge (" + v + ", " + i + ") = " + (int)adjacencyMatrix[v][i]);

                    }
                    else {
                        System.out.println("edge (" + v + ", " + i + ")");
                    }
                }
            }
        }
    }

    // Print the entire graph
    public void printGraph() {
        System.out.println("\n=== Graph (" + (directed ? "Directed" : "Undirected") + ") ===");
        printVertices();
        printEdges();
    }
    public void createAndPrintSubgraph(List<Integer> vertices) {
        System.out.println("\n=== Subgraph ===");

        // Print vertices in subgraph
        System.out.println("\nVertices in subgraph:");
        for (int v : vertices) {
            if (v >= 0 && v < maxVertices && vertexExists[v]) {
                System.out.println("vertex: " + v);
            }
        }

        // Print edges between vertices in subgraph
        System.out.println("\nEdges in subgraph:");
        for (int i : vertices) {
            if (vertexExists[i]) {
                for (int j : vertices) {
                    if (adjacencyMatrix[i][j] != -1) {
                        if (weighted) {
                            System.out.println("edge (" + i + ", " + j + ") = " + (int)adjacencyMatrix[i][j]);

                        } else {
                            System.out.println("edge (" + i + ", " + j + ")");
                        }
                    }
                }
            }
        }
    }

}

/*

Note: basic pattern for any operation involving the adjacency matrix is like this:

public ReturnType methodName(int v) {
    1. Check if valid
    if (v >= 0 && v < maxVertices && vertexExists[v]) {

    2. Loop through row v
        for (int i = 0; i < maxVertices; i++) {
            if (adjacencyMatrix[v][i] != -1) {
                // Do something with edge v→i
            }
        }
        return result;
    }
    return defaultValue;
}

 */