package alice.cute.gui.components;


import java.util.ArrayList;

import alice.cute.gui.ClickUI;
import alice.cute.gui.components.sub.CheckboxButton;
import alice.cute.gui.components.sub.ColorPickerButton;
import alice.cute.gui.components.sub.DropDownButton;
import alice.cute.gui.components.sub.KeybindButton;
import alice.cute.gui.components.sub.ModeButton;
import alice.cute.gui.components.sub.SliderButton;
import alice.cute.module.Module;
import alice.cute.setting.Checkbox;
import alice.cute.setting.ColorPicker;
import alice.cute.setting.Mode;
import alice.cute.setting.Setting;
import alice.cute.setting.Slider;
import alice.cute.setting.ListSelection;
import alice.cute.setting.SubSetting;
import alice.cute.util.FontUtil;
import alice.cute.util.RenderUtil;

public class Button extends Component 
{
	public final Module mod;
	public final Frame parent;
	public final ArrayList<Component> subcomponents;

	public int offset;
	
	public Button(Module mod, Frame parent, int offset) 
	{
		this.mod    = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		
		int opY  = offset + this.height;
		int opY2 = offset + this.height;
		
		Component last ;
		
		for(Setting s : mod.getSettings())
		{
			switch(s.getSettingType())
			{
				default:
					System.out.println(s.getName());
					continue;
				case LIST:
					last = new DropDownButton((ListSelection)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
				case CHECKBOX:
					last = new CheckboxButton((Checkbox)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
					
				case SLIDER:
					last = new SliderButton((Slider)s, this, opY);
					this.subcomponents.add(new SliderButton((Slider)s, this, opY));
					opY += this.height;
					break;
					
				case MODE:
					last = new ModeButton((Mode)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
			}
			
			opY2 = opY;
			
			for(SubSetting ss : s.getSubSettings())
			{
				last.subcomponents.add(new ColorPickerButton((ColorPicker)ss, this, opY2));	
				opY2 += this.height;
			}
		}
		
		this.subcomponents.add(new KeybindButton(this, opY));
	}


	@Override
	public int getHeight() 
	{
		if(this.open) 
		{
			int h = this.height;
			for(Component c : this.subcomponents)
			{
				h += c.getHeight();
			}
			return h;
		}
		
		return this.height;
	}

	@Override
	public void setOff(int newOff) 
	{
		this.offset = newOff;
		
		int opY = newOff + this.height;
		
		for(Component comp : this.subcomponents) 
		{
			comp.setOff(opY);
			opY += comp.getHeight();
		}
	}
	
	@Override
	public void renderComponent()
	{
		int x = this.parent.getX();
		int y = this.parent.getY() + this.offset;
		int x2 = x + this.parent.getWidth();
		int y2 = y + this.height;
		
		RenderUtil.beginRenderRect();
		RenderUtil.setColor(this.backColor);
		RenderUtil.renderRect(x, y, x2, y2);
		RenderUtil.setColor(this.sliderColor);
		RenderUtil.renderRect(x, y, x2, y2);
		
		if(this.hovered)
		{
			RenderUtil.renderRect(x, y, x2, y2);
		}
		
		if(this.mod.isEnabled())
		{
			RenderUtil.renderRect(x, y, x2, y2);
		}

		RenderUtil.endRenderRect();
		
		FontUtil.drawTotalCenteredStringWithShadowMC(
				this.mod.getName(), 
				x + this.parent.getWidth() / 2, 
				y + (int)(this.height / 2) + 1, 
				this.textColorInt);

		
		if(!this.open)
			return;
		
		if (this.subcomponents.isEmpty())
			return;
	
		for (Component comp : this.subcomponents) 
		{
			comp.renderComponent();
		}
		
		
		RenderUtil.setColor(ClickUI.color);
		RenderUtil.renderRectSingle(x + 2, y2, x + 3, y + ((this.subcomponents.size() + 1) * this.height));
	}
	
	
	
	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		 this.hovered = isMouseOnButton(mouseX, mouseY);
		
		if(this.subcomponents.isEmpty())
			return;
		
		for(Component comp : this.subcomponents) 
		{
			comp.updateComponent(mouseX, mouseY);
		}
	}
	
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(isMouseOnButton(mouseX, mouseY))
		{
			if(button == 0) 
			{
				this.mod.toggle();
			}

			else if(button == 1) 
			{
				this.open = !this.open;
				this.parent.refresh();
			}
		}
		
			
		for(Component comp : this.subcomponents) 
		{
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	
	@Override
	public void keyTyped(char typedChar, int key) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.keyTyped(typedChar, key);
		}
	}
	
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > parent.getX() && 
			   x < parent.getX() + parent.getWidth() && 
			   y > this.parent.getY() + this.offset &&
			   y < this.parent.getY() + this.height + this.offset;
	}
}











