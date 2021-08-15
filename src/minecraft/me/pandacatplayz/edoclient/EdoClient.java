package me.pandacatplayz.edoclient;

import me.pandacatplayz.edoclient.event.EventManager;
import me.pandacatplayz.edoclient.gui.SplashProgress;
import me.pandacatplayz.edoclient.rpc.DiscordRP;
import net.minecraft.client.Minecraft;

public class EdoClient {
	
	public static final EdoClient INSTANCE = new EdoClient();
	public Minecraft mc = Minecraft.getMinecraft();
	
	public static final EdoClient getInstance() {
		return INSTANCE;
	}
	
	private DiscordRP discordRP = new DiscordRP();
	
	public void init() {
		//SplashProgress.setProgress(1, "Initializing DiscordRPC");
		discordRP.start();
	}
	
	public void shutdown() {
		discordRP.shutdown();
	}
	
	public DiscordRP getDiscordRP() {
		return discordRP;
	}
}
