package ml.gorlem.modules.syntax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IMacroActionProcessor;
import net.eq2online.macros.scripting.parser.ActionParserAbstract;
import net.eq2online.macros.scripting.parser.ScriptContext;

public class ActionParserInc extends ActionParserAbstract {
	
	private Pattern incPattern = Pattern.compile("^(#[a-z][a-z0-9_\\-]*)([+-]{2})$");

	protected ActionParserInc(ScriptContext context) {
		super(context);
	}

	@Override
	public IMacroAction parse(IMacroActionProcessor actionProcessor,
			String scriptEntry) {
		
		Matcher incMatcher = incPattern.matcher(scriptEntry);
		
		if( incMatcher.matches() ) {
			String variable = incMatcher.group(1);
			String type = incMatcher.group(2).equals("++") ? "INC" : "DEC";
			
			return this.getInstance(actionProcessor, type, variable, new String[] { variable }, null);
		}
		
		return null;
	}

}
