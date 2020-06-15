package com.example.examplemod.backbone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandler
{
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event)
	{
		System.out.println("hellop");
		EntityPlayer player = event.player;
		IChatStates chatStates = player.getCapability(ChatProvider.wc, null);
		
		String message = String.format("Hello there General Kenobi, you have World Chat " + chatStates.getChatStates());
		player.sendMessage(new TextComponentString(message));
	}
	
	//Save and reset data from dead or moved player
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		EntityPlayer player = event.getEntityPlayer();
		IChatStates chatStates = player.getCapability(ChatProvider.wc, null);
		IChatStates oldChatStates = event.getOriginal().getCapability(ChatProvider.wc, null);
		
		chatStates.setChatStates(oldChatStates.getChatStates());
	}
}
