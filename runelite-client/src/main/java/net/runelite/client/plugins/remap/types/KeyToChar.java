package net.runelite.client.plugins.remap.types;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.remap.Remapper;

@Slf4j
public class KeyToChar extends Remapper
{

	private Robot robot;

	@Getter
	private Integer fromKey;

	@Getter
	private Character toChar;

	@Getter
	private boolean consume;

	public KeyToChar(Integer fromKey, Character toChar)
	{
		this(fromKey, toChar, true);
	}

	public KeyToChar(Integer fromKey, Character toChar, boolean consume)
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}

		this.fromKey = fromKey;
		this.toChar = toChar;
		this.consume = consume;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey && consume)
		{
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey)
		{
			if (consume)
			{
				e.consume();
			}

			if (robot != null)
			{
				// Hopefully use mixin here (dont know how yet)
				//robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(toChar));
			}
			else
			{
				log.info("Failed to create robot");
			}
		}
	}
}
