package vtc.cis4150.svnclient.connection_manager;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc2.ng.SvnDiffGenerator;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;
import org.tmatesoft.svn.core.wc2.SvnCommit;
import org.tmatesoft.svn.core.wc2.SvnDiff;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;
import vtc.cis4150.svnclient.window_manager.connection_display.ConnectionPanel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class WorkingCopy implements Comparable
{
    private String _name, _username, _password, _url, _localRoot;

    private SVNClientManager _client;

    private ISVNAuthenticationManager _auth;

    private SVNRepository _repository;

    private ConnectionPanel _connectionPanel;

    public WorkingCopy(String hash) throws Exception
    {
        String[] split = hash.split(",");
        if(split.length != 5)
            throw new Exception("Failed to load working copy from: " + hash);
        create(split[0], split[1], split[2], split[3], split[4]);
    }

    /**
     * Creates a new instance of this class using default ISVNOptions and ISVNAuthenticationManager drivers. That means this SVNClientManager will use the SVN's default run-time configuration area. Default options are obtained via a call to SVNWCUtil.createDefaultOptions(boolean).
	Returns:
		a new SVNClientManager instance
     */
    public WorkingCopy(String name, String username, String password, String url, String localRoot) throws SVNException
    {
        create(name, username, password, url, localRoot);
    }

    private void create(String name, String username, String password, String url, String localRoot) throws SVNException
    {
        _name = name;
        _username = username;
        _password = password;
        _url = url;
        _localRoot = localRoot;

        updateClient();

        _connectionPanel = new ConnectionPanel(this);
    }

    public void updateClient() throws SVNException
    {
        if(_client != null)
            _client.dispose();

        _client = SVNClientManager.newInstance();

        DAVRepositoryFactory.setup();
        _repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(getUrl()));
        _auth = SVNWCUtil.createDefaultAuthenticationManager(getUsername(), getPassword());
        _repository.setAuthenticationManager(_auth);
    }

    public String getDiff(String file) throws SVNException, UnsupportedEncodingException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SVNDiffClient diffClient = _client.getDiffClient();
        diffClient.doDiff(new File(getLocalRoot() + "/" + file), SVNRevision.UNDEFINED, SVNRevision.WORKING, SVNRevision.HEAD,
                SVNDepth.INFINITY, true, os, null);
        String diffs = new String(os.toByteArray(),"UTF-8");
        return diffs;
    }

    public long checkout() throws SVNException
    {
        return _client.getUpdateClient().doCheckout(SVNURL.parseURIEncoded(getUrl()), new File(getLocalRoot()), SVNRevision.HEAD, SVNRevision.HEAD, true);
    }

    public void update() throws SVNException
    {
        _client.getUpdateClient().doUpdate(new File(getLocalRoot()), SVNRevision.HEAD, true);
    }

    /**
     * Locks file items in a Working Copy as well as in a repository so that no other user can commit changes to them.
	Parameters:
		paths - an array of local WC file paths that should be locked
		stealLock - if true then all existing locks on the specified paths will be "stolen"
		lockMessage - an optional lock comment
	Throws:
		SVNException - if one of the following is true:
			a path to be locked is not under version control
			can not obtain a URL of a local path to lock it in the repository - there's no such entry
			paths to be locked belong to different repositories
     * @param wcPath
     * @param isStealLock
     * @param lockComment
     * @throws SVNException
     */
    public void lock( File[] wcPath , boolean isStealLock , String lockComment ) throws SVNException
    {
    	_client.getWCClient().doLock(wcPath, isStealLock, lockComment);
    }
    
    /**
     * Unlocks file items in a Working Copy as well as in a repository.
	Parameters:
		paths - an array of local WC file paths that should be unlocked
		breakLock - if true and there are locks that belong to different users then those locks will be also unlocked - that is "broken"
	Throws:
		SVNException - if one of the following is true:
			a path is not under version control
			can not obtain a URL of a local path to unlock it in the repository - there's no such entry
			if a path is not locked in the Working Copy and breakLock is false
			paths to be unlocked belong to different repositories
     * @param wcPath
     * @param breakLock
     * @throws SVNException
     */
    public void unlock( File[] wcPath, boolean breakLock) throws SVNException
    {
    	_client.getWCClient().doUnlock(wcPath, breakLock);
    }
    
    /**
     * Schedules a Working Copy item for deletion. This method is equivalent to doDelete(path, force, true, dryRun).
	Parameters:
		path - a WC item to be deleted
		force - true to force the operation to run
		dryRun - true only to try the delete operation without actual deleting
	Throws:
		SVNException - if one of the following is true:
			path is not under version control
			can not delete path without forcing
     * @param wcPath
     * @param force
     * @throws SVNException
     */
    public void delete( File wcPath , boolean force ) throws SVNException
    {
    	_client.getWCClient().doDelete(wcPath, force, false);
    }

    public String toString()
    {
        return _name + "," + _username + "," + _password + "," + _url + "," + _localRoot;
    }

    public String toDescription()
    {
        return _name + " (" + _username + "@" + _url.replaceAll("/$","").replaceAll(".*/","") + ")";
    }

    public boolean testConnection()
    {
        try
        {
            _repository.getRepositoryRoot(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SVNRepository getRepository()
    {
        return _repository;
    }

    public String getUsername()
    {
        return _username;
    }

    public String getPassword()
    {
        return _password;
    }

    public String getUrl()
    {
        return _url;
    }

    public void setUsername(String username)
    {
        _username = username;
    }

    public void setPassword(String password)
    {
        _password = password;
    }

    public void setUrl(String url)
    {
        _url = url;
    }

    public void setLocalRoot(String localRoot)
    {
        _localRoot = localRoot;
    }

    public String getLocalRoot()
    {
        return _localRoot;
    }

    public ISVNAuthenticationManager getAuth()
    {
        return _auth;
    }

    @Override
    public int compareTo(Object o)
    {
        if(o instanceof WorkingCopy)
        {
            WorkingCopy workingCopy = (WorkingCopy)o;
            return this.toDescription().compareTo(workingCopy.toDescription());
        }
        else return -1;
    }

    public ConnectionPanel getConnectionPanel()
    {
        return _connectionPanel;
    }

    public ArrayList<WorkingCopyCompare.Entry> getAllStatusType(WorkingCopyCompare.Entry root, WorkingCopyCompare.EntryStatus ... statuses)
    {
        ArrayList<WorkingCopyCompare.Entry> sameType = new ArrayList<>();
        ArrayList<WorkingCopyCompare.Entry> nodes = new ArrayList<>();
        nodes.add(root);
        while(nodes.size() > 0)
        {
            WorkingCopyCompare.Entry node = nodes.get(0);
            nodes.remove(0);
            nodes.addAll(node.getChildren());
            for(WorkingCopyCompare.EntryStatus status:statuses)
            {
                if(node.getEntryStatus() == status)
                {
                    sameType.add(node);
                    continue;
                }
            }
        }
        return sameType;
    }

    public File[] getFiles(ArrayList<WorkingCopyCompare.Entry> nodes)
    {
        File[] files = new File[nodes.size()];
        for(int id = 0;id < files.length;id++)
            files[id] = new File(getLocalRoot() + "/" + nodes.get(id).getFullPath());
        return files;
    }

    public void commitAll(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        File[] commits = getFiles(getAllStatusType(root, WorkingCopyCompare.EntryStatus.Different));
        _client.getCommitClient().doCommit(commits, true, message, true, true);
    }

    public void commitSingle(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        File file = new File(getLocalRoot() + "/" + root.getFullPath());
        _client.getCommitClient().doCommit(new File[] {file}, true, message, true, true);
    }

    public void addAll(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        ArrayList<WorkingCopyCompare.Entry> nodes = new ArrayList<>();
        nodes.add(root);
        while(nodes.size() > 0)
        {
            WorkingCopyCompare.Entry node = nodes.remove(0);
            if(node.getEntryStatus() == WorkingCopyCompare.EntryStatus.ExistsLocally)
            {
                addSingle(node, message);
            }
            nodes.addAll(node.getChildren());
        }
    }

    public void addSingle(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        File[] files = new File[] { new File(getLocalRoot() + "/" + root.getFullPath()) };
        _client.getWCClient().doAdd(files, false, false, false, SVNDepth.IMMEDIATES, false, false, false);
        _client.getCommitClient().doCommit(files, true, message, true, true);
    }

    public void deleteAllRemote(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        ArrayList<WorkingCopyCompare.Entry> delFiles = getAllStatusType(root, WorkingCopyCompare.EntryStatus.ExistsRemotely);
        SVNURL[] urls = new SVNURL[delFiles.size()];
        for(int id = 0;id < delFiles.size();id++)
            urls[id] = SVNURL.parseURIEncoded(getUrl() + "/" + delFiles.get(id).getFullPath());
        _client.getCommitClient().doDelete(urls, message);
    }

    public void deleteSingleRemote(WorkingCopyCompare.Entry root, String message) throws SVNException
    {
        SVNURL[] urls = new SVNURL[] { SVNURL.fromFile(new File(getLocalRoot() + "/" + root.getFullPath())) };
        _client.getCommitClient().doDelete(urls, message);
    }

    public void deleteAllLocal(WorkingCopyCompare.Entry root) throws SVNException
    {
        File[] oldFiles = getFiles(getAllStatusType(root, WorkingCopyCompare.EntryStatus.ExistsLocally));
        for(File oldFile:oldFiles)
        {
            try
            {
                if(oldFile.delete())
                    System.out.println("Deleted");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void deleteSingleLocal(WorkingCopyCompare.Entry root) throws SVNException
    {
        File[] files = new File[] { new File(getLocalRoot() + "/" + root.getFullPath()) };
        for(File oldFile:files)
        {
            try
            {
                if(oldFile.delete())
                    System.out.println("Deleted");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void updateAll(WorkingCopyCompare.Entry root) throws SVNException
    {
        File[] diffFiles = getFiles(getAllStatusType(root, WorkingCopyCompare.EntryStatus.Different, WorkingCopyCompare.EntryStatus.ExistsRemotely));
        _client.getUpdateClient().doUpdate(diffFiles, SVNRevision.HEAD, SVNDepth.INFINITY, true, true);
    }

    public void updateSingle(WorkingCopyCompare.Entry root) throws SVNException
    {
        File[] files = new File[] { new File(getLocalRoot() + "/" + root.getFullPath()) };
        _client.getUpdateClient().doUpdate(files, SVNRevision.HEAD, SVNDepth.INFINITY, true, true);
    }

    public Collection<SVNFileRevision> getRevisionHistory(WorkingCopyCompare.Entry file) throws SVNException
    {
        Collection revsCol = _repository.getFileRevisions(file.getFullPath(), null, 0, SVNRevision.HEAD.getNumber());

        return revsCol;
    }

    public String getName()
    {
        return _name;
    }
}
