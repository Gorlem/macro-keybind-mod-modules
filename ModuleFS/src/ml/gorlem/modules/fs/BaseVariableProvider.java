package ml.gorlem.modules.fs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.eq2online.macros.scripting.api.IArrayProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;

import com.google.common.base.Joiner;


public abstract class BaseVariableProvider implements IArrayProvider {
	
	private Map<String, Object> items = new HashMap<String, Object>();
	private Map<String, List<String>> arrays = new HashMap<String, List<String>>();
	
	private String defaultGlue = " ";
	
	public void store(String variableName, Object value) {
		items.put(variableName, value);
	}
	
	public void store(String variableName, String[] array) {
		store(variableName, array, defaultGlue);
	}
	
	public void store(String variableName, String[] array, String glue) {
		store(variableName, Arrays.asList(array), glue);
	}
	
	public void store(String variableName, List<String> array) {
		store(variableName, array, defaultGlue);
	}
	
	public void store(String variableName, List<String> array, String glue) {
		arrays.put(variableName, array);
		items.put(variableName, Joiner.on(glue).join(array));
	}
	
	@Override
	public void onInit() {
		ScriptContext.MAIN.getCore().registerVariableProvider(this);
		initialUpdate();
	}
	
	@Override
	public void updateVariables(boolean clock) {
		
	}
	
	public abstract void initialUpdate();
	
	@Override
	public int indexOf(String arrayName, String value, boolean caseSensitive) {
		return arrays.get(arrayName).indexOf(value);
	}

	@Override
	public int getMaxArrayIndex(String arrayName) {
		return arrays.get(arrayName).size()-1;
	}

	@Override
	public boolean checkArrayExists(String arrayName) {
		return arrays.containsKey(arrayName);
	}

	@Override
	public Object getArrayVariableValue(String variableName, int offset) {
		return arrays.get(variableName).get(offset);
	}

	@Override
	public Object getVariable(String variableName) {
		
		VariableHelper helper = new VariableHelper(variableName);
		
		if( helper.isArray() && checkArrayExists(helper.getName()) ) {
			return getArrayVariableValue(helper.getName(), helper.getIndex());
		} else {
			return items.get(variableName);			
		}
		
	}

	@Override
	public Set<String> getVariables() {
		return items.keySet();
	}

}
