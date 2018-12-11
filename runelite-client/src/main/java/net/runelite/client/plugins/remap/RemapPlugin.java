package net.runelite.client.plugins.remap;

import com.google.inject.Provides;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.IconID;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.remap.modes.PlayMode;
import net.runelite.client.plugins.remap.modes.TypingMode;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;

@PluginDescriptor(
	name = "Modal bindings",
	description = "Remap functionality in rs to other keys"
)
@Slf4j
public class RemapPlugin extends Plugin
{

	private static final String PRESS_ENTER_TO_CHAT = "Press Enter to Chat...";

	@Inject
	private RemapConfig config;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

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
	private Mode playMode;

	@Getter
	private Mode typeMode;

	@Provides
	RemapConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RemapConfig.class);
	}

	@Override
	protected void startUp()
	{
		//overlayManager.add(overlay);

		makeKeyListener();
		keyManager.registerKeyListener(keyListener);

		playMode = new PlayMode(this);
		typeMode = new TypingMode(this);

		playMode.setup();
		typeMode.setup();

		setMode(playMode);

		active = client.getGameState() != GameState.LOGIN_SCREEN;
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(keyListener);
	}


	@Subscribe
	public void onGameStateChanged(final GameStateChanged event)
	{
		active = event.getGameState() != GameState.LOGIN_SCREEN;
		if (active && playMode == playMode)
		{
			clientThread.invoke(() -> {
				lockChat();
			});
		}
	}

	public void setMode(Mode mode)
	{
		if (client.getGameState() == GameState.LOGIN_SCREEN && mode == playMode)
		{
			this.currentMode = typeMode;
			return;
		}

		this.currentMode = mode;
		if (this.currentMode == playMode)
		{
			clientThread.invoke(() ->
				{
					lockChat();
				}
			);
		}
		else
		{
			clientThread.invoke(() ->
			{
				unlockChat();
			});
		}

		log.info("Current mode: {}", mode.getName());
	}

	public void lockChat()
	{
		Widget chatboxInput = client.getWidget(WidgetInfo.CHATBOX_INPUT);
		if (chatboxInput != null)
		{
			if (chatboxFocused())
			{
				chatboxInput.setText(getPlayerNameWithIcon() + ": " + PRESS_ENTER_TO_CHAT);
			}
		}
	}

	public void unlockChat()
	{
		Widget chatboxInput = client.getWidget(WidgetInfo.CHATBOX_INPUT);
		if (chatboxInput != null)
		{
			if (chatboxFocused())
			{
				final boolean isChatboxTransparent = client.isResized() && client.getVar(Varbits.TRANSPARENT_CHATBOX) == 1;
				final Color textColor = isChatboxTransparent ? JagexColors.CHAT_TYPED_TEXT_TRANSPARENT_BACKGROUND : JagexColors.CHAT_TYPED_TEXT_OPAQUE_BACKGROUND;
				chatboxInput.setText(getPlayerNameWithIcon() + ": " + ColorUtil.wrapWithColorTag(client.getVar(VarClientStr.CHATBOX_TYPED_TEXT) + "*", textColor));
			}
		}
	}


	private String getPlayerNameWithIcon()
	{
		IconID icon;
		switch (client.getAccountType())
		{
			case IRONMAN:
				icon = IconID.IRONMAN;
				break;
			case ULTIMATE_IRONMAN:
				icon = IconID.ULTIMATE_IRONMAN;
				break;
			case HARDCORE_IRONMAN:
				icon = IconID.HARDCORE_IRONMAN;
				break;
			default:
				return client.getLocalPlayer().getName();
		}
		return icon + "kernal_task";
	}

	boolean chatboxFocused()
	{
		Widget chatboxParent = client.getWidget(WidgetInfo.CHATBOX_PARENT);
		if (chatboxParent == null || chatboxParent.getOnKeyListener() == null)
		{
			return false;
		}

		// the search box on the world map can be focused, and chat input goes there, even
		// though the chatbox still has its key listener.
		Widget worldMapSearch = client.getWidget(WidgetInfo.WORLD_MAP_SEARCH);
		if (worldMapSearch != null && client.getVar(VarClientInt.WORLD_MAP_SEARCH_FOCUSED) == 1)
		{
			return false;
		}

		return true;
	}

	private KeyListener makeKeyListener()
	{
		keyListener = new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (!active || !chatboxFocused())
				{
					return;
				}
				currentMode.keyTyped(e);
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (!active || !chatboxFocused())
				{
					return;
				}
				currentMode.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (!active || !chatboxFocused())
				{
					return;
				}
				currentMode.keyReleased(e);
			}
		};

		return keyListener;
	}

	public Client getClient()
	{
		return client;
	}

	public ClientThread getClientThread()
	{
		return clientThread;
	}
}
