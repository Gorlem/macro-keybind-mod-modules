package ml.gorlem.modules.util;

import java.util.List;

import ml.gorlem.modules.lib.util.BaseScriptedIterator;
import net.eq2online.macros.scripting.IDocumentationEntry;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;

@APIVersion(17)
public class ScriptedIteratorActions extends BaseScriptedIterator {
	
	public ScriptedIteratorActions(IScriptActionProvider provider, IMacro macro) {
		super(provider, macro);
		ScriptCore core = ScriptContext.MAIN.getCore();
		
		for( IScriptAction action : core.getActionsList() ) {
			createWave();
			
			IDocumentationEntry doc = core.getDocumentor().getDocumentation(action);
			
			String usage = "";
			String returnType = "";
			String description = "";
			
			if( doc != null ) {
				usage = doc.getUsage();
				returnType = doc.getReturnType();
				description = doc.getDescription();
			}
			
			store("ACTIONNAME", action.getName());
			store("ACTIONUSAGE", usage);
			store("ACTIONRETURN", returnType);
			store("ACTIONDESCRIPTION", description);
			
			increment();
		}
		
		reset();
	}
	
	public ScriptedIteratorActions() { }


	@Override
	public String getName() {
		return "actions";
	}

}
