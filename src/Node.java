import java.util.ArrayList;

public class Node implements Comparable<Node> {
    Node parent;
    State state;
    int cost;

    Node(Node parent, State state, boolean isHeuristic) {
        this.parent = parent;
        this.state = state;
        if (isHeuristic)
            cost = calcHeuristic();
        else
            cost = calcCost();
    }

    ArrayList<Node> getChildren(boolean isHeuristic) {
        ArrayList<Node> children = new ArrayList<Node>();

        ArrayList<State> a = this.state.possibleSteps();
        for (State child : a) {
            Node childNode = new Node(this, child, isHeuristic);
            children.add(childNode);
        }
        return children;
    }

    // calculates the cost of the current node
    int calcCost() {
        if (this.parent == null) {
            return 0;
        }
        // cost = parent.cost+1;
        return difference(parent) + parent.cost;
    }

    // Counts
    int difference(Node n) {
        int count = 0;
        for (int i = 0; i < state.board.length; i++) {
            for (int j = 0; j < state.board[0].length; j++) {
                if (this.state.board[i][j] && this.state.board[i][j] != n.state.board[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    int calcHeuristic() {
        int count = 0;
        for (int i = 0; i < state.board.length; i++) {
            for (int j = 0; j < state.board[0].length; j++) {
                if (this.state.board[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(cost, node.cost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Node node = (Node) obj;
        return state.equals(node.state);
    }

    // Overriding the default 'hashCode' function to create Hashes based on each
    // board
    // Such that states with identical boards produce the same Hash for easier
    // comparison
    @Override
    public int hashCode() {
        return state.hashCode();
    }

}
