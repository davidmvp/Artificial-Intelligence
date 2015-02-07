

import java.io.Serializable;

/**
 * This interface is implemented by any class which represents a policy for a reinforcement agent.
 * @author Zachary Palmer
 */
public interface Policy extends Serializable
{
    /**
     * Decides which action to take from a given state.
     * @param state The current state of the agent.
     */
    public Action decide(State state);
}
