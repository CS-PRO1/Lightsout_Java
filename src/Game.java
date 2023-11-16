import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Scanner input = new Scanner(System.in);
    // A construct holding all the states that's been created thus far
    State curState;
    ArrayList<State> states = new ArrayList<State>();

    // Initialization of the game, creating the initial state
    Game() {
        System.out.println(
                "Welcome to Lights Out! A puzzle game where you have to turn off the lights in all the board's fields\n "
                        +
                        "Activated lights are marked by the number 1\n" +
                        "Deactivated lights are marked by the number 0 \n" +
                        "Interacting with the board is done by selecting a field where the light is activated \n"
                        + "When you select a field, the light is turned off in that field, however it'll reverese the state of the surrounding fields");
        // Allowing the user to enter specific dimensions of the board
        System.out.println("please enter the number of rows in the board");
        int x = input.nextInt();
        input.nextLine();

        System.out.println("please enter the number of columns in the board: ");
        int y = input.nextInt();
        input.nextLine();

        // Creating the initial state, will ask to manually input the gird values
        // or to use the randomizer
        curState = new State(x, y, input);

        // Printing the board
        curState.print_board();

        // Adds a copy of the current state to the global states list
        states.add(new State(curState));

    }

    // Main hub for all functionality of the game.
    void Play() {
        Solver solve = new Solver();
        while (!curState.isSolved()) {
            System.out.println("Enter 1 to print possible steps from current state");
            System.out.println("Enter 2 to solve the puzzle yourself");
            System.out.println("Enter 3 to use the DFS Solver");
            System.out.println("Enter 4 to use the BFS Solver");
            System.out.println("Enter 5 to use the UCS Solver");
            System.out.println("Enter 6 to use the Hill-Climbing Solver");
            System.out.println("Enter 0 to exit the game");
            int x = input.nextInt();
            input.nextLine();

            switch (x) {
                case 0:
                    return;

                case 1:
                    solve.printpossibleSteps(input, curState);
                    break;

                case 2:
                    solve.userPlay(input, curState, states);
                    break;

                case 3:
                    solve.dfsSolve(curState);
                    break;

                case 4:
                    solve.bfsSolve(curState);
                    break;
                case 5:
                    solve.UCSSovler(curState);
                    break;
                case 6:
                    solve.HillClimbingSolver(curState);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 4.");
                    break;
            }

            states.add(curState);
        }

        // We only get here if the user solved the puzzle manually
        endGame();
    }

    // Called after successfully solving the puzzle manually, finishes the game.
    void endGame() {
        System.out.println("Congratulations! You've solved the puzzle in " + (states.size() - 1) + " moves!");
        System.out.println("Thank you for playing!");
        // Allowing the user to printout the game's states
        System.out.println("Print solve path? (Y/N)");
        char q = input.nextLine().charAt(0);
        if (q == 'y' || q == 'Y') {
            printSolvePath();
            input.close();
        }
        if (q == 'n' || q == 'N') {
            input.close();
            return;
        }
    }

    // Prints the steps the user went through while manually solving the game
    void printSolvePath() {
        {
            for (State s : states) {
                s.print_board();
                System.out.println("-----------------");
            }
        }
    }

}