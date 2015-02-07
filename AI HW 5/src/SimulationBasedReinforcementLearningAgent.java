

import java.util.Set;

/**
 * This interface is implemented by those agents which require a simulator in order to iterate their learning functions.
 * @author Zachary Palmer
 */
public interface SimulationBasedReinforcementLearningAgent extends ReinforcementLearningAgent
{
    /**
     * Provides a simulator for the agent to use.
     * @param simulator The simulator for the agent to use in its next iteration.
     */
    public void setSimulator(Simulator simulator);
    
    /**
     * Retrieves the listeners that this agent needs to be applied to a simulator whenever it executes a pass.
     * @return The listeners which will be applied to any simulator using a policy from this agent.
     */
    public Set<? extends SimulatorListener> getSimulatorListeners();
}
