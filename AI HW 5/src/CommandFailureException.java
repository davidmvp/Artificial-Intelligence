

/**
 * This class of exception is thrown when a command cannot be executed successfully.
 * @author Zachary Palmer
 */
public class CommandFailureException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CommandFailureException()
    {
        
        super();
        System.out.println("i mean");
    }

    public CommandFailureException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CommandFailureException(String message)
    {
        super(message);
    }

    public CommandFailureException(Throwable cause)
    {
        super(cause);
    }
}
