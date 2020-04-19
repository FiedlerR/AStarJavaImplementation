import java.util.List;


/**
 * Created by Rafael on 10.01.2018.
 */
public class Main {
    static int sizeX = 200;
    static int sizeY = 20;
    public static boolean[][] walkableMask;
    static int destX = 100;
    static int destY = 17;
    static int startX = 1;
    static int startY = 1;

    public static void main(String[] args) {
        //generate map with random not walkable fields
        init(30);

        //draw map without path
        drawMap();

        //create path to destination
        List path = new AStarV2().findPath(startX,startY,destX,destY,false);

        //draw map with path
        drawMapWithPath(path);
    }

    static void init(int probabilityForNotWalkable){
        walkableMask = new boolean[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int k = 0; k < sizeY; k++) {
                if((int)(Math.random()*100) <= probabilityForNotWalkable){
                    walkableMask[i][k] = false;
                }else{
                    walkableMask[i][k] = true;
                }
            }
        }
        walkableMask[startX][startY] = true;
        walkableMask[destX][destY] = true;
    }

    static void drawMap(){
        System.out.println("|----------------------------------------------------------|");
        for (int i = 0; i < sizeY; i++) {
            for (int k = 0; k < sizeX; k++) {
                if(i == startY && k == startX){
                    System.out.print("S");
                } else if ( !walkableMask[k][i]) {
                    System.out.print("X");
                }else if(k == destX && i == destY){
                    System.out.print("D");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("|----------------------------------------------------------|");
    }

    static void drawMapWithPath(List path){
        System.out.println("|----------------------------------------------------------|");
        System.out.println("Start: S");
        System.out.println("Destination: D");
        System.out.println("Not walkable:  X");
        System.out.println("Path:  P");
        System.out.println("|----------------------------------------------------------|");
            for (int i = 0; i < sizeY; i++) {
                for (int k = 0; k < sizeX; k++) {
                    boolean isPath = false;
                    for (int j = 0; j < path.size(); j++) {
                        if (((Node) path.get(j)).x == k && ((Node) path.get(j)).y == i && !(k == destX && i == destY) && !(i == startY && k == startX)) {
                            System.out.print("P");
                            isPath = true;
                        }
                    }
                    if (!isPath) {
                        if (i == startY && k == startX) {
                            System.out.print("S");
                        } else if (!walkableMask[k][i]) {
                            System.out.print("X");
                        } else if (k == destX && i == destY) {
                            System.out.print("D");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("|----------------------------------------------------------|");

    }
}
