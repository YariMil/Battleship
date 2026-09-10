import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner input;


    public HumanPlayer(BattleshipBoard board, Player enemyPlayer) {
        super(board, enemyPlayer);
        input = new Scanner(System.in);
    }

    public void placeShips() {
        System.out.println("Time to place your ships!");
        boardClass.revealAllSpots();
        for (Ship ship : ships) {
            boolean placingShip = true;
            System.out.println(
                    "You are currently placing the " + ship.getName() + ", size " + ship.getSize());
            boardClass.printBoard();
            while (placingShip) {
                System.out.println("Which coordinates do you want to place your ship?");
                String action = getAction(true).toUpperCase();
                int row = convertLetterToNum(action.charAt(0));
                int colIndex = findColIndex(action);
                String colString = action.substring(1, colIndex);
                int col = Integer.parseInt(colString) - 1;
                boolean vertical = convertLetterToBool(action.charAt(colIndex));
                if (!legalPlacement(ship, row, col, vertical)) {
                    System.out.println("Your ship does not have enough space to be placed there!");
                } else {
                    placeShip(ship, row, col, vertical);
                    boardClass.printBoard();
                    System.out.println(
                            "The Xs represent squares where you will be unable to place other ships.");
                    System.out.println("Are you sure this is where you want your ship (y/n)? ");
                    String yOrN = input.nextLine();
                    while (!yOrN.equals("y") && !yOrN.equals("n")) {
                        System.out.println("Please enter 'y' for yes and 'n' for no.");
                        System.out.println("Are you sure this is where you want your ship (y/n)? ");
                        yOrN = input.nextLine();
                    }
                    if (yOrN.equals("y")) {
                        System.out.println("You have placed the " + ship.getName() + ".");
                        placingShip = false;
                    } else {
                        System.out.println("Alright, let's try again.");
                        updateLegalSpots(ship, false);
                        ship.resetPlacements();
                        ship.resetSurroundingSquares();
                    }
                }
            }

        }
        System.out.println("Congratulations! You have placed all your ships!");
        fixUpBoard();

    }

    public int findColIndex(String action) {
        for (int i = 1; i < action.length(); i++) {
            if (!(action.charAt(i) >= '0' && action.charAt(i) <= '9')) {
                return i;
            }
        }
        return 0;
    }

    public int convertLetterToNum(char letter) {
        for (int i = 0; i < letterCoords.length; i++) {
            if (letterCoords[i] == letter) {
                return i;
            }
        }
        return -1;
    }

    public boolean convertLetterToBool(char letter) {
        return letter == 'V';
    }

    public String getAction(boolean placing) {
        String verticalOrNot = "";
        System.out.println("Enter coordinates as a single word (ex. A1 or E4): ");
        String action = input.nextLine();
        if (placing) {
            System.out.println(
                    "Please enter if you want to place the ship vertically or horizontally.");
            System.out.println("Enter v for vertical and h for horizontal: ");
            verticalOrNot = input.nextLine();
        }
        while (invalidAction(action, verticalOrNot, placing)) {
            System.out.println("Enter coordinates as a single word (ex. A1 or E4): ");
            action = input.nextLine();
            if (placing) {
                System.out.println("Enter v for vertical and h for horizontal: ");
                verticalOrNot = input.nextLine();
            }
        }
        return action + verticalOrNot;
    }

    public boolean invalidAction(String action, String verticalOrNot, boolean placing) {
        action = action.toLowerCase();
        verticalOrNot = verticalOrNot.toLowerCase();
        if (action.length() < 2 || action.length() > 3) {
            System.out.println("Your input must be 2 or 3 characters long.");
            return true;
        } else if (action.charAt(0) < 'a' || action.charAt(0) > 'j') {
            System.out.println("The first character in your input must be a letter A-J");
            return true;
        }
        int colCheck = 0;
        try {
            String colString = action.substring(1);
            colCheck = Integer.parseInt(colString);
        } catch (Exception e) {
            System.out.println(
                    "Your action must be a row followed by a number corresponding to a column.");
            return true;
        }
        if (colCheck < 1 || colCheck > 10) {
            System.out.println("Your column number is invalid! The column number must be 1-10.");
            return true;
        }
        if (placing) {
            if (!verticalOrNot.equals("v") && !verticalOrNot.equals("h")) {
                System.out.println("Only input v for vertical and h for horizontal!");
                return true;
            }
        }
        return false;
    }

    public String takeAction() {
        System.out.println("Enter a shot to fire on.");
        String action = getAction(false).toUpperCase();
        return action;
    }

    @Override
    public void analyseShot(boolean hit, int row, int column) {
        super.analyseShot(hit, row, column);
    }
}
