public class Plant extends Organism{

    /**
     * constructor for plant
     * @param row plant's row
     * @param col plant's column
     */
    protected Plant(int row, int col) {
        super("* ", "Plant", row, col);
    }

    @Override
    public String toString() {
        return "*  ";
    }
}