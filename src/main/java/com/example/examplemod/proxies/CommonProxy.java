package com.example.examplemod.proxies;

import com.example.examplemod.backbone.CapabilityHandler;
import com.example.examplemod.backbone.ChatStateStorage;
import com.example.examplemod.backbone.ChatStates;
import com.example.examplemod.backbone.IChatStates;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy
{	
	public void preInit(FMLPreInitializationEvent e)
	{
		CapabilityManager.INSTANCE.register(IChatStates.class, new ChatStateStorage(), ChatStates::new);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
	}
	
	public void init(FMLInitializationEvent e) 
	{

	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}
