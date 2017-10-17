public class EmptySpace extends Organism{

    /**
     * constructor for empty space
     * @param row empty space's row
     * @param col empty space's column
     */
    protected EmptySpace(int row, int col) {
        super(". ", "EmptySpace", row, col);
    }

    @Override
    public String toString() {
        return ".  ";
    }
}