package ml.gorlem.modules.fs;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

@APIVersion(17)
public class ScriptActionList extends BaseScriptAction {

	@Override
	public String getName() {
		return "fs_list";
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		
		return new MacroFile(params[0], provider, macro).list();
	}

}
