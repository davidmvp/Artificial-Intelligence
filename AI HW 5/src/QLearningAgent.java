

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * A reinforcement agent which uses the Q-learning technique.
 * 
 * @author Zachary Palmer
 */
public class QLearningAgent implements SimulationBasedReinforcementLearningAgent
{
    private static final long serialVersionUID = 1L;
   
    /** The number of times the agent will explore a given state-action pair before giving up on it. */
    private int minimumExplorationCount;
    /** The discount factor used by this agent to allow control over how important short-term gains are considered. */
    private double discountFactor;
    /** The learning factor for this agent. */
    private double learningFactor;
    /** The convergence tolerance (epsilon). */
    private double convergenceTolerance;
    /** Previous state for the agent*/
    private State previousState;
    /**
     * Previous action for Q agent.
     */
    private Action previousAction;
    /**
     * Previous Reward for Q agent.
     */
    private double previousReward;
    /**
     * All the avaiables an agent can take.
     */
    private Set<Action> allactions = new HashSet<Action>();
    
    /** Tracks the maximum change in our perception of the environment during an iteration. */
    double maximumChange = 0;

    /** The record of how frequently each action has been explored from each state. */
    private Map<Pair<State, Action>, Integer> visitEvents;
    /** The expected reward for the provided state-action pair. */
    private Map<Pair<State, Action>, Double> expectedReward;

    /** The simulator which is simulating the environment in which this agent is learning. */
    private transient Simulator simulator;

    /**
     * General constructor.
     */
    public QLearningAgent()
    {
        this.minimumExplorationCount = 1;
        this.discountFactor = 0.99;
        this.learningFactor = 0.5;
        this.convergenceTolerance = 0.1;
       
        this.visitEvents = new DefaultValueHashMap<Pair<State, Action>, Integer>(0);
        this.expectedReward = new DefaultValueHashMap<Pair<State, Action>, Double>(0.0);
       
        Action a = new Action();
        /**
         * Get all the available actions. 
         */
        this.allactions = a.Allaction();
       
    }

    @Override
    public Policy getPolicy()
    {
        return new QPolicy();
    }
    
    /**
     * Iterates a single learn-as-I-go simulation for this Q learning agent. A
     * single iteration of this algorithm will walk the agent to a goal state;
     * thus, lower order iterations are likely to take much longer.  Return
     * value specifies whether a termination criterion has been met.
     */
    public boolean iterate()
    {
       
       
        
        this.maximumChange = 0;
      
        simulator.simulate(this.getPolicy());//Call the simulator
         
       
        //System.out.println("maximum change is " + this.maximumChange); //Print out the maximum change for this iteration.
       
        if (this.maximumChange < this.convergenceTolerance) {  //If maximum change is less than convergence return true.
            return true;
        }
        
        return false;
    }
    
        
    @Override
    public Set<? extends SimulatorListener> getSimulatorListeners()
    {
        return Collections.singleton(new QLearningListener());
    }

    @Override
    public QLearningAgent duplicate()
    {
        QLearningAgent ret = new QLearningAgent();
        ret.setConvergenceTolerance(this.convergenceTolerance);
        ret.setDiscountFactor(this.discountFactor);
        ret.setLearningFactor(this.learningFactor);
        ret.setMinimumExplorationCount(this.minimumExplorationCount);
        ret.expectedReward.putAll(this.expectedReward);
        ret.visitEvents.putAll(this.visitEvents);
        
        return ret;
    }

    
    
  
    
    
    public int getMinimumExplorationCount()
    {
        return minimumExplorationCount;
    }

    public void setMinimumExplorationCount(int minimumExplorationCount)
    {
        this.minimumExplorationCount = minimumExplorationCount;
    }

    public double getDiscountFactor()
    {
        return discountFactor;
    }

    public void setDiscountFactor(double discountFactor)
    {
        this.discountFactor = discountFactor;
    }

    public double getLearningFactor()
    {
        return learningFactor;
    }

    public void setLearningFactor(double learningFactor)
    {
        this.learningFactor = learningFactor;
    }

    public double getConvergenceTolerance()
    {
        return convergenceTolerance;
    }

    public void setConvergenceTolerance(double convergenceTolerance)
    {
        this.convergenceTolerance = convergenceTolerance;
    }

    public Simulator getSimulator()
    {
        return simulator;
    }

    public void setSimulator(Simulator simulator)
    {
        this.simulator = simulator;
    }

   
    
    /**
     * The policy used by this agent.
     */
    class QPolicy implements Policy
    {
        private static final long serialVersionUID = 1L;
       
        public QPolicy()
        {
            super();
        }

        @Override
        /**
         * Returns the action the agent chooses to take for the given state.
         */
        public Action decide(State state)
        {   
            
           
            Action bestAction = new Action();  //The best Action
            double max = -10000; //Set max to a negative number.
         
            Iterator<Action> it = allactions.iterator();  //Iterator to iterate through all the availables actions.
           
            
            while (it.hasNext()) {
                Action ac = new Action();
                ac = it.next();     //Get the action.
                Pair<State,Action> pa = new Pair<State,Action>(state,ac); //Create a pair of current state and current action.
                double score = 0;
                // If we have not explored this enough, the score is 0 (the best reward)
                if (visitEvents.get(pa) < minimumExplorationCount) {
                    score = 0;
                   
                }
                else {
                    score = expectedReward.get(pa); //Other wise we get the reward from Q table.
                   
                }
               
                if (score > max) {
                   
                    max = score;
                    bestAction = ac; // If score the greater than the score we have right now, reset max and best action.
                 
                }
            
            }
          
        
            return bestAction;
           
    }
    }

    /**
     * The listener which learns on behalf of this agent.
     */
    class QLearningListener implements SimulatorListener
    {
        /**
         * Called once for every timestep of a simulation; every 
         * time an agent takes an action, an "event" occurs.  
         * Q-learning needs to do an update after every step, and this
         * function is where it takes place.
         */
        @Override
        public void simulationEventOccurred(SimulatorEvent event)
        {
           
          
            State curr = new State();
            curr = event.getStep().getState(); //Get the current state from the event.
            
            Pair<State, Action> pa = new Pair<State,Action>(previousState,previousAction);  //Create a pair of previousStaet and previousAction
            
            int frequency = visitEvents.get(pa) + 1;
           
            visitEvents.put(pa, frequency);  //Increment the frequency.
        
            double max = -10000;
            double reward = expectedReward.get(pa);
            Iterator<Action> it = allactions.iterator();
                 while (it.hasNext()) {
                     Action ac = it.next();
                     Pair<State,Action> pai = new Pair<State,Action>(curr,ac);
                  
                     double value = expectedReward.get(pai);
                     
                     if (max < value) {
                         max = value;
                        
                     }
                 }
              
                 double newReward = reward + learningFactor *  (previousReward + discountFactor * max - reward);
              
                 double change = Math.abs(newReward - reward);  //Find out the change.
               
                 expectedReward.put(pa, newReward); //Update Q table.
                
                 if (change > maximumChange) {  // Check to see the change greater than maximumChange.
                     maximumChange = change;
                 }
                 previousState = event.getStep().getState(); //Set current state to be previousState.
                 previousAction = event.getStep().getAction(); //Set action to be previousAction.
                 previousReward = event.getStep().getAfterScore() - event.getStep().getBeforeScore(); //Set reward to previousReward.
          
               
           
          
          
        }
    
    }
}
    
    

