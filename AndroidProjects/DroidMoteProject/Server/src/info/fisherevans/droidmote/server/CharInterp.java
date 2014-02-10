package info.fisherevans.droidmote.server;

import java.awt.event.KeyEvent;

public class CharInterp // just used to translate char to key presses
{
	private Main parent;
	
	public CharInterp(Main main)
	{
		parent = main;
	}
	
	public void typeKey(char key)
	{
		boolean shift = false;
		int keyCode = 65; // default to 65

		switch (key) // simple char switch
		{   
	        case 'a': keyCode = KeyEvent.VK_A; break;
	        case 'b': keyCode = KeyEvent.VK_B; break;
	        case 'c': keyCode = KeyEvent.VK_C; break;
	        case 'd': keyCode = KeyEvent.VK_D; break;
	        case 'e': keyCode = KeyEvent.VK_E; break;
	        case 'f': keyCode = KeyEvent.VK_F; break;
	        case 'g': keyCode = KeyEvent.VK_G; break;
	        case 'h': keyCode = KeyEvent.VK_H; break;
	        case 'i': keyCode = KeyEvent.VK_I; break;
	        case 'j': keyCode = KeyEvent.VK_J; break;
	        case 'k': keyCode = KeyEvent.VK_K; break;
	        case 'l': keyCode = KeyEvent.VK_L; break;
	        case 'm': keyCode = KeyEvent.VK_M; break;
	        case 'n': keyCode = KeyEvent.VK_N; break;
	        case 'o': keyCode = KeyEvent.VK_O; break;
	        case 'p': keyCode = KeyEvent.VK_P; break;
	        case 'q': keyCode = KeyEvent.VK_Q; break;
	        case 'r': keyCode = KeyEvent.VK_R; break;
	        case 's': keyCode = KeyEvent.VK_S; break;
	        case 't': keyCode = KeyEvent.VK_T; break;
	        case 'u': keyCode = KeyEvent.VK_U; break;
	        case 'v': keyCode = KeyEvent.VK_V; break;
	        case 'w': keyCode = KeyEvent.VK_W; break;
	        case 'x': keyCode = KeyEvent.VK_X; break;
	        case 'y': keyCode = KeyEvent.VK_Y; break;
	        case 'z': keyCode = KeyEvent.VK_Z; break;
	        case 'A': shift = true; keyCode = KeyEvent.VK_A; break;
	        case 'B': shift = true; keyCode = KeyEvent.VK_B; break;
	        case 'C': shift = true; keyCode = KeyEvent.VK_C; break;
	        case 'D': shift = true; keyCode = KeyEvent.VK_D; break;
	        case 'E': shift = true; keyCode = KeyEvent.VK_E; break;
	        case 'F': shift = true; keyCode = KeyEvent.VK_F; break;
	        case 'G': shift = true; keyCode = KeyEvent.VK_G; break;
	        case 'H': shift = true; keyCode = KeyEvent.VK_H; break;
	        case 'I': shift = true; keyCode = KeyEvent.VK_I; break;
	        case 'J': shift = true; keyCode = KeyEvent.VK_J; break;
	        case 'K': shift = true; keyCode = KeyEvent.VK_K; break;
	        case 'L': shift = true; keyCode = KeyEvent.VK_L; break;
	        case 'M': shift = true; keyCode = KeyEvent.VK_M; break;
	        case 'N': shift = true; keyCode = KeyEvent.VK_N; break;
	        case 'O': shift = true; keyCode = KeyEvent.VK_O; break;
	        case 'P': shift = true; keyCode = KeyEvent.VK_P; break;
	        case 'Q': shift = true; keyCode = KeyEvent.VK_Q; break;
	        case 'R': shift = true; keyCode = KeyEvent.VK_R; break;
	        case 'S': shift = true; keyCode = KeyEvent.VK_S; break;
	        case 'T': shift = true; keyCode = KeyEvent.VK_T; break;
	        case 'U': shift = true; keyCode = KeyEvent.VK_U; break;
	        case 'V': shift = true; keyCode = KeyEvent.VK_V; break;
	        case 'W': shift = true; keyCode = KeyEvent.VK_W; break;
	        case 'X': shift = true; keyCode = KeyEvent.VK_X; break;
	        case 'Y': shift = true; keyCode = KeyEvent.VK_Y; break;
	        case 'Z': shift = true; keyCode = KeyEvent.VK_Z; break;
	        case '`': keyCode = KeyEvent.VK_BACK_QUOTE; break;
	        case '0': keyCode = KeyEvent.VK_0; break;
	        case '1': keyCode = KeyEvent.VK_1; break;
	        case '2': keyCode = KeyEvent.VK_2; break;
	        case '3': keyCode = KeyEvent.VK_3; break;
	        case '4': keyCode = KeyEvent.VK_4; break;
	        case '5': keyCode = KeyEvent.VK_5; break;
	        case '6': keyCode = KeyEvent.VK_6; break;
	        case '7': keyCode = KeyEvent.VK_7; break;
	        case '8': keyCode = KeyEvent.VK_8; break;
	        case '9': keyCode = KeyEvent.VK_9; break;
	        case '-': keyCode = KeyEvent.VK_MINUS; break;
	        case '=': keyCode = KeyEvent.VK_EQUALS; break;
	        case '~': shift = true; keyCode = KeyEvent.VK_BACK_QUOTE; break;
	        case '!': shift = true; keyCode = KeyEvent.VK_1; break;
	        case '@': shift = true; keyCode = KeyEvent.VK_2; break;
	        case '#': shift = true; keyCode = KeyEvent.VK_3; break;
	        case '$': shift = true; keyCode = KeyEvent.VK_4; break;
	        case '%': shift = true; keyCode = KeyEvent.VK_5; break;
	        case '^': shift = true; keyCode = KeyEvent.VK_6; break;
	        case '&': shift = true; keyCode = KeyEvent.VK_7; break;
	        case '*': shift = true; keyCode = KeyEvent.VK_8; break;
	        case '(': shift = true; keyCode = KeyEvent.VK_9; break;
	        case ')': shift = true; keyCode = KeyEvent.VK_0; break;
	        case '_': shift = true; keyCode = KeyEvent.VK_MINUS; break;
	        case '+': shift = true; keyCode = KeyEvent.VK_EQUALS; break;
	        case '\n': keyCode = KeyEvent.VK_ENTER; break;
	        case '[': keyCode = KeyEvent.VK_OPEN_BRACKET; break;
	        case ']': keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
	        case '\\': keyCode = KeyEvent.VK_BACK_SLASH; break;
	        case '{': shift = true; keyCode = KeyEvent.VK_OPEN_BRACKET; break;
	        case '}': shift = true; keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
	        case '|': shift = true; keyCode = KeyEvent.VK_BACK_SLASH; break;
	        case ';': keyCode = KeyEvent.VK_SEMICOLON; break;
	        case ':': keyCode = KeyEvent.VK_COLON; break;
	        case '\'': keyCode = KeyEvent.VK_QUOTE; break;
	        case '"': keyCode = KeyEvent.VK_QUOTEDBL; break;
	        case ',': keyCode = KeyEvent.VK_COMMA; break;
	        case '<': keyCode = KeyEvent.VK_LESS; break;
	        case '.': keyCode = KeyEvent.VK_PERIOD; break;
	        case '>': keyCode = KeyEvent.VK_GREATER; break;
	        case '/': keyCode = KeyEvent.VK_SLASH; break;
	        case '?': shift = true; keyCode = KeyEvent.VK_SLASH; break;
	        case ' ': keyCode = KeyEvent.VK_SPACE; break;
	        case '	': keyCode = KeyEvent.VK_TAB; break;
		}
		if (shift) // if it's a shift char, click shift first
		{
			parent.keyPress(KeyEvent.VK_SHIFT);
			parent.keyPress(keyCode);
			parent.keyRelease(keyCode);
			parent.keyRelease(KeyEvent.VK_SHIFT);
		}
		else // else just key
		{
			parent.keyPress(keyCode);
			parent.keyRelease(keyCode);
		}
	}
}
