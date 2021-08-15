package me.pandacatplayz.edoclient.gui.hud;

public interface IRender extends IRenderConfig {
	
	int getWidth();
	int getHeight();
	
	void render(ScreenPosition pos);
	
	default void renderDummy(ScreenPosition pos) {
		render(pos);
	}
	
	public default boolean isEnabled = true;
}
