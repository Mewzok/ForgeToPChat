package com.example.examplemod.backbone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class utilitymethods
{
	public static String wcStat;
	public static String tcStat;
	public static String lcStat;
	public static String gcStat;
	
	public static void chatStatusInfo(IChatStates cs, EntityPlayer player)
	{
		// Get and set chat settings to strings
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
		
		String message = String.format("World chat is " + wcStat + TextFormatting.WHITE + "\nTrade chat is " + tcStat + 
				TextFormatting.WHITE +  "\nLocal chat is " + lcStat + TextFormatting.WHITE + "\nTeam chat is " + gcStat);
		player.sendMessage(new TextComponentString(message));
	}
	
}
