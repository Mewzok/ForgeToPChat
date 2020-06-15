package com.example.examplemod.backbone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandlerCommon
{	
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		IChatStates chatStates = player.getCapability(ChatProvider.WC_DEFAULT, null);
		utilitymethods.chatStatusInfo(chatStates, player);
	}
	
	//Save and reset data from dead or moved player
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		EntityPlayer player = event.getEntityPlayer();
		IChatStates chatStates = player.getCapability(ChatProvider.WC_DEFAULT, null);
		IChatStates oldChatStates = event.getOriginal().getCapability(ChatProvider.WC_DEFAULT, null);
		
		chatStates.setWC(oldChatStates.getWC());
		chatStates.setTC(oldChatStates.getTC());
		chatStates.setLC(oldChatStates.getLC());
		chatStates.setGC(oldChatStates.getGC());
	}
}
