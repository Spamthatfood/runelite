package net.runelite.client.plugins.remap;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.input.KeyListener;

public abstract class Remapper implements KeyListener
{
    /*
    charToChar          # Don't bother consuming the key events
    charToMode          # Flag to consume the type/char event

    keyToKey            # Flag to consume the typed/char events if possible
    keyToChar           # Flag to consume (BOTH) key events
    keyToMode           # Flag to consume (BOTH) key events
    */

	@Setter
	@Getter
	protected RemapPlugin remapPlugin;
}
