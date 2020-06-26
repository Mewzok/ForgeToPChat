package com.example.examplemod.commands;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.backbone.ChatProvider;
import com.example.examplemod.backbone.IChatStates;
import com.example.examplemod.backbone.UtilityMethods;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ChatStatus implements ICommand
{
	@Override
	public int compareTo(ICommand arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return "chatstatus";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		// TODO Auto-generated method stub
		return "Displays current status of world chat.";
	}

	@Override
	public List<String> getAliases()
	{
		// TODO Auto-generated method stub
		List<String> commandAliases = new ArrayList();
		commandAliases.add("cs");
		commandAliases.add("chatstats");
		commandAliases.add("chats");
		return commandAliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		String is = "";
		// Set wording for chat state
		if(sender instanceof EntityPlayer)
		{
			// Get player information stored in cs
			IChatStates cs = ((EntityPlayer) sender).getCapability(ChatProvider.WC_DEFAULT, null);
			
			UtilityMethods.chatStatusInfo(cs, (EntityPlayer) sender);
		}

	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
