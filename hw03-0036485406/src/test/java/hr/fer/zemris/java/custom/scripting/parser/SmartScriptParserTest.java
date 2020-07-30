package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	@Test
	public void parserTest() {
		SmartScriptParser parser = new SmartScriptParser("This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
				+ "		This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}"
				+ "		sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}");
	
		DocumentNode documentNode = new DocumentNode();
		documentNode.addChildNode(new TextNode("This is sample text.\n"));
		ForLoopNode forLoopNode = new ForLoopNode(new ElementVariable("i"), 
											new ElementConstantInteger(1), 
											new ElementConstantInteger(10), 
											new ElementConstantInteger(1));
		
		forLoopNode.addChildNode(new TextNode("\n		This is "));
		forLoopNode.addChildNode(new EchoNode(new Element[] {new ElementVariable("i")}));
		forLoopNode.addChildNode(new TextNode("-th time this message is generated.\n"));
		
		documentNode.addChildNode(forLoopNode);
		documentNode.addChildNode(new TextNode("\n"));
		forLoopNode = new ForLoopNode(new ElementVariable("i"), 
				new ElementConstantInteger(0), 
				new ElementConstantInteger(10), 
				new ElementConstantInteger(2));
		
		forLoopNode.addChildNode(new TextNode("		sin("));
		forLoopNode.addChildNode(new EchoNode(new Element[] {new ElementVariable("i")}));
		forLoopNode.addChildNode(new TextNode("^2) = "));
		forLoopNode.addChildNode(new EchoNode(new Element[] {
												new ElementVariable("i"),
												new ElementVariable("i"),
												new ElementOperator("*"),
												new ElementFunction("sin"),
												new ElementString("0.000"),
												new ElementFunction("decfmt")}));
		forLoopNode.addChildNode(new TextNode("\n"));
		documentNode.addChildNode(forLoopNode);
		
		assertEquals(true, parser.getDocumentNode().equals(documentNode));
	}

}
