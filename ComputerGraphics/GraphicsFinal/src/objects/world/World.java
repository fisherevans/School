package objects.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objects.block.Block;
import objects.block.BlockType;

public class World
{
	private int _worldX;
	private int _worldY;
	private int _worldZ;
	
	Block[][][] _world;
	
	public World(int x, int y, int z)
	{
		_worldX = x;
		_worldY = y;
		_worldZ = z;
		
		_world = new Block[_worldX][_worldY][_worldZ];
	}
	
	public void addBlock(Block block, int x, int y, int z)
	{
		_world[x][y][z] = block;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return _world[x][y][z];
	}
	
	 public void initWorld()
    {
        for(int x = 0; x < _worldX; x++)
        {
        	for(int y = 0; y < _worldY; y++)
        	{
        		for(int z = 0; z < _worldZ; z++)
        		{
        			if(y == 2)
        			{
        				_world[x][y][z] = new Block(BlockType.GRASS, x, y, z);
       				
        			}
        			else if(y < 2)
        			{
        				_world[x][y][z] = new Block(BlockType.DIRT, x, y, z);
        			}
        			else
        			{
        				_world[x][y][z] = new Block(BlockType.EMPTY, x, y, z);
       				
        			}
					//System.out.println("hello, world");
        			
        		}
        	}
        }
    }
	 
    /**
     * 
     * Name:		writeWorld
     * Date:		Dec 13, 2012
     * Comments:	writes world to text file
     * @throws IOException
     */
    public void writeWorld() throws IOException
    {
        
    	FileWriter writer = new FileWriter("data/test.txt");
    	
    	for(int y = 0; y < _worldY; y++)
        {
        	for(int x = 0; x < _worldX; x++)
        	{
        		for(int z = 0; z < _worldZ; z++)
        		{
					//System.out.println("hello, world");
        			Block block = _world[x][y][z];
        			writer.write(block.getType() + ",");
        		}
        		writer.write("\n");
        	}
        	writer.write("\n");
        	writer.write("\n");
        }
    	
    	writer.close();
    }
    
    /**
     * 
     * Name:		initWorldFromFile
     * Date:		Dec 13, 2012
     * Comments:	loads world from text file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void initWorldFromFile() throws FileNotFoundException,  IOException
    {
		BufferedReader reader = new BufferedReader(new FileReader("data/test.txt"));
		List<String> worldData = new ArrayList<String>();

		String strLine;
		while ((strLine = reader.readLine()) != null)   
		{
			if(!strLine.equals(""))
			{
				// Print the content on the console
				//System.out.println (strLine);
				String[] temp = strLine.split(",");
              
				for(String s: temp)
				{
					worldData.add(s);
				}
			}
		}
    	
		reader.close();
		
    	int i = 0;
		//System.out.println("worldData:" + worldData.size());
    	if(worldData.size() > 0)
    	{
    		
        	for(int y = 0; y < _worldY; y++)
            {
            	for(int x = 0; x < _worldX; x++)
            	{
            		for(int z = 0; z < _worldZ; z++)
            		{
    					//System.out.println("hello, world");
            			String s = worldData.get(i);
            			if(s.equals("EMPTY"))
    					{
            				_world[x][y][z] = new Block(BlockType.EMPTY, x, y, z);
            				
    					}
            			else if(s.equals("GRASS"))
    					{
            				_world[x][y][z] = new Block(BlockType.GRASS, x, y, z);
            				
    					}
            			else if(s.equals("DIRT"))
    					{
            				_world[x][y][z] = new Block(BlockType.DIRT, x, y, z);
            				
    					}
            			else if(s.equals("STONE"))
    					{
            				_world[x][y][z] = new Block(BlockType.STONE, x, y, z);
            				
    					}
        				i++;
            		}
            	}
            }
    	}
    }    
}
