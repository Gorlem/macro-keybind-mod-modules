package ml.gorlem.modules.lib.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.IScriptedIterator;
import net.eq2online.macros.scripting.parser.ScriptContext;

public abstract class BaseScriptedIterator extends BaseVariableProvider implements
		IScriptedIterator {
	
	protected int index = 0;
	private boolean breakLoop = false;
	
	protected List<Map<String, Object>> itemWaves = new ArrayList<Map<String, Object>>();
	protected List<Map<String, List<Object>>> arrayWaves = new ArrayList<Map<String, List<Object>>>();

	public BaseScriptedIterator() {}
	
	public BaseScriptedIterator(IScriptActionProvider provider, IMacro macro) {
		macro.registerVariableProvider(this);
		
		load();
	}
	
	public abstract String getName();
	
	@Override
	public void onInit() {
		for( ScriptContext ctx : ScriptContext.getAvailableContexts() ) {
			ctx.getCore().registerIterator(getName(), this.getClass());
		}
	}
	
	@Override
	public void populate() {}
	
	@Override
	public boolean continueLooping() {
		return !breakLoop && index < itemWaves.size();
	}

	@Override
	public void increment() {
		save();
		
		index++;
		
		load();
	}

	@Override
	public void reset() {
		index = 0;
		
		load();
	}

	@Override
	public void breakLoop() {
		breakLoop = true;
	}
	
	private void save() {
		itemWaves.set(index, items);
		arrayWaves.set(index, arrays);
	}
	
	private void load() {
		if( index < itemWaves.size() ) {
			items = itemWaves.get(index);
			arrays = arrayWaves.get(index);
		}
	}
	
	protected void createWave() {
		itemWaves.add(new HashMap<String, Object>());
		arrayWaves.add(new HashMap<String, List<Object>>());
		
		load();
	}

}
