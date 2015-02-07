

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;


/**
 * Allows a persisted agent to be loaded.
 * @author Zachary Palmer
 */
public class LoadCommand extends Command
{
    @Override
    public void execute(Shell shell, String[] args) throws CommandFailureException
    {
        checkArgCount(args, 1, 1);
        
        FileInputStream fis = null;
        System.out.println("ss");
        try
        {
            fis = new FileInputStream(args[0]);
        } catch (FileNotFoundException e)
        {
            throw new CommandFailureException(e.getMessage());
        }
        
        GZIPInputStream gzis = null;
        ObjectInputStream ois = null;
        System.out.println("2s");
        try
        {
            System.out.println("3s");
            gzis = new GZIPInputStream(fis);
            System.out.println("3s");
            ois = new ObjectInputStream(gzis);
        } catch (IOException e)
        {
            throw new CommandFailureException(e.getMessage());
        }
        System.out.println("3s");
        Object o;
        try
        {
            o = ois.readObject();
        } catch (IOException e)
        {
            throw new CommandFailureException(e.getMessage());
        } catch (ClassNotFoundException e)
        {
            throw new CommandFailureException("The provided file is not a persisted agent (bad class).");
        }
        System.out.println("3s");
        try
        {
            ois.close();
        } catch (IOException e)
        {
            throw new CommandFailureException("Could not close input stream!");
        }
        
        if (!(o instanceof MetricTrackingAgent))
        {
            throw new CommandFailureException("The provided file is not a persisted agent (wrong class: " +
                    o.getClass().getName() + ").");
        }
        
        shell.setAgent((MetricTrackingAgent)o);
        System.out.println("4s");
    }

    @Override
    public String getLongHelp(String name)
    {
        return
            "Usage: " + name + " <pathname>\n\n"+
            "Loads a persisted agent from disk.  Note that the agent's world must be loaded separately.";
    }

    @Override
    public String getShortHelp()
    {
        return "loads a saved agent";
    }
}
