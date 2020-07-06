import java.util.List;

/**
 * AStar Java Implementation
 * *) generates random map
 * *) calculates a path from start position and destination position
 * *) print map with calculated path
 */
public class Main {

    public static void main(String[] args) {
        //define map size
        int sizeX = 200;
        int sizeY = 20;
        //destination position
        int destX = 100;
        int destY = 17;
        //start position
        int startX = 1;
        int startY = 1;
    
        //generate map with random not walkable fields
        boolean[][] walkableMask = generateRandomMap(30, sizeX, sizeY, startX,startY,destX,destY);

        //use the walkable mask to visualize which fields are walkable or not
        drawMap(walkableMask,startX,startY,destX,destY);


        //create path to destination
        List path = new AStar().findPath(walkableMask ,startX, startY, destX, destY, false);

        //print map with path
        drawMapWithPath(path, walkableMask, startX, startY, destX, destY);
    }

    /**
     * generates a mask which defines whether a field is walkable or not
     * @param probabilityForNotWalkable - defines the probability that a certain field is not walkable
     * @param sizeX - defines the width of the map
     * @param sizeY-  defines the height of the map
     * @param startX - defines startpoint on x axis
     * @param startY - defines startpoint on y axis
     * @param destX - defines destinationpoint on x axis
     * @param destY - defines destinationpoint on y axis
     * @return - return 2d map with walkable and not walkable fields
     */
    static boolean[][] generateRandomMap(int probabilityForNotWalkable, int sizeX, int sizeY,int startX, int startY, int destX, int destY){
        boolean[][] walkableMask = new boolean[sizeX][sizeY]; // create new empty array
        for (int i = 0; i < sizeX; i++) {
            for (int k = 0; k < sizeY; k++) {
                if((int)(Math.random()*100) <= probabilityForNotWalkable){ //decide if field is walkable or not
                    walkableMask[i][k] = false; // set field not walkable
                }else{
                    walkableMask[i][k] = true; // set field walkable
                }
            }
        }
        walkableMask[startX][startY] = true; // set start field walkable
        walkableMask[destX][destY] = true; // set destination field walkable
        return walkableMask;
    }


    /**
     * print walkablemap in console
     * @param walkableMask - defines which fields are walkable and which not walkable
     * @param startX - defines startpoint on x axis
     * @param startY - defines startpoint on y axis
     * @param destX - defines destinationpoint on x axis
     * @param destY - defines destinationpoint on y axis
     */
    static void drawMap(boolean[][] walkableMask,int startX, int startY,int destX, int destY){

        //define console codes 
        String resetCode = "\033[0m";  // code to reset text color
        String redCode = "\033[0;31m"; // code to color text red

        System.out.println("|----------------------------------------------------------|");
        for (int i = 0; i < walkableMask[0].length; i++) {
            for (int k = 0; k < walkableMask.length; k++) {
                if(i == startY && k == startX){ // check if field is the start field
                    System.out.print(redCode + "S");
                } else if ( !walkableMask[k][i]) { // check if field is not walkable
                    System.out.print(resetCode + "X");
                }else if(k == destX && i == destY){ // check if field is the destination field
                    System.out.print(redCode + "D"); 
                }else{
                    System.out.print(resetCode + " "); // field is walkable
                }
            }
            System.out.println();
        }
        System.out.println("|----------------------------------------------------------|");
    }



    /**
     * print walkablemap with path in console
     * @param path - path which should shown on the map
     * @param walkableMask - defines which fields are walkable and which not walkable
     * @param startX - defines startpoint on x axis
     * @param startY - defines startpoint on y axis
     * @param destX - defines destinationpoint on x axis
     * @param destY - defines destinationpoint on y axis
     */
    static void drawMapWithPath(List path,boolean [][] walkableMask, int startX, int startY, int destX, int destY){

        //define console codes 
        String resetCode = "\033[0m";  // code to reset console text color
        String redCode = "\033[0;31m"; // code to color console text red
        String greenCode = "\033[0;32m"; // code to color console text green
        
        //print map description
        System.out.println(resetCode + "|----------------------------------------------------------|");
        System.out.println(redCode + "Start: S");
        System.out.println(redCode + "Destination: D");
        System.out.println(resetCode + "Not walkable:  X");
        System.out.println(greenCode + "Path:  P");
        System.out.println(resetCode + "|----------------------------------------------------------|");

        //print map
            for (int i = 0; i < walkableMask[0].length; i++) {
                for (int k = 0; k < walkableMask.length; k++) {
                    boolean isPath = false;
                    for (int j = 0; j < path.size(); j++) {
                        //check if position is a path field
                        if (((Node) path.get(j)).x == k && ((Node) path.get(j)).y == i && !(k == destX && i == destY) && !(i == startY && k == startX)) {
                            System.out.print(greenCode + "P");
                            isPath = true;
                        }
                    }
                    if (!isPath) {
                        if (i == startY && k == startX) { // check if field is the start field
                            System.out.print(redCode + "S");
                        } else if (!walkableMask[k][i]) { // check if field is not walkable
                            System.out.print(resetCode + "X");
                        } else if (k == destX && i == destY) { // check if field is the destination field
                            System.out.print(redCode + "D");
                        } else {
                            System.out.print(resetCode + " "); // field is walkable
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("|----------------------------------------------------------|");

    }
}
