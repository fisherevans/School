package info.fisherevans.far.input;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class KeyboardInput
{
	boolean[] tempKeys = {false, false, false, false, false, false};
	Input keyboardInput;
	
	public KeyboardInput()
	{ }
	
	public boolean[] getKeys(GameContainer gc)
	{
		keyboardInput = gc.getInput();

		tempKeys[0] = keyboardInput.isKeyDown(Keyboard.KEY_W); // W
		tempKeys[1] = keyboardInput.isKeyDown(Keyboard.KEY_A); // A
		tempKeys[2] = keyboardInput.isKeyDown(Keyboard.KEY_S); // S
		tempKeys[3] = keyboardInput.isKeyDown(Keyboard.KEY_D); // D
		tempKeys[4] = keyboardInput.isKeyDown(Keyboard.KEY_SPACE); // SPACE
		tempKeys[5] = keyboardInput.isKeyDown(Keyboard.KEY_ESCAPE); // ESCAPE
		
		return tempKeys;
	}
}
