package DaD.Commons.Utils;

import java.util.Scanner;

public class InputFunction {
    private static final String confirmationWord = "oui";

    /**
     * Ask a string to user
     * and return it
     * @return String
     */
    public static String getStringInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Ask an int to user
     * and return it, handle exception.
     * Return integer max value in case
     * of user error input.
     * @return int
     */
    public static int getIntInput(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            // Input parsing failed, it means user entered a string, not an int
            return Integer.MAX_VALUE;
        }
        // Input parsing succeed, it means user entered an int
        return choice;
    }

    /**
     * Use to confirm dangerous choices,
     * return true if player enter "yes",
     * false otherwise.
     * @return boolean
     */
    public static boolean askConfirmation(){
        System.out.println("Etes vous sur de votre choix ?");
        System.out.println("Tapez '" + confirmationWord + "' pour confirmer, autre pour annuler.");
        return (getStringInput().equals(confirmationWord));
    }
}
