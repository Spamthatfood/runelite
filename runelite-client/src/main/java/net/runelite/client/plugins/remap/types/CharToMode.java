package net.runelite.client.plugins.remap.types;

import java.awt.event.KeyEvent;
import lombok.Getter;
import net.runelite.client.plugins.remap.Mode;
import net.runelite.client.plugins.remap.Remapper;

public class CharToMode extends Remapper
{

	@Getter
	private Character fromChar;

	@Getter
	private Character toChar;

	@Getter
	private Mode toMode;

	public CharToMode(Character fromChar, Mode toMode)
	{
		this(fromChar, toMode, null);
	}

	public CharToMode(Character fromChar, Mode toMode, Character toChar)
	{
		this.fromChar = fromChar;
		this.toMode = toMode;
		this.toChar = toChar;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == fromChar)
		{
			if (toChar == null)
			{
				e.consume();
			}
			else
			{
				e.setKeyChar(toChar);
			}

			remapPlugin.setMode(toMode);
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
