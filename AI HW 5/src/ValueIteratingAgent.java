

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;



public class ValueIteratingAgent implements ReinforcementLearningAgent
{
    private static final long serialVersionUID = 1L;
    
    /** A mapping between states in that world and their expected values. */
    private Map<State, Double> expectedValues;
    
    /**
     * A temporary mapping used to compare the old value to new value.
     */
    private Map<State, Double> temporary;
    /** The world in which this agent is operating. */
    private WorldMap world; 
    /** The discount factor for this agent. */
    private double discountFactor;
    /** The transition function that this agent uses. */
    private TransitionFunction transitionFunction;
    /** The reward function that this agent uses. */
    private RewardFunction rewardFunction;
    /** The convergence tolerance (epsilon). */
    private double convergenceTolerance;
    
    /**
     * Set s is intended to get all the available actions.
     */
    private Set<Action> set = new HashSet<Action>();
    /**
     * Creates a new value iterating agent.
     * @param world The world in which the agent will learn.
     */
    public ValueIteratingAgent()
    {
        this.expectedValues = new DefaultValueHashMap<State, Double>(0.0);
        this.temporary = new DefaultValueHashMap<State, Double>(0.0);
        this.world = null;
        this.discountFactor = 0.5;
        this.transitionFunction = null;
        this.rewardFunction = null;
        this.convergenceTolerance = 0.000000001;
       // this.convergenceTolerance = 0.01;
       
    }

    @Override
    public Policy getPolicy()
    {
        return new ValuePolicy();
    }
    
    /**
     * Create all the possible state.
     * @return
     */
    public ArrayList<State> createState() {
        
        ArrayList<State> allStates = new ArrayList<State>();
        Pair<Integer,Integer> size= new Pair<Integer,Integer>(0,0);
        
         size = world.getSize();
        for (int i = 0 ; i < size.getFirst() ; i++) {
            for (int j = 0; j < size.getSecond() ; j++) {
                for (int k = -5; k <= 5 ; k++) {
                    for (int p = -5; p <= 5 ; p++) {
                        
                        
                        Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(i,j);
                        Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(k, p);
                        Terrain t = this.world.getTerrain(p1);
                        if (t.toString().equals("GROUND")){  //If t is equal to ground,add it to the possible state.
                        State s = new State(p1,p2);
                        allStates.add(s);
                        }
                    }
                }
            }
        }
      
       
        
        return allStates;
        
    }
    
    
    /**
     * Value Iteration algorithm.
     * @return
     */
    public void value_Iteratrion() {
        
    
        
        //transitionFunction = new TerrainBasedTransitionFunction(world,true); //create a transitionFunction
       
       
        Action a = new Action();
        set = a.Allaction();  
        boolean done = false;
        ArrayList<State> allstates = createState();
        State st = new State();
        
     
        while (!done) {
            if (this.iterate() == true) { //check if it has met the termination requirement.
                return ;
            }
         
        
            
         
            for (int i = 0; i < allstates.size(); i++) {
                st = allstates.get(i);
                Iterator<Action> it =  set.iterator();
               
              
                double max = -100;
                while (it.hasNext()) {
                    
                    Set<Pair<State, Double>> set = new HashSet<Pair<State, Double>>();
                    a = it.next();
                    set = transitionFunction.transition(st, a);
                    Iterator<Pair<State,Double>> ite =  set.iterator();
                    double sum = 0;
                    while (ite.hasNext()) { //Find the best action.
                        Pair<State,Double> pa = new Pair<State,Double>(st,0.0);
                        pa = ite.next();
                        State sg = new State();
                        sg = pa.getFirst();
                        
                        sum = sum + pa.getSecond() * this.expectedValues.get(sg);
                    }
                
                    if (sum > max) {
                        max = sum;
                    }
                }
               
                double newValue =   this.rewardFunction.reward(st) + this.discountFactor * max; //calculate the new reward.
               
                this.temporary.put(st, newValue);  //Put the value in temporary first.
             
              
              
            
            }
           
          
        }
    
     System.out.println(this.rewardFunction);
        return;
    }
    /**
     * Iterate performs a single update of the estimated utilities of each
     * state.  Return value specifies whether a termination criterion has been
     * met.
     */
    
    
 
