import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


    // method that returns the graph object

    public Graph getGraph() {
        return this;
    }

    // other getters

    public double[][] getAdjacencyMatrix() {return adjacencyMatrix;}
    public boolean[] getVertexExists() {return vertexExists;}
    public int getMaxVertices() {return maxVertices;}
    public boolean isDirected() {return directed;}
    public boolean isWeighted() {return weighted;}


    // helper methods for path search

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

        path.remove(path.size() -1);
        return false;


    }

    public double getPathCost(List<Integer> path) {
        double cost = 0;
        for (int i = 0; i < path.size(); i++) {
            cost += adjacencyMatrix[path.get(i)][path.get(i+1)];

        }
        return cost;
    }

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


    //Uses DFS

    public List<Integer> findPath() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter start vertex: ");
        int from = scnr.nextInt();
        System.out.println("Enter end vertex: ");
        int to = scnr.nextInt();

        if (from < 0 || from >= maxVertices || to < 0 || to >= maxVertices) {
            System.out.println("Invalid vertex number");
            return null;
        }

        if (!vertexExists[from] || !vertexExists[to]) {
            System.out.println("One of the vertices does not exist");
            return null;

        }

        boolean[] visited = new boolean[maxVertices];
        List<Integer> path = new ArrayList<>();

        if (dfsPath(from, to, visited, path)) {
            System.out.println("Path found: " + path);
            System.out.println("Path cost: " + (int) getPathCost(path));
            return path;
        }

        else {System.out.println("Path not found"); return null;}

    }

    public List<Integer> findCycle(){

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

        System.out.println("Cycle not found");
        return cycle;
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