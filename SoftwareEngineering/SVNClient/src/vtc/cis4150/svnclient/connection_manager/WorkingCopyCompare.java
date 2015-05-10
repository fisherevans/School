package vtc.cis4150.svnclient.connection_manager;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNDiffClient;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/2/13
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkingCopyCompare
{
    private WorkingCopy _workingCopy;

    public WorkingCopyCompare(WorkingCopy workingCopy)
    {
        _workingCopy = workingCopy;
    }

    public Entry getLocalTree(String root, Entry parent, String path) throws SVNException
    {
        Entry top = new Entry(path.replaceAll(".*/",""));
        top.setParent(parent);
        try
        {
            File[] localFiles = null;
            File localDir = new File(root + "/" + path);
            try
            {
                localFiles = localDir.listFiles();
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }
            if(localFiles == null)
                localFiles =  new File[0];

            ArrayList<String> remoteFileNames = new ArrayList<>();
            try
            {
                Collection<SVNDirEntry> remoteFiles = _workingCopy.getRepository().getDir(path, -1, null, (Collection) null);
                for(SVNDirEntry remoteFile:remoteFiles)
                    remoteFileNames.add(remoteFile.getName() + (remoteFile.getKind() == SVNNodeKind.DIR ? "/" : ""));
            }
            catch(Exception e)
            {
                //e.printStackTrace();
            }
            for(File localFile:localFiles)
            {
                if(!localFile.getName().startsWith("."))
                {
                    Entry child = null;
                    String fileName = localFile.getName();
                    if(localFile.isDirectory())
                    {
                        child = getLocalTree(root, top, path + "/" + fileName);
                    }
                    else
                        child = new Entry(localFile.getName(), top, EntryType.File);

                    if(remoteFileNames.contains(fileName + (localFile.isDirectory() ? "/" : "")))
                    {
                        remoteFileNames.remove(fileName + (localFile.isDirectory() ? "/" : ""));
                        try
                        {
                            String fullPath = child.getFullPath();
                            boolean diff = !_workingCopy.getDiff(fullPath).equals("");
                            if(diff)
                                child.setEntryStatus(EntryStatus.Different);
                        }
                        catch(Exception e)
                        {
                            System.out.println("Failed to check for diffs: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        child.setEntryStatus(EntryStatus.ExistsLocally);
                    }

                    top.addChild(child);
                }
            }
            for(String remoteFile:remoteFileNames)
            {
                EntryType type = remoteFile.contains("/") ? EntryType.Directory : EntryType.File;
                String name = remoteFile.replaceAll("/","");
                if(type == EntryType.Directory)
                {
                    Entry child = getLocalTree(root, top, path + "/" + name);
                    child.setEntryStatus(EntryStatus.ExistsRemotely);
                    top.addChild(child);
                }
                else
                {
                    Entry child = new Entry(name, top, type);
                    child.setEntryStatus(EntryStatus.ExistsRemotely);
                    top.addChild(child);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error fetching local tree: " + e.getMessage());
        }
        return top;
    }

    public enum EntryType { File, Directory }

    public enum EntryStatus { ExistsLocally, ExistsRemotely, Same, Different }

    public class Entry
    {
        private String _name;
        private EntryType _entryType;
        private Entry _parent;
        private List<Entry> _children;
        private EntryStatus _entryStatus = EntryStatus.Same;

        public Entry(String name)
        {
            this(name, null, EntryType.Directory);
        }

        public Entry(String name, EntryType entryType)
        {
            this(name, null, entryType);
        }

        public Entry(String name, Entry parent, EntryType entryType)
        {
            _name = name;
            _parent = parent;
            _entryType = entryType;
            _children = new ArrayList<>();
        }

        public String getName()
        {
            return _name + (getEntryType() == EntryType.Directory ? "/" : "");
        }

        public void setName(String name)
        {
            _name = name;
        }

        public EntryType getEntryType()
        {
            return _entryType;
        }

        public Entry getParent()
        {
            return _parent;
        }

        public void setParent(Entry parent)
        {
            _parent = parent;
        }

        public List<Entry> getChildren()
        {
            return _children;
        }

        public void addChild(Entry childEntry)
        {
            _children.add(childEntry);
        }

        public EntryStatus getEntryStatus()
        {
            return _entryStatus;
        }

        public void setEntryStatus(EntryStatus entryStatus)
        {
            _entryStatus = entryStatus;
        }

        public String getFullPath()
        {
            if(_parent == null)
                return getName();
            else
                return _parent.getFullPath() + getName();
        }

        public Set<String> getAllFullPaths()
        {
            Set<String> paths = new HashSet<>();
            addAllFullPaths(paths);
            return paths;
        }

        public void addAllFullPaths(Set<String> paths)
        {
            paths.add(getFullPath());
            for(Entry child:_children)
                child.addAllFullPaths(paths);
        }

        public boolean contains(Entry entry)
        {
            if(entry.getFullPath().endsWith(getFullPath()))
            {
                return true;
            }
            else
            {
                for(Entry child:_children)
                {
                    if(child.contains(entry))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString()
        {
            return getName();
        }
    }
}
