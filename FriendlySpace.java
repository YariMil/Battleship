public class FriendlySpace extends Space {
    // These spaces are the spaces where you see your ships and whether the enemy has hit your
    // ships.
    // Instead of ~ and *, it is + for unrevealed, M for missed, and H for hit
    public FriendlySpace(int row, int col) {
        super(row, col, '+', 'H', 'M');
    }
}
