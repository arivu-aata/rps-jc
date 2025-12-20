package org.arivuaata.io;

public interface IOHandler {

	Object takeInput(String inputType);
	
	void writeOutput(Object outputInfo, String outputType);

	void writeError(Object errorInfo, String errorType);
}