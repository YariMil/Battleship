public class Space {
    protected char fogOfWarIcon;
    protected char onFireIcon;
    protected char revealedIcon;
    private int stage;
    private boolean ableToBeOccupied;
    private int row;
    /**
     * @return the ableToBeOccupied
     */
    public boolean isAbleToBeOccupied() {
        return ableToBeOccupied;
    }

    /**
     * @param ableToBeOccupied the ableToBeOccupied to set
     */
    public void setAbleToBeOccupied(boolean ableToBeOccupied) {
        this.ableToBeOccupied = ableToBeOccupied;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    private int col;

    /**
     * @return the fogOfWarIcon
     */
    public char getFogOfWarIcon() {
        return fogOfWarIcon;
    }

    /**
     * @param fogOfWarIcon the fogOfWarIcon to set
     */
    public void setFogOfWarIcon(char fogOfWarIcon) {
        this.fogOfWarIcon = fogOfWarIcon;
    }

    /**
     * @return the onFireIcon
     */
    public char getOnFireIcon() {
        return onFireIcon;
    }

    /**
     * @param onFireIcon the onFireIcon to set
     */
    public void setOnFireIcon(char onFireIcon) {
        this.onFireIcon = onFireIcon;
    }

    /**
     * @return the revealedIcon
     */
    public char getRevealedIcon() {
        return revealedIcon;
    }

    /**
     * @param revealedIcon the revealedIcon to set
     */
    public void setRevealedIcon(char revealedIcon) {
        this.revealedIcon = revealedIcon;
    }

    /**
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    public Space(int row, int col, char fogOfWar, char onFire, char revealed) {
        // "revealed" means there's nothing there. On fire means there's something there
        fogOfWarIcon = fogOfWar;
        onFireIcon = onFire;
        revealedIcon = revealed;
        stage = 0;
        ableToBeOccupied = false;
        this.row = row;
        this.col = col;
    }

    public String toString() {
        if (stage == 0) {
            return "" + fogOfWarIcon;
        } else if (stage == 1) {
            return "" + onFireIcon;
        } else {
            return "" + revealedIcon;
        }
    }

    public boolean equals(Space other) {
        if (other.getRow() == row && other.getCol() == col) {
            return true;
        }
        return false;
    }
}
