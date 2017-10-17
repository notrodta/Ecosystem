import java.security.SecureRandom;
import java.util.Objects;

public abstract class Animal extends Organism {

	
   /**
    * Constructor for animal
    * @param symbol - symbol of animal
    * @param name - name of animal
    * @param row - coordinate purposes
    * @param col - coordinate purposes
    * @param energy - indicates what state the animal currently is in
    */
    protected Animal(String symbol, String name, int row, int col, int energy) {
        super(symbol, name, row, col);
        this.energy = energy;
        this.age = 1;
        PositionHistory[positionCount] = "(" + row + "," + col + ") ";
        ++positionCount;
    }

    //Abstract classes
    /**
     * @return At what cycle the animal is able to move
     */
    public abstract int GetClockPerMove(); //Indicates how often the animal is allow to move
    /**
     * @return earliest age the animal can give birth
     */
    public abstract int GetEarliestBirthAge(); //Indicates the earliest age the animal is allow to give birth
    /**
     * @return latest age the animal can give birth
     */
    public abstract int GetLatestBirthAge(); //Indicates the latest age the animal is allow to give birth
    /**
     * Lose energy from giving birth
     */
    public abstract void LoseEnergyFromBirth(); //Lose energy from giving birth
    /**
     * @return least amount of energy needed for animal to not be hungry
     */
    public abstract int GetSatisfactoryEnergy(); //Indicate the energy where the animal does not want to eat anymore
    /**
     * @return least energy needed to give birth
     */
    public abstract int GetAvaliableEnergyForBirth(); //indicate the minimum energy needed for animal to give birth

    /**
     * Controls the animal's moving behavior
     */
    public abstract void Move(); //Animal move action
    /**
     * Check to see if the animal can give birth
     */
    public abstract void CheckForAvaliableBirth(); //Check to see if the animal is able to give birth

    protected double energy;
    protected int age;

    protected int organismCurrentCycle;//used to trace if animal has moved today
    protected int spotCount;
    

    protected Organism[] avaliableSpot = new Organism[4];//stores position for where animal can go

    protected String[] PositionHistory = new String[10];//stores every position the animal has been to up to 10 cycles ago
    protected int positionCount = 0;


    /**
     * getter for energy
     * @return energy
     */
    public double GetEnergy()
    {
        return this.energy;
    }

    /**
     * method for animal to lose energy after every cycle
     * @param isMoved - energy is lost differently based on if they moved or not
     */
    public void LoseEnergy(boolean isMoved)
    {
        if (isMoved)
            --this.energy;
        else
            this.energy = this.energy - 0.5;
    }

    /**
     * method for animal to gain energy after eating
     * @param currentAge - animal gain different energy base on their age
     */
    public void GainEnergy(int currentAge)
    {
        if (currentAge > 10)
            this.energy = this.energy + 3;
        else
            this.energy = this.energy + 4;
    }

    /**
     * getter for animal age
     * @return animal's age
     */
    public int GetAge()
    {
        return this.age;
    }

    /**
     * Check to see if animal is alive
     * @return animal's lively status (alive or dead)
     */
    public boolean GetIsAlive()
    {
        if (this.energy <= 0)
        {
            return false;
        }
        return true;
    }

    /**
     * Controls the behavior of a dead animal
     */
    public void Dead() //Deals with dead animals
    {
        Organism e = new EmptySpace(this.GetRow(),this.GetCol());
    }

    /**
     * @return - boolean indicating if the animal has moved this current cycle
     */
    protected boolean isNotMoved() { //Check to see if this animal has moved this cycle
        if (this.organismCurrentCycle != Ecosystem.currentCycle){ //organism cycle is current one less than environment cycle
            ++this.organismCurrentCycle; //organism cycle = environment cycle, indicating it has moved
            return true;
        }
        else
            return false;
    }


