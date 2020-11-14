package ml.gorlem.modules.lib.controls;

import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IMacroActionProcessor;
import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;

public abstract class BaseScriptAction implements IScriptAction {

	@Override
	public void onInit() {
		
		for( ScriptContext ctx : ScriptContext.getAvailableContexts() ) {
			ctx.getCore().registerScriptAction(this);
		}

	}

	@Override
	public ScriptContext getContext() {
		return null;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean isThreadSafe() {
		return true;
	}

	@Override
	public boolean isStackPushOperator() {
		return false;
	}

	@Override
	public boolean isStackPopOperator() {
		return false;
	}

	@Override
	public boolean canBePoppedBy(IScriptAction action) {
		return false;
	}

	@Override
	public boolean executeStackPush(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params) {
		return false;
	}

	@Override
	public boolean executeStackPop(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params, IMacroAction popAction) {
		return false;
	}

	@Override
	public boolean canBreak(IMacroActionProcessor processor,
			IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, IMacroAction breakAction) {
		return false;
	}

	@Override
	public boolean isConditionalOperator() {
		return false;
	}

	@Override
	public boolean isConditionalElseOperator(IScriptAction action) {
		return false;
	}

	@Override
	public boolean matchesConditionalOperator(IScriptAction action) {
		return false;
	}

	@Override
	public boolean executeConditional(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params) {
		return false;
	}

	@Override
	public void executeConditionalElse(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params, IMacroActionStackEntry top) {

	}

	@Override
	public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		return true;
	}

	@Override
	public int onTick(IScriptActionProvider provider) {
		return 0;
	}

	@Override
	public boolean isClocked() {
		return true;
	}

	@Override
	public boolean isPermissable() {
		return false;
	}

	@Override
	public String getPermissionGroup() {
		return null;
	}

	@Override
	public void registerPermissions(String actionName, String actionGroup) {

	}

	@Override
	public boolean checkExecutePermission() {
		return true;
	}

	@Override
	public boolean checkPermission(String actionName, String permission) {
		return false;
	}

	@Override
	public void onStopped(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance) {
		
	}

}
