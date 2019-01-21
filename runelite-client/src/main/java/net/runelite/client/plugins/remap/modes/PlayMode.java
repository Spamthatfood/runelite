package net.runelite.client.plugins.remap.modes;

import java.awt.event.KeyEvent;
import net.runelite.client.plugins.remap.Mode;
import net.runelite.client.plugins.remap.RemapPlugin;
import net.runelite.client.plugins.remap.types.CharToChar;
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
		add(new KeyToKey(KeyEvent.VK_C, KeyEvent.VK_F6)); // magic

		add(new KeyToKey(KeyEvent.VK_X, KeyEvent.VK_F12)); // world / logout
		add(new KeyToKey(KeyEvent.VK_Z, KeyEvent.VK_F2)); // stats
		add(new KeyToKey(KeyEvent.VK_G, KeyEvent.VK_F7)); // cc
		add(new KeyToKey(KeyEvent.VK_V, KeyEvent.VK_F8)); // friends

		// chats
		add(new CharToMode('t', getPlugin().getTypeMode()));
		add(new CharToMode('T', getPlugin().getTypeMode()));
		add(new KeyToMode(KeyEvent.VK_ENTER, getPlugin().getTypeMode()));

		add(new CharToMode('/', getPlugin().getTypeMode(), '/'));

		// passthrough
		add(CharToChar.passthrough('1'));
		add(CharToChar.passthrough('2'));
		add(CharToChar.passthrough('3'));
		add(CharToChar.passthrough('4'));
		add(CharToChar.passthrough('5'));
		add(CharToChar.passthrough('6'));
		add(CharToChar.passthrough('7'));
		add(CharToChar.passthrough('8'));
		add(CharToChar.passthrough('9'));
		add(CharToChar.passthrough('0'));
		add(KeyToKey.passthrough(KeyEvent.VK_ESCAPE));
		add(KeyToKey.passthrough(KeyEvent.VK_BACK_SPACE));
		add(KeyToKey.passthrough(KeyEvent.VK_ENTER));
		add(KeyToKey.passthrough(KeyEvent.VK_TAB));
		add(KeyToKey.passthrough(KeyEvent.VK_SPACE));
		add(KeyToKey.passthrough(KeyEvent.VK_SHIFT));
		add(KeyToKey.passthrough(KeyEvent.VK_UP));
		add(KeyToKey.passthrough(KeyEvent.VK_LEFT));
		add(KeyToKey.passthrough(KeyEvent.VK_DOWN));
		add(KeyToKey.passthrough(KeyEvent.VK_RIGHT));
		add(KeyToKey.passthrough(KeyEvent.VK_CONTROL));
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		plugin.getClientThread().invoke(() -> {
			plugin.lockChat();
		});

		super.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		plugin.getClientThread().invoke(() -> {
			plugin.lockChat();
		});

		super.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		plugin.getClientThread().invoke(() -> {
			plugin.lockChat();
		});

		super.keyTyped(e);
	}
}
