/* 
    Note
    ````
    This system is for educational purpose only and may not
    be used in real-life situation as the system implements
    substandard/insecure encryption techniques.
*/

import java.io.IOException;
import java.util.Scanner;

import utils.Text;

import models.Owner;
import models.Password;

public class App {
    /* declare scanner once */
    public static final Scanner SCANNER = new Scanner(System.in);
    
    /* helper functions for user input */
    public static String inputString(String label) {
        System.out.print(label);
        try {
            return SCANNER.nextLine();
        } catch(Exception e) {
            System.out.println("Invalid input, try again...");
            return inputString(label);
        }
    }

    /* helper functions for user input */
    public static int inputInt(String label) {
        System.out.print(label);
        try {
            int out = SCANNER.nextInt();
            SCANNER.nextLine();
            return out;
        } catch(Exception e) {
            System.out.println("Invalid input, try again...");
            SCANNER.nextLine();
            return inputInt(label);
        }
    }

    /* additional function to clear console */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    public static void main(String[] args) throws Exception {
        String username, password, pwdInput, key, output = "";

        /* the system will loop until the user say stop */
        boolean running = true;
        while (running) {
            /* clear the console */
            clearConsole();

            System.out.println(Text.Color.GREEN + "SECURE PASSWORD STORE\n" + Text.Color.RESET);

            /* Create user account and save the given info as "Owner" class */
            System.out.println(Text.Color.GREEN + "***Create owner account***" + Text.Color.RESET);
            username = inputString("Enter username: ");
            password = inputString("Enter password: ");

            Owner owner = new Owner(username, password);

            /* 
                Give feedback to the user of their created account
                along with generated key
            */
            System.out.println("\n" + Text.Color.GREEN + "***Account created!***" + Text.Color.RESET);
            System.out.println(
                "Username: " + Text.Color.YELLOW + owner.getUsername() + Text.Color.RESET + '\n' +
                "Password (hashed): " + Text.Color.YELLOW + owner.getPassword() + Text.Color.RESET + "\n" +
                "Generated key (private): " + Text.Color.YELLOW + owner.getKey() + Text.Color.RESET + "\n\n" +
                "[⚡] Owner account passwords are hashed on purpose to protect users privacy\n" +
                "[⚡] This system will never store user plain password\n" +
                "[⚡] Generated key will be used to decrypt/read all passwords owned by the user\n\n"
            );

            /* Ask the user for a password that need to be stored */
            System.out.println(Text.Color.GREEN + "***Store a password***" + Text.Color.RESET);
            pwdInput = inputString("Enter your password: ");

            /* Save the password as "Password" class */
            Password pwd = new Password(pwdInput, owner);

            /* Feedback */
            System.out.println("\n" + Text.Color.GREEN + "***Password stored!***" + Text.Color.RESET);
            System.out.println(
                "Stored password (encrypted): " + Text.Color.YELLOW + pwd.read() + Text.Color.RESET + "\n\n" +
                "[⚡] The given password has been encrypted and only can be read by using a key\n" + 
                "[⚡] Use generated key (" + Text.Color.YELLOW + owner.getKey() + Text.Color.RESET + ") to read the stored password\n\n"
            );

            /* Test the system using correct/wrong key */
            boolean retry = true;
            while (retry) {
                key = inputString("Enter your key: ");
                output = pwd.read(key);
                if (output == "") {
                    retry = inputInt("\nEnter number [0 - Give up, 1 - Retry]:") == 1;
                } else {
                    System.out.println("\nYour password is " + output);
                    retry = false;
                }
            }
            
            /* No data will be saved at this moment at the of the system, only exit or restart */
            if (inputInt("\nEnter number [0 - Exit, 1 - Restart]: ") == 0) {
                running = false;
            }
        }
    }
}
