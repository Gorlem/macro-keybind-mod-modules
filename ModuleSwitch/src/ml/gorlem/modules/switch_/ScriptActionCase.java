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
import net.eq2online.macros.scripting.parser.ScriptCore;

@APIVersion(17)
public class ScriptActionCase extends BaseScriptAction {

	@Override
	public String getName() {
		return "case";
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
		
		if( top.getIfFlag() ) {
			top.setConditionalFlag(false);
			return;
		}
		
		String switchParam = top.getAction().getParams()[0];
		String value = provider.getVariable(switchParam, macro).toString();
		
		for( String param : params ) {
			String parsed = ScriptCore.parseVars(provider, macro, param, false);
			if( parsed.equals(value) ) {
				top.setConditionalFlag(true);
				top.setIfFlag(true);
				return;
			}
		}
		
		top.setConditionalFlag(false);
		
	}

}
