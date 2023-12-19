import java.util.ArrayList;

public class Node implements Comparable<Node> {
    Node parent; // parent node
    State state; // state and board of the current node
    int cost; // UCS cost <g()>
    int heuristic; // HillClimbing Heuristic <h()>
    int totalcost; // A* combined cost <f() = g() + h()>

    // Constructor to set all values.
    Node(Node parent, State state) {
        this.parent = parent;
        this.state = state;
        cost = calcCost();
        heuristic = calcHeuristic();
        totalcost = cost + heuristic;

    }

    // Returns an ArrayList of child nodes that spawn from the current node.
    ArrayList<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<Node>();
        for (State child : this.state.possibleSteps()) {
            Node childNode = new Node(this, child);
            children.add(childNode);
        }
        return children;
    }

    // calculates the UCS/A* cost of the current node
    int calcCost() {
        if (this.parent == null) {
            return 0;
        }
        // cost = parent.cost+1;
        return difference(parent) + parent.cost;
    }

    // Counts how many lights are on that were previously off in the previous state
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

    // Counts how many lights are currently turned on to use as heuristic cost for
    // HillClimbing/A*
    int calcHeuristic() {
        int count = 0;
        for (int i = 0; i < state.board.length; i++) {
            for (int j = 0; j < state.board[0].length; j++) {
                if (this.state.board[i][j]) {
                    count++;
                }
            }
        }
        return count / 5;
    }

    // Compares 2 different Nodes based on the UCS cost, for use in PriorityQueue
    // addition
    @Override
    public int compareTo(Node node) {
        return Integer.compare(cost, node.cost);
    }

    // detects if a current node is equal to a different node, for use in the
    // 'visited' HashSets
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Node node = (Node) obj;
        return state.equals(node.state);
    }

    // creates HasheCodes for each node based on their state
    @Override
    public int hashCode() {
        return state.hashCode();
    }

}
