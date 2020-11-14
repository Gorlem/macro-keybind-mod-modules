package ml.gorlem.modules.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import ml.gorlem.modules.lib.util.BaseScriptedIterator;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.IScriptedIterator;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;

@APIVersion(17)
public class ScriptedIteratorIterators extends BaseScriptedIterator {
	
	public ScriptedIteratorIterators(IScriptActionProvider provider, IMacro macro) {
		super(provider, macro);
		ScriptCore core = ScriptContext.MAIN.getCore();
		
		try {
			Field iteratorsField = core.getClass().getDeclaredField("iterators");
			iteratorsField.setAccessible(true);
			
			@SuppressWarnings("unchecked")
			Map<String, Class<? extends IScriptedIterator>> iterators =
				(Map<String, Class<? extends IScriptedIterator>>) iteratorsField.get(core);
			
			for( Entry<String, Class<? extends IScriptedIterator>> entry : iterators.entrySet() ) {
				createWave();
				
				store("ITERATORNAME", entry.getKey());
				
				increment();
			}
			
			reset();
			
		} catch (Exception e) {}
	}
	
	public ScriptedIteratorIterators() {}

	@Override
	public String getName() {
		return "iterators";
	}

}
