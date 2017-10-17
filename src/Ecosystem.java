import java.security.SecureRandom;

public class Ecosystem {

    public static int dimension = 30; //size of the max : dimension x dimension
    private static int carnivorePercentage = 10;
    private static int herbivorePercentage = 20;
    private static int plantPercentage = 30;

    public static Organism[][] map = new Organism[dimension][dimension]; //Array to store all the organisms

    public static int currentCycle = 0;
    private static int totalCycle = 30;

    /**
     * Constructor when no parameter is passed in
     * initializes the ecosystem with default values
     */
    public Ecosystem()
    {
        dimension = 30;
        carnivorePercentage = 10;
        herbivorePercentage = 20;
        plantPercentage = 30;
        InitializeMap(carnivorePercentage,herbivorePercentage,plantPercentage);
    }

    /**
     * initializes the ecosystem with values passed in the parameter
     */
    public Ecosystem(int mapSize, int carnPercent, int herbPercent, int plantPercent)
    {
        dimension = mapSize;
        carnivorePercentage = carnPercent;
        herbivorePercentage = herbPercent;
        plantPercentage = plantPercent;
        InitializeMap(carnPercent,herbPercent,plantPercent);
    }


    //Call this function to start the program
    private static void RunCycle()
    {
        new Ecosystem(30,10,20,30); //Create the map
        System.out.println("Day:" + 1);
        PrintMap();
        //running the cycle in a loop
        for (int i = 1; i < totalCycle; i++)
        {
            System.out.println("Day: " + (i+1));
            NextCycle();
            PrintMap();
        }
        PrintCensus(); //Prints out census of the environment
    }


    /**
     * Determines what the environment will look like during the next cycle
     */
    public static void NextCycle()
    {
        ++currentCycle;
        //Looks through array
        for(int row = 0; row < map.length; row++)
        {
            for (int col = 0; col< map[row].length; col++){

                if (map[row][col] instanceof Animal) //Find animal in array
                {
                    if (!((Animal) map[row][col]).GetIsAlive()) //check to see if animal is dead
                    {
                        ((Animal) map[row][col]).Dead();
                    }
                    else
                    { //if animal is not dead
                        ((Animal) map[row][col]).Move();
                    }
                }
            }
        }

        //Looks through array
        for(int row = 0; row < map.length; row++)
        {
            for (int col = 0; col< map[row].length; col++){
                //Look for any animal ready to give birth
                if (map[row][col] instanceof Animal){
                    ((Animal) map[row][col]).CheckForAvaliableBirth();
                }
            }
        }
        SpawnPlant();
    }

    /**
     * Spawn plants every 7 interval cycle
     */
    private static void SpawnPlant()
    {
        if (currentCycle != 0 && currentCycle % 7 == 0)
        {
            SecureRandom rand = new SecureRandom();
            for (int row = 0; row < map.length; row++)
            {
                for (int col = 0; col < map[row].length; col++)
                {
                    if (map[row][col] instanceof EmptySpace)
                    {
                        int num = rand.nextInt(5); //20% chance of spawning a plant at an empty space
                        if (num == 0)
                        {
                            Organism p = new Plant(row,col);
                        }
                    }
                }
            }
        }
    }

    //Display what the environment looks like
    private static void PrintMap()
    {
        for(int row = 0; row < map.length; row++)
        {
            for (int col = 0; col< map[row].length; col++){
                System.out.print(map[row][col].GetSymbol());
            }
            System.out.println();
        }
    }


    /**
     * initializes the ecosystem for the program to work with
     * @param carnPercent percentage of carnivores
     * @param herbPercent percentage of herbivores
     * @param plantPercent percentage of plants
     */
    private static void InitializeMap(int carnPercent, int herbPercent, int plantPercent)
    {
        nullify();
        SecureRandom ranNum = new SecureRandom();
        int counter = 0;
        int cStartPercent = carnPercent;
        int cStart= ((dimension*dimension)*cStartPercent)/100; // We'll have cStart carnivores in beginning.
        int hStartPercent = herbPercent;
        int hStart= ((dimension*dimension)*hStartPercent)/100; // We'll have cStart carnivores in beginning.
        int pStartPercent = plantPercent;
        int pStart= ((dimension*dimension)*pStartPercent)/100; // We'll have cStart carnivores in beginning.
        // Populating with carnivores.
        while(counter < cStart)
        {
            int i = ranNum.nextInt(dimension);
            int j = ranNum.nextInt(dimension);

            if(map[i][j] == null)
            {
                new Carnivore(i,j);
                counter++;
            }
        }

        // Populating with herbivores.
        counter = 0;
        while(counter < hStart)
        {
            int i = ranNum.nextInt(dimension);
            int j = ranNum.nextInt(dimension);

            if(map[i][j] == null)
            {
                // Spawn Herbivore
                new Herbivore(i,j);
                counter++;
            }
        }

        // Populating with plants.
        counter = 0;
        while(counter < pStart)
        {
            int i = ranNum.nextInt(dimension);
            int j = ranNum.nextInt(dimension);

            if(map[i][j] == null)
            {
                // Spawn Plant
                new Plant(i,j);
                counter++;
            }
        }

        //empty space
        for(int row = 0; row < map.length; row++)
        {
            for (int col = 0; col< map[row].length; col++){
                if (map[row][col] == null)
                    new EmptySpace(row,col);
            }
        }

        System.out.println("Day:" + 1);
        PrintMap();
    }

    //Displays census of the ecosystem
    private static void PrintCensus()
    {
        //census purposes
        int ca = 0;
        int he = 0;
        int pl = 0;
        int em = 0;
        for (int row = 0; row < map.length; row++)
        {
            for (int col = 0; col < map[row].length; col++)
            {
                if (map[row][col] instanceof Carnivore)
                    ca++;
                if (map[row][col] instanceof Herbivore)
                    he++;
                if (map[row][col] instanceof Plant)
                    pl++;
                if (map[row][col] instanceof EmptySpace)
                    em++;
            }
        }
        System.out.printf("%s\n%s %d%n%s %d%n%s %d%n%s %d%n\n","Census of map:", "carnivore:",ca,"herbivore:",he,"plant:",pl,"emptySpace:",em);
    }

    /**
     * Makes the 2d organism array empty
     */
    public static void nullify()
    {
        //empty space
        for(int row = 0; row < map.length; row++)
        {
            for (int col = 0; col< map[row].length; col++){
                map[row][col] = null;
            }
        }
    }
}