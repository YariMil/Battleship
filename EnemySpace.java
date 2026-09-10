public class EnemySpace extends Space {
    // These are the spaces that you see on your enemy's board, representing the fog of war.
    public EnemySpace(int row, int col) {
        super(row, col, '~', '*', '+');
    }
}
