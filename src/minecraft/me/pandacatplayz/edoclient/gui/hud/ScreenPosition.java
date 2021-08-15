package me.pandacatplayz.edoclient.gui.hud;

import net.minecraft.client.Minecraft;

public class ScreenPosition {
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	private double x, y; 
	
	public ScreenPosition(double x, double y) {
		setRelative(x, y);
	}
	
	public ScreenPosition(int x, int y) {
		setAbsolute(x, y);
	}
	
	public static ScreenPosition fromRelativePostion(double x, double y) {
		return new ScreenPosition(x, y);
	}
	
	public static ScreenPosition fromAbsolutePostion(int x, int y) {
		return new ScreenPosition(x, y);
	}
	
	private void setRelative(double x2, double y2) {
		
	}
	
	private void setAbsolute(int x2, int y2) {
	}
}
