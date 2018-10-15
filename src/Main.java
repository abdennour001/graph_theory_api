import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by AMOKRANE Abdennour on 22/09/2018.
 */
public class Main {

    private static ArrayList<Boolean> isColored=new ArrayList<>();
    private static ArrayList<Boolean> markedList=new ArrayList<>();
    private static Stack<Vertex> vertexStack=new Stack<>();
    private static ArrayList<Boolean> isOnStack=new ArrayList<>();
    private static int ID=0;
    private static boolean isCycle=false;

    private static void initMarkedList(Graph graph) {
        markedList.clear();
        for (Vertex v:
             graph.getVerticesList()) {
            markedList.add(graph.indexOf(v), false);
        }
    }

    private static void initIsColored(Graph graph) {
        for (Vertex vertex:
                graph.getVerticesList()) {
            isColored.add(graph.indexOf(vertex), false);
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

    public static void TarjanAlgorithm(Graph g) {
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

    public static HashSet getSaturationDegree(Vertex vertex) {
        HashSet<Integer> colorsSet=new HashSet<Integer>();

        for (Vertex neighbor:
             vertex.getAdjacencyList()) {
            if(neighbor.getColor() > -1) {
                colorsSet.add(neighbor.getColor());
            }
        }
        return colorsSet;
    }

    public static Boolean allColored(Graph graph) {
        for (Boolean b:
             isColored) {
            if(!b) return false;
        }
        return true;
    }

    public static void main(String args[]) {

        Graph graph=new Graph("src/origin.txt");
        //g.toMatrix();
        //DFS(graph);
        //TarjanAlgorithm(graph);
        //DSatur(graph);// Turns graph with colored vertices
        Graph minimalSpanningTree = KruskalAlgorithm(graph);
        print(minimalSpanningTree.toString());
    }

    private static void DFSCycle(Graph graph, Vertex root, Vertex isCycleVertex) {
        if(root.equals(isCycleVertex)) isCycle=true;
        markedList.set(graph.indexOf(root), true);
        for (Vertex next:
                root.getAdjacencyList()) {
            if(!markedList.get(graph.indexOf(next))) {
                DFSCycle(graph, next, isCycleVertex);
            }
        }
    }

    private static Graph KruskalAlgorithm(Graph graph) {
        Graph minimalSpanningTree=new Graph("Spanning Tree", false);
        minimalSpanningTree.setOrder(graph.getOrder());
        minimalSpanningTree.setNumberOfEdges(graph.getOrder() - 1);
        int k=0;
        (graph.getEdgesList()).sort((e1, e2) -> (Integer.parseInt(e1.getValue()) - Integer.parseInt(e2.getValue())));
        for (Edge e:
             graph.getEdgesList()) {
            Boolean containsHead=false;
            Boolean containsTail=false;

            for (Vertex v:
                 minimalSpanningTree.getVerticesList()) {
                if(v.getName().equals(e.getHeadVertex().getName())) {
                    containsHead=true;
                }

                if(v.getName().equals(e.getTailVertex().getName())) {
                    containsTail=true;
                }

                if(containsHead && containsTail) break;
            }

            if(k <= graph.getOrder()-1) {

                if (containsHead && containsTail) {
                    isCycle=false;
                    initMarkedList(minimalSpanningTree);
                    DFSCycle(minimalSpanningTree, minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(e.getHeadVertex().getName()))
                            , minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(e.getTailVertex().getName())));
                    if(!isCycle) {
                        String headName=e.getHeadVertex().getName();
                        String tailName=e.getTailVertex().getName();

                        Vertex vertexHead=minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(headName));
                        Vertex vertexTail=minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(tailName));

                        vertexHead.addAdjacentVertex(vertexTail);
                        vertexTail.addAdjacentVertex(vertexHead);

                        minimalSpanningTree.addEdge(e);
                        k++;
                    }
                } else {

                    if (containsHead) {
                        String headName=e.getHeadVertex().getName();
                        Vertex vertexHead=minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(headName));
                        Vertex vertexTail=new Vertex(e.getTailVertex().getName());
                        vertexHead.addAdjacentVertex(vertexTail);
                        vertexTail.addAdjacentVertex(vertexHead);
                        minimalSpanningTree.addVertex(vertexTail);
                    }

                    if (containsTail) {
                        String tailName=e.getTailVertex().getName();
                        Vertex vertexTail=minimalSpanningTree.getVerticesList().get(minimalSpanningTree.indexOfByName(tailName));
                        Vertex vertexHead=new Vertex(e.getHeadVertex().getName());

                        vertexHead.addAdjacentVertex(vertexTail);
                        vertexTail.addAdjacentVertex(vertexHead);

                        minimalSpanningTree.addVertex(vertexHead);
                    }

                    if (!containsHead && !containsTail) {
                        Vertex vertexHead=new Vertex(e.getHeadVertex().getName());
                        Vertex vertexTail=new Vertex(e.getTailVertex().getName());

                        vertexHead.addAdjacentVertex(vertexTail);
                        vertexTail.addAdjacentVertex(vertexHead);

                        minimalSpanningTree.addVertex(vertexHead);
                        minimalSpanningTree.addVertex(vertexTail);

                    }

                    minimalSpanningTree.addEdge(e);
                    k++;
                }
            }
        }

        return minimalSpanningTree;
    }

    public static void DSatur(Graph graph) { // For assign a color to each vertex
        int currentColor=1;
        ArrayList<Vertex> noColoredVertices=new ArrayList<>();
        initIsColored(graph);

        for (Vertex v:
                graph.getVerticesList()) {
            noColoredVertices.add(v);
        }

        while (!allColored(graph)) {
            int max=0;
            Vertex vertexMax=null;
            HashSet<Integer> vertexNeighborsColorsSet=null;
            for (Vertex v:
                    noColoredVertices) { // Get max saturation degree
                HashSet<Integer> neighborsColors=getSaturationDegree(v);
                if(neighborsColors.size() >= max) {
                    max = neighborsColors.size();
                    vertexMax = v;
                    vertexNeighborsColorsSet = neighborsColors;
                }
            }
            for (int i=1; i<=currentColor; i++) {
                if(!vertexNeighborsColorsSet.contains(i)) {
                    vertexMax.setColor(i);
                    isColored.set(graph.indexOf(vertexMax), true);
                    noColoredVertices.remove(vertexMax);
                    break;
                }
            }
            currentColor++;
        }
    }

    private static void print(Object ToPrint) {
        System.out.println(ToPrint);
    }

    private static void print() {
        System.out.println();
    }

}
