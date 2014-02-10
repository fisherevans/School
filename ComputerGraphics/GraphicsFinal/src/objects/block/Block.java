/**
 * @author 		Philip Lipman
 * @date 		Nov 29, 2012
 *
 * @class		Block
 * @comments	Insert your comments here.
 */

package objects.block;

import utils.Color;

/**
 * 
 */

public class Block
{
	private Color _color;
	private BlockType _type;
	private Coordinate _coordinate;
	
	public int x;
	public int y;
	public int z;
	
	public int topName;
	public int bottomName;
	public int frontName;
	public int backName;
	public int leftName;
	public int rightName;
	
	public int blockId;
	
	public static final float BLOCKSIZE = 1;
	/**
	 * 
	 */
	public Block(BlockType type, int x, int y, int z)
	{
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.z = z;
				
		_type = type;
		
		switch(type)
		{
		case EMPTY:
		{
			_color = new Color(1.0f, 1.0f, 1.0f, 0.0f);
			break;
		}
		case STONE:
		{
			_color = new Color(0.5f, 0.5f, 0.5f, 1.0f);
			break;
		}
		case GRASS:
		{
			_color = new Color(0.0f, 1.0f, 0.0f, 1.0f);
			break;
		}
		case DIRT:
		{
			_color = new Color(0.8f, 0.5f, 0.25f, 1.0f);
			break;
		}
		}
	}
	
	public Color getColor()
	{
		return _color;
	}
	
	public BlockType getType()
	{
		return _type;
	}

}
