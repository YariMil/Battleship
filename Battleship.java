public class Battleship {
    public static void main(String[] args) {
        // BattleshipBoard playerBoard = new BattleshipBoard(false);
        // BattleshipBoard aiBoard = new BattleshipBoard(false);
        // BattleshipBoard aiBoard2 = new BattleshipBoard(false);
        AIPlayer testAI = new AIPlayer(null);
        // // AIPlayer testAI2 = new AIPlayer(aiBoard2, testAI);
        HumanPlayer human = new HumanPlayer(testAI);
        testAI.placeShips();
        // testAI.setPlayer(human);
        human.placeShips();
        testAI.setPlayer(human);
        assignEnemyBoards(human, testAI);
        // testAI.placeShips();
        // aiBoard.revealAllSpots();
        boolean gameRunning = false;
        // testAI.placeShips();
        // aiBoard.printBoard();
        human.printGameStatus();
        for (int i = 0; i < 20; i++) {
            testAI.takeShot();
        }
        human.printGameStatus();
        while (gameRunning) {
            // testAI2.takeShot();
            // aiBoard.printBoard();
            // break;
            // aiBoard.printBoard();
            // playerBoard.printBoard();
            // String action = human.takeAction();
            // int row = human.convertLetterToNum(action.charAt(0));
            // String colString = action.substring(1);
            // int col = Integer.parseInt(colString) - 1;
            // BattleshipGame.processHit(human, testAI, row, col);
            // testAI.takeShot();
            // gameRunning = BattleshipGame.checkForWin(testAI);
        }
        // TODO: Place ships for the player
        // aiBoard.printBoard();
        // BattleshipGame.processHit(ai, 4, 4);
        // System.out.println();
        // aiBoard.printBoard();
        // The board in Player is intertwined with all other references to the board passed in
        // constructor
        // Boards share the same memory slot so all their changes carry
        // Places can be modified outside the array since they share the same memory slot, meaning
        // we can do stuff in BattleshipBoard with them and still be fine

    }

    public static void assignEnemyBoards(Player p1, Player p2) {
        // Necessary for the game since the players are shooting at the other's enemyBoards.
        BattleshipBoard p1EnemyBoard = p1.getEnemyBoard();
        p1.setEnemyBoard(p2.getEnemyBoard());
        p2.setEnemyBoard(p1EnemyBoard);
    }
}
