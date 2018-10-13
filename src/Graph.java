import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by AMOKRANE Abdennour on 23/09/2018.
 */
public class Graph {

    private String name;
    private int order=0;
    private int numberOfEdges=0;
    private ArrayList<Vertex> verticesList=new ArrayList<>();
    private ArrayList<Edge> edgesList=new ArrayList<>();
    private boolean isDirected=false;
    private String pathToOriginFile="";
    private ArrayList<Boolean> markedVerticesList=new ArrayList<>();

    /**
     * Origin File Format
     *
     * Start Of File
     * ----------------------
     * #Graph name "name"
     * #Graph order "order"
     * #Graph number of edges "numberOfEdges"
     * #Graph type "directed/undirected"
     *
     * #Vertex 1 "name_1"
     * #Vertex 2 "name_2"
     * .
     * .
     * .
     * #Vertex n "name_n"
     *
     * #Edge 1 "name_vertex_i1 * name_vertex_j1" * "value_1"
     * #Edge 2 "name_vertex_i2 * name_vertex_j2" * "value_2"
     * .
     * .
     * .
     * #Edge n "name_vertex_in * name_vertex_jn" * "value_n"
     *
     * ----------------------
     * End Of File
     *
     * */

    public Graph() {}

    public Graph(String name, boolean isDirected) {
        this.name = name;
        this.isDirected = isDirected;
    }

    public Graph(String pathToOriginFile) {
        this.pathToOriginFile = pathToOriginFile;
        this.makeGraph();
    }

    private void makeGraph() {
        try {
            File originFile=new File(this.pathToOriginFile);
            Scanner sc=new Scanner(originFile);

            this.name = sc.nextLine();
            this.order = Integer.parseInt(sc.nextLine().trim());
            this.numberOfEdges = Integer.parseInt(sc.nextLine().trim());
            this.isDirected = !sc.nextLine().equals("undirected");

            for (int i=0; i<this.order; i++) {
                Vertex v=new Vertex(sc.nextLine());
                this.addVertex(v);
            }

            for (int i=0; i<this.numberOfEdges; i++) {
                String buffer=sc.nextLine();
                String tailVertexName=buffer.split("\\*")[0].trim();
                String headVertexName=buffer.split("\\*")[1].trim();
                String edgeValue;
                try {
                    edgeValue = buffer.split("\\*")[2].trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    edgeValue = "";
                }
                Vertex tail=vertexAt(indexOfByName(tailVertexName));
                Vertex head=vertexAt(indexOfByName(headVertexName));
                if (this.isDirected) {
                    Edge e=new Edge(tail, head);
                    if (!edgeValue.equals("")) e.setValue(edgeValue);
                    e.setDirected(this.isDirected);
                    this.addEdge(e);
                    tail.addAdjacentVertex(head);
                } else {
                    boolean aig=false;
                    for (Edge e:
                         this.edgesList) {
                        if (e.getTailVertex().equals(head) &&
                                e.getHeadVertex().equals(tail)) {
                            aig = true;
                            break;
                        }
                    }
                    if (!aig) {
                        Edge edge=new Edge(tail, head);
                        if (!edgeValue.equals("")) edge.setValue(edgeValue);
                        edge.setDirected(this.isDirected);
                        this.addEdge(edge);
                        tail.addAdjacentVertex(head);
                        head.addAdjacentVertex(tail);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[!] Graph origin file not found!");
            return;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Vertex> getVerticesList() {
        return verticesList;
    }

    public void setVerticesList(ArrayList<Vertex> verticesList) {
        this.verticesList = verticesList;
    }

    public void addVertex(Vertex vertex) {
        this.verticesList.add(vertex);
    }

    public ArrayList<Edge> getEdgesList() {
        return edgesList;
    }

    public void setEdgesList(ArrayList<Edge> edgesList) {
        this.edgesList = edgesList;
    }

    public void addEdge(Edge edge) {
        this.edgesList.add(edge);
    }

    public int indexOfByName(String vertexName) {
        for (Vertex v:
             this.verticesList) {
            if (v.getName().equals(vertexName)) return verticesList.indexOf(v);
        }
        return -1;
    }

    public int indexOf(Vertex vertex) {
        return this.verticesList.indexOf(vertex);
    }

    public Vertex vertexAt(int index) {
        return this.verticesList.get(index);
    }

    public int indexOf(Edge edge) {
        return this.edgesList.indexOf(edge);
    }

    public Edge edgeAt(int index) {
        return this.edgesList.get(index);
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPathToOriginFile() {
        return pathToOriginFile;
    }

    public void setPathToOriginFile(String pathToOriginFile) {
        this.pathToOriginFile = pathToOriginFile;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public void toMatrix() {
        for (int i=0; i<this.order; i++) {
            for (int j=0; j<this.order; j++) {
                if(vertexAt(i).isAdjacent(vertexAt(j))) System.out.print("1 ");
                else System.out.print("0 ");
            }
            System.out.println("");
        }
    }

}