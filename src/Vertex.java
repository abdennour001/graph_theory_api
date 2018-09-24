import java.util.ArrayList;

/**
 * Created by AMOKRANE Abdennour on 23/09/2018.
 */
public class Vertex {

    private String name;
    private int degree=0;
    private ArrayList<Vertex> adjacencyList=new ArrayList<>();


    public Vertex() {}

    public Vertex(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public ArrayList<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(ArrayList<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public void addAdjacentVertex(Vertex vertex) {
        this.adjacencyList.add(vertex);
        this.incrementDegree();
    }

    public boolean isAdjacent(Vertex vertex) {
        return this.adjacencyList.contains(vertex);
    }

    public void incrementDegree() {
        this.degree++;
    }
}
