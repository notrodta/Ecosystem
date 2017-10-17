import java.security.SecureRandom;

public class Herbivore extends Animal{

    /**
     * constructor for Herbivore
     * @param row herbivore's row
     * @param col herbivore's column
     */
    protected Herbivore(int row, int col) {
        super("& ", "Herbivore", row, col, 4);
    }

    @Override
    public int GetClockPerMove() {
        return 2;
    }

    @Override
    public int GetEarliestBirthAge() {
        return 2;
    }

    @Override
    public int GetLatestBirthAge() {
        return 6;//4
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
    public int GetAvaliableEnergyForBirth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        return "&  ";
    }

    @Override
    public void CheckForAvaliableBirth() {
        if (this.energy > GetAvaliableEnergyForBirth() && this.age >= GetEarliestBirthAge() && this.age <= GetLatestBirthAge())
        {
            StoreAvaliableSpot("EmptySpace");

            if (spotCount > 0){//Gives birth at a nearby avaliable emptySpace
                SecureRandom rand = new SecureRandom();

                int num = rand.nextInt(spotCount);
                int newRow = avaliableSpot[num].GetRow();
                int newCol = avaliableSpot[num].GetCol();
                LoseEnergyFromBirth();
                Organism h = new Herbivore(newRow,newCol);
            }
        }
    }

    @Override
    public void Move() {
        UpdatePositionHistory();
        if (isNotMoved()){ //able to move
            //Assuming its hungry:
            if (isHungry()){
                Eat("Plant");
            }
            else
            {
                NotHungry();
            }
        }
        this.age++;
    }


}