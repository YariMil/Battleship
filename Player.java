public class Player {
    protected Ship[] ships;
    protected BattleshipBoard enemyBoard;
    protected Space[][] board;
    protected static final char[] letterCoords = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    protected Player enemyPlayer;
    protected BattleshipBoard friendlyBoard;

    public Player(Player enemyPlayer) {
        // All players have an enemyBoard. AI also uses the enemyBoard as its friendlyBoard.
        // ships = new Ship[] {new Ship('A', 5, "Aircraft Carrier"), new Ship('B', 4, "Battleship"),
        // new Ship('C', 3, "Cruiser"), new Ship('S', 3, "Submarine"),
        // new Ship('D', 2, "Destroyer")}; // Each player starts with a carrier, a battleship,
        // a cruiser, a sub, and a destroyer
        // For testing ship placement
        ships = new Ship[] {new Ship('A', 5, "Aircraft Carrier")};
        this.enemyPlayer = enemyPlayer;
    }

    public void setPlayer(Player enemyPlayer) {
        this.enemyPlayer = enemyPlayer;
    }


    public boolean legalPlacement(Ship ship, int r, int c, boolean vertical) {
        // Checks if the ship is able to be placed in a certain orientation at (row, col)
        for (int i = 0; i < ship.getSize(); i++) {
            if (vertical) {
                if (r + i < 0 || r + i >= board.length) { // Out of bounds
                    return false;
                }
                if (board[r + i][c].getRevealedIcon() != friendlyBoard.getUnoccupiedIcon()) { // Spot
                                                                                              // is
                                                                                              // occupied
                    return false;
                }
            } else {
                if (c + i < 0 || c + i >= board[r].length) { // Out of bounds
                    return false;
                }
                if (board[r][c + i].getRevealedIcon() != friendlyBoard.getUnoccupiedIcon()) { // Spot
                                                                                              // is
                                                                                              // occupied
                    return false;
                }
            }
        }
        return true;
    }


    public void placeShip(Ship ship, int r, int c, boolean vertical) {
        // Places ship at r, c
        int row = r;
        int column = c;
        for (int i = 0; i < ship.getSize(); i++) {
            if (vertical) {
                board[row + i][column].setRevealedIcon(ship.getIcon());
                ship.addPlacement(board[row + i][column]);
            } else {
                board[row][column + i].setRevealedIcon(ship.getIcon());
                ship.addPlacement(board[row][column + i]);
            }
        }
        updateLegalSpots(ship, true);
        // The Places that are now in the placement array can be modified outside the array
        // ship.printPlacement();
    }

    public void resetBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setRevealedIcon('+');
            }
        }
        for (Ship ship : ships) {
            ship.resetPlacements();
        }
    }

    public void fixUpBoard() {
        // Called at the end of the random placing process
        // Placing process leaves a bunch of 'X' on the board which are supposed to be '+', so this
        // fixes that
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getRevealedIcon() == 'X') {
                    board[row][col].setRevealedIcon(friendlyBoard.getUnoccupiedIcon());
                }
            }
        }
    }

    public boolean validateCoords(int row, int col) {
        if ((row >= 0 && row < board.length) && (col >= 0 && col < board[row].length)) {
            // Checks if everything is in bounds
            return true;
        }
        return false;
    }

    public void updateLegalSpots(Ship ship, boolean placing) {
        // This goes around the ship just placed and zones off all the squares around it
        // To prevent adjacent ship placement
        // This works without any further methods needed because the method for determining
        // Legal placement checks if the ship's attempted placement would occupy only
        // '+' territory (signifying misses/no ship), so zoning off adjacent squares
        // Using '?' causes legalPlacement to determine all those squares as occupied.
        // The placing boolean is true when we just placed a ship, false when we are
        // removing a ship
        Space[] placements = ship.getPlacement();
        for (int i = 0; i < placements.length; i++) {
            int initialRow = placements[i].getRow();
            int initialCol = placements[i].getCol();
            for (int row = -1; row < 2; row++) { // Access row above, same level, below
                for (int column = -1; column < 2; column++) { // Access column above, same level,
                                                              // below
                    int updatedRow = initialRow + row;
                    int updatedCol = initialCol + column;
                    if (validateCoords(updatedRow, updatedCol)) {
                        if (placing) {
                            if (board[updatedRow][updatedCol].getRevealedIcon() == friendlyBoard
                                    .getUnoccupiedIcon()) {
                                board[updatedRow][updatedCol].setRevealedIcon('X');
                                ship.addSurroundingSquare(board[updatedRow][updatedCol]);
                            }
                        } else {
                            if (board[updatedRow][updatedCol].getRevealedIcon() == 'X') {
                                Ship adjacentShip =
                                        spaceHasShip(board[updatedRow][updatedCol], ship);
                                if (adjacentShip == null) {
                                    board[updatedRow][updatedCol]
                                            .setRevealedIcon(friendlyBoard.getUnoccupiedIcon());
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public Ship spaceHasShip(Space space, Ship removedShip) {
        for (Ship ship : ships) {
            if (!ship.equals(removedShip)) {
                if (ship.spaceHasShip(space)) {
                    return ship;
                }
            }
        }
        return null;
    }

    /**
     * @return the ships
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * @param ships the ships to set
     */
    public void setShips(Ship[] ships) {

        this.ships = ships;
    }

    public Space[][] getBoard() {
        return board;
    }

    public BattleshipBoard getEnemyBoard() {
        return enemyBoard;
    }

    public BattleshipBoard getFriendlyBoard() {
        return friendlyBoard;
    }

    public void setEnemyBoard(BattleshipBoard enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public void setFriendlyBoard(BattleshipBoard friendlyBoard) {
        this.friendlyBoard = friendlyBoard;
    }

    public Ship identifyShip(Space space) {
        // Taking the space we shot and looping through each ship's placed spots
        // To identify which ship it is.
        for (Ship ship : ships) {
            for (Space place : ship.getPlacement()) {
                if (place.equals(space)) {
                    return ship;
                }
            }
        }
        // If we passed the loop there's no ship so return null
        return null;
    }

    public boolean hasAliveShips() {
        for (Ship ship : ships) {
            if (ship.getHitPoints() > 0) {
                return true;
            }
        }
        return false;
    }

    public void analyseShot(boolean hit, int row, int column) {
        char letterRow = letterCoords[row];
        column++; // To line up with standard coordinate systems used in Battleship
        if (hit) {
            System.out
                    .println("A hit! The ship at (" + letterRow + ", " + column + ") is on fire!");
        } else {
            System.out.println("A miss... there's nothing at (" + letterRow + ", " + column + ").");
        }
    }

    public void printEnemyBoard() {
        // As of 9/19, this method is only used for debugging purposes and the end of the game
        enemyBoard.revealAllSpots();;
        enemyBoard.printBoard();
        // For debugging, TODO: Remove later
        System.out.println("== WITH SPOTS COVERED ==");
        enemyBoard.coverAllSpots();
        enemyBoard.printBoard();
    }

    public String printOneRowTwoBoards(int row) {
        // Printing one row of two first rows of the friendlyBoard and the enemyBoard
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < friendlyBoard.getColumns(); i++) {
            output.append(friendlyBoard.getSpace(row, i).toString() + " ");
        }
        output.append(" | ");
        for (int i = 0; i < friendlyBoard.getColumns(); i++) {
            output.append(enemyBoard.getSpace(row, i).toString() + " ");
        }
        return output.toString();
    }

    public void printGameStatus() {
        System.out.println(
                "=== END OF TURN ===\n Friendly territory on left, enemy territory on right");
        for (int i = 0; i < friendlyBoard.getRows(); i++) {
            System.out.println(printOneRowTwoBoards(i));
        }
    }

    public void printFriendlyBoard() {
        friendlyBoard.printBoard();
    }
}