    @Override
    public boolean iterate()
    {
        double maxChange = -10;
        Set<State> set = new HashSet<State>();
        set = this.temporary.keySet();//Get the set from temporary.
      
        Iterator<State> it = set.iterator();
       
        while (it.hasNext()) {  //now update the utlity function and also check the maximum change.
            State st = it.next();
            double value = temporary.get(st);
          
            double previous = expectedValues.get(st);
            expectedValues.put(st, value);  
            double change = Math.abs(value-previous);  //find out the change.
            if (change > maxChange) {
                maxChange = change;
            }
            }
        System.out.println("Change in this iteration is " + maxChange);
       
        if (Math.abs(maxChange) < this.convergenceTolerance * ( 1 - this.discountFactor) / this.discountFactor) {
            System.out.println("done!");
            
            return true;
        }
        
        
        else {
        
            return false;
            }
        // TODO: implement value iteration; this is basically the inside of the
        // while(!done) loop.
    }

    public ValueIteratingAgent duplicate()
    {
        ValueIteratingAgent ret = new ValueIteratingAgent();
        ret.setConvergenceTolerance(this.convergenceTolerance);
        ret.setDiscountFactor(this.discountFactor);
        ret.setRewardFunction(this.rewardFunction);
        ret.setTransitionFunction(this.transitionFunction);
        ret.setWorld(this.world);
        ret.expectedValues.putAll(this.expectedValues);
        return ret;
    }
    
    public double getLearningFactor()
    {
        return discountFactor;
    }

    public void setDiscountFactor(double discountFactor)
    { 
        this.discountFactor = discountFactor;
    }

    public TransitionFunction getTransitionFunction()
    {
        return transitionFunction;
    }

    public void setTransitionFunction(TransitionFunction transitionFunction)
    { 
        this.transitionFunction = transitionFunction;
    }

    public RewardFunction getRewardFunction()
    {
        return rewardFunction;
    }

    public void setRewardFunction(RewardFunction rewardFunction)
    {
        this.rewardFunction = rewardFunction;
    }
    
    public WorldMap getWorld()
    {
        return world;
    }

    public void setWorld(WorldMap world)
    { 
        this.world = world;
    }
    
    public double getConvergenceTolerance()
    {
        return convergenceTolerance;
    }

    public void setConvergenceTolerance(double convergenceTolerance)
    {
        this.convergenceTolerance = convergenceTolerance;
    }

    /**
     * Represents a policy that this agent would produce.
     */
    public class ValuePolicy implements Policy
    {
        private static final long serialVersionUID = 1L;
       
        /**
         * The action an agent decides to take from a given state 
         */
        public Action decide(State state)
        {
           
          
            
            Action a = new Action();
            set = a.Allaction();  //get all the possible action.
            Iterator<Action> it = set.iterator();
            Set<Pair<State, Double>> ste = new HashSet<Pair<State, Double>>();
            Action bestAction = new Action();
            double max = -100;
         
            while (it.hasNext()) { //check every action and find out the best score.
          
              Action ac = new Action();
              ac = it.next();
              ste = transitionFunction.transition(state, ac);
              Iterator<Pair<State, Double>> ite = ste.iterator();
              Pair<State,Double> pa = ite.next();
              State s1 = pa.getFirst();
              pa = ite.next();
              State s2 = pa.getFirst();
              double d2 = pa.getSecond();
              double d = 0;
              if (d2 == 0.9) {
                   d = expectedValues.get(s2);
              }
              
              else {
                    d = expectedValues.get(s1);
              }
                
              if (d >= max) {
                      max = d;
                      bestAction = ac;
                  }
              }
         
            
            return bestAction;
            // TODO: this function should return an appropriate action based on
            // an exploration policy and the current estimate of expected
            // future reward. 
        }
    }
}
