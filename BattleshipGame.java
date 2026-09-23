public class BattleshipGame {

    public static void processHit(Player player, Player hitPlayer, int row, int col) {
        // Grabs the grid from the BattleshipBoard
        Space[][] hitBoard = player.getEnemyBoard().getBoard();
        Space[][] mirroringBoard = hitPlayer.getFriendlyBoard().getBoard();
        if (hitBoard[row][col].getRevealedIcon() == '+') {
            hitBoard[row][col].setStage(2);
            mirroringBoard[row][col].setStage(2);
            player.analyseShot(false, row, col);
        } else {
            if (hitBoard[row][col].getStage() == 0) {
                Ship affectedShip = hitPlayer.identifyShip(hitBoard[row][col]);
                affectedShip.setHitPoints(affectedShip.getHitPoints() - 1);
                if (shipSunk(affectedShip)) {
                    affectedShip.revealPlacements();
                } else {
                    hitBoard[row][col].setStage(1);
                    mirroringBoard[row][col].setStage(1);
                }
                player.analyseShot(true, row, col);
                if (shipSunk(affectedShip)) {
                    System.out
                            .println("The player's " + affectedShip.getName() + " has been sunk!");
                }
            } else {
                System.out.println("A hit... but that square has already been sunk!");
            }
        }
        // Returns a boolean mostly for the AI, I might change that later
    }

    public static boolean checkForWin(Player player) {
        return player.hasAliveShips();
    }

    public static boolean shipSunk(Ship ship) {
        if (ship.getHitPoints() == 0) {
            return true;
        }
        return false;
    }

}
