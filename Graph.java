import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Scanner;

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
    // getters for DOTMAN
    public int getMaxVertices(){
        return maxVertices;
    }
    public boolean vertexExists(int v){
        return vertexExists[v];
    }
    public double getEdgeWeight(int from,int to){
        return adjacencyMatrix[from][to];
    }
    public boolean isWeighted() {
        return weighted;
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

    // Compatibility getters
    public boolean vertexExists(int v) {
        return vertexExists[v];
    }

    public double getEdgeWeight(int from, int to) {
        return adjacencyMatrix[from][to];
    }

    // Additional getters
    public Graph getGraph() {
        return this;
    }

    public double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public boolean[] getVertexExists() {
        return vertexExists;
    }

    public boolean isDirected() {
        return directed;
    }

    // Path finding helpers
    private boolean dfsPath(int current, int target, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        if (current == target) {
            return true;
        }

        for (int i = 0; i < maxVertices; i++) {
            if (adjacencyMatrix[current][i] != -1 && !visited[i]) {
                if (dfsPath(i, target, visited, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public double getPathCost(List<Integer> path) {
        double cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {  // Fixed: was path.size()
            cost += adjacencyMatrix[path.get(i)][path.get(i + 1)];
        }
        return cost;
    }

    // Cycle detection helper
    private boolean dfsCycle(int current, boolean[] visited, boolean[] inStack, List<Integer> cycle) {
        visited[current] = true;
        inStack[current] = true;

        for (int i = 0; i < maxVertices; i++) {
            if (adjacencyMatrix[current][i] != -1) {
                if (!visited[i]) {
                    if (dfsCycle(i, visited, inStack, cycle)) {
                        cycle.add(0, current);
                        return true;
                    }
                } else if (inStack[i]) {
                    cycle.add(i);
                    cycle.add(0, current);
                    return true;
                }
            }
        }

        inStack[current] = false;
        return false;
    }

    // Find path using DFS with user input
    public List<Integer> findPath() {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Enter start vertex: ");
        int start = scnr.nextInt();
        System.out.print("Enter end vertex: ");
        int end = scnr.nextInt();

        if (start < 0 || start >= maxVertices || end < 0 || end >= maxVertices) {
            System.out.println("Invalid vertex number.");
            return null;
        }

        if (!vertexExists[start] || !vertexExists[end]) {
            System.out.println("One or both vertices do not exist.");
            return null;
        }

        boolean[] visited = new boolean[maxVertices];
        List<Integer> path = new ArrayList<>();

        if (dfsPath(start, end, visited, path)) {
            System.out.println("Path found: " + path);
            System.out.println("Path cost: " + (int) getPathCost(path));
            return path;
        }

        System.out.println("No path exists from " + start + " to " + end);
        return null;
    }

    // Find cycle in graph
    public List<Integer> findCycle() {
        boolean[] visited = new boolean[maxVertices];
        boolean[] inStack = new boolean[maxVertices];
        List<Integer> cycle = new ArrayList<>();

        for (int i = 0; i < maxVertices; i++) {
            if (vertexExists[i] && !visited[i]) {
                if (dfsCycle(i, visited, inStack, cycle)) {
                    System.out.println("Cycle found: " + cycle);
                    return cycle;
                }
            }
        }

        System.out.println("No cycle exists in the graph.");
        return cycle;
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
