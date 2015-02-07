import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Super Graph.
 * @author davidmvp23
 *
 */
public class SuperGraph {
    
    private Vertex start;
    private Vertex end;
    private static  Vertex[][] graph;
    private Comparator<Vertex> c;
    private PriorityQueue<Vertex> q;
    private boolean[][] s; 
    private int width;
    private int length;
   
    
    public SuperGraph(String widt, String lengt) {
        
        int w = Integer.parseInt(widt);
        int l = Integer.parseInt(lengt);
        width = w;
        length = l;
        graph = new Vertex[w][l];
      }
   
   
    public void setStart(Vertex v){
        start = v;
    }
    
    public void setEnd(Vertex v){
        end = v;
    }
    
    public void addVertex(Vertex v) {
        
        graph[v.width()][v.length()] = v;
        
    }
    
    public Vertex findVertex(int w, int h){
        return graph[w][h];
        
    }
 
    public void addNeighbors(Vertex v, Vertex v1) {
        graph[v.width()][v.length()].addNeighbors(v1);
        graph[v1.width()][v1.length()].addNeighbors(v);
    }
    
    
    
   
    public void DepthFirstSearchHelper() {
        System.out.println("Below is the result for Depth First Search.");
        int cost = 0;
         s = new boolean[width][length];
         ArrayList<Vertex> temp = new ArrayList<Vertex>();
        for (int i = 0;i<length;i++){
            for (int j=0;j<width;j++){
                s[j][i] = false;
            }
        }
        s[start.width()][start.length()] = true;
        final long startTime = System.nanoTime();
        boolean t = DepthFirst();
        final long endTime = System.nanoTime();
        Vertex a = end;
        
        while (a != null && a!= start) {
            
            temp.add(a);
            a = a.returnParent();
        }
        if (t == false) {
            System.out.println("Fail, there is no path!");
            System.out.println("Cost is infinity");
            }
        else {
            System.out.println("Cost is " + temp.size() + ".");
            System.out.print("Start " + "(" + (start.width()+1) + ", " + (1+start.length()) + ") ");
            for (int i = temp.size()-1;i>0;i--) {
                System.out.print(" -> " + "(" + (1+temp.get(i).width()) + ", " + (temp.get(i).length()+1) + ") ");
        
            }
            System.out.println(" ->Goal " + "(" + (1+end.width()) + ", " + (1+end.length()) +") ");
            System.out.println("Execution time is " + (endTime - startTime) + " nanoseconds.");
            }
    }
    
    public boolean DepthFirst() {
        int count = 0;
        Stack<Vertex> st = new Stack<Vertex>();
        ArrayList<Vertex> neighb = start.Neighbors();
        for (int i = 0; i < neighb.size(); i++) {
            st.push(neighb.get(i));
        }
       
        while (st.empty() == false) {
            Vertex w = st.pop();
            count++;
           
            ArrayList<Vertex> n = w.Neighbors();
            for (int i = 0 ; i < n.size(); i++) {
                Vertex u = n.get(i);
                if (u==end) {
                    u.setParent(w);
                    System.out.println("Number of nodes explored " + count);
                    return true;
                }
              
                if ( s[u.width()][u.length()] == false) {
                    s[w.width()][w.length()] = true;
                    count++;
                    st.push(u);
                    u.setParent(w);
                    
                }
                
                
                }
            }
      
        System.out.println("Number of nodes explored " + count);
        return false;
    }
    
