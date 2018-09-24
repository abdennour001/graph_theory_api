import java.util.Scanner;

/**
 * Created by AMOKRANE Abdennour on 22/09/2018.
 */
public class Main {

    public static void main(String args[]) {

        Graph g=new Graph("src/origin.txt");
        g.toMatrix();
        /*for (Vertex v:
             g.getVerticesList()) {
            System.out.println(v.toString());
        }*/
        for (Edge e:
             g.getEdgesList()) {
            System.out.println(e.toString());
        }
    }
}
