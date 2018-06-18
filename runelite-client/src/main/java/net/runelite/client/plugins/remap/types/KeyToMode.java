package net.runelite.client.plugins.remap.types;

import java.awt.event.KeyEvent;
import lombok.Getter;
import net.runelite.client.plugins.remap.Mode;
import net.runelite.client.plugins.remap.Remapper;

public class KeyToMode extends Remapper
{

	@Getter
	private Integer fromKey;

	@Getter
	private Mode toMode;

	@Getter
	private boolean consume;

	public KeyToMode(Integer fromKey, Mode toMode)
	{
		this(fromKey, toMode, true);
	}

	public KeyToMode(Integer fromKey, Mode toMode, boolean consume)
	{
		this.fromKey = fromKey;
		this.toMode = toMode;
		this.consume = consume;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (fromKey == e.getKeyChar() && consume)
		{
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey)
		{
			if (consume)
			{
				e.consume();
			}

			remapPlugin.setMode(toMode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey && consume)
		{
			e.consume();
		}
	}
}
