package net.runelite.client.plugins.remap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import javax.inject.Inject;
import net.runelite.client.plugins.remap.modes.PlayMode;
import net.runelite.client.plugins.remap.modes.TypingMode;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class RemapOverlay extends Overlay
{
	private final RemapPlugin plugin;
	private final RemapConfig config;

	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	private RemapOverlay(RemapPlugin plugin, RemapConfig config)
	{
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();
		final String modeString = plugin.getCurrentMode().getName();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text(modeString)
			.color(plugin.getCurrentMode() instanceof TypingMode ? Color.RED : Color.WHITE)
			.build());

		panelComponent.setPreferredSize(new Dimension(
			graphics.getFontMetrics().stringWidth(modeString) + 10,
			0));

		return panelComponent.render(graphics);
	}
}
