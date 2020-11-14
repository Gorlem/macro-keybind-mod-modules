package ml.gorlem.modules.util;

import java.util.ArrayList;
import java.util.Collections;

import ml.gorlem.modules.lib.util.BaseScriptAction;
import net.eq2online.macros.scripting.Variable;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

@APIVersion(17)
public class ScriptActionShuffle extends BaseScriptAction {

	@Override
	public String getName() {
		return "shuffle";
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		
		String arrayName = Variable.getValidVariableOrArraySpecifier(params[0]);
		int size = provider.getArraySize(macro, arrayName);
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		for(int i=0; i < size; i++) {
			list.add( provider.getArrayElement(macro, arrayName, i) );
		}
		
		Collections.shuffle(list);
		
		provider.clearArray(macro, arrayName);
		
		for( Object item : list ) {
			provider.pushValueToArray(macro, arrayName, item.toString());
		}
		
		return null;
	}

}
