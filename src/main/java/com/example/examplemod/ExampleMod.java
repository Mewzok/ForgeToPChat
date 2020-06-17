package com.example.examplemod;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

import com.example.examplemod.commands.ToggleLocalChat;
import com.example.examplemod.commands.ToggleTeamChat;
import com.example.examplemod.commands.ToggleTradeChat;
import com.example.examplemod.commands.ToggleWorldChat;
import com.example.examplemod.commands.ChatStatus;
import com.example.examplemod.commands.SetLocalChat;
import com.example.examplemod.commands.SetTradeChat;
import com.example.examplemod.commands.SetWorldChat;
import com.example.examplemod.proxies.CommonProxy;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "topchat";
    public static final String NAME = "TOPChat";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ExampleMod.proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ExampleMod.proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ExampleMod.proxy.postInit(event);
    }
    
    @EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new ToggleLocalChat());
		//event.registerServerCommand(new teamchat());
		event.registerServerCommand(new ToggleTradeChat());
		event.registerServerCommand(new ToggleWorldChat());
		event.registerServerCommand(new ChatStatus());
		event.registerServerCommand(new SetWorldChat());
		event.registerServerCommand(new SetTradeChat());
		event.registerServerCommand(new SetLocalChat());
	}
    
    @SidedProxy(clientSide="com.example.examplemod.proxies.Clientproxy", serverSide="com.example.examplemod.proxies.ServerProxy")
    public static CommonProxy proxy;
}
