import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class State {
    boolean[][] board; // Defined as boolean to save on memory
    // 0 mean light is off, and 1 mean light is on

    // Constructor to initialize the board with the width and height.
    State(int x, int y, Scanner input) {
        this.board = new boolean[x][y];

        // Asking to use the automatic random filling or manual fill
        System.out.print("Do you want to fill the board maunally? (Y/N): ");
        char c = input.next().charAt(0);
        if (c == 'y' || c == 'Y')
            manual_fill_board(input);
        else if (c == 'N' || c == 'n')
            random_fill_board();
    }

    // Constructor to clone a state
    State(State s) {
        this.board = new boolean[s.board.length][s.board[0].length];
        this.clone(s);
    }

    // cloning function, clones the board's values
    void clone(State s) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j] = s.board[i][j];
            }
        }
    }

    // Fills the board with random 0s and 1s
    void random_fill_board() {
        Random rand = new Random();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j] = rand.nextBoolean();
            }
        }
        if (this.isSolved()) {
            System.out.println("Generated board was already solved. Randomizing again...");
            random_fill_board();
        }
    }

    // Allows the user to manually input the grid's values when intializing it
    void manual_fill_board(Scanner input) {
        int value;
        System.out.println("Enter 0s for off and 1s for on");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                while (true) {
                    System.out.print("Enter the value for the (" + i + "," + j + ") coordinate: ");
                    value = input.nextInt();
                    input.nextLine();
                    if (value == 0) {
                        board[i][j] = false;
                        break;
                    } else if (value == 1) {
                        board[i][j] = true;
                        break;
                    } else {
                        System.out.println("You've entered a wrong value, please only enter 0s and 1s");
                    }
                }
            }
        }
        if (this.isSolved()) {
            System.out.println("This board is already solved, please try again");
            manual_fill_board(input);
        }

    }

    // Prints the board
    void print_board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" | " + " " +
                        booltostring(board[i][j])
                        + " " + " | " + " ");
            }
            System.out.println("\n");
        }
    }

    // Internal function to turn the boolean values to 0s and 1s for printing
    String booltostring(Boolean b) {
        if (b == null) {
            return "null";
        } else {
            return b ? "1" : "0";
        }
    }

    // Changes the board upon user interaction with a certain field
    void changestate(int x, int y) {
        if (x < 0 || y < 0) {
            System.out.println("No negative values are allowed");
            return;
        }
        if (x < board.length && y < board[0].length) {
            toggle(x, y);
            if (x + 1 < board.length)
                toggle(x + 1, y);
            if (x - 1 >= 0 && x - 1 < board.length)
                toggle(x - 1, y);
            if (y + 1 < board[0].length)
                toggle(x, y + 1);
            if (y - 1 >= 0 && y - 1 < board[0].length)
                toggle(x, y - 1);
        }
        return;
    }

    // toggles the field's value (turns the light off/on)
    void toggle(int x, int y) {
        board[x][y] = !board[x][y];
    }

    // Checks if the board is completely filled with 0s (all lights are off)
    boolean isSolved() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j])
                    return false;
            }
        }
        return true;
    }

    // Returns an arraylist of all possible steps coming from a current state
    ArrayList<State> possibleSteps() {

        ArrayList<State> a = new ArrayList<State>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                State temp = new State(this);
                temp.changestate(i, j);
                a.add(new State(temp));
            }
        }
        return a;
    }

    // Overriding the default 'equals' function to compare objects and boards inside
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        State state = (State) obj;
        return Arrays.deepEquals(this.board, state.board);
    }

    // Overriding the default 'hashCode' function to create Hashes based on each
    // board
    // Such that states with identical boards produce the same Hash for easier
    // comparison
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }


}
