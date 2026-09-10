public class Ship {
    private char icon;
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    private int size;
    private int hitPoints;

    /**
     * @return the icon
     */
    public char getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(char icon) {
        this.icon = icon;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the hitPoints
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @param hitPoints the hitPoints to set
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * @return the placement
     */
    public Space[] getPlacement() {
        return placement;
    }

    /**
     * @param placement the placement to set
     */
    public void addPlacement(Space place) {
        for (int i = 0; i < placement.length; i++) {
            if (placement[i] == null) {
                placement[i] = place;
                return;
            }
        }
    }

    public void revealPlacements() {
        for (Space space : placement) {
            space.setStage(2);
        }
    }

    public void printPlacement() {
        for (int i = 0; i < placement.length; i++) {
            System.out.print(placement[i] + " ");
        }
    }

    public void resetSurroundingSquares() {
        for (int i = 0; i < surroundingSquares.length; i++) {
            surroundingSquares[i] = null;
        }
    }

    public void resetPlacements() {
        for (int i = 0; i < placement.length; i++) {
            placement[i].setRevealedIcon('+');
            placement[i] = null;
        }

    }

    private Space[] placement;
    private Space[] surroundingSquares;

    public void addSurroundingSquare(Space square) {
        for (int i = 0; i < surroundingSquares.length; i++) {
            if (surroundingSquares[i] == null) {
                surroundingSquares[i] = square;
                return;
            }
        }
    }

    public int calculateSquareFootage() {
        int squareFootage = 6; // Start with 6 due to the ends of the ship taking up 3 squares
        return squareFootage + 2 * size; // 2 squares on each side.
    }

    public boolean spaceHasShip(Space space) {
        for (int i = 0; i < surroundingSquares.length; i++) {
            if (surroundingSquares[i] != null) {
                if (surroundingSquares[i].equals(space)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Ship other) {
        if (name.equals(other.getName())) {
            return true;
        }
        return false;
    }

    public Ship(char icon, int size, String name) {
        this.icon = icon;
        this.size = size;
        this.hitPoints = size;
        this.placement = new Space[size];
        this.surroundingSquares = new Space[calculateSquareFootage()];
        this.name = name;
    }
}
