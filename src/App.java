import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Creating a new instance of the game and running it.
        Game g = new Game();
        g.Play();
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Wanna play again? (Y/N)");
            char r = input.nextLine().charAt(0);
            if (r == 'y' || r == 'Y') {
                g = new Game();
                g.Play();
            } else {
                input.close();
                break;
            }
        }
        System.out.println("Thanks for Playing!");
        input.close();
    }

}
