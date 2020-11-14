package ml.gorlem.modules.util;

import java.util.ArrayList;
import java.util.List;

import ml.gorlem.modules.lib.util.BaseScriptedIterator;
import net.eq2online.macros.core.MacroModCore;
import net.eq2online.macros.event.MacroEventManager;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroEvent;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

@APIVersion(17)
public class ScriptedIteratorEvents extends BaseScriptedIterator {
	
	int size;
	
	public ScriptedIteratorEvents(IScriptActionProvider provider, IMacro macro) {		
		MacroEventManager eventManager =
				MacroModCore.getInstance().getMacroManager().getEventManager();
		
		for( IMacroEvent event : eventManager.getEvents() ) {
			createWave();
			
			int id = eventManager.getEventID(event);
			List<String> help = new ArrayList<String>();
			
			for(int i=0; i <= 5; i++) {
				help.add(event.getHelpLine(id, i));
			}
			
			store("EVENTNAME", event.getName());
			store("EVENTID", id);
			store("EVENTHELP", help);
			
			increment();
		}
		
		reset();
	}
	
	public ScriptedIteratorEvents() { }

	@Override
	public String getName() {
		return "events";
	}

}