    public void breadthFirstHelper() {
        System.out.println();
        System.out.println();
        System.out.println();
        s = new boolean[width][length];
        for (int i = 0;i<length;i++){
           for (int j=0;j<width;j++){
               s[j][i] = false;
           }
       }
        System.out.println("Below is the result for Breadth First Search.");
        final long startTime = System.nanoTime();
       boolean t = breadthFirst();

       final long endTime = System.nanoTime();
       Vertex v = end;
       ArrayList<Vertex> a = new ArrayList<Vertex>();
       
       while (v!=null && v!= start) {
           a.add(v);
           v = v.returnParent();
       }
       if (t == false) {
           System.out.println("Fail, there is no path!");
           System.out.println("Cost is infinity");
           }
       else {
           System.out.println("Cost is " + a.size() + ".");
           System.out.print("Start " + "(" + (start.width()+1) + ", " + (start.length()+1) + ") ");
           for (int i = a.size()-1;i>0;i--) {
               System.out.print(" -> " + "(" + (a.get(i).width()+1) + ", " + (a.get(i).length()+1)+ ") ");
           
           }
           System.out.println(" - >Goal " + "(" + (end.width()+1 )+ ", " + (end.length()+1) +") ");
           System.out.println("Execution time is " + (endTime - startTime) + " nanoseconds.");
           }
    }
    public boolean breadthFirst() {
        int count = 0;
      
            Queue<Vertex> q = new LinkedList<Vertex>();
            s[start.width()][start.length()] = true;
            q.add(start);
            while (q.isEmpty() == false) {
                Vertex t = q.remove();
                if (t == end) {
                    System.out.println("Number of nodes explored " + count);
                    return true;
                }
                ArrayList<Vertex> arr = new ArrayList<Vertex>();
                arr = t.Neighbors();
                for (int i = 0; i < arr.size(); i++) {
                    Vertex u = arr.get(i);
               
                    if (s[u.width()][u.length()] == false) {
                        count++;     
                        s[u.width()][u.length()] = true;
                        u.setParent(t);
                        q.add(u);
                    }
                }
            }
            System.out.println("Number of nodes explored " + count);
            return false;
    }
public void AstarHelper() {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("Below is the result for A* Search.");
    c = new costFcomparator();
    q = new PriorityQueue<Vertex>(100000,c);
    s = new boolean[width][length];
    for (int i = 0;i<length;i++){
       for (int j=0;j<width;j++){
           s[j][i] = false;
       }
    }
    final long startTime = System.nanoTime();
    boolean t = AStar();
    final long endTime = System.nanoTime();
   
    Vertex v = end;
    ArrayList<Vertex> a = new ArrayList<Vertex>();
    
    while (v!=null && v!= start) {
        a.add(v);
        v = v.returnParent();
    }
    
    if (t == false) {
        System.out.println("Fail, there is no path!");
        System.out.println("Cost is infinity");
        }
    else    {
        System.out.println("Cost is " + a.size() + ".");
        System.out.print("Start " + "(" + (start.width()+1) + ", " + (start.length()+1) + ") ");
        System.out.println(a.size());
    for (int i = a.size()-1;i>0;i--) {
        System.out.print(" -> " + "(" + (a.get(i).width()+1) + ", " + (a.get(i).length()+1)+ ") ");
        }
    System.out.println(" - >Goal " + "(" + (end.width()+1 )+ ", " + (end.length()+1) +") ");
    System.out.println("Execution time is " + (endTime - startTime) + " nanoseconds.");
    }
 
}

    public boolean AStar() {
      
        
       
       int counte = 0;
        q.add(start);
        while (q.isEmpty() == false) {
         
            Vertex curr = q.peek();
            
            if (curr == end) {
                
                System.out.println("Number of nodes explored " + counte);
                return true;
            }   
            q.remove();
           
            
            ArrayList<Vertex> arr = new ArrayList<Vertex>();
            arr = curr.Neighbors();
           
            
            for (int i = 0;i<arr.size();i++) {
                
                Vertex temp = arr.get(i);
              
               
                double newgCost = curr.getgCost()+1;
                if (s[temp.width()][temp.length()] == false) {
                    counte++;
                    temp.setgCost(newgCost);
                    int w = Math.abs(temp.width() - end.width()) ;
                    int h = Math.abs(temp.length()-end.length());
                    double hCost = Math.sqrt(w*w + h*h);
                    temp.setfCost(newgCost+hCost);
                    q.add(temp);
                    s[temp.width()][temp.length()] = true;
                    temp.setParent(curr);
                }
                else if ( s[temp.width()][temp.length()] == true && newgCost < temp.getgCost()) {
                   
                    counte++;
                    temp.setParent(curr);  
                    temp.setgCost(newgCost);
                   
                    int w = Math.abs(temp.width() - end.width()) ;
                    int h = Math.abs(temp.length()-end.length());
                    double hCost = Math.sqrt(w*w + h*h);
                    temp.setfCost(newgCost+hCost);
                    
                }
               
            }
        }
        System.out.println("Number of nodes explored " + counte);
        return false;
}

    
}