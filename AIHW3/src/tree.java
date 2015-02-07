import java.io.*;

import java.util.*;
/**
 * Decision learing tree and GA
 * @author davidmvp23
 *
 */
public class tree 

{   
    
    
    /**
     * Root of the tree.
     */
    TreeNode root = new TreeNode();
    /**
     * 
     */
    int positiveExamples = 0;
    int negativeExamples = 0;
    /**
     * number of attributes.
     */
    int numAttributes;
    /**
     * number of examples.
     */
    int numExamples;
    /**
     * randomly choose what is correct.
     */
    String correct = "";
    /**
     * all the examples, converted to booleans.
     */
    boolean[] example = new boolean[10000];
    /**
     * all the attributes.
     */
    String[][] attributes = new String [10000][10000];
    /**
     * examples.
     */
    LinkedList<String> examples = new LinkedList<String>();
    LinkedList<Integer> l = new LinkedList<Integer>();
    ArrayList<Integer> left = new ArrayList<Integer>();
    ArrayList<TreeNode> allNode = new ArrayList<TreeNode>();
    
/**
 * Decision Tree.
 * @throws Exception 
 */
public void Decision_Tree() throws Exception {
    TreeNode s = new TreeNode();
    long start = System.currentTimeMillis();
   
    s = Decision_Tree_Learning(examples,attributes,examples);
    
    long end =  System.currentTimeMillis();;
    System.out.println("Time is takes " + (end - start) );
    System.out.println("example size " + examples.size());
   
    this.stats(s); 
  
}
    public void clear() {
        examples = new LinkedList<String>();
        for (int i  = 0 ; i < 10000 ; i++) {
            example[i] = null != null;
            for (int j = 0 ; j < 10000; j++) {
                attributes[i][j] = null;
            }
        }
      
  
    }
/**
 * Genetic algorithm
 * @throws Exception 
 */

public void Genetic_algorithm(int po,boolean s) throws Exception {
    long avgtime = 0;
    double avgFint = 0;
   for (int i = 0 ; i < 10 ; i++) {
  
   TreeNode node = new TreeNode();
    System.out.println("example size " + examples.size());
    ArrayList<TreeNode> q = new ArrayList<TreeNode>();
    //First we need to create a population.
    q =   this.CreatePopulation(po);
    
    System.out.println("example size " + examples.size());
    for (int j = 0; j< q.size() ; j++) {
        q.get(i).setFitness(this.fitness(q.get(i)));
        
    }
    long start = System.currentTimeMillis();
    node = this.GA(q,s);
    long end =  System.currentTimeMillis();;
  avgFint = avgFint + node.getFitness();
  avgtime = avgtime + (end - start);
}
   System.out.println("Average time " + avgtime/10 );
   System.out.println("average best fitness " + avgFint/10);
   
}

  
/**
 * return the same Attribute of the same column.
 * @param column
 * @param att
 * @return
 */
public LinkedList<String> sameAttribute(int column, String[][] att) {
    LinkedList<String> temp = new LinkedList<String>();
    
    for (int i = 0; i< numExamples; i++) {
    
        temp.add(att[i][column]);
    }
   
    return temp;
}

/**
 * Read data from an input .txt file.
 * @param filename
 * @throws Exception
 */
public void readData(String filename) throws Exception {
   
    boolean g = true;
    String str = "";
    
    int counter = 0;
    FileInputStream in = null;
    File inputFile = new File(filename);
    in = new FileInputStream(inputFile);
    BufferedReader bin = new BufferedReader(new InputStreamReader(in) );

    String input;

    while(true) {
        
        int count = 0;
        input = bin.readLine();
      
        if (input == null) {
            numExamples = examples.size();
            int e = 1;
         
            while (attributes[1][e] != null) {
               
                e++;
              
            }
            numAttributes = e-1;
            correct = examples.get(0);
            // Assume the first example is correct and change all the strings to a boolean.
            changePosiOrNega(examples);
           
            for (int i = 0 ; i < numAttributes+1 ; i ++){
                l.add(i);
            }
            return ;

        }
        // If there is , in the input, use , to separate them.
        if (input.contains(",")) {
            g = false;
        }
        StringTokenizer tokenizer = new StringTokenizer(input);

       
        
       
        while (tokenizer.hasMoreTokens()) {
            if (g == false) {
                 str = tokenizer.nextToken(",");
                 
               
            } else {
                str = tokenizer.nextToken();
            }
       
        if (count == 0) {
            examples.add(str);
        
            count++;
          
        
            }
        else {
             attributes[counter][count-1] = str;
       
            count++;
            }
        }
        counter++;

        
    }
    
   
    
}
/**
 * Change examples to either true or false.
 * @param examp
 */
public void changePosiOrNega(LinkedList<String> examp) {
   
    String s = correct;
    
    example[0] = true;
    for (int i = 0; i < examp.size() ; i++) {
        if(examp.get(i).equals(s)) {
            example[i] = true;
            positiveExamples++;
        }
        else {
            example[i] = false;
            negativeExamples++;
        }
    }
   
    
}
/**
 * Since I changed everything to nothing after I used the examples, I need to check if the size is truly 0.
 * @param ex
 * @return
 */
public boolean checkExampleSize(LinkedList<String> ex) {
  
    for (int i = 0 ; i < ex.size(); i++) {
     
        if (!ex.get(i).equals("nothing")) {
            // if one of them is not nothing, return false
            return false;
        }
    }
   
    return true;
}
/**
 * This is the decision tree algorithm.
 * @param example2
 * @param attribu
 * @param parentExamp
 * @return
 */
public TreeNode Decision_Tree_Learning(LinkedList<String> example2, String[][] attribu, LinkedList<String> parentExamp)
{
  //if the exmples is empty
    if (checkExampleSize(example2)) {
     
    
        boolean b = Plurality_Value(parentExamp);
       
        TreeNode newNode = new TreeNode();
       
        newNode.setValue(b);
        newNode.setLeaf();
        return newNode;
    }
    
    // if all examples have the same classification.
    else if (sameClassification(example2)) {
      
        boolean b = Plurality_Value(example2);
        
        TreeNode newNode = new TreeNode();
        
        newNode.setValue(b);
        newNode.setLeaf();
        return newNode;
    }
    // if attributes is empty.
    else if (l.size() == 0) {
      
        boolean b = Plurality_Value(example2);
        TreeNode newNode = new TreeNode();
        newNode.setValue(b);
        newNode.setLeaf();
        
        return newNode;
    }
    else {
       // else none of the above applies
        double bestAttribute = -100;
        int bestColumn = 0;
        for (int i = 0 ; i< l.size() ; i++) {
           
           LinkedList<String> temp = new LinkedList<String>();
         
           
           temp = sameAttribute(l.get(i),attribu);
           // calculate entropy.
           double entropy = this.calculateEntropy(temp,example2);
           // find out the attribute that gives us the most gain.
           if (bestAttribute < entropy ) {
               bestAttribute = entropy;
               bestColumn = l.get(i);
               
             
               
           }
       }
      
       // since i know we need to go through every column, delete a column if we already used it
       int d = l.indexOf(bestColumn);
  
       l.remove(d);
       
       TreeNode tree = new TreeNode();
       tree.setAttribute(bestColumn);
       LinkedList<String> differ = new LinkedList<String>();
       // find out all the classification under that attribute.
       
       for (int i = 0; i < attribu.length; i++) {
           if (!differ.contains(attribu[i][bestColumn]) && attribu[i][bestColumn] != null) {
               differ.add(attribu[i][bestColumn]);
           }
       }
       // Pick the attributes based on the classification.
       for (int i = 0; i < differ.size() ; i++) {
           LinkedList<String> newexam = new LinkedList<String>();
          
           for (int j = 0 ; j < example2.size() ; j++) {
               // add nothing to show that example is not in that classificaiton.
               newexam.add("nothing");
             
               
               if (attribu[j][bestColumn].equals(differ.get(i))) {
                   newexam.removeLast();
                 
                   newexam.add(j,example2.get(j));
                 
               }
              
           }
           // create a subtree. And set its label, child.
           TreeNode subtree = new TreeNode();
         
           subtree = Decision_Tree_Learning( newexam, attribu,  example2);
         
           subtree.setLabel(differ.get(i));
           
           // set the subtree as its tree , ans set its label.
           tree.setChild(subtree);
           tree.setChildLabel(differ.get(i));
       }
       return tree;
    }
    
    
}

/**
 * Check if all the examples that are left belong to the same classification.
 * @param e
 * @return return true if yes.
 */
public boolean sameClassification(LinkedList<String> e) {
    String s= "nothing";
    int w = 0;
    
    while (w < e.size()) {
        if (!e.get(w).equals("nothing")) {
            // find the first non- nothing string.
            s = e.get(w);
            break;
         
        }
        w++;
    }
    
    for (int i = 0 ; i < e.size(); i++) {
        // check if its not equal to s or nothing, then return false.
        if (!s.equals(e.get(i)) && !e.get(i).equals("nothing")) {
          
            return false;
        }
       
    }
   
    return true;
    
    
}
/**
 * This method is to find the gain fromm different attribute.
 * @param data
 * @param exa
 * @return
 */
public double calculateEntropy(LinkedList<String> data, LinkedList<String> exa) {
    
    String str = correct;
    double count = 0;
    LinkedList<Integer> inte = new LinkedList<Integer>();
   
    for (int i = 0 ; i < exa.size() ; i++) {
    if (exa.get(i).equals(str)) {
        count++;
    }
    
    
    if (!exa.get(i).equals("nothing")) {
        inte.add(i);
    }
    }
    
    LinkedList<String> differ = new LinkedList<String>();
    // find out the different classes existed in the attribute.
    for (int i = 0; i < inte.size(); i++) {
        if (!differ.contains(data.get(inte.get(i)))) {
            differ.add(data.get(inte.get(i)));
        }
    }
  
    double intesize = inte.size();
    // find out b
    double b = calculateB(count,intesize);
   
    count = 0;
    double sum = 0;
    double positive = 0;
    for (int i = 0; i < differ.size(); i++) {
        positive = 0;
        count = 0;
        for (int j = 0; j < inte.size() ; j++) {
        if (differ.get(i).equals(data.get(inte.get(j)))){
            count++;
          
            if (exa.get(inte.get(j)).equals(str)) {
                positive++;
            }
        }
            
        }
       
        double co = count;
       // calculate the sum.
        sum = sum + co/intesize * calculateB(positive,co);
      
       
    }
   
    double gain = b - sum;
    
    


return gain;

}
/**
 * Another  method to find the best attribute using gain ratio.
 * @param data
 * @param exa
 * @return
 */
public double GainRatio(LinkedList<String> data, LinkedList<String> exa) {
    
    String str = correct;
    double count = 0;
    LinkedList<Integer> inte = new LinkedList<Integer>();
  
    for (int i = 0 ; i < exa.size() ; i++) {
    if (exa.get(i).equals(str)) {
        count++;
    }
    if (!exa.get(i).equals("nothing")) {
        inte.add(i);
    }
    }
    
    LinkedList<String> differ = new LinkedList<String>();
    for (int i = 0; i < inte.size(); i++) {
        if (!differ.contains(data.get(inte.get(i)))) {
            differ.add(data.get(inte.get(i)));
        }
    }
  
   double intesize = inte.size();
    double b = calculateB(count,intesize);
  
    count = 0;
    double sum = 0;
    double intri = 0;
    double positive = 0;
    for (int i = 0; i < differ.size(); i++) {
        positive = 0;
        count = 0;
        for (int j = 0; j < inte.size() ; j++) {
        if (differ.get(i).equals(data.get(inte.get(j)))){
            count++;
           
            if (exa.get(inte.get(j)).equals(str)) {
               
                positive++;
            }
        }
            
        }
       
        double co = count;
       
        sum = sum + co/intesize * calculateB(positive,co);
        intri = intri + co/intesize * Math.log(positive/co) / Math.log(2);
                
       
    }
   
    double gain = b - sum;
    // it is exactly as the gain information except we the ratio.
    double ratio = gain / intri;
  
    

return ratio;

}
/**
 * a help method for gain calculation.
 * @param a
 * @param b
 * @return
 */
public double calculateB(double a, double b) {
    if (a == 0 ) {
        return 0;
    }
    if (a==b) {
        return 0;
    }
    double c = a/b;
   
    double z = -((c * Math.log(c)/Math.log(2) + (1-c) * Math.log(1-c)/Math.log(2)));
    
    return z;
}
/**
 * Plurality value, this method is decide value of leaf node. Set the value of node to be the majority value.
 * @param example
 * @return
 */
public boolean Plurality_Value(LinkedList<String> example) {
    String str = examples.get(0); 
    int count = 0;
    int numTrue = 0;
    
    for (int i = 0 ; i< example.size();i++) {
        if (!example.get(i).equals("nothing")) {
            count++;
        }
        if (example.get(i).equals(str)) {
            numTrue++;
        }
    }
    
    if (numTrue*2 <= count) {
       
            return false;
    }
    
    return true;
}

/**
 * Genetic Algorithm.
 * @param population
 * @return
 */
public TreeNode GA (ArrayList<TreeNode> population, boolean b) {
    TreeNode best = new TreeNode();
    Random r = new Random();
    double bestA = 0;
    // Population is not fit yet
    boolean fitnessOfPopulation = false;
    long start = System.currentTimeMillis();
    long end =  start + 6 *   1000;
    while (fitnessOfPopulation == false && System.currentTimeMillis() < end) {
        // Create a new Population for every Generation  
       
        ArrayList<TreeNode> newPopulation = new ArrayList<TreeNode>();
           
        for (int i = 0; i < population.size(); i++ ) {
                TreeNode x = new TreeNode();
                TreeNode y = new TreeNode();
                x = this.randomSelection(population);
                y = this.randomSelection(population);
                double d = r.nextDouble();
                TreeNode cross = new TreeNode(); 
                if ( d < 0.6) {
                
                   
                    cross = this.crossOver(x, y);
                  
                        if (d > 0.95 ) {
                       
                       cross =  this.mutate(cross);
                    }
                }
                else {
                    cross = x;
                }
                 
                    
                newPopulation.add(cross);
            
        }
        
                
            population = newPopulation;
     
        double bestFitness = 0;
       
        for (int i = 0; i < population.size() ; i++) {
            double fitnes = population.get(i).getFitness();
            if (fitnes > bestFitness) {
                bestFitness = fitnes;
                best = population.get(i);
                bestA = bestFitness;
            }
        }
      
        if (b == true) {
        System.out.println("Best Accuracy in the population now is " + bestFitness);
        }
        // If the accuracy is above 95% , I stop the algorithm.
        if (bestFitness > 0.9) {
            fitnessOfPopulation =true;
        }
        
        
   }
    System.out.println("Done, the best accuracy is " + bestA);
    // Return the best node.
    return best;
}
/**
 * Create the population for Genetic algorithm
 * @param population
 * @return
 */
public ArrayList<TreeNode> CreatePopulation(int population) {
   int numAtt = numAttributes+1;
    ArrayList<TreeNode> Population = new ArrayList<TreeNode>();
    for (int i = 0 ; i < numAtt ; i++) {
        left.add(i);
    }
    // create individual one by one. ArrayList left is used to make sure all the attributes are included in the tree.
    for (int j = 0 ; j < population; j++) {
        TreeNode t = new TreeNode();
        t = randomTree(left);
        left = new ArrayList<Integer>();
        for (int i = 0 ; i < numAtt ; i++) {
            left.add(i);
        }
        
        Population.add(t);
    }
    // Set their accuracy .
    for (int i = 0; i< Population.size() ; i++) {
        Population.get(i).setFitness(this.fitness(Population.get(i)));
        
    }

    return Population;
}
/**
 * Create a random Tree.
 * @param attri
 * @return
 */
public TreeNode randomTree(ArrayList<Integer> attri) {
    Random r = new Random();
    // If there is no attributes left it has to be a leaf node.
    if (left.size() == 0) {
      
        TreeNode newNode = new TreeNode();
        // Decide the leaf node value randomly.
        int v = r.nextInt(2);

        if (v == 1) {
            newNode.setValue(true); 
        }
        else {
            newNode.setValue(false); 
        }
        
        newNode.setLeaf();
        return newNode;
    }
    TreeNode tree = new TreeNode();
 
    int att = r.nextInt(left.size());
  
    LinkedList<String> attr = new LinkedList<String>();
    attr =  sameAttribute(left.get(att),attributes);
    ArrayList<String> diff = new ArrayList<String>();
    diff = differentElement(attr);
   
    tree.setAttribute(left.get(att));
   
    left.remove(att);
   
    for (int i = 0; i < diff.size() ; i++) {
       
        TreeNode subtree = new TreeNode();
      
        subtree = randomTree(left);
        //Set children and set pareent fro tree and subtree.
        tree.setChildLabel(diff.get(i));
       
        tree.setChild(subtree);
        subtree.setParent(tree);
       
       
            
           
          
           
            
           
            
        }
    return tree;
    }
   
 
/**
 * Crossover(Genetic Alrorithm)
 * @param x
 * @param y
 * @return
 */
public TreeNode crossOver (TreeNode x, TreeNode y) {
   
   ArrayList<TreeNode> tx = new ArrayList<TreeNode>();
   ArrayList<TreeNode> tx1 = new ArrayList<TreeNode>();
   ArrayList<TreeNode> ty = new ArrayList<TreeNode>();
   //First get all the nodes from tree x and y.
   tx = this.getAllNode(x);
   ty = this.getAllNode(y);
   //copy every node in tree x.
   for ( int i = 0; i < tx.size() ; i ++) {
     TreeNode sd = new TreeNode();
     sd = tx.get(i).clone();
     tx1.add(sd);
    
   }
   
   //for every copy, reset its children and paretn.
   for (int i = 0 ; i < tx1.size() ; i++) {
     ArrayList<TreeNode> arr = new ArrayList<TreeNode>();
     ArrayList<Integer> ar = new ArrayList<Integer>();
     arr = tx.get(i).getChildren();
     for (int j = 0 ; j< arr.size() ; j++) {
         ar.add(tx.indexOf((arr.get(j))));
         
     }
    
     tx1.get(i).deleteAllChildren();
    
     for (int j = 0 ; j < ar.size(); j++) {
        tx1.get(i).setChild(tx1.get(ar.get(j)));
        tx1.get(ar.get(j)).setParent(tx1.get(i));
     }
   }
   
   Random r = new Random();
   // Now randomly pick two nodes from copies of tree x and y.
   int op = r.nextInt(ty.size());
   int op1 = r.nextInt(tx1.size());
   //Make a copy of the random node from tree y.
   TreeNode temp  = new TreeNode();
   TreeNode temp1 = new TreeNode();
   temp = ty.get(op);
   temp1 = temp.clone();
   // If op1 = 0, that means we switch the root. Set its parent to null.
   if (op1 == 0) {
       
       temp1.setParent(null);
      
       if (temp1.getLeaf()) {
           
           temp1.setFitness(0);
          
           return temp1;
       }
       
       double fitn = this.fitness(temp1);
       temp1.setFitness(fitn);
      
       return temp1; 
   }
   // Switch the two nodes, and reset its children and parent.
   TreeNode t = new TreeNode();
   t = tx1.get(op1);
   TreeNode pa = new TreeNode();
   pa = t.getParent();
   int ss = pa.getChildren().indexOf(t);
   String se1 = pa.removeChild(ss);
   pa.setChild(temp1);
   pa.setChildLabel(se1);
   temp1.setParent(pa);
   double s = this.fitness(tx1.get(0));
   tx1.get(0).setFitness(s);
   

   return tx1.get(0);
    
}

/**
 * To get all the children, use recursive.
 * @param x
 * @return
 */
public ArrayList<TreeNode> getAllNode(TreeNode x) {
    ArrayList<TreeNode> arr = new ArrayList<TreeNode>();
    addAllNode(x,arr);
    return arr;
}
/**
 * Get all the nodes's children.
 * @param node
 * @param list
 */
public void addAllNode(TreeNode node, ArrayList<TreeNode> list) {
    
    if (node != null) {
        list.add(node);
      
        ArrayList<TreeNode> a = new ArrayList<TreeNode>();
        a = node.getChildren();
      
        if ( a.size()!= 0) {
            for (int i = 0; i < a.size() ; i++ ) {
                addAllNode(a.get(i),list);
            }
        }
    }
}
/**
 * Return a list of different element under
 * @param att
 * @return
 */
public ArrayList<String> differentElement(LinkedList<String> att) {
    ArrayList<String> diff = new ArrayList<String>();
    // Simply go through all the examples, add one by one.
    for (int i = 0; i < att.size(); i++) {
        if (!diff.contains(att.get(i))) {
            diff.add(att.get(i));
        }
    }
    return diff;
}
/**
 * Find the accuray of a tree.
 * @param t
 * @return
 */
public double fitness(TreeNode t) {
    // Set correct and incorrect to 0 frst.
    int correct = 0;
    int incorrect = 0;
    //Check row by row.
    for (int i = 0; i < examples.size(); i ++) {
        boolean b;
        // Find out the value of leaf.
        b = fitNess(t,i);
       
         if (example[i] == b){
         
            correct++;
        }
        else {
           
            incorrect++;
        }
    
        
    }
   
    double co = correct;
    double in = incorrect;
    double fit = co / (in + co);
    //Calcualte the accuracy and return it.
    return fit ;
}
/**
 * Fintess helper method, help to find the value of each leaf node.
 * @param t
 * @param a
 * @return
 */
public boolean fitNess(TreeNode t,int a) {
    ArrayList<Boolean> b = new ArrayList<Boolean>();
    int ri = -10;
   
    
    while (true  ) {
        ri = -10;
        String s = attributes[a][t.getAttribute()];
        ArrayList<TreeNode> children = t.getChildren();
        
        for (int j = 0; j < children.size(); j++){
            //If find the matching label, break.
            if (t.returnLabel().get(j).equals(s)) {
                ri = j;
                break;
               
            }
           
        }
        
        if (children.get(ri).getLeaf()) {
            b.add(children.get(ri).returnValue());
            b.add(true);
          
            return children.get(ri).returnValue();
        }
         
         
        //else keep going down the tree. 
        else {
            
            t = children.get(ri);
            
        }
    }
    
}

// find the recall, precision, and accuracy of a deicison tree.
public void  stats(TreeNode t) {
    double correct = 0;
    double incorrect = 0;
    double falseNegatives = 0;
    double truePositives = 0;
    double falsePositives = 0;
    System.out.println("example size " + examples.size());
    for (int i = 0; i < examples.size(); i ++) {
        
       
        boolean b;
        b = fitNess(t,i);
 
         if (example[i] == b){
         
            correct = correct + 1;
         }
         if (example[i] == false && b == true) {
             falseNegatives = falseNegatives+1;
         }
        
         if (example[i] == true && b == false) {
             falsePositives = falsePositives + 1;
         }
         if (example[i] == true && b == true) {
             truePositives = truePositives + 1;
         }
         if (example[i] != b) {
             incorrect = incorrect + 1;
         }
        }

   

   System.out.println(truePositives + " " + falsePositives + " " + positiveExamples);
    double recall = truePositives / (truePositives + falseNegatives);
    double precision =truePositives / ( truePositives + falsePositives);
    double accuracy =  correct / (correct + incorrect);
    System.out.println("Stat for this data set: ");
    System.out.println("Recall: " + recall);
    System.out.println("Precision: " + precision);
    System.out.println("Accuracy: " + accuracy);
   
}
        
        
    
   
       
/**
 * Randomly select a tree from the population, the better its accracy, the greater chance it will get selected.
 * @param population
 * @return
 */
public TreeNode randomSelection(ArrayList<TreeNode> population) {
    double totalProbability = 0;
  
    for (int i = 0; i < population.size() ; i++) {
        
        totalProbability = totalProbability + population.get(i).getFitness();
    }
    double total = totalProbability * 100000;
    // Pick a random from 0 to 1 and muptilpy tatal.
    Random r = new Random();
    double random = r.nextDouble();
    double result = random * total;
  
    int con = 0;
    totalProbability = 0;
    //Basically if total probability is greater than the result, return that tree.
    while (totalProbability < result ) {
        totalProbability = totalProbability + population.get(con).getFitness()*100000;
  
        con++;
    }
    
    return population.get(con-1);
}

/**
 * mutate a node.
 * @param tree
 * @return
 */
public TreeNode mutate(TreeNode tree) {
    // If the tree is a leaf node, you can not mutate, so just return it.
    if (tree.getLeaf()) {
        return tree;
    }
    // Just like crossover, first make a copy of the tree.
    ArrayList<TreeNode> tx = new ArrayList<TreeNode>();
    ArrayList<TreeNode> tx1 = new ArrayList<TreeNode>();
    tx = this.getAllNode(tree);
 
  

    for ( int i = 0; i < tx.size() ; i ++) {
        TreeNode sd = new TreeNode();
        sd = tx.get(i).clone();
        tx1.add(sd);
    
    }

    for (int i = 0 ; i < tx1.size() ; i++) {
        ArrayList<TreeNode> arr = new ArrayList<TreeNode>();
        ArrayList<Integer> ar = new ArrayList<Integer>();
        arr = tx.get(i).getChildren();
        for (int j = 0 ; j< arr.size() ; j++) {
            ar.add(tx.indexOf((arr.get(j))));
         
        }
        tx1.get(i).deleteAllChildren();
     
  
        for (int j = 0 ; j < ar.size(); j++) {
        tx1.get(i).setChild(tx1.get(ar.get(j)));
        tx1.get(ar.get(j)).setParent(tx1.get(i));
     }
 }
 
   Random r = new Random();
   
  
   // randomly select two nodes in the tree.
   //Keep selecting nodes until two of them are not leaf nodes and have the same number of children.
   while (true) {
       int op = r.nextInt(tx1.size());
       int op1 = r.nextInt(tx1.size());
       TreeNode temp = new TreeNode();
       TreeNode temp1 = new TreeNode();
       temp = tx1.get(op);
       temp1 = tx1.get(op1);  
       if (temp.getChildren().size() != 0 && (temp.getChildren().size() == temp1.getChildren().size())) {
           ArrayList<String> temp3 = new ArrayList<String>();
           int at = temp.getAttribute();
           temp3 = temp.returnLabel();
           temp.switchAttribute(temp1.getAttribute());
           temp.switchLabel(temp1.returnLabel());
           temp1.switchAttribute(at);
           temp1.switchLabel(temp3);
           double fi = this.fitness(tx1.get(0));
           //set its accuracy
           tx1.get(0).setFitness(fi);
           return tx1.get(0);
       }
     
   }
   
}

/* main function */

public static void main(String[] args) throws Exception {

tree me = new tree();
System.out.println(" Hi , Welcome.  Please type in the name of the file you want to read data from");
Scanner input = new Scanner(System.in);
String file = "congression.txt";
me.readData(file);
me.Decision_Tree();
System.out.println("Do you want to run Genetic Algorithm ? y/n");
String choice = input.nextLine();
if (choice.equals("y")) {
System.out.println("What do you want the population to be ?");
String po = input.nextLine();
int popu = Integer.parseInt(po);
System.out.println("Do you want to see the accuracy of  best individual in the population as each generation reproduces? y/n");
String ch = input.nextLine();
boolean b = false;
System.out.println("GA will stop if the best indiviudal has an accuracy above 95%, or 1 min has passed.");
if (ch.equals("y")) {
    b = true;
}
me.Genetic_algorithm(popu,b);
}
else {
    System.out.println("Thank you and have a nice day!");
}



}

}