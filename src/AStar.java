import java.util.*;

/**
 * Created by Rafael Fiedler on 30.09.2017.
 */
public class AStar extends Thread{
    Set<Node> open = new HashSet<>(600);
    List<Node> close = new ArrayList<>(600);
    Node start;
    Node end;
    Boolean allowDiagonalPath = false;
    Comparator comparator;

    public AStar(int startX, int startY, int endX, int endY,Boolean allowDiagonalPath){
        List path = new ArrayList(300);
        if(startX == endX && startY == endY){
            path.add(new Node(endX,endY));
        }
        start = new Node(startX, startY);
        end = new Node(endX, endY);
        this.allowDiagonalPath = allowDiagonalPath;
    }

    List findPath(int startX, int startY, int endX, int endY) {
        List path = new ArrayList(300);
        if(startX == endX && startY == endY){
            path.add(new Node(endX,endY));
            return  path;
        }
        path = getPath(startX, startY, endX, endY);
        return path;
    }


    @Override
    public void run(){
        addNode(Main.walkableMask, start);
        comparator = new nodeComparator(end,start);
        close.add(start);
        open.add(start);

        while (!close.contains(end)) {
            Node temp = Collections.min(open, comparator);
            close.add(temp);

            addNode(Main.walkableMask, temp);
            open.remove(temp);
            if (open.size() == 0 || containNode(close, end.x,end.y)) {
                break;
            }
        }
        List<Node> result = new ArrayList<>();
        result.add(close.get(close.size() - 1));
        for (int i = 0; result.get(i).vorganger != null; i++) {
            result.add(result.get(i).vorganger);
        }
    }

    private List getPath(int startX, int startY, int endX, int endY) {
        start = new Node(startX, startY);
        end = new Node(endX, endY);
        addNode(Main.walkableMask, start);
        comparator = new nodeComparator(end,start);
        close.add(start);
        open.add(start);

        while (!close.contains(end)) {
            Node temp = Collections.min(open, comparator);
            close.add(temp);

            addNode(Main.walkableMask, temp);
            open.remove(temp);
            if (open.size() == 0 || containNode(close, end.x,end.y)) {
                break;
            }
        }
        List<Node> result = new ArrayList<>();
        result.add(close.get(close.size() - 1));
        for (int i = 0; result.get(i).vorganger != null; i++) {
            result.add(result.get(i).vorganger);
        }
        return result;
    }

    private static boolean containNode(Collection<Node> list, int x, int y) {
        for (Node node : list) {
            if (node.x == x && node.y == y) {
                return true;
            }
        }
        return false;
    }

    private Node addNode(boolean[][] walkable, Node n) {
        int xRight = n.x + 1;
        int xLeft = n.x - 1;
        int yUnder = n.y-1;
        int yOver = n.y+1;

        if (xRight < walkable.length && walkable[xRight][n.y]) {
            if (!containNode(close, xRight, n.y)) {
                open.add(new Node(xRight, n.y, n,10));
            }
        }

        if (n.x  > 0 && walkable[xLeft][n.y]) {
            if (!containNode(close, xLeft, n.y)) {
                open.add(new Node(xLeft, n.y, n,10));
            }
        }
        if (n.y + 1 < walkable[0].length && walkable[n.x][yOver]) {
            if (!containNode(close, n.x, yOver)) {
                open.add(new Node(n.x, yOver, n, 10));
            }
        }
        if (n.y > 0 && walkable[n.x][yUnder]) {
            if (!containNode(close, n.x, yUnder)) {
                open.add(new Node(n.x, yUnder, n, 10));
            }
        }

        if (allowDiagonalPath) {
            if (n.x > 0 && n.y + 1 < walkable.length && walkable[xLeft][n.y + 1]) {
                if (!containNode(close, xLeft, n.y + 1) && !containNode(open, xLeft, n.y + 1)) {
                    open.add(new Node(xLeft, n.y + 1, n, 14));
                }
            }

            if (xRight < walkable.length && n.y + 1 < walkable.length && walkable[xRight][n.y + 1]) {
                if (!containNode(close, xRight, n.y + 1) && !containNode(open, xRight, n.y + 1)) {
                    open.add(new Node(xRight, n.y + 1, n, 14));
                }
            }

            if (n.y > 0 && n.x > 0 && walkable[xLeft][n.y - 1]) {
                if (!containNode(close, xLeft, n.y - 1) && !containNode(open, xLeft, n.y - 1)) {
                    open.add(new Node(xLeft, n.y - 1, n, 14));
                }
            }

            if (n.y > 0 && xRight < walkable.length && walkable[xRight][n.y - 1]) {
                if (!containNode(close, xRight, n.y - 1) && !containNode(open, xRight, n.y - 1)) {
                    open.add(new Node(xRight, n.y - 1, n, 14));
                }
            }
        }

        return n;
    }

    public static class nodeComparator implements Comparator<Node> {
        Node end;
        Node start;

        public nodeComparator(Node end,Node start) {
            this.end = end;
            this.start = start;
        }

        @Override
        public int compare(Node o1, Node o2) {
            double startd1 = Math.sqrt(Math.abs(o1.x - start.x)*Math.abs(o1.x -start.x)+ Math.abs(o1.y - start.y)* Math.abs(o1.y - start.y));
            double startd2 = Math.sqrt(Math.abs(o2.x - start.x)*Math.abs(o2.x -start.x)+ Math.abs(o2.y - start.y)* Math.abs(o2.y - start.y));
            double d1 = Math.sqrt(Math.abs(o1.x - end.x)*Math.abs(o1.x - end.x)+ Math.abs(o1.y - end.y)* Math.abs(o1.y - end.y));
            double d2 =Math.sqrt(Math.abs(o2.x - end.x)*Math.abs(o2.x - end.x)+ Math.abs(o2.y - end.y)* Math.abs(o2.y - end.y));
            double F1 = d1;
            double F2 = d2;
            return Double.compare(F1, F2);
        }
    }
}