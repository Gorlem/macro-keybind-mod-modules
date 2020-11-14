package ml.gorlem.modules.fs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableHelper {
	
	private Pattern pattern = Pattern.compile("^(\\w+)\\[(\\d+)\\]$");
	private Matcher matcher;
	
	private String name;
	private int index;
	private boolean matches;
	
	public VariableHelper(String variableName) {
		matcher = pattern.matcher(variableName);
		
		if( matches = matcher.matches() ) {
			name = matcher.group(1);
			index = Integer.parseInt( matcher.group(2) );
		}
		
	}
	
	public boolean isArray() {
		return matches;
	}
	
	public int getIndex() {
		if( isArray() ) {
			return index;
		}
		
		return -1;
	}
	
	public String getName() {
		if( isArray() ) {
			return name;
		}
		
		return null;
	}
	
}
