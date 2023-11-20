import java.util.Comparator;

public class A_starComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.totalcost < n2.totalcost)
            return -1;
        if (n2.totalcost > n2.totalcost)
            return 1;
        return compareHeauristic(n1, n2);

    }

    int compareHeauristic(Node n1, Node n2) {
        return Integer.compare(n1.heuristic, n2.heuristic);
    }
}
