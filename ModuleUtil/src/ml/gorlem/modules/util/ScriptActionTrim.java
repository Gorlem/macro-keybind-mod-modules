package ml.gorlem.modules.util;

import ml.gorlem.modules.lib.util.BaseScriptAction;
import net.eq2online.macros.scripting.VariableExpander;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.parser.ScriptContext;

@APIVersion(17)
public class ScriptActionTrim extends BaseScriptAction {

	@Override
	public String getName() {
		return "trim";
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		
		String str = new VariableExpander(provider, macro, params[0], false).toString();
		
		return new ReturnValue( str.trim() );
	}

}
