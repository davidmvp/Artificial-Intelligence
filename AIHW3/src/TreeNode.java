import java.util.ArrayList;


/**
 * This class is for TreeNode.
 * @author davidmvp23
 *
 */
public class TreeNode implements Cloneable{
    
    
   
    /**
     * all of its children
     */
    ArrayList<TreeNode> children;
    /**
     * all of its children's label.
     */
    ArrayList<String> childrenLabel;
    /**
     * number of attribute.
     */
    private int attribute;
    /**
     * label 
     */
    private String label;
    /**
     * if its a leaf or not.
     */
    private boolean leaf = false;
    /**
     * decision value, true of false
     */
    private boolean value = false;
    /**
     * its only parent.
     */
    private TreeNode parent;
    /**
     * fitness value(accuracy)
     */
    private double fitness;
    /**
     * Constructor
     */
    public TreeNode() {
       
         this.children = new ArrayList<TreeNode>();
         
         this.attribute = -100;
         this.parent = null;
         this.fitness = 0;
         this.childrenLabel = new ArrayList<String>();
        
    }
    /**
     * Set fitness Value.
     * @param f
     */
    public void setFitness(double f){
        fitness = f;
    }
    
    public void deleteAllChildren() {
        this.children = new ArrayList<TreeNode>();
    }
    /**
     * Set child label.
     * @param s
     */
    public void setChildLabel(String s) {
        childrenLabel.add(s);
        
        
    }
    
    /**
     * return the label
     * @return
     */
    
    public ArrayList<String> returnLabel() {
        return childrenLabel;
    }
    /**
     * get the fitness of this node.
     * @return
     */
    public double getFitness() {
        return fitness;
    }
    /**
     * clone this node.
     */
   public TreeNode clone() {
       
       // clone every attribute, label, parent,child, childlabel and fitness.
       TreeNode s = new TreeNode();
       s.setAttribute(attribute);
       s.setLabel(label);
       if (leaf == true) {
           s.setLeaf();
       }
       s.setValue(value);
       s.setParent(parent);
       for (int i = 0 ; i < children.size();i++) {
      s.setChild(children.get(i));
       s.setChildLabel(childrenLabel.get(i));
       }
       s.setFitness(fitness);
    return s;
       
   }
   
   /**
    * remove the child.
    * @param v
    * @return
    */
    public String removeChild(int v) {
       // remove the child, at the same time return its label.
       children.remove(v);
       String s = childrenLabel.get(v);
       childrenLabel.remove(v);
      
       return s;
    }
    /**
     * get all the children.
     * @return
     */
    public ArrayList<TreeNode> getChildren() {
        return this.children;
    }
    /**
     * set a child.
     * @param ch
     */
    public void setChild(TreeNode ch) {
        children.add(ch);
    }
    
    /**
     * set its parent.
     * @param p
     */
    public void setParent(TreeNode p) {
        parent = p;
    }
    /**
     * get its parent.
     * @return
     */
    public TreeNode getParent(){
        return parent;
    }
    /**
     * set leaft.
     */
    public void setLeaf(){
        leaf = true;
    }
    /**
     * see if its a leaf
     * @return
     */
    public boolean getLeaf() {
        return leaf;
    }
    /**
     * set its value.
     * @param b
     */
    public void setValue(boolean b) {
        value = b;
    }
    /**
     * return its value.
     * @return
     */
    public boolean returnValue() {
        return value;
    }
    
  
    public void setLabel(String s) {
        label = s;
    }
    
    public String getLabel() {
        return label;
    }
    
    
    public int getAttribute() {
        return attribute;
    }
    
    public void setAttribute(int attribu) {
        attribute = attribu;
    }
    public void switchLabel(ArrayList<String> at) {
        this.childrenLabel = new ArrayList<String>();
        this.childrenLabel = at;
        
    }
    public void switchAttribute(int n) {
        this.attribute = n;
    }
    
    /**
     * This method is mainly for me when testing.
     */
    public String toString() {
        if (leaf == true) {
            if (value == true) {
                return label + " yes";
            }
            return label + " no" ;
        }
       
        
        else {
        return label + " " + this.getAttribute() ;
        }

    }
    
    
  
}
