package ml.gorlem.modules.switch_;

import ml.gorlem.modules.lib.switch_.BaseScriptAction;
import net.eq2online.console.Log;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

@APIVersion(17)
public class ScriptActionSwitch extends BaseScriptAction {

	@Override
	public String getName() {
		return "switch";
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		return null;
	}
	
	@Override
	public boolean isConditionalOperator() {
		return true;
	}

}
