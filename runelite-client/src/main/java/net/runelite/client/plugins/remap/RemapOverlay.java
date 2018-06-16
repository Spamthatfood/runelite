package net.runelite.client.plugins.remap;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.inject.Inject;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TextComponent;

class RemapOverlay extends Overlay
{
	private final RemapPlugin plugin;
	private final RemapConfig config;

	@Inject
	private RemapOverlay(RemapPlugin plugin, RemapConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		graphics.setFont(FontManager.getRunescapeSmallFont());
		final TextComponent textComponent = new TextComponent();
		textComponent.setText(plugin.getCurrentMode().getName());
		textComponent.setPosition(new Point(50, 50));
		textComponent.render(graphics);
		return null;
	}
}
