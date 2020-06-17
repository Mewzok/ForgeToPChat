package com.example.examplemod.backbone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class UtilityMethods
{
	public static String wcStat;
	public static String tcStat;
	public static String lcStat;
	public static String gcStat;
	
	// Get and set chat settings to strings
	public static void chatStatusInfo(IChatStates cs, EntityPlayer player)
	{
		if(cs.getWC()==true)
			wcStat = TextFormatting.GREEN + "enabled";
		else
			wcStat = TextFormatting.RED + "disabled";
		
		if(cs.getTC()==true)
			tcStat = TextFormatting.GREEN + "enabled";
		else
			tcStat = TextFormatting.RED + "disabled";
		
		if(cs.getLC()==true)
			lcStat = TextFormatting.GREEN + "enabled";
		else
			lcStat = TextFormatting.RED + "disabled";
		
		if(cs.getGC()==true)
			gcStat = TextFormatting.GREEN + "enabled";
		else
			gcStat = TextFormatting.RED + "disabled";
		
		String message = String.format("Viewing World chat is " + wcStat + TextFormatting.WHITE + "\nViewing Trade chat is " + tcStat + 
				TextFormatting.WHITE +  "\nViewing Local chat is " + lcStat + TextFormatting.WHITE + "\nViewing Team chat is " + gcStat)
				+ "\nYou are currently speaking in " + TextFormatting.BOLD + cs.getMode() + TextFormatting.RESET + " chat";
		player.sendMessage(new TextComponentString(message));
	}
	
	// Display the player's current chat mode
	public static void currentChatModeInfo(IChatStates cs, EntityPlayer player)
	{
		String msg = "You are currently speaking in " + TextFormatting.BOLD + cs.getMode() + TextFormatting.RESET + " chat.";
		player.sendMessage(new TextComponentString(msg));
	}
	
	// Display a message notifying the player of their chat mode switch
	public static void switchChatModeInfo(IChatStates cs, EntityPlayer player)
	{
		String msg = "You are now speaking in " + TextFormatting.BOLD + cs.getMode() + TextFormatting.RESET + " chat.";
		player.sendMessage(new TextComponentString(msg));
	}
}
