package ml.gorlem.modules.fs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

import net.eq2online.macros.scripting.VariableExpander;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.api.ReturnValueArray;

public class MacroFile {
	
	String fileNotFound = "§7[§cFS§7] §eFile not found!";
	String notAFolder = "§7[§cFS§7] §eNot a folder!";
	
	String path;
	File file;
	
	String line;
	
	IScriptActionProvider provider;
	IMacro macro;
	
	ReturnValueArray returnValue = new ReturnValueArray(false);
	
	public MacroFile(String rawPath, IScriptActionProvider provider, IMacro macro) {
		this.path = new VariableExpander(provider, macro, rawPath, false).toString();
		this.file = new File(path);
		
		this.provider = provider;
		this.macro = macro;
		
		LiteLoaderLogger.info(path);
	}
	
	private boolean fileExists() {
		if( !file.exists() ) {
			provider.actionAddChatMessage(fileNotFound);
			return false;
		}
		return true;
	}
	
	public ReturnValue exists() {
		return new ReturnValue( file.exists() );
	}
	
	public ReturnValueArray read() {
		
		if( fileExists() ) {
			ArrayList<String> lines = new ArrayList<String>();
			
			try {
				BufferedReader reader = new BufferedReader( new FileReader(file) );
				
				while( (line = reader.readLine()) != null ) {
					lines.add(line);
				}
				
				reader.close();
				
			} catch( Exception e ) {
				LiteLoaderLogger.severe(e, "Couldn't read from file %s", file);
			}
			
			returnValue.putStrings(lines);
		}
		
		return returnValue;
	}
	
	public ReturnValue write(String rawLine) {
		
		line = new VariableExpander(provider, macro, rawLine, false).toString();
		
		if( fileExists() ) {
			
			try {
				PrintWriter writer =
					new PrintWriter(
						new BufferedWriter( // Buffers everything
							new OutputStreamWriter( // Converts Bytes to characters
								new FileOutputStream(file, true), // Returns an Output Stream
								Charset.forName("UTF-8")
							)
						)
					);
				
				writer.println(line);
				
				writer.close();
			} catch( Exception e) {
				LiteLoaderLogger.severe(e, "Couldn't write to file %s", file);
				return new ReturnValue(false);
			}
			
			return new ReturnValue(true);
			
		}
		
		return new ReturnValue(false);
		
	}
	
	public ReturnValue create() {

		if( file.exists() )
			return new ReturnValue(false);
		
		try {
			return new ReturnValue( file.createNewFile() );
		} catch( Exception e ) {
			LiteLoaderLogger.severe(e, "Couldn't create file %s", file);
			return new ReturnValue(false);
		}
	}
	
	public ReturnValue delete() {
		
		if( !file.exists() )
			return new ReturnValue(false);
		
		try {
			return new ReturnValue( file.delete() );
		} catch( Exception e ) {
			LiteLoaderLogger.severe(e, "Couldn't delete file %s", file);
			return new ReturnValue(false);
		}
	}
	
	public ReturnValueArray list() {
		
		String[] files = file.list();
		
		if( files == null )
			provider.actionAddChatMessage(notAFolder);
		else
			returnValue.putStrings( Arrays.asList( files ) );			
		
		
		return returnValue;
	}
}
