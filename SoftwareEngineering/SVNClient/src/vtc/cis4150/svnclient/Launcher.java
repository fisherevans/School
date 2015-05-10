package vtc.cis4150.svnclient;

/**
 * User: David Fisher Evans
 * Date: 10/29/13
 */
public class Launcher
{
    /**
     * Creates the SVNClient GUI instance
     * @param args any program arguments
     */
    public static void main(String[] args)
    {
        SVNClient client = new SVNClient(args);
        client.init();
    }
}
