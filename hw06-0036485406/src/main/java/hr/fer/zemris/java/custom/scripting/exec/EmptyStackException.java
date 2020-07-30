package hr.fer.zemris.java.custom.scripting.exec;

public class EmptyStackException extends RuntimeException {
	
	public EmptyStackException() {
	}
	
	public EmptyStackException(String message) {
		super(message);
	}

}
