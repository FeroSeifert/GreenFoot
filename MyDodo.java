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
     * helper for eggTrailToNest
     */
    
        public boolean eggInFront() {
            return eggAhead();
    }

    /**
     * helper for eggTrailToNest
     */
    
        public boolean eggToLeft() {
            turnLeft();
            boolean found = eggAhead();
            turnRight();
            return found;
    }

    /**
     * helper for eggTrailToNest
     */
    
        public boolean eggToRight() {
            turnRight();
            boolean found = eggAhead();
            turnLeft();
            return found;
    }

    /**
     * helper for eggTrailToNest
     */
    
        public boolean eggAhead() {
            if (!canMove()) return false;

            move();
            boolean found = onEgg();
            turn180();
            move();
            turn180();

            return found;
    }

    /**
     * Follows an egg trail till theres a nest
     */
    
        public void eggTrailToNest() {

            // Step 1: hatch the egg you're standing on (if any)
            if (onEgg()) {
                hatchEgg();
            }

            // Step 2: follow the trail until nest is reached
            while (!onNest()) {
                // Look for next egg
                if (eggInFront()) {
                    move();
                }
                else if (eggToLeft()) {
                    turnLeft();
                    move();
                }
                else if (eggToRight()) {
                    turnRight();
                    move();
                }
                else {
                    showError("Trail broken — no egg found!");
                    return;
                }

                // Hatch egg after moving
                if (onEgg()) {
                    hatchEgg();
                }
            }

            // Step 3: final action when nest is reached
            if (canLayEgg()) {
                layEgg();
            }
    }
}