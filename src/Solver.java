import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Solver {

    // Allowing the user to showcase all the possible children state from the
    // current one.
    void printpossibleSteps(Scanner input, State curState) {
        for (State s : curState.possibleSteps()) {
            s.print_board();
            System.out.println("\n --------------------------- \n");
        }
    }

    // Function to select the desired field and call the board modification method
    ArrayList<State> userPlay(Scanner input, State curState, ArrayList<State> states) {
        while (!curState.isSolved()) {
            System.out.println("Enter the desired field's coordinates:");
            System.out.println("Row: ");
            int r = input.nextInt();
            input.nextLine();

            System.out.println("Column: ");
            int c = input.nextInt();
            input.nextLine();

            curState.changestate(r, c);
            curState.print_board();
            states.add(new State(curState));
        }
        return states;
    }

    // Solves the puzzle using DFS Algorithm and a HashSet to store visited states
    State dfsSolve(State start) {
        // current time in milliseconds, used to calculate time
        double begin = System.currentTimeMillis();

        // memory usage of the program before starting the algorithm, used to calculate
        // space
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // Memory usage in the current time
        long curMemUsage = 0;

        // Maximum value of the Memory usage
        long maxMemUsage = beforeUsedMem;

        Set<State> visited = new LinkedHashSet<State>();
        // LinkedHashSet allows to iterate over the objects stored inside by insertion
        // order.
        Stack<State> st = new Stack<State>();
        st.push(start);
        while (!st.isEmpty()) {

            // Calculating Memory Usage
            curMemUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem;
            if (curMemUsage > maxMemUsage) {
                maxMemUsage = curMemUsage;
            }

            State temp = new State(st.pop());

            if (temp.isSolved()) {
                visited.add(temp);
                // for (State state : visited) {
                // state.print_board();
                // System.out.println("-------------------------");
                // }

                System.out.println("The board was solved in " + (visited.size() - 1) + " steps");
                System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
                System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");

                return temp;
            }

            if (!visited.contains(temp)) {
                visited.add(temp);

                for (State state : temp.possibleSteps()) {
                    if (!visited.contains(state)) {
                        State child = new State(state);
                        st.push(child);
                    }
                }
            }
        }
        System.out.println("Not Solvable");
        System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
        System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");

        return null;
    }

    // Solves the puzzle using BFS Algorithm and a HashSet to store visited states
    State bfsSolve(State start) {
        // current time in milliseconds, used to calculate time
        double begin = System.currentTimeMillis();

        // memory usage of the program before starting the algorithm, used to calculate
        // space
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // Memory usage in the current time
        long curMemUsage = 0;

        // Maximum value of the Memory usage
        long maxMemUsage = beforeUsedMem;

        Set<State> visited = new LinkedHashSet<State>();
        Queue<State> q = new LinkedList<State>();

        q.add(start);
        while (!q.isEmpty()) {

            // Calculating Memory Usage
            curMemUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem;
            if (curMemUsage > maxMemUsage) {
                maxMemUsage = curMemUsage;
            }

            State temp = new State(q.poll());

            if (temp.isSolved()) {
                visited.add(temp);
                // for (State state : visited) {
                // state.print_board();
                // System.out.println("-------------------------");
                // }
                System.out.println("The board was solved in " + (visited.size() - 1) + " steps");
                System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
                System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");

                return temp;
            }
            if (!visited.contains(temp)) {
                visited.add(temp);

                for (State state : temp.possibleSteps()) {
                    if (!visited.contains(state)) {
                        State child = new State(state);
                        q.add(child);
                    }
                }

            }
        }
        System.out.println("Not Solvable");
        System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
        System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");

        return null;
    }

    void UCSSovler(State start) {

        double begin = System.currentTimeMillis();
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long curMemUsage = 0;
        long maxMemUsage = beforeUsedMem;

        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        Set<Node> visited = new HashSet<Node>();
        Node Startnode = new Node(null, start);
        pq.add(Startnode); 
        // System.out.println("added start");
        while (!pq.isEmpty()) {
            curMemUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem;
            if (curMemUsage > maxMemUsage) {
                maxMemUsage = curMemUsage;
            }
            // System.out.println("polling..");
            Node tempNode = pq.poll();
            if (tempNode.state.isSolved()) {
                System.out.println("Solution Path: ");
                int steps = printPath(tempNode);
                System.out.println("The board was solved in " + steps + " steps");
                System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
                System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");
                // printPath(tempNode);
                return;
            }
            // System.out.println("adding to visited..");
            visited.add(tempNode);

            for (Node childNode : tempNode.getChildren()) {
                if (!visited.contains(childNode)) {
                    // System.out.println("not contain");
                    pq.add(childNode);
                    // System.out.println("adding");
                } else {
                    PriorityQueue<Node> pqCopy = new PriorityQueue<>(pq);
                    for (Node n : pqCopy) {
                        if (n.state.equals(childNode.state)
                                && childNode.cost < n.cost) {
                            pq.remove(n);
                            pq.add(childNode);
                            // System.out.println("adding");
                        }
                    }
                }
            }
        }
        System.out.println("Not Solvable");
    }

    void HillClimbingSolver(State start) {

        double begin = System.currentTimeMillis();
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long curMemUsage = 0;
        long maxMemUsage = beforeUsedMem;

        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        Set<Node> visited = new HashSet<Node>();
        Node Startnode = new Node(null, start);
        pq.add(Startnode);
        while (!pq.isEmpty()) {
            curMemUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem;
            if (curMemUsage > maxMemUsage) {
                maxMemUsage = curMemUsage;
            }
            Node tempNode = pq.poll();
            if (tempNode.state.isSolved()) {
                System.out.println("Solution Path: ");
                int steps = printPath(tempNode);
                System.out.println("The board was solved in " + steps + " steps");
                System.out.println("Time elapsed: " + ((System.currentTimeMillis() - begin) / 1000.0) + " seconds");
                System.out.println("Memory usage: " + maxMemUsage / 1000.0 + " KBs");
                return;
            }
            visited.add(tempNode);

            for (Node childNode : tempNode.getChildren()) {
                if (!visited.contains(childNode)) {
                    pq.add(childNode);
                } else {
                    //Cannot iterate over a Queue while also modifying it.
                    //Easiest solution is to iterate over a copy of the queue while modifying the original
                    PriorityQueue<Node> pqCopy = new PriorityQueue<>(pq);
                    for (Node n : pqCopy) {
                        if (n.state.equals(childNode.state)
                                && childNode.heuristicValue < n.heuristicValue) {
                            pq.remove(n);
                            pq.add(childNode);
                        }
                    }
                }
            }
        }
        System.out.println("Not Solvable");
    }

    public int printPath(Node node) {
        if (node == null) {
            return 0;
        }
        int steps = printPath(node.parent);
        node.state.print_board();
        System.out.println("-------------------------");
        return steps + 1;
    }
}