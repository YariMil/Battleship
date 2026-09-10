public class BattleshipBoard {
    private int rows;
    private int columns;

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    private Space[][] board;

    public BattleshipBoard() {
        rows = 10;
        columns = 10;
        board = createBoard();
    }

    public Space[][] createBoard() {
        Space[][] board = new Space[rows][columns];
        for (int i = 0; i < rows; i++) {
            Space[] row = new Space[columns];
            for (int j = 0; j < columns; j++) {
                row[j] = new Space(i, j);
            }
            board[i] = row;
        }
        return board;
    }

    public void printBoard() {
        printLetters();
        System.out.println();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col].toString() + " ");
            }
            System.out.print((char) (row + 'A'));
            System.out.println();
        }

    }

    public void printLetters() {
        for (int i = 1; i < columns + 1; i++) {
            System.out.print(i + " ");
        }
    }

    public Space[][] getBoard() {
        return board;
    }

    public void revealAllSpots() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setStage(2);
            }
        }
    }
}
