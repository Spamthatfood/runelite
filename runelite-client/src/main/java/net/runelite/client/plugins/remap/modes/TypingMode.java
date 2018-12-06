package net.runelite.client.plugins.remap.modes;

import com.google.common.base.Strings;
import java.awt.event.KeyEvent;
import net.runelite.api.VarClientStr;
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

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (Strings.isNullOrEmpty(plugin.getClient().getVar(VarClientStr.CHATBOX_TYPED_TEXT)))
			{
				plugin.setMode(plugin.getPlayMode());
				return;
			}
		}
		super.keyPressed(e);
	}
}
