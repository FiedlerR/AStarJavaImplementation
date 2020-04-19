/**
 * Created by Rafael Fiedler on 30.09.2017.
 */
public class Node {
    public int x = 0;
    public int y= 0;
    int cost = 0;
    Node vorganger;

    public Node(int x, int y){
       this.x = x;
       this.y= y;
       this.cost = 10;
    }

    public Node(int x, int y,Node vorganger,int cost){
        this.x = x;
        this.y= y;
        this.vorganger = vorganger;
        this.cost = cost;
    }

    @Override
    public  boolean equals (Object obj){
        if(obj instanceof Node) {
            if (((Node) obj).x == x && ((Node) obj).y == y) {
                return true;
            }
        }
        return false;
    }

    public String toString(){
        return "("+x+"/"+y+")";
    }
}
