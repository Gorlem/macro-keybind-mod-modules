package ml.gorlem.modules.switch_;

import ml.gorlem.modules.lib.switch_.BaseScriptAction;
import net.eq2online.console.Log;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

@APIVersion(17)
public class ScriptActionDefault extends BaseScriptAction {

	@Override
	public String getName() {
		return "default";
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		return null;
	}
	
	@Override
	public boolean isStackPopOperator() {
		return true;
	}
	
	@Override
	public boolean isConditionalElseOperator(IScriptAction action) {
		return (action instanceof ScriptActionSwitch);
	}
	
	@Override
	public void executeConditionalElse(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params, IMacroActionStackEntry top) {
		
		top.setConditionalFlag(!top.getIfFlag());
		top.setElseFlag(true);
	}

}
