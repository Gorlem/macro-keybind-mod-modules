package ml.gorlem.modules.params;

import java.util.ArrayList;
import java.util.List;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

import ml.gorlem.modules.lib.params.BaseVariableProvider;
import net.eq2online.macros.compatibility.AbstractionLayer;
import net.eq2online.macros.core.MacroModCore;
import net.eq2online.macros.core.params.MacroParam;
import net.eq2online.macros.gui.controls.GuiListBox;
import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
import net.eq2online.macros.gui.controls.ParamsHelper;
import net.eq2online.macros.gui.controls.specialised.GuiListBoxFriends;
import net.eq2online.macros.gui.controls.specialised.GuiListBoxHomes;
import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
import net.eq2online.macros.gui.controls.specialised.GuiListBoxTowns;
import net.eq2online.macros.gui.controls.specialised.GuiListBoxWarps;
import net.eq2online.macros.gui.helpers.ListProvider;
import net.eq2online.macros.interfaces.IListObject;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMutableArrayProvider;
import net.eq2online.macros.struct.Place;
import net.minecraft.util.MathHelper;

@APIVersion(17)
public class VariableProviderParams extends BaseVariableProvider implements IMutableArrayProvider {
	
	ListProvider listProvider;
	
	private enum Prompts {
		FRIENDS(MacroParam.Type.Friend),
		PLACES(MacroParam.Type.Place),
		WARPS(MacroParam.Type.Warp),
		HOMES(MacroParam.Type.Home),
		TOWNS(MacroParam.Type.Town);
		
		private MacroParam.Type type;
		
		private Prompts(MacroParam.Type type) {
			this.type = type;
		}
		
		public MacroParam.Type getType() {
			return this.type;
		}
	}
	
	private final int DEFAULT_ICON = 0;

	@Override
	public void populate() {
		listProvider = MacroModCore.getInstance().getListProvider();
	}
	
	@Override
	public void updateVariables(boolean clock) {
		
		for( Prompts prompt : Prompts.values() ) {
			store(prompt.toString(), getValues(prompt.getType()));
		}
		
	}
	
	private List<String> getValues(MacroParam.Type type) {
		GuiListBox box = listProvider.getListBox(type.toString());
		
		if( box != null ) {
			ArrayList<String> values = new ArrayList<String>();
			
			for( IListObject item : ParamsHelper.getItems(box) ) {
				if( item.getId() == -1 )
					continue;
				values.add(item.getDisplayName());
			}
			
			return values;
		}
		
		return null;
	}

	@Override
	public boolean push(String arrayName, String value) {
		Prompts prompt = Prompts.valueOf(arrayName);
		
		if( prompt == null )
			return false;
		
		GuiListBoxFilebound box = (GuiListBoxFilebound)listProvider.getListBox(prompt.getType().toString());
		
		IListObject newItem;
		
		if( prompt == Prompts.FRIENDS ) {
			newItem = ((GuiListBoxFriends)box).createObject(value, DEFAULT_ICON);
		} else if( prompt == Prompts.PLACES ) {
			String x = String.valueOf(MathHelper.floor_double(AbstractionLayer.getPlayer().posX));
			String y = String.valueOf(MathHelper.floor_double(AbstractionLayer.getPlayer().posY));
			String z = String.valueOf(MathHelper.floor_double(AbstractionLayer.getPlayer().posZ));
			
			Place place = Place.parsePlace(value, x, y, z, true);
			
			newItem = ((GuiListBoxPlaces)box).createObject(value, DEFAULT_ICON, place);
		} else if( prompt == Prompts.WARPS ) {
			newItem = ((GuiListBoxWarps)box).createObject(value, DEFAULT_ICON);
		} else if( prompt == Prompts.HOMES ) {
			newItem = ((GuiListBoxHomes)box).createObject(value, DEFAULT_ICON);
		} else if( prompt == Prompts.TOWNS ) {
			newItem = ((GuiListBoxTowns)box).createObject(value, DEFAULT_ICON);
		} else {
			newItem = box.createObject(value);
		}
		
		// Add item at the end, but before the <Add> item
		box.addItemAt(newItem, box.getItemCount()-1);
		box.save();
		
		return true;
	}

	@Override
	public String pop(String arrayName) {
		Prompts prompt = Prompts.valueOf(arrayName);
		
		if( prompt == null )
			return null;
		
		GuiListBoxFilebound box = (GuiListBoxFilebound)listProvider.getListBox(prompt.getType().toString());
		List<IListObject> items = ParamsHelper.getItems(box);
		
		if( items.size() == 1 )
			return null;
		
		// Get the last item that isn't the <Add> item
		IListObject lastItem = items.get(items.size()-2);
		
		box.removeItem(lastItem);
		box.save();
		
		return lastItem.getDisplayName();
	}

	@Override
	public boolean put(String arrayName, String value) {
		return push(arrayName, value);
	}

	@Override
	public void delete(String arrayName, int offset) {
		
	}

	@Override
	public void clear(String arrayName) {
		Prompts prompt = Prompts.valueOf(arrayName);
		
		if( prompt == null )
			return;
		
		GuiListBoxFilebound box = (GuiListBoxFilebound)listProvider.getListBox(prompt.getType().toString());
		
		box.clear();
		box.save();
	}

}
