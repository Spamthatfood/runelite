package net.runelite.client.plugins.remap;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.runelite.client.input.KeyListener;
import net.runelite.client.plugins.remap.types.CharToChar;
import net.runelite.client.plugins.remap.types.CharToMode;
import net.runelite.client.plugins.remap.types.KeyToChar;
import net.runelite.client.plugins.remap.types.KeyToKey;
import net.runelite.client.plugins.remap.types.KeyToMode;

public class Mode implements KeyListener
{

	@Getter
	protected RemapPlugin plugin;

	@Getter
	private String name;

	@Getter
	private boolean consumeUnbound;

	private List<Remapper> remapperList;
	private Map<Character, Remapper> charRemaps;
	private Map<Integer, Remapper> keyRemaps;

	public Mode(RemapPlugin plugin, String name, boolean consumeUnbound)
	{
		this.plugin = plugin;
		this.name = name;
		this.consumeUnbound = consumeUnbound;

		remapperList = new ArrayList<>();
		charRemaps = new HashMap<>();
		keyRemaps = new HashMap<>();
	}

	public void add(Remapper remapper)
	{
		remapper.setRemapPlugin(plugin);
		remapperList.add(remapper);
		cache(remapper);
	}

	public void remove(Remapper remapper)
	{
		remapperList.remove(remapper);
		bust(remapper);
	}

	private void cache(Remapper remapper)
	{
		if (remapper instanceof CharToChar)
		{
			charRemaps.put(((CharToChar) remapper).getFromChar(), remapper);
		}
		else if (remapper instanceof CharToMode)
		{
			charRemaps.put(((CharToMode) remapper).getFromChar(), remapper);
		}
		else if (remapper instanceof KeyToChar)
		{
			keyRemaps.put(((KeyToChar) remapper).getFromKey(), remapper);
		}
		else if (remapper instanceof KeyToKey)
		{
			keyRemaps.put(((KeyToKey) remapper).getFromKey(), remapper);
		}
		else if (remapper instanceof KeyToMode)
		{
			keyRemaps.put(((KeyToMode) remapper).getFromKey(), remapper);
		}
	}

	private void bust(Remapper remapper)
	{
		if (remapper instanceof CharToChar)
		{
			charRemaps.remove(((CharToChar) remapper).getFromChar());
		}
		else if (remapper instanceof CharToMode)
		{
			charRemaps.remove(((CharToMode) remapper).getFromChar());
		}
		else if (remapper instanceof KeyToChar)
		{
			keyRemaps.remove(((KeyToChar) remapper).getFromKey());
		}
		else if (remapper instanceof KeyToKey)
		{
			keyRemaps.remove(((KeyToKey) remapper).getFromKey());
		}
		else if (remapper instanceof KeyToMode)
		{
			keyRemaps.remove(((KeyToMode) remapper).getFromKey());
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (consumeUnbound && !charRemaps.containsKey(e.getKeyChar()))
		{
			e.consume();
		}

		remapperList.forEach(remapper -> remapper.keyTyped(e));
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (consumeUnbound && !keyRemaps.containsKey(e.getKeyCode()))
		{
			e.consume();
		}

		remapperList.forEach(remapper -> remapper.keyPressed(e));
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (consumeUnbound && !keyRemaps.containsKey(e.getKeyCode()))
		{
			e.consume();
		}

		remapperList.forEach(remapper -> remapper.keyReleased(e));
	}

	public void setup()
	{

	}
}
