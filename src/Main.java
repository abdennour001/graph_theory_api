import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by AMOKRANE Abdennour on 22/09/2018.
 */
public class Main {

    private static ArrayList<Boolean> markedList=new ArrayList<>();
    private static Stack<Vertex> vertexStack=new Stack<>();
    private static ArrayList<Boolean> isOnStack=new ArrayList<>();
    private static int ID=0;

    private static void initMarkedList(Graph graph) {
        for (Vertex v:
             graph.getVerticesList()) {
            markedList.add(graph.indexOf(v), false);
        }
    }

    private static void initStackList(Graph graph) {
        for (Vertex v:
             graph.getVerticesList()) {
            isOnStack.add(graph.indexOf(v), false);
        }
    }

    private static void emptyStack() {
        vertexStack.empty();
    }

    private static void DFS(Graph graph, Vertex root) {
        System.out.println(root.toString());
        markedList.set(graph.indexOf(root), true);
        for (Vertex next:
             root.getAdjacencyList()) {
            if(!markedList.get(graph.indexOf(next))) {
                DFS(graph, next);
            }
        }
    }

    public static void DFS(Graph graph) {
        initMarkedList(graph);
        for (Vertex v:
             graph.getVerticesList()) {
            if (!markedList.get(graph.indexOf(v))) {
                DFS(graph, v);
            }
        }
    }


    private static ArrayList<ArrayList<Vertex>> findSccs(Graph graph) {
        initMarkedList(graph);
        initStackList(graph);
        emptyStack();
        ArrayList<ArrayList<Vertex>> sccs=new ArrayList<>();
        ID=0;

        for (Vertex vertex:
             graph.getVerticesList()) {
            if (!markedList.get(graph.indexOf(vertex))) {
                DfsSccs(graph, sccs, vertex);
            }
        }

        return sccs;
    }


    private static void DfsSccs(Graph graph, ArrayList<ArrayList<Vertex>> sccs, Vertex vertex) {

        vertexStack.push(vertex);
        isOnStack.set(graph.indexOf(vertex), true);
        markedList.set(graph.indexOf(vertex), true);
        vertex.setId(ID++);
        vertex.setLowLink(vertex.getId());

        for (Vertex next:
             vertex.getAdjacencyList()) {

            if (!markedList.get(graph.indexOf(next))) {
                DfsSccs(graph, sccs, next);
            }

            if (isOnStack.get(graph.indexOf(next))) {
                int accLowLink=vertex.getLowLink();
                int nextLowLink=next.getLowLink();
                vertex.setLowLink(Math.min(accLowLink, nextLowLink));
            }
        }

        if (vertex.getId() == vertex.getLowLink()) {

            ArrayList<Vertex> scc=new ArrayList<>();

            while (true) {
                Vertex popVertex=vertexStack.pop();
                isOnStack.set(graph.indexOf(popVertex), false);
                popVertex.setLowLink(vertex.getId());
                scc.add(popVertex);
                if (popVertex.equals(vertex)) break;
            }

            sccs.add(scc);
        }

    }


    public static void main(String args[]) {

        Graph g=new Graph("src/origin.txt");
        //g.toMatrix();
        //DFS(g);
        System.out.println("[Strongly Connected Components]");
        System.out.println();
        int i=0;
        for (ArrayList<Vertex> scc:
             findSccs(g)) {
            System.out.println("[SCC " + i++ + "]");
            for (Vertex v:
                 scc) {
                System.out.println(v.toString() + " low-link = "+ v.getLowLink());
            }
            System.out.println();
        }
    }
}
