package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program accepts a file name (as a single argument from command line). Program
 * opens given file (is should be a smart script), parses it into a tree and
 * that reproduces its (aproximate) original form onto standard output.
 * 
 * @author nikola
 *
 */
public class TreeWriter {
	/**
	 * Main function.
	 * 
	 * @param args command line arguments - single argument; file name of a smart script
	 * @throws IOException
	 */
	public static void main(String[] args) {
		String filePath = null;
		if (args.length != 1) {
			System.err.println("Missing path to document.");
			System.err.println("Using default path: ./src/main/resources/document1.txt");
			System.err.println();
			filePath = "./src/main/resources/document1.txt";
		} else {
			filePath = args[0];
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Can't open document " + filePath);
			System.exit(1);
		}

		SmartScriptParser p = null;
		try {
			p = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Used for generating original form of parsed smart script onto standard output.
	 * 
	 * @author nikola
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR ");
			System.out.print(node.getVariable().asText() + " ");
			System.out.print(node.getStartExpression().asText() + " ");
			System.out.print(node.getEndExpression().asText() + " ");
			if (node.getStepExpression() != null) {
				System.out.print(node.getStepExpression().asText() + " ");
			}
			System.out.print("$}");
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$ = ");
			for (Element e : node.getElements()) {
				System.out.print(e.asText() + " ");
			}
			System.out.print("$}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}

	}

}
