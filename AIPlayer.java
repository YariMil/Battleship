import java.util.ArrayList;

public class AIPlayer extends Player {
    private boolean tunnelvisioned;
    private int tunnelRow;
    private int tunnelCol;
    private String tunnelDirection;
    private boolean directionSwapped;
    private int tunnelShotsTaken;
    private final String[] directions = {"N", "E", "S", "W"};
    private ArrayList<String> directionsTried;

    public AIPlayer(Player enemyPlayer) {
        super(enemyPlayer);
        // The AIs enemyBoard acts as the enemyBoard for the player. The AI
        // doesn't actually know what its friendlyBoard contains.
        // Unlike other players, where the enemyBoard is the board
        // OF the enemy, this is the board FOR the enemy.
        friendlyBoard = new BattleshipBoard(false);
        this.tunnelvisioned = false;
        this.tunnelCol = 0;
        this.tunnelRow = 0;
        this.tunnelDirection = "";
        this.directionSwapped = false;
        directionsTried = new ArrayList<String>();
        tunnelShotsTaken = 0;
        this.board = friendlyBoard.getBoard();
        enemyBoard = null;
    }

    public void placeShips() {
        // Check for legality on all spaces
        // If the ship in question can be placed in at least ONE position, we're good
        // Take the ship, randomize a position, checking if its valid, place a ship there

        for (Ship ship : ships) {
            int row = (int) (Math.random() * friendlyBoard.getRows());
            int col = (int) (Math.random() * friendlyBoard.getColumns());
            while (!legalPlacement(ship, row, col, false)
                    && !legalPlacement(ship, row, col, true)) {
                // While placement is illegal, reroll
                row = (int) (Math.random() * friendlyBoard.getRows());
                col = (int) (Math.random() * friendlyBoard.getColumns());
            }
            if (legalPlacement(ship, row, col, false) && legalPlacement(ship, row, col, true)) {
                // Flip a coin for vertical or horizontal
                boolean vertical = ((int) (Math.random() * 2) == 1);
                placeShip(ship, row, col, vertical);
            } else if (legalPlacement(ship, row, col, false)) {
                placeShip(ship, row, col, false);
            } else {
                placeShip(ship, row, col, true);
            }
        }
        fixUpBoard();
        enemyBoard = new BattleshipBoard(friendlyBoard);
    }

    public boolean checkForPlacement(Ship ship) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (legalPlacement(ship, row, column, false)
                        || legalPlacement(ship, row, column, true)) {
                    return true; // There's a legal position
                }
            }
        }
        return false; // No legal positions
    }

    public void takeShot() {
        if (tunnelvisioned) {
            String action = getTunnelVisionAction();
            int row = Integer.parseInt(action.substring(0, 1));
            int col = Integer.parseInt(action.substring(1));
            BattleshipGame.processHit(this, enemyPlayer, row, col);
        } else {
            String action = getRandomAction(enemyBoard.getBoard());
            int row = Integer.parseInt(action.substring(0, 1));
            int col = Integer.parseInt(action.substring(1));
            BattleshipGame.processHit(this, enemyPlayer, row, col);
        }
    }

    public String getTunnelVisionAction() {
        if (tunnelDirection.equals("N")) {
            return "" + (tunnelRow - tunnelShotsTaken) + tunnelCol;
        } else if (tunnelDirection.equals("E")) {
            return "" + tunnelRow + (tunnelCol + tunnelShotsTaken);
        } else if (tunnelDirection.equals("S")) {
            return "" + (tunnelRow + tunnelShotsTaken) + tunnelCol;
        } else {
            return "" + tunnelRow + (tunnelCol - tunnelShotsTaken);
        }
    }

    public String getRandomAction(Space[][] b) {
        int row = (int) (Math.random() * enemyBoard.getRows());
        int col = (int) (Math.random() * enemyBoard.getColumns());
        while (b[row][col].getStage() != 0) {
            row = (int) (Math.random() * enemyBoard.getRows());
            col = (int) (Math.random() * enemyBoard.getColumns());
        }
        return "" + row + col;
    }

    public boolean checkDirection(String direction) {
        for (String d : directionsTried) {
            if (d.equals(direction)) {
                return true;
            }
        }
        return false;
    }

    public void determineTunnelDirection() {
        int randomDirection = (int) (Math.random() * 4);
        while (directionsTried.contains(directions[randomDirection])) {
            randomDirection = (int) (Math.random() * 4);
        }
        tunnelDirection = directions[randomDirection];
        tunnelShotsTaken = 1;
    }

    public void turnAroundDirection() {
        if (tunnelDirection.equals("N")) {
            tunnelDirection = "S";
        } else if (tunnelDirection.equals("E")) {
            tunnelDirection = "W";
        } else if (tunnelDirection.equals("S")) {
            tunnelDirection = "N";
        } else {
            tunnelDirection = "E";
        }
        tunnelShotsTaken = 1;
    }


    public void switchTunnelDirection() {
        directionsTried.add(tunnelDirection);
        if (!directionSwapped) {
            turnAroundDirection();
            directionSwapped = true;
        } else {
            determineTunnelDirection();
            directionSwapped = false;
        }
    }

    @Override
    public boolean validateCoords(int row, int col) {
        if (tunnelDirection.equals("N")) {
            return super.validateCoords(row - tunnelShotsTaken, col);
        } else if (tunnelDirection.equals("E")) {
            return super.validateCoords(row, col + tunnelShotsTaken);
        } else if (tunnelDirection.equals("S")) {
            return super.validateCoords(row + tunnelShotsTaken, col);
        } else {
            return super.validateCoords(row, col - tunnelShotsTaken);
        }
    }

    public void clearDirections() {
        while (directionsTried.size() != 0) {
            directionsTried.remove(0);
        }
    }

    @Override
    public void analyseShot(boolean hit, int row, int column) {
        super.analyseShot(hit, row, column);
        if (hit) {
            if (tunnelvisioned) { // If we're already tunnelvisioned
                if (enemyPlayer.getBoard()[tunnelRow][tunnelCol].getStage() == 2) {
                    tunnelvisioned = false;
                    tunnelRow = 0;
                    tunnelCol = 0;
                    directionSwapped = false;
                    clearDirections();
                } else {
                    tunnelShotsTaken++;
                }
            } else { // Begin tunnelvision
                tunnelvisioned = true;
                tunnelRow = row;
                tunnelCol = column;
                determineTunnelDirection();
            }
        } else {
            if (tunnelvisioned) {
                switchTunnelDirection();
            }
        }
        if (tunnelvisioned)
            if (!validateCoords(tunnelRow, tunnelCol)) {
                switchTunnelDirection();
            }
    }
}
