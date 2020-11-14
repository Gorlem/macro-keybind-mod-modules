package ml.gorlem.modules.lib.controls;

import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IMacroActionProcessor;
import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;

public abstract class Bootstrap implements IScriptAction {

	@Override
	public abstract void onInit();

	@Override
	public ScriptContext getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isThreadSafe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackPushOperator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackPopOperator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBePoppedBy(IScriptAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeStackPush(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeStackPop(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params, IMacroAction popAction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBreak(IMacroActionProcessor processor,
			IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, IMacroAction breakAction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConditionalOperator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConditionalElseOperator(IScriptAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean matchesConditionalOperator(IScriptAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeConditional(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void executeConditionalElse(IScriptActionProvider provider,
			IMacro macro, IMacroAction instance, String rawParams,
			String[] params, IMacroActionStackEntry top) {
		// TODO Auto-generated method stub

	}

	@Override
	public IReturnValue execute(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance, String rawParams, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int onTick(IScriptActionProvider provider) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPermissionGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerPermissions(String actionName, String actionGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkExecutePermission() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPermission(String actionName, String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onStopped(IScriptActionProvider provider, IMacro macro,
			IMacroAction instance) {
		// TODO Auto-generated method stub

	}

}
