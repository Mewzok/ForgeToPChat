package com.example.examplemod.backbone;

import javax.swing.text.html.parser.Entity;

import com.example.examplemod.ExampleMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Attaches capabilities
public class CapabilityHandler
{
	public static final ResourceLocation wc = new ResourceLocation(ExampleMod.MODID, "TOPChat");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent event)
	{
		if(!(event.getObject() instanceof EntityPlayer)) return;
		
		event.addCapability(wc, new ChatProvider());
	}
}
