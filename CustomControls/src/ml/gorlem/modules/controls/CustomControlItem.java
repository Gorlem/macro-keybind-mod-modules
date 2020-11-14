package ml.gorlem.modules.controls;

import static com.mumfrey.liteloader.gl.GL.*;

import java.awt.Rectangle;

import net.eq2online.macros.compatibility.LocalisationProvider;
import net.eq2online.macros.gui.designable.DesignableGuiControl;
import net.eq2online.macros.gui.designable.DesignableGuiLayout;
import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
import net.eq2online.macros.gui.shared.GuiScreenEx;
import net.eq2online.macros.scripting.VariableExpander;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.variable.ItemID;
import net.eq2online.macros.struct.ItemStackInfo;
import net.eq2online.util.Colour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class CustomControlItem extends DesignableGuiControl {
	
	private RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	private IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
	
	private ItemStack item;

	public CustomControlItem(int id) {
		super(id);
		
		ItemControlLocalisation.register();
	}

	@Override
	public boolean getWidgetIsBindable() {
		return false;
	}

	@Override
	protected void initProperties() {
		this.setProperty("item", "grass");
		
		this.setProperty("colour", foreColour);
		this.setProperty("background", backColour);
		
		this.setProperty("itembox", true);
		this.setProperty("controlbox", false);
		
		this.setProperty("visible", true);
	}
	
	@Override
	public void setPropertyWithValidation(String prop, String stringValue,
			int intValue, boolean boolValue) {
		
		if( prop.equals("item") )
			this.setProperty(prop, stringValue);
		
		if( prop.equals("itembox") || prop.equals("controlbox") )
			this.setProperty(prop, boolValue);
		
		if( prop.equals("colour") || prop.equals("background") )
			this.setProperty(prop, Colour.sanitiseColour(stringValue, prop.equals("colour") ? foreColour : backColour));
		
		super.setPropertyWithValidation(prop, stringValue, intValue, boolValue);
	}

	@Override
	protected void onTick() {
		String name = new VariableExpander(provider, null, getProperty("item", ""), false).toString();
		
		item = new ItemID(name).toItemStack(1);
	}

	@Override
	protected String getControlType() {
		return "item";
	}

	@Override
	protected void update() {
		foreColour = Colour.parseColour(this.getProperty("colour", "FF00FF00"), foreColour);
		backColour = Colour.parseColour(this.getProperty("background", "B0000000"), backColour);
	}

	@Override
	protected void draw(DesignableGuiLayout parent, Rectangle boundingBox,
			int mouseX, int mouseY) {
		
		if( this.isVisible() )
			drawItemBox(boundingBox, true);
	}

	@Override
	protected void drawWidget(DesignableGuiLayout parent,
			Rectangle boundingBox, int mouseX, int mouseY, int drawColor) {
		drawItemBox(boundingBox, false);
	}
	
	private void drawItemBox(Rectangle box, boolean backdrop) {
		int centerX = (int) box.getCenterX();
		int centerY = (int) box.getCenterY();
		
		if(backdrop) {
			if( this.getProperty("controlbox", false) ) {
				drawRect(box, backColour);
				drawRectOutline(box, foreColour, 1);
			}
			if( this.getProperty("itembox", true) ) {
				Rectangle itemBox = new Rectangle(centerX-10, centerY-10, 20, 20);
				
				drawRect(itemBox, backColour);
				drawRectOutline(itemBox, foreColour, 1);
			}
		}
		
		if(item != null && item.getItem() != null)
			drawItem(item, centerX-8, centerY-8);
	}
	
	private void drawItem(ItemStack stack, int x, int y) {
		glDepthFunc(GL_LEQUAL);
        glEnableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();

    	renderItem.renderItemIntoGUI(stack, x, y);
    	
        glDisableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        glDepthFunc(515);
	}
	
	@Override
	public GuiDialogBoxControlProperties getPropertiesDialog(
			GuiScreenEx parentScreen) {
		return new Properties(parentScreen, this);
	}
	
	public class Properties extends GuiDialogBoxControlProperties {

		public Properties(GuiScreenEx parentScreen, DesignableGuiControl control) {
			super(parentScreen, control);
		}
		
		@Override
		protected void initDialog() {
			super.initDialog();
			
			this.addTextField("Item Name", "item", false);
			
			this.addColourButton(LocalisationProvider.getLocalisedString("control.properties.forecolour"),
					"colour", foreColour);
			this.addColourButton(LocalisationProvider.getLocalisedString("control.properties.backcolour"),
					"background", backColour);
			
			this.addCheckBox("Show box around item", "itembox");
			this.addCheckBox("Show box around Control", "controlbox");
		}
		
	}

}
