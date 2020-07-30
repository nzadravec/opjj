package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Writes a constant string.
 * 
 * @author nikola
 *
 */
public class ConstantNameBuilder implements NameBuilder {
	
	/**
	 * Constant string.
	 */
	private String constant;
	
	/**
	 * Default constructor.
	 * 
	 * @param constant constant string
	 */
	public ConstantNameBuilder(String constant) {
		super();
		this.constant = constant;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(constant);
	}

	@Override
	public String toString() {
		return "ConstantNameBuilder: \"" + constant + "\"";
	}
	
}
