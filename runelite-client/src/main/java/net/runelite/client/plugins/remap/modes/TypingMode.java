package net.runelite.client.plugins.remap.modes;

import java.awt.event.KeyEvent;
import net.runelite.client.plugins.remap.Mode;
import net.runelite.client.plugins.remap.RemapPlugin;
import net.runelite.client.plugins.remap.types.KeyToMode;

public class TypingMode extends Mode
{

	public TypingMode(RemapPlugin plugin)
	{
		super(plugin, "TYPE", false);
	}

	@Override
	public void setup()
	{
		add(new KeyToMode(KeyEvent.VK_ENTER, getPlugin().getPlayMode(), false));
	}
}