    /**
     * Store avaliable spots into avaliableSpot array with the parameter "food name"
     * @param foodName - name of food
     */
    protected void StoreAvaliableSpot(String foodName) {	//Store avaliable spots into avaliableSpot array with the parameter "food name"
        int count = 0;

        //Checks to see if there is an avaliable spot to be stored
        try{
            if (Objects.equals(Ecosystem.map[this.GetRow() - 1][this.GetCol()].GetName(), new String(foodName))){
                this.avaliableSpot[count] = Ecosystem.map[this.GetRow() - 1][this.GetCol()];
                ++count;
            }
        }catch(ArrayIndexOutOfBoundsException unimportant){}

        try{
            if (Objects.equals(Ecosystem.map[this.GetRow() + 1][this.GetCol()].GetName(), new String(foodName))){
                this.avaliableSpot[count] = Ecosystem.map[this.GetRow() + 1][this.GetCol()];
                ++count;
            }
        }catch(ArrayIndexOutOfBoundsException unimportant){}

        try{
            if(Objects.equals(Ecosystem.map[this.GetRow()][this.GetCol() - 1].GetName(), new String(foodName))){
                this.avaliableSpot[count] = Ecosystem.map[this.GetRow()][this.GetCol() - 1];
                ++count;
            }
        }catch(ArrayIndexOutOfBoundsException unimportant){}

        try{
            if (Objects.equals(Ecosystem.map[this.GetRow()][this.GetCol() + 1].GetName(), new String(foodName))){
                this.avaliableSpot[count] = Ecosystem.map[this.GetRow()][this.GetCol() + 1];
                ++count;
            }
        }catch(ArrayIndexOutOfBoundsException unimportant){}

        spotCount = count;

    }

 
    /**
     * Randomly chooses an avaliable spot the animal can go to
     */
    protected void GoToAvaliableSpot() { //Randomly choose an avaliable spot to go to

        SecureRandom rand = new SecureRandom();

        int num = rand.nextInt(spotCount);
        //Store new position
        int newRow = avaliableSpot[num].GetRow();
        int newCol = avaliableSpot[num].GetCol();
        //Store current position
        int currentRow = this.GetRow();
        int currentCol = this.GetCol();

        Ecosystem.map[newRow][newCol] = this;//Stores this animal into new position
        this.SetNewPosition(newRow, newCol);//Store the new position of this animal
        Organism e = new EmptySpace(currentRow,currentCol); //Replace the old position with an empty space
    }

    
	/**
	 * @return check to see if animal is hungry
	 */
    protected boolean isHungry() {     //Check to see if animal is hungry
        if (this.energy > 0 && this.energy < GetSatisfactoryEnergy())
        {
            return true;
        }
        return false;
    }

    /**
     * Animal behavior when not hungry
     */
    protected void NotHungry()    //Animal action when not hungry
    {
        //Will try to go to an emptySpace
        StoreAvaliableSpot("EmptySpace");
        if (spotCount > 0)
        {
            GoToAvaliableSpot();
            this.LoseEnergy(true);//lose 1 energy for moving
        }
        else
        { //Unable to find empty Space and will have to stay still
            this.LoseEnergy(false);//lose 0.5 energy for staying still
        }
    }


    /**
     * Animal behavior when they eat
     * @param foodToEat string parameter for the food name they want
     */
    protected void Eat(String foodToEat)     //Animal eat actions
    {
        StoreAvaliableSpot(foodToEat); //Store avaliable nearby food into array
        if (spotCount > 0) //If food was found
        {
            GoToAvaliableSpot(); //Go to where the food is
            this.LoseEnergy(true);//lose 1 energy for moving
            this.GainEnergy(this.GetAge());//gain energy for eating
        }
        else
        {//If no nearby food was found, try go to empty space
            StoreAvaliableSpot("EmptySpace");
            if (spotCount > 0)
            {
                GoToAvaliableSpot();//Go to where the empty space is
                this.LoseEnergy(true);//lose 1 energy form moving
            }
            else
            { //No emptyspace and food was found. Have to stay still
                this.LoseEnergy(false);//lose 0.5 energy for staying still
            }
        }
    }

   /**
    * Storing the position of the animal history into array "PositionHistory"
    */
    protected void UpdatePositionHistory()//Storing the position of the animal history
    {
        if (positionCount < 10){
            PositionHistory[positionCount] = "(" + this.row + "," + this.col + ") ";
        }

        //Deleting old memory that will not be needed anymore
        if (positionCount >= 10)
        {
            String[] NewPositionHistory = new String[10];
            positionCount = 9;
            for (int i = 0; i < 9; i++)
            {
                NewPositionHistory[i] = PositionHistory[i+1];
            }
            NewPositionHistory[9] = "(" + this.row + "," + this.col + ") ";
            PositionHistory = NewPositionHistory;
        }
        ++positionCount;
    }

}