package net.runelite.client.plugins.remap.modes;

import java.awt.event.KeyEvent;
import net.runelite.client.plugins.remap.Mode;
import net.runelite.client.plugins.remap.RemapPlugin;
import net.runelite.client.plugins.remap.types.CharToMode;
import net.runelite.client.plugins.remap.types.KeyToKey;
import net.runelite.client.plugins.remap.types.KeyToMode;

public class PlayMode extends Mode
{

	public PlayMode(RemapPlugin plugin)
	{
		super(plugin, "PLAY", true);
	}

	@Override
	public void setup()
	{
		// controls
		add(new KeyToKey(KeyEvent.VK_W, KeyEvent.VK_UP));
		add(new KeyToKey(KeyEvent.VK_A, KeyEvent.VK_LEFT));
		add(new KeyToKey(KeyEvent.VK_S, KeyEvent.VK_DOWN));
		add(new KeyToKey(KeyEvent.VK_D, KeyEvent.VK_RIGHT));

		add(new KeyToKey(KeyEvent.VK_Q, KeyEvent.VK_ESCAPE)); // inven
		add(new KeyToKey(KeyEvent.VK_F, KeyEvent.VK_F1)); // attack (fight)
		add(new KeyToKey(KeyEvent.VK_R, KeyEvent.VK_F4)); // equip (remove)
		add(new KeyToKey(KeyEvent.VK_E, KeyEvent.VK_F5)); // prayer
		add(new KeyToKey(KeyEvent.VK_X, KeyEvent.VK_F6)); // magic

		// chats
		add(new CharToMode('t', getPlugin().getTypeMode()));
		add(new CharToMode('T', getPlugin().getTypeMode()));

		add(new CharToMode('c', getPlugin().getTypeMode(), '/'));
		add(new CharToMode('C', getPlugin().getTypeMode(), '/'));

		add(new KeyToMode(KeyEvent.VK_TAB, getPlugin().getTypeMode(), false));

		// passthrough
		add(KeyToKey.passthrough(KeyEvent.VK_SHIFT));
		add(KeyToKey.passthrough(KeyEvent.VK_UP));
		add(KeyToKey.passthrough(KeyEvent.VK_LEFT));
		add(KeyToKey.passthrough(KeyEvent.VK_DOWN));
		add(KeyToKey.passthrough(KeyEvent.VK_RIGHT));
	}
}
