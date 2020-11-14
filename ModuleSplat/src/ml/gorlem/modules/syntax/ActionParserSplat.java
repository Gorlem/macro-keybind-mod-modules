package ml.gorlem.modules.syntax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.eq2online.macros.scripting.Variable;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IMacroActionProcessor;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ActionParserAbstract;
import net.eq2online.macros.scripting.parser.ScriptContext;

import com.google.common.base.Joiner;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

public class ActionParserSplat extends ActionParserAbstract {
	
	// Syntax: function ...array[]
	private Pattern splatPattern = Pattern.compile("^([a-z\\_]+) \\.{3}(.+)");

	protected ActionParserSplat(ScriptContext context) {
		super(context);
	}

	@Override
	public IMacroAction parse(IMacroActionProcessor actionProcessor,
			String scriptEntry) {
		
		Matcher splatMatcher = splatPattern.matcher(scriptEntry);
		
		if( splatMatcher.matches()
				&& Variable.couldBeArraySpecifier(splatMatcher.group(2))
				&& Variable.isValidVariableOrArraySpecifier(splatMatcher.group(2)) ) {
			
			String arrayName = Variable.getValidVariableOrArraySpecifier(splatMatcher.group(2)),
					action = splatMatcher.group(1);
			
			return this.getInstance(actionProcessor, "SPLAT", "", new String[] { arrayName, action }, null);
		}
		
		return null;
	}

}
