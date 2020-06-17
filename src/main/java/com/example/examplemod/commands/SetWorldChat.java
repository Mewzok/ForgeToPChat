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

public class SetWorldChat implements ICommand
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
		return "setworldchat";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		// TODO Auto-generated method stub
		return "Sets your current chat mode to World.";
	}

	@Override
	public List<String> getAliases()
	{
		// TODO Auto-generated method stub
		List<String> commandAliases = new ArrayList();
		commandAliases.add("world");
		commandAliases.add("worldchat");
		commandAliases.add("w");
		return commandAliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		// TODO Auto-generated method stub
		if (sender instanceof EntityPlayer)
		{
			IChatStates cs = ((EntityPlayer) sender).getCapability(ChatProvider.WC_DEFAULT, null);
			
			cs.setMode("World");
			UtilityMethods.switchChatModeInfo(cs, (EntityPlayer)sender);
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
