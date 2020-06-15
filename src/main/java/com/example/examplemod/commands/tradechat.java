package com.example.examplemod.commands;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.backbone.ChatProvider;
import com.example.examplemod.backbone.IChatStates;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class tradechat implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "toggletradechat";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Toggles trade chat on or off.";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		List<String> commandAliases = new ArrayList();
		commandAliases.add("tc");
		commandAliases.add("ttc");
		commandAliases.add("ttrade");
		commandAliases.add("tradechat");
		return commandAliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		if ( sender instanceof EntityPlayer ) 
		{
			IChatStates cs = ((EntityPlayer) sender).getCapability(ChatProvider.WC_DEFAULT, null);
			
			// Check for state, then toggle
			if(cs.getTC()==true)
			{
				cs.setTC(false);
				sender.sendMessage(new TextComponentString("Trade chat has been " + TextFormatting.RED + "disabled."));
			}
			else
			{
				cs.setTC(true);
				sender.sendMessage(new TextComponentString("Trade chat has been " + TextFormatting.GREEN + "enabled"));
			}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
