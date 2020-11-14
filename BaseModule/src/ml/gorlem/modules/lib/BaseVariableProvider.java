package ml.gorlem.modules.lib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.eq2online.macros.scripting.api.IArrayProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;

import com.google.common.base.Joiner;

public abstract class BaseVariableProvider implements IArrayProvider {
	
	protected Map<String, Object> items = new HashMap<String, Object>();
	protected Map<String, List<Object>> arrays = new HashMap<String, List<Object>>();
	
	private Pattern arrayPattern = Pattern.compile("^(\\w+)\\[(\\d+)\\]$");
	
	private Joiner defaultGlue = Joiner.on(" ");
	
	protected static final String DEFAULT_STRING = "";
	protected static final int MISSING_ENTRY = -1;
	protected static final Object UNKNOWN_VAR = null;
	
	public void store(String variableName, Object value) {
		items.put(variableName, value);
	}
	public void store(String variableName, List<Object> list) {
		store(variableName, defaultGlue.join(list));
		arrays.put(variableName, list);
	}
	public void store(String variableName, Object[] array) {
		store(variableName, Arrays.asList(array));
	}

	@Override
	public void updateVariables(boolean clock) {
		
	}
	
	public abstract void populate();

	@Override
	public Object getVariable(String variableName) {
		
		Matcher arrayMatcher = arrayPattern.matcher(variableName);
		
		if( arrayMatcher.matches() ) {
			String arrayName = arrayMatcher.group(1);
			int arrayIndex = Integer.parseInt(arrayMatcher.group(2));
			
			return getArrayVariableValue(arrayName, arrayIndex);
		}
		
		return items.get(variableName);
	}

	@Override
	public Set<String> getVariables() {
		return items.keySet();
	}

	@Override
	public void onInit() {
		for( ScriptContext ctx : ScriptContext.getAvailableContexts() ) {
			ctx.getCore().registerVariableProvider(this);
		}
		
		populate();
	}

	@Override
	public int indexOf(String arrayName, String value, boolean caseSensitive) {
		List<Object> values = arrays.get(arrayName);
		
		if(values == null)
			return MISSING_ENTRY;
		
		for(int i=0; i < values.size(); i++) {
			Object item = values.get(i);
			
			if((!caseSensitive && value.equalsIgnoreCase(item.toString())) || value.equals(item.toString())) {
				return i;
			}
		}
		
		return MISSING_ENTRY;
	}

	@Override
	public int getMaxArrayIndex(String arrayName) {
		List<Object> values = arrays.get(arrayName);
		
		if(values == null)
			return MISSING_ENTRY;
		
		return values.size()-1;
	}

	@Override
	public boolean checkArrayExists(String arrayName) {
		return arrays.containsKey(arrayName);
	}

	@Override
	public Object getArrayVariableValue(String variableName, int offset) {
		List<Object> values = arrays.get(variableName);
		
		if(values == null)
			return UNKNOWN_VAR;
		
		if( offset < 0 || offset >= values.size() )
			return DEFAULT_STRING;
		
		return values.get(offset);
	}

}
