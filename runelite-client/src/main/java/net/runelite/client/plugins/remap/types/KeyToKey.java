package net.runelite.client.plugins.remap.types;

import java.awt.event.KeyEvent;
import lombok.Getter;
import net.runelite.client.plugins.remap.Remapper;

public class KeyToKey extends Remapper
{

	public static Remapper passthrough(int keyCode)
	{
		return new KeyToKey(keyCode, keyCode);
	}

	@Getter
	private Integer fromKey;

	@Getter
	private Integer toKey;

	@Getter
	private boolean consume;

	public KeyToKey(Integer fromKey, Integer toKey)
	{
		this(fromKey, toKey, true);
	}

	public KeyToKey(Integer fromKey, Integer toKey, boolean consume)
	{
		this.fromKey = fromKey;
		this.toKey = toKey;
		this.consume = consume;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (fromKey == KeyEvent.getExtendedKeyCodeForChar(e.getKeyChar()) && consume)
		{
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey)
		{
			e.setKeyCode(toKey);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == fromKey)
		{
			e.setKeyCode(toKey);
		}
	}
}
