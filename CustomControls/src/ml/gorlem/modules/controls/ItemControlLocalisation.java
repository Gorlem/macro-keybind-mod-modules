package ml.gorlem.modules.controls;

import java.lang.reflect.Field;

import net.eq2online.macros.compatibility.LocalisationProvider;
import net.eq2online.macros.core.MacroModCore;
import net.eq2online.macros.interfaces.ILocalisationProvider;

import org.apache.commons.lang3.text.WordUtils;

public class ItemControlLocalisation implements ILocalisationProvider {
	
	static ILocalisationProvider oldProvider;
	
	public static void register() {
		try {
			Field activeProvider = LocalisationProvider.class.getDeclaredField("activeProvider");
			
			oldProvider = (ILocalisationProvider) activeProvider.get(null);
		} catch(Exception e) {
			oldProvider = MacroModCore.getInstance();
		}
		
		LocalisationProvider.setProvider(new ItemControlLocalisation());
	}

	@Override
	public String getRawLocalisedString(String key) {
		return oldProvider.getRawLocalisedString(key);
	}

	@Override
	public String getLocalisedString(String key) {
		String translation = oldProvider.getLocalisedString(key);
		
		if( !key.equals(translation) )
			return translation;
		
		if( key.startsWith("layout.editor.new.") )
			return "Add "+WordUtils.capitalize( key.substring(18) );
		
		return key;
	}

	@Override
	public String getLocalisedString(String key, Object... params) {
		return oldProvider.getLocalisedString(key, params);
	}

}
