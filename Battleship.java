public class Battleship {
    public static void main(String[] args) {
        BattleshipBoard playerBoard = new BattleshipBoard();
        BattleshipBoard aiBoard = new BattleshipBoard();
        BattleshipBoard aiBoard2 = new BattleshipBoard();
        AIPlayer testAI = new AIPlayer(aiBoard, null);
        // AIPlayer testAI2 = new AIPlayer(aiBoard2, testAI);
        HumanPlayer human = new HumanPlayer(playerBoard, testAI);
        testAI.setPlayer(human);
        human.placeShips();
        testAI.placeShips();
        // aiBoard.revealAllSpots();
        boolean gameRunning = true;
        testAI.placeShips();
        // aiBoard.printBoard();
        while (gameRunning) {
            // testAI2.takeShot();
            // aiBoard.printBoard();
            // break;
            aiBoard.printBoard();
            String action = human.takeAction();
            int row = human.convertLetterToNum(action.charAt(0));
            String colString = action.substring(1);
            int col = Integer.parseInt(colString) - 1;
            BattleshipGame.processHit(human, testAI, row, col);
            gameRunning = BattleshipGame.checkForWin(testAI);
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
}
