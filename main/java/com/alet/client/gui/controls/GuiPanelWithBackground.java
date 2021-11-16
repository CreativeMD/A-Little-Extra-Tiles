package com.alet.client.gui.controls;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.gui.client.style.ColoredDisplayStyle;
import com.creativemd.creativecore.common.gui.client.style.Style;
import com.creativemd.creativecore.common.gui.container.GuiParent;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;

public class GuiPanelWithBackground extends GuiParent {
	
	Style panelStyle = new Style("panel", new ColoredDisplayStyle(0, 0, 0, 0), new ColoredDisplayStyle(0, 0, 0, 0), new ColoredDisplayStyle(0, 0, 0, 0), new ColoredDisplayStyle(0, 0, 0, 0), new ColoredDisplayStyle(0, 0, 0, 0));
	
	public GuiPanelWithBackground(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		setStyle(panelStyle);
	}
	
	/** String name, DisplayStyle border, DisplayStyle background, DisplayStyle mouseOverBackground, DisplayStyle face, DisplayStyle disableEffect) */
	public void setColor(int color) {
		Color colour = ColorUtils.IntToRGBA(color);
		int r = colour.getRed();
		int g = colour.getGreen();
		int b = colour.getBlue();
		panelStyle = new Style("panel", new ColoredDisplayStyle(0, 0, 0), new ColoredDisplayStyle(r, g, b), new ColoredDisplayStyle(r, g, b), new ColoredDisplayStyle(0, 0, 0, 0), new ColoredDisplayStyle(0, 0, 0, 0));
		setStyle(panelStyle);
	}
	
}
