package alice.cute.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import alice.cute.module.*;
import alice.cute.module.Module.Category;
import alice.cute.module.modules.render.*;
import alice.cute.util.IMixin;
import alice.cute.util.Util;
import alice.cute.module.modules.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Int;

import alice.cute.util.physics.*;

public class ModuleManager implements IMixin
{
	public static ArrowPhysics arrow = new ArrowPhysics();
	
	public ModuleManager() 
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static List<Module> modules = Arrays.asList
				(
					new BlockESP(),
					new EntityESP(),
					new Fullbright(),
					new Tracers(),
					new ProjectileTracer()
				);
	
	public static List<Module> getModules()
	{
		return new ArrayList(modules);
	}
	
	public static List<Module> getModulesInCategory(Category cat)
	{
		List<Module> module = new ArrayList();
		
		for (Module m : modules) 
		{
			if (m.getCategory().equals(cat))
				module.add(m);
		}
		
		return module;
	}
	
	public static Module getModuleByName(String name) 
	{
		return null;
		// return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public static Module getModuleByClass(Class<?> clazz) 
	{
		return null;
		// return modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onTick(TickEvent.ClientTickEvent event) 
	{
		ModuleManager.onUpdate();
		
		if(mc.thePlayer == null || mc.theWorld == null)
			return;
		
//		ItemStack item = mc.thePlayer.getHeldItem();
////		
//		if(item == null)
//			return;
////		
//		if(item.getItem() instanceof ItemBow) 
//		{
//			ItemBow i = (ItemBow)item.getItem();
//			String message = "";
//			
//			
//			message += "item in use time: " + mc.thePlayer.getItemInUseDuration();
////			message += "\nitem use action: " + i.getItemUseAction(item);
//			message += "\narrow launch speed: " + arrow.launchSpeed(mc.thePlayer);
////			message += "\nitem max use duration: " + Items.bow.getMaxItemUseDuration(item); 
//			
//			
//			Util.sendRawClientMessage(message);
//		}
		
		// Util.sendRawClientMessage(Integer.toString(mc.thePlayer.arrowHitTimer));
//		ModuleManager.keyListen();
		// ThemeColor.updateColors();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onFastTick(TickEvent event) 
	{
		ModuleManager.onFastUpdate();
	}

	
	public static void onUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())			
				m.onUpdate();
		}
	}
	
	public static void onFastUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())
				m.onFastUpdate();
		}
	}
	
	public static void onServerUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())
				m.onServerUpdate();
		}
	}
	
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void keyboardEvent(InputEvent.KeyInputEvent key) 
	{
		if (mc.currentScreen instanceof GuiScreen)
			return;
		
		for (Module m : modules) 
		{
			try 
			{
				if (Keyboard.isKeyDown(m.getKeybind().getKeyCode()) && !m.isKeyDown()) 
				{
					m.setKeyDown(true);
					m.toggle();
				}
				else 
				{
					m.setKeyDown(false);
				}
			}
			catch (Exception e) 
			{
				 e.printStackTrace();
			}
		}
	}
//	
//	public static void keyListen() 
//	{
//		if (mc.currentScreen instanceof GuiScreen)
//			return;
//		
//		for (Module m : modules) 
//		{
//			try 
//			{
//				if (Keyboard.isKeyDown(Keyboard.KEY_NONE) || Keyboard.isKeyDown(Keyboard.CHAR_NONE))
//					return;
//				
//				if (Keyboard.isKeyDown(m.getKeybind().getKeyCode()) && !m.isKeyDown()) 
//				{
//					m.setKeyDown(true);
//					m.toggle();
//				}
//	
//				else if (!Keyboard.isKeyDown(m.getKeybind().getKeyCode()))
//					m.setKeyDown(false);
//	
//			}
//			catch (Exception e) 
//			{
//				 e.printStackTrace();
//			}
//		}
//	}
}
