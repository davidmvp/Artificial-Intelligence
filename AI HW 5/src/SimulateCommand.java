

import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



/**
 * Runs a simulation using the policy of the current agent.
 * @author Zachary Palmer
 */
public class SimulateCommand extends Command
{
    @Override
    public void execute(Shell shell, String[] args)
        throws CommandFailureException
    {
        checkArgCount(args, 0, 1);
        checkWorldMap(shell);
        checkAgent(shell);
        
        int simulationCount = 1;
        if (args.length>0)
        {
            simulationCount = parsePositiveInteger(args[0], "simulation count");
        }
        
        Simulator simulator = buildSimulator(shell);        
        double totalScore = 0.0;
        Scanner input = new Scanner(System.in);
        System.out.println("Is it a Value Iteration agent or Q learning agent? vi/q ");
        String choice = input.nextLine();
        Formatter formatter = new Formatter();
        for (int i=0;i<simulationCount;i++)
        {
            MetricTrackingAgent agent = shell.getAgent().duplicate();
            Set<? extends SimulatorListener> listeners = agent.getSimulatorListeners();
            for (SimulatorListener listener : listeners) simulator.addSimulatorListener(listener);
            agent.setSimulator(simulator);
            Policy policy = agent.getPolicy();
            if (choice.equals("q")) {
            boolean done = false;
          
            long start = System.currentTimeMillis();
            while (!done) {
                //System.out.println("haha11");
                done = agent.iterate();
              
            }
            long end =  System.currentTimeMillis();;
            System.out.println("Time is takes " + (end - start) );
            }
           
            List<SimulationStep> simulation = simulator.simulate(policy);
            double score = simulation.get(simulation.size()-1).getAfterScore();
            totalScore += score;
            formatter.format(" %.2f", score);
            shell.setSimulation(simulation);
            
            for (SimulatorListener listener : listeners) simulator.removeSimulatorListener(listener);
            
            if (simulationCount>1)
            {
                progressReport(shell, i+1==simulationCount, "Simulation: %d/%d (%.2f%%) complete", i+1, simulationCount,
                        (double)(i+1)/simulationCount*100);
            }
        }
        
        if (simulationCount>1)
        {
            shell.print("Simulation scores:" + formatter.toString());
            shell.print("Average simulation score: " + (totalScore / simulationCount));
        } else
        {
            shell.print("Simulation score:" + formatter.toString());
        }
    }

    @Override
    public String getLongHelp(String name)
    {
        return
            "Usage: " + name + " [count]\n\n" +
            "Runs a simulation of the policy that the current agent is using.  If a count is specified, more than " +
            "one simulation is executed.";
    }

    @Override
    public String getShortHelp()
    {
        return "runs a simulation";
    }
}
