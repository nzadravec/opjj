package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) {
		String filepath = null;
		if(args.length != 1) {
			System.err.println("Missing path to document.");
			System.err.println("Using default path: ./src/main/resources/document1.txt");
			System.err.println();
			filepath = "./src/main/resources/document1.txt";
			//System.exit(1);
		} else {
			filepath = args[0];
		}
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.err.println("Can't open document " + filepath);
			System.exit(1);
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		if(document.equals(document2)) {
			System.out.println("They are identical.");
		} else {
			System.out.println("They are not identical.");
		}
	}
	
	private static final String END_TEXT = "{$END$}";
	private static final TextNode END_NODE = new TextNode(END_TEXT);

	private static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();

		ObjectStack stack = new ObjectStack();
		for (int i = document.numberOfChildren() - 1; i >= 0; i--) {
			stack.push(document.getChild(i));
		}

		while (!stack.isEmpty()) {
			Node node = (Node) stack.pop();
			sb.append(node);

			if (node.numberOfChildren() != 0) {
				stack.push(END_NODE);
			}

			for (int i = node.numberOfChildren() - 1; i >= 0; i--) {
				stack.push(node.getChild(i));
			}
		}

		return sb.toString();
	}

}
