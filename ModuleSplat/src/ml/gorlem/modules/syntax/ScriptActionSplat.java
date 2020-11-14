package ml.gorlem.modules.syntax;

import net.eq2online.macros.scripting.Variable;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;

import com.google.common.base.Joiner;

@APIVersion(17)
public class ScriptActionSplat extends BaseScriptAction {
	
	private Joiner argumentJoiner = Joiner.on(",");
	
	@Override
	public void onInit() {
		for( ScriptContext context : ScriptContext.getAvailableContexts() ) {
			context.getParser().addActionParser(new ActionParserSplat(context));
			context.getParser().addActionParser(new ActionParserInc(context));
			context.getCore().registerScriptAction(this);
		}
	}

	@Override
	public String getName() {
		return "splat";
	}
	
	@Override
	public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		
		SplatState state = instance.getState();
		
		if( state == null ) {
			String actionName = params[1];
			String arrayName = Variable.getValidVariableOrArraySpecifier(params[0]);
			
			int size = provider.getArraySize(macro, arrayName);
			
			String[] arguments = new String[size];
			
			for(int i = 0; i < size; i++) {
				arguments[i] = provider.getArrayElement(macro, arrayName, i).toString();
			}
			
			IScriptAction action = macro.getContext().getScriptContext().getAction(actionName);
			
			state = new SplatState(action, arguments);
			
			instance.setState(state);
		}
		
		return state.action.canExecuteNow(provider, macro, instance, 
				argumentJoiner.join(state.arguments), state.arguments );
	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		
		SplatState state = instance.getState();
		
		return state.action.execute(provider, macro, instance, 
				argumentJoiner.join(state.arguments), state.arguments );
		
	}
	
	private class SplatState {
		
		public IScriptAction action;
		public String[] arguments;
		
		public SplatState(IScriptAction action, String[] arguments) {
			this.action = action;
			this.arguments = arguments;
		}
	}

}
