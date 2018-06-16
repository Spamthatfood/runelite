package net.runelite.client.plugins.remap;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.remap.modes.PlayMode;
import net.runelite.client.plugins.remap.modes.TypingMode;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Modal bindings"
)
@Slf4j
public class RemapPlugin extends Plugin
{

	@Inject
	private RemapConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RemapOverlay overlay;

	private KeyListener keyListener;

	private boolean active = false;

	@Getter
	private Mode currentMode;

	@Getter
	private Mode playMode = new PlayMode(this);

	@Getter
	private Mode typeMode = new TypingMode(this);

	@Provides
	RemapConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RemapConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);

		makeKeyListener();
		keyManager.registerKeyListener(keyListener);

		playMode.setup();
		typeMode.setup();

		setMode(playMode);
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(keyListener);
	}


	@Subscribe
	public void onGameStateChanged(final GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			active = true;
		}
	}

	public void setMode(Mode mode)
	{
		this.currentMode = mode;
		log.info("Current mode: {}", mode.getName());
	}

	private KeyListener makeKeyListener()
	{
		keyListener = new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (!active)
				{
					return;
				}
				currentMode.keyTyped(e);
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (!active)
				{
					return;
				}
				currentMode.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (!active)
				{
					return;
				}
				currentMode.keyReleased(e);
			}
		};

		return keyListener;
	}
}
