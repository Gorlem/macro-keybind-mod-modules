package ml.gorlem.modules.fs;

import java.io.File;

import net.eq2online.macros.core.MacroModSettings;
import net.eq2online.macros.scripting.api.APIVersion;

import com.mumfrey.liteloader.core.LiteLoader;

@APIVersion(17)
public class VariableProviderFS extends BaseVariableProvider {

	@Override
	public void initialUpdate() {
		String minecraftDir = LiteLoader.getInstance().getGameDirectory().getAbsolutePath();
		String macrosDir = new File(minecraftDir, MacroModSettings.getMacrosDirName()).getAbsolutePath();
		
		store("MODULEFS", true);
		store("MACROS_DIR", macrosDir);
		store("MINECRAFT_DIR", minecraftDir);		
	}
	
}
