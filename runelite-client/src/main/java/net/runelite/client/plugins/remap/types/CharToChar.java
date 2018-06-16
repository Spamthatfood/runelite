package net.runelite.client.plugins.remap.types;

import java.awt.event.KeyEvent;
import lombok.Getter;
import net.runelite.client.plugins.remap.Remapper;

public class CharToChar extends Remapper
{

	public static Remapper passthrough(char keyChar)
	{
		return new CharToChar(keyChar, keyChar);
	}

	@Getter
	private Character fromChar;

	@Getter
	private Character toChar;

	public CharToChar(Character fromChar, Character toChar)
	{
		this.fromChar = fromChar;
		this.toChar = toChar;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == fromChar)
		{
			e.setKeyChar(toChar);
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}
}
