import java.util.Scanner;

/**
 * Created by AMOKRANE Abdennour on 22/09/2018.
 */
public class Main {

    public static void main(String args[]) {

        Graph g=new Graph("src/origin.txt");
        g.toMatrix();
    }
}
