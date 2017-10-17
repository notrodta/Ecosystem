import java.security.SecureRandom;
import java.util.Objects;

public abstract class Organism {

    protected String symbol;
    protected String name;

    //Position
    protected int row;
    protected int col;

    /**
     * Constructor for organism class
     * @param symbol organism's symbol
     * @param name organism's name
     * @param row organism's row coordinate
     * @param col organism's column coordinate
     */
    protected Organism(String symbol,String name, int row, int col)
    {
        this.symbol = symbol;
        this.name = name;
        this.row = row;
        this.col = col;
        Ecosystem.map[row][col] = this;
    }

    /**
     * getter for organism's symbol
     * @return organism's symbol
     */
    public String GetSymbol()
    {
        return this.symbol;
    }

    /**
     * getter for organism's name
     * @return organism's name
     */
    public String GetName()
    {
        return this.name;
    }

    /**
     * set's organism's new position after they move
     * @param row organism's new row
     * @param col organism's new column
     */
    public void SetNewPosition(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    /**
     * getter for organism's row
     * @return organism's row
     */
    public int GetRow()
    {
        return this.row;
    }

    /**
     * getter for organism's column
     * @return organism's column
     */
    public int GetCol()
    {
        return this.col;
    }

}