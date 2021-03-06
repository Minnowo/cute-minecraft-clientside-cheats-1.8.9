package alice.cute.module;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import alice.cute.Reference;
import alice.cute.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

public class Module
{
	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected String     _name;
	protected Category   _category;
	protected String     _description;
	protected KeyBinding _key;

	protected boolean _enabled;
	protected boolean _opened;
	protected boolean _drawn;
	protected boolean _isKeyDown = false;
	protected boolean _isBinding;
	protected float   _remainingAnimation = 0.0f;

	protected List<Setting> _settingsList = new ArrayList();

	public Module(String name, Category category, @Nullable String description) 
	{
		this._name = name;
		this._category = category;
		this._description = description;
		this._enabled = false;
		this._opened = false;
		this._drawn = true;

		this._key = new KeyBinding(name, Keyboard.KEY_NONE, Reference.NAME + " Keybind");
		
		// WTF i just spent like 2 hours wondering why the fucking keycode wasn't updating
		// and it gets set to 0 if oyu register it and it just doesn't work wt
		// ClientRegistry.registerKeyBinding(this._key);
		this.setup();
		
		
	}

	public boolean nullCheck() 
	{
		return mc.theWorld == null || 
			   mc.thePlayer == null;
	}
	
	public void setup() 
	{

	}

	public List<Setting> getSettings()
	{
		return this._settingsList;
	}
	
	public void addSetting(Setting s) 
	{
		this._settingsList.add(s);
	}

	public boolean isEnabled() 
	{
		return this._enabled;
	}

	public boolean isKeyDown() 
	{
		return this._isKeyDown;
	}

	public void setKeyCode(int key) 
	{
		this._key.setKeyCode(key);
	}
	
	public void unbindKey()
	{
		this._key.setKeyCode(Keyboard.KEY_NONE);
	}
	
	public void setKeyDown(boolean b) 
	{
		this._isKeyDown = b;
	}

	public void onEnable() 
	{
		this._remainingAnimation = 0.0f;

//		if (ModuleManager.getModuleByClass(EnableMessage.class).isEnabled() && 
//				!this._name.equalsIgnoreCase("ClickGUI"))
//			MessageUtil.sendClientMessage(this._name + ChatFormatting.GREEN + " ENABLED");

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void onDisable() 
	{
		this._remainingAnimation = 0.0f;
		// mc.timer.tickLength = 50;
		
//		if (ModuleManager.getModuleByClass(EnableMessage.class).isEnabled() && 
//				!this._name.equalsIgnoreCase("ClickGUI"))
//			MessageUtil.sendClientMessage(this._name + ChatFormatting.RED + " DISABLED");

		MinecraftForge.EVENT_BUS.unregister(this);
	}

	public void onToggle() 
	{
		this._remainingAnimation = 0.0f;
	}

	public void onUpdate() 
	{

	}

	public void onFastUpdate() 
	{

	}

	public void onServerUpdate() 
	{

	}

	public void onValueChange() 
	{

	}

	public void toggle() 
	{
		this._enabled = !this._enabled;
		
		try 
		{
			if (this.isEnabled()) 
			{
				this.onEnable();	
			}
			else 
			{
				this.onDisable();
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void enable() 
	{
		if (this.isEnabled())
			return;
		
		this._enabled = true;
		
		try 
		{
			this.onEnable();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void disable() 
	{
		if (!this.isEnabled()) 
			return;
		
		this._enabled = false;
		
		try 
		{
			this.onDisable();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void setEnabled(boolean enabled) 
	{
		this._enabled = enabled;
		
		if(this._enabled) 
		{
			this.onEnable();
		}
		else 
		{
			this.onDisable();
		}
	}

	public String getName() 
	{
		return this._name;
	}

	public Category getCategory() 
	{
		return this._category;
	}

	public String getDescription() 
	{
		return this._description;
	}

	public KeyBinding getKeybind() 
	{
		return this._key;
	}

	public String getHUDData() 
	{
		return "";
	}

	public boolean hasSettings() 
	{
		return this._settingsList.size() > 0;
	}

	public void toggleState() 
	{
		this._opened = !this._opened;
	}

	public boolean isOpened() 
	{
		return this._opened;
	}

	public boolean isBinding() 
	{
		return this._isBinding;
	}

	public boolean isDrawn() 
	{
		return this._drawn;
	}

	public void setBinding(boolean b) 
	{
		this._isBinding = b;
	}

	public void setDrawn(boolean in) 
	{
		this._drawn = in;
	}

	
	
}
