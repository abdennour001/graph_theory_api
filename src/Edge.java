/**
 * Created by AMOKRANE Abdennour on 23/09/2018.
 */
public class Edge {

    private String value="";
    private Vertex tailVertex;
    private Vertex headVertex;
    private boolean isDirected=false;


    public Edge() {}

    public Edge(String value) {
        this.value = value;
    }

    public Edge(Vertex tail, Vertex head) {
        this.tailVertex = tail;
        this.headVertex = head;
    }

    public Edge(Vertex tail, Vertex head, String value) {
        this.tailVertex = tail;
        this.headVertex = head;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public Vertex getHeadVertex() {
        return headVertex;
    }

    public void setHeadVertex(Vertex headVertex) {
        this.headVertex = headVertex;
    }

    public Vertex getTailVertex() {
        return tailVertex;
    }

    public void setTailVertex(Vertex tailVertex) {
        this.tailVertex = tailVertex;
    }
}
