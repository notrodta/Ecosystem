import java.security.SecureRandom;
import java.util.Objects;

public class Carnivore extends Animal{
    //constructor
	/**
	 * constructor for carnivore
	 * @param row store carnivore's row
	 * @param col store carnivore's column
	 */
    protected Carnivore(int row, int col) {
        super("@ ", "Carnivore", row, col, 4);
    }

    @Override
    public String toString() {
        return "@  ";
    }

    @Override
    public int GetClockPerMove() {
        return 1;
    }

    @Override
    public int GetEarliestBirthAge() {
        return 5; //5
    }

    @Override
    public int GetLatestBirthAge() {
        return 5; //5
    }

    @Override
    public int GetAvaliableEnergyForBirth() {
        return 4;
    }

    @Override
    public void LoseEnergyFromBirth() {
        this.energy = this.energy - 2;
    }
    @Override
    public int GetSatisfactoryEnergy() {
        return 5;
    }

    @Override
    public void CheckForAvaliableBirth() {
        if (this.energy > GetAvaliableEnergyForBirth() && this.age >= GetEarliestBirthAge() && this.age <= GetLatestBirthAge())
        {
            StoreAvaliableSpot("EmptySpace");

            if (spotCount > 0){ //Gives birth at a nearby avaliable emptySpace
                SecureRandom rand = new SecureRandom();

                int num = rand.nextInt(spotCount);
                int newRow = avaliableSpot[num].GetRow();
                int newCol = avaliableSpot[num].GetCol();
                LoseEnergyFromBirth();
                Organism c = new Carnivore(newRow,newCol);
                //System.out.println("C birth! " + ((Animal)c).GetAge());
            }
            else //Gives birth at any avaliable emptySpace
            {
            	SecureRandom rand = new SecureRandom();
            	boolean isGaveBirth = false;
            	while(isGaveBirth == false)
            	{
            		int i = rand.nextInt(Ecosystem.dimension);
                    int j = rand.nextInt(Ecosystem.dimension);
                    if (Ecosystem.map[i][j] instanceof EmptySpace)
                    {
                    	new Carnivore(i,j);
                    	isGaveBirth = true;
                    }
            	}
            }
        }

    }

    @Override
    public void Move() {
        UpdatePositionHistory();
        if (isNotMoved()){ //able to move
            //Assuming its hungry:
            if (isHungry()){
                //System.out.println("is hungry");
                Eat("Herbivore");
            }
            else
            {
                NotHungry();
            }
        }
        this.age++;
    }
}