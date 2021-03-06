package alice.cute.gui.components.sub;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import alice.cute.gui.components.Button;
import alice.cute.gui.components.Component;
import alice.cute.setting.Checkbox;
import alice.cute.setting.ListSelection;
import alice.cute.setting.enums.ListType;
import alice.cute.util.FontUtil;
import alice.cute.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;

public class DropDownButton extends Component
{
	private final Button parent;
	private final ListSelection setting;
	private boolean hovered;
	
	private int offset;
	private int x;
	private int y;
	
	private int scrollButtonSize = this.height - 3;
	private int scrollIndex = 0;
	
	private ListType type;
	
	public DropDownButton(ListSelection setting, Button button, int offset) 
	{
		this.setting = setting;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		
		this.type = setting.getListType();
	}
	

	@Override
	public int getHeight() 
	{
		return this.height;
	}

	
	public int getListHeight()
	{
		return this.height * Math.min(7, this.setting.getSize()) + this.scrollButtonSize * 2;
	}
	
	public int getListY()
	{
		return this.y - this.scrollButtonSize;
	}
	
	public int getListX()
	{
		return this.x + this.width;
	}
	
	@Override
	public void setOff(int newOff) 
	{
		this.offset = newOff;
	}

	

	@Override
	public void renderComponent() 
	{
		RenderUtil.beginRenderRect();
		
//		background 
		RenderUtil.setColor(this.backColor);
		RenderUtil.renderRect(x + 2, y, x + width, y + this.getHeight());
		RenderUtil.renderRect(x    , y, x + 2    , y + this.getHeight());
		RenderUtil.endRenderRect();
		
		// draw the text for the setting 
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);

		FontUtil.drawStringWithShadow(
				this.setting.getName(), 
				(this.x + 3) * this.tScale + 4, 
				(this.y + 2) * this.tScale + 2,
				this.textColorInt);
		
		GL11.glPopMatrix();
		
		
		// if the block list isn't open we're done here
		if(!this.isOpen())
			return;
		
		int lx = this.getListX();
		int ly = this.getListY();
		int range = Math.min(this.setting.getSize(), scrollIndex + 7);
		
		// render background for the list of blocks 
		RenderUtil.setColor(this.backColor);
		RenderUtil.renderRectSingle(lx + 2, ly, lx + width, ly + this.getListHeight());
		
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);
		
		// render up and down arrows for the scroll buttons 
		FontUtil.drawStringWithShadow(
				"/\\     " + String.valueOf(scrollIndex) + "-" + String.valueOf(range) + "/" + String.valueOf(this.setting.getSize()), 
				lx * this.tScale + 4, 
				ly * this.tScale + 4, 
				this.textColorInt);
		
		FontUtil.drawStringWithShadow(
				"\\/", 
				lx * this.tScale + 4, 
				(ly + this.getListHeight() - this.scrollButtonSize) * this.tScale + 2, 
				this.textColorInt);
		
		
		// render all the blocks 
		ly += this.scrollButtonSize;
		
		for(int i = scrollIndex; i < Math.min(this.setting.getSize(), scrollIndex + 7); i++)
		{
			String display;
			
			// this control is also supposed to work for pots, so need the proper toString
			if(this.type == ListType.BLOCK)
			{
				display = ((Block)this.setting.getItem(i)).getLocalizedName();
			}
			else
			{
				display = ((Potion)this.setting.getItem(i)).getName();
			}
			
			FontUtil.drawStringWithShadow(
					display + " ", 
					lx * this.tScale + 4, 
					ly * this.tScale + 4, 
					this.textColorInt);

			ly += this.height;
		}
		
		GL11.glPopMatrix();
	}
	

	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen())
			return;
		
		if(button == 1)
		{
			if(!isMouseOnButton(mouseX, mouseY))
				return;
			
			this.open = !this.open;
			return;
		}
		
		if(button == 0)
		{
			if(this.isMouseOnList(mouseX, mouseY))
			{
				int index = this.getListHoverIndex(mouseX, mouseY);
				
				if(index >= 0 && index < this.setting.getSize())
				{
					System.out.println(this.setting.getItem(index));
				}
				return;
			}
			
			if(this.isMouseOnScrollUp(mouseX, mouseY))
			{
				this.scrollIndex = Math.max(0, this.scrollIndex - 1);
				return;
			}
			
			if(this.isMouseOnScrollDown(mouseX, mouseY))
			{	
				this.scrollIndex = Math.min(this.setting.getSize()-7, this.scrollIndex + 1);
				return;
			}
			
		}		
	}	
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		
	}
	
	public int getListHoverIndex(int x, int y)
	{
		return (int)((y - this.y) / this.height);
	}
	
	public boolean isMouseOnScrollUp(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.getListY();
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + this.scrollButtonSize;
	}
	
	public boolean isMouseOnScrollDown(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.getListY() + this.getListHeight() - this.scrollButtonSize;
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + this.scrollButtonSize;
	}
	
	
	public boolean isMouseOnList(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.y;
		int height = this.getListHeight() - this.scrollButtonSize*2;
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + height;
	}
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + this.width && 
			   y > this.y &&
			   y < this.y + this.height;
	}
}
