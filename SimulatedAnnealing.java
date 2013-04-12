// Jacobson's Java Code from http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
// For reference purposes only
    /
    *
    * City.java
    * Models a city
    */
    
    package sa;
    
    public class City {
        int x;
        int y;
        
        // Constructs a randomly placed city
        public City(){
            this.x = (int)(Math.random()*200);
            this.y = (int)(Math.random()*200);
        }
        
        // Constructs a city at chosen x, y location
        public City(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        // Gets city's x coordinate
        public int getX(){
            return this.x;
        }
        
        // Gets city's y coordinate
        public int getY(){
            return this.y;
        }
        
        // Gets the distance to given city
        public double distanceTo(City city){
            int xDistance = Math.abs(getX() - city.getX());
            int yDistance = Math.abs(getY() - city.getY());
            double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
            
            return distance;
        }
        
        @Override
        public String toString(){
            return getX()+", "+getY();
        }
    }
    
    Next let's create a class that can keep track of the cities.
    
    TourManager.java
    /*
    * TourManager.java
    * Holds the cities of a tour
    */
    
    package sa;
    
    import java.util.ArrayList;
    
    public class TourManager {
    
        // Holds our cities
        private static ArrayList destinationCities = new ArrayList();
    
        // Adds a destination city
        public static void addCity(City city) {
            destinationCities.add(city);
        }
        
        // Get a city
        public static City getCity(int index){
            return (City)destinationCities.get(index);
        }
        
        // Get the number of destination cities
        public static int numberOfCities(){
            return destinationCities.size();
        }
        
    }
    
    Now to create the class that can model a traveling salesman tour.
    
    Tour.java
    /*
    * Tour.java
    * Stores a candidate tour through all cities
    */
    
    package sa;
    
    import java.util.ArrayList;
    import java.util.Collections;
    
    public class Tour{
    
        // Holds our tour of cities
        private ArrayList tour = new ArrayList();
        // Cache
        private int distance = 0;
        
        // Constructs a blank tour
        public Tour(){
            for (int i = 0; i < TourManager.numberOfCities(); i++) {
                tour.add(null);
            }
        }
        
        // Constructs a tour from another tour
        public Tour(ArrayList tour){
            this.tour = (ArrayList) tour.clone();
        }
        
        // Returns tour information
        public ArrayList getTour(){
            return tour;
        }
    
        // Creates a random individual
        public void generateIndividual() {
            // Loop through all our destination cities and add them to our tour
            for (int cityIndex = 0; cityIndex < TourManager.numberOfCities(); cityIndex++) {
              setCity(cityIndex, TourManager.getCity(cityIndex));
            }
            // Randomly reorder the tour
            Collections.shuffle(tour);
        }
    
        // Gets a city from the tour
        public City getCity(int tourPosition) {
            return (City)tour.get(tourPosition);
        }
    
        // Sets a city in a certain position within a tour
        public void setCity(int tourPosition, City city) {
            tour.set(tourPosition, city);
            // If the tours been altered we need to reset the fitness and distance
            distance = 0;
        }
        
        // Gets the total distance of the tour
        public int getDistance(){
            if (distance == 0) {
                int tourDistance = 0;
                // Loop through our tour's cities
                for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                    // Get city we're traveling from
                    City fromCity = getCity(cityIndex);
                    // City we're traveling to
                    City destinationCity;
                    // Check we're not on our tour's last city, if we are set our 
                    // tour's final destination city to our starting city
                    if(cityIndex+1 < tourSize()){
                        destinationCity = getCity(cityIndex+1);
                    }
                    else{
                        destinationCity = getCity(0);
                    }
                    // Get the distance between the two cities
                    tourDistance += fromCity.distanceTo(destinationCity);
                }
                distance = tourDistance;
            }
            return distance;
        }
    
        // Get number of cities on our tour
        public int tourSize() {
            return tour.size();
        }
        
        @Override
        public String toString() {
            String geneString = "|";
            for (int i = 0; i < tourSize(); i++) {
                geneString += getCity(i)+"|";
            }
            return geneString;
        }
    }
    
    Finally, let's create our simulated annealing algorithm.
    
    SimulatedAnnealing.java
    package sa;
    
    public class SimulatedAnnealing {
    
        // Calculate the acceptance probability
        public static double acceptanceProbability(int engery, int newEngery, double temperature) {
            // If the new solution is better, accept it
            if (newEngery < engery) {
                return 1.0;
            }
            // If the new solution is worse, calculate an acceptance probability
            return Math.exp((engery - newEngery) / temperature);
        }
    
        public static void main(String[] args) {
            // Create and add our cities
            City city = new City(60, 200);
            TourManager.addCity(city);
            City city2 = new City(180, 200);
            TourManager.addCity(city2);
            City city3 = new City(80, 180);
            TourManager.addCity(city3);
            City city4 = new City(140, 180);
            TourManager.addCity(city4);
            City city5 = new City(20, 160);
            TourManager.addCity(city5);
            City city6 = new City(100, 160);
            TourManager.addCity(city6);
            City city7 = new City(200, 160);
            TourManager.addCity(city7);
            City city8 = new City(140, 140);
            TourManager.addCity(city8);
            City city9 = new City(40, 120);
            TourManager.addCity(city9);
            City city10 = new City(100, 120);
            TourManager.addCity(city10);
            City city11 = new City(180, 100);
            TourManager.addCity(city11);
            City city12 = new City(60, 80);
            TourManager.addCity(city12);
            City city13 = new City(120, 80);
            TourManager.addCity(city13);
            City city14 = new City(180, 60);
            TourManager.addCity(city14);
            City city15 = new City(20, 40);
            TourManager.addCity(city15);
            City city16 = new City(100, 40);
            TourManager.addCity(city16);
            City city17 = new City(200, 40);
            TourManager.addCity(city17);
            City city18 = new City(20, 20);
            TourManager.addCity(city18);
            City city19 = new City(60, 20);
            TourManager.addCity(city19);
            City city20 = new City(160, 20);
            TourManager.addCity(city20);
    
            // Set initial temp
            double temp = 10000;
    
            // Cooling rate
            double coolingRate = 0.003;
    
            // Initialize intial solution
            Tour currentSolution = new Tour();
            currentSolution.generateIndividual();
            
            System.out.println("Initial solution distance: " + currentSolution.getDistance());
    
            // Set as current best
            Tour best = new Tour(currentSolution.getTour());
            
            // Loop until system has cooled
            while (temp > 1) {
                // Create new neighbour tour
                Tour newSolution = new Tour(currentSolution.getTour());
    
                // Get a random positions in the tour
                int tourPos1 = (int) (newSolution.tourSize() * Math.random());
                int tourPos2 = (int) (newSolution.tourSize() * Math.random());
    
                // Get the cities at selected positions in the tour
                City citySwap1 = newSolution.getCity(tourPos1);
                City citySwap2 = newSolution.getCity(tourPos2);
    
                // Swap them
                newSolution.setCity(tourPos2, citySwap1);
                newSolution.setCity(tourPos1, citySwap2);
                
                // Get energy of solutions
                int currentEngery = currentSolution.getDistance();
                int neighbourEngery = newSolution.getDistance();
    
                // Decide if we should accept the neighbour
                if (acceptanceProbability(currentEngery, neighbourEngery, temp) > Math.random()) {
                    currentSolution = new Tour(newSolution.getTour());
                }
    
                // Keep track of the best solution found
                if (currentSolution.getDistance() < best.getDistance()) {
                    best = new Tour(currentSolution.getTour());
                }
                
                // Cool system
                temp *= 1-coolingRate;
            }
    
            System.out.println("Final solution distance: " + best.getDistance());
            System.out.println("Tour: " + best);
        }
    }
