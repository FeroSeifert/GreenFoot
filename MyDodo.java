import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
        System.out.println("Mimi staat op: " + getX() + ", " + getY());

    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if (borderAhead() || fenceAhead()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               
        while ( nrStepsTaken < distance ) { 
            move();                        
            nrStepsTaken++;                 
            System.out.println("moved " + nrStepsTaken);
        }
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     */

    public void walkToWorldEdge( ){
        while(!borderAhead() ){
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if (onEgg()) {
            return false;
        }else{
            return true;
       }
    }  
    
    /**
     * Turns dodo 180 degrees with two right turns.
     * 
     * <p>Initial: Dodo is somewhere.
     * <p>Final: Dodo is in the same cell, facing the opposite direction.
     */
    
    public void turn180() {
        turnRight();
        turnRight();
    }
    
    /**
     * Climbs over a fence
     */
    
    public void climbOverFence() {
        turnLeft(); // Step 1 turn left
        move(); // Step 2 move forward
        turnRight(); // Step 3 turn right
        move(); // Step 4 move forward
        move(); // Step 5 another forward
        turnRight(); // Step 6 turn right
        move(); // Step 7 move forward
        turnLeft(); // Step 8 turn left to end up in the same direction again
    }
    
    /**
     * Checks if theres grain and goes back to original position
     */
    
    public boolean grainAhead() {
        if (!canMove()) {
             return false;
        }
        move(); // Step 1: move forward to check the cell ahead
        
        boolean grainFound = onGrain(); // Step 2: check if grain is present
        
        turn180(); // Step 3: turn around
        move(); // Step 4: move back to original position
        turn180(); // Step 5: turn around again to face original direction
        
        return grainFound; // Step 6: return result
    }
    
    /**
     * Makes dodo go to an egg
     */
    
    public boolean gotoEgg() {
        while (!onEgg()) {
            if (!canMove()) {
                showError("I'm stuck!");
                return false;
            }
            move();
        }
        return true;
    }
    
    /**
     * Goes back to the start of the row and faces back
     */
    
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }
    
    /**
     * Walks to the worlds edge while also climbing over fences that are in the way
     */
    
    public void walkToWorldEdgeClimbingOverFences() {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }
    
    /**
     * Picks up all the all the grains to world edge and prints the coordinates in console
     */
    
    public void pickUpGrainsAndPrintCoordinates() {
        if (onGrain()) {
            System.out.println("Grain at: (" + getX() + ", " + getY() + ")");
            pickUpGrain();
        }
        
        while (!borderAhead()) {
            move();
            if (onGrain()) {
                System.out.println("Grain at: (" + getX() + ", " + getY() + ")");
                pickUpGrain();
            }
        }
    }
    
    /**
     * Fill all empty nests with an egg till world edge
     */
    
    public void fillEmptyNestToWorldEdge() {
        while (!borderAhead()) {
             if (onNest() && canLayEgg()) {
                layEgg();
            }
            
            move();
        }
        
        if (onNest() && canLayEgg()) {
            layEgg();
        }
    }
    
    /**
     * Walks to nest in a straight line climbing over fences
     */
    
    public void walkToNestClimbingOverFences() {
        while (!onNest()) {
            if (borderAhead()) {
                showError("No nest found before world edge!");
                return;
            }

            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }

        if (canLayEgg()) {
            layEgg();
        } else {
            showError("There is already an egg in this nest");
        }
    }
    
    /**
     * walks around a fenced area 
     */
    
    public void walkAroundFencedArea() {
        while (!onEgg()) {
            turnRight();
                
            if (canMove()) {
                move();
                continue;
            }
                
            turnLeft();   
        
            if (canMove()) {
                move();
                continue;
            }
                    
            turnLeft();
        }
    }    
    
    /**
     * 
     */
    
    public boolean eggAhead() {
        if (!canMove()) {
            return false;
        }

        move();
        boolean found = onEgg();
        turn180();
        move();
        turn180();

        return found;
    }
    
    /**
     * 
     */
    
    public boolean nestAhead() {
        if (!canMove()) {
        return false;
        }

        move();
        boolean found = onNest();
        turn180();
        move();
        turn180();

        return found;
    }
    
    /**
     * 
     */
    
    public void followEggTrail() {
        if (eggAhead()) {
            move();
            return;
        }

        turnRight();

        if (eggAhead()) {
            move();
            return;
        }

        turnLeft();
        turnLeft();

        if (eggAhead()) {
            move();
            return;
        }

        showError("Geen volgend ei gevonden");
    }

    /**
     * 
     */
    
    public void eggTrailToNest() {
        while (!onNest()) {
            if (onEgg()) {
                hatchEgg();
            }
            
            if (nestAhead()) {
            move();
            return;
            }
            
            followEggTrail();
        }
    }  
    
    /**
     * 
     */
    
    public boolean wallOnRight() {
        turnRight();
        boolean wall = fenceAhead() || borderAhead();
        turnLeft();
        return wall;
    }

    /**
     * 
     */
    
    public boolean wallOnLeft() {
        turnLeft();
        boolean wall = fenceAhead() || borderAhead();
        turnRight();
        return wall;
    }

    /**
     * 
     */
    
    public boolean canMoveRight() {
        turnRight();
        boolean can = canMove();
        turnLeft();
        return can;
    }

    /**
     * 
     */
    
    public boolean canMoveLeft() {
        turnLeft();
        boolean can = canMove();
        turnRight();
        return can;
    }

    /**
     * 
     */
    
    public void walkMazeToNest() {
        while (!onNest()) {
            if (!wallOnRight() && canMoveRight()) {
                turnRight();
                move();
            }
            else if (canMove()) {
                move();
            }
            else if (canMoveLeft()) {
                turnLeft();
                move();
            }
                else {
                turn180();
            }
        }

        if (canLayEgg()) {
            layEgg();
        } else {
            showError("There is already an egg in this nest");
        }
    }
    
    /**
     * 
     */
    
    public void testSwap() {
        int waardeBlauweEi = 2;
        int waardeGoudenEi = 10;

        int tijdelijkeWaardeEi;

        tijdelijkeWaardeEi = waardeBlauweEi;
        waardeBlauweEi = waardeGoudenEi;
        waardeGoudenEi = tijdelijkeWaardeEi;

        System.out.println("Blauw: " + waardeBlauweEi);
        System.out.println("Goud: " + waardeGoudenEi);
        System.out.println("Tijdelijk: " + tijdelijkeWaardeEi);
    }

    /**
     * 
     */
    
    public boolean locationReached(int x, int y) {
        return getX() == x && getY() == y;
    }
    
    public void faceEast() {
        while (getDirection() != EAST) {
            turnRight();
        }
    }

    public void faceWest() {
        while (getDirection() != WEST) {
            turnRight();
        }
    }

    public void faceNorth() {
        while (getDirection() != NORTH) {
            turnRight();
        }
    }

    public void faceSouth() {
        while (getDirection() != SOUTH) {
        turnRight();
        }
    }
    
    public void goToLocation(int targetX, int targetY) {
        while (getX() != targetX) {
            if (getX() < targetX) {
                faceEast();
            } else {
                faceWest();
            }

            if (!canMove()) {
                showError("Obstacle while moving in X direction");
                return;
            }

            move();
        }
        
        while (getY() != targetY) {
            if (getY() < targetY) {
                faceSouth();
            } else {
                faceNorth();
            }

            if (!canMove()) {
                showError("Obstacle while moving in Y direction");
                return;
            }

            move();
        
        }
    }
    
    public boolean validCoordinates(int x, int y) {
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

        boolean valid = (x >= 0 && x < worldWidth) &&
                        (y >= 0 && y < worldHeight);

        if (!valid) {
            showError("Invalid coordinates");
        }

        return valid;
    }
    
    public int countEggsInRow() {
        int count = 0;

        // Tel ei op startpositie
        if (onEgg()) {
            count++;
        }

        // Loop naar de rand van de wereld
        while (!borderAhead()) {
            move();

            if (onEgg()) {
                count++;
            }
        }

        // Ga terug naar startpositie
        goBackToStartOfRowAndFaceBack();

        return count;
    }
    
    public int countAllEggs() {
        int total = 0;

        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

        // Beginpositie opslaan
        int startX = getX();
        int startY = getY();

        // Loop door alle rijen (y = 0 t/m height-1)
        for (int row = 0; row < worldHeight; row++) {

            // Ga naar het begin van de rij (kolom 0)
            goToLocation(0, row);

            // Zorg dat Dodo naar rechts kijkt
            faceEast();

            // Tel eieren in deze rij
            int eggsInRow = countEggsInRow();

            // Voeg toe aan totaal
            total += eggsInRow;

            System.out.println("Rij " + row + ": " + eggsInRow + " eieren");
        }

        // Ga terug naar startpositie
        goToLocation(startX, startY);
        
        faceEast();

        System.out.println("Totaal aantal eieren: " + total);
        return total;
    }
    
    public int findRowWithMostEggs() {
        int worldHeight = getWorld().getHeight();

        int startX = getX();
        int startY = getY();

        int maxEggs = -1;
        int rowWithMostEggs = -1;

        for (int row = 0; row < worldHeight; row++) {

            // Ga naar begin van de rij
            goToLocation(0, row);
            faceEast();

            // Tel eieren in deze rij
            int eggs = countEggsInRow();

            System.out.println("Rij " + (row + 1) + " heeft " + eggs + " eieren");

            // Check of dit de nieuwe max rij is
            if (eggs > maxEggs) {
                maxEggs = eggs;
                rowWithMostEggs = row;
            }
        }

        // Terug naar startpositie
        goToLocation(startX, startY);

        System.out.println("Rij met de meeste eieren: " + (rowWithMostEggs + 1) + " (aantal: " + maxEggs + ")");

        return rowWithMostEggs + 1;
    }

    public void buildEggMonumentTriangle() {
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

        int startX = getX();
        int startY = getY();

        int rowIndex = 0;
        
        while (startY + rowIndex < worldHeight) {

            int eggsToLay = rowIndex + 1;
            int maxEggsInRow = worldWidth - startX;

            if (eggsToLay > maxEggsInRow) {
                eggsToLay = maxEggsInRow; // afkappen als we tegen de rand komen
            }

            // Leg eieren naast elkaar in deze rij
            for (int colOffset = 0; colOffset < eggsToLay; colOffset++) {
                int x = startX + colOffset;
                int y = startY + rowIndex;

                goToLocation(x, y);

                if (canLayEgg()) {
                    layEgg();
                }
            }

            rowIndex++;
        }

        // Terug naar startpositie
        goToLocation(startX, startY);
    }
}