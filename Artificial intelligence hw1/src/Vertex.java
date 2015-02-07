import java.util.ArrayList;


public class Vertex {
    /**
     * all the neighbors.
     */
    private double fCost;
    private double gCost;
    private int width;
    private int height;
    private String symbol;
    private ArrayList<Vertex> neighbors;
  
    /**
     * vertex pa.
     */
    private Vertex pa;
    /**
     * constructor.
     */
    public Vertex() {
        
    }
    public Vertex(int width, int height) {
        this.width = width;
        this.height = height;
        neighbors = new ArrayList<Vertex>();
        gCost = 0;
        fCost = 0;
        
       
    }
   
    public void sym(String stuff) {
       symbol = stuff;
    }
    public String returnSym() {
        return symbol;
    }
    public void addNeighbors(Vertex v1) {
       
       neighbors.add(v1);
       
    }
    
    /**
     * set its parent.
     * @param v pa.
     */
    public int width() {
        return width;
    }
    public int length() {
        return height;
    }
    public void setParent(Vertex v) {
        this.pa = v;
    }
    
   
    public Vertex returnParent() {
        return this.pa;
    }
    public ArrayList<Vertex> Neighbors() {
        return neighbors;
    }
    
    public double getfCost() {
      
        return fCost;
    }
    public double getgCost() {
        return gCost;
    }
    public double getHCost() {
        return gCost;
    }
    public void sethCost(double c) {
    }
   
    public void setgCost(double c) {
   
      gCost = c;
      
    }
    public void setfCost ( double c) {
       
        fCost = c;
    }
    public String toString() {
      
       return this.symbol;
    }
    


}