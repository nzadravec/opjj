package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Object of this class is used to execute the document (smart script) whose parsed tree it obtains.
 * 
 * @author nikola
 *
 */
public class SmartScriptEngine {

	/**
	 * Document node of the document
	 */
	private DocumentNode documentNode;
	/**
	 * Request context object; used for executing the document (storing/fetching parameters and writing result)
	 */
	private RequestContext requestContext;
	/**
	 * Multistack object; used for storing and fetching {@link ForLoopNode} variable
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Used to execute smart script.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new SmartScriptEngineException(e.getMessage());
			}

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			String startExpr = node.getStartExpression().asText();
			String endExpr = node.getEndExpression().asText();
			String stepExpr;
			if(node.getStepExpression() != null) {
				stepExpr = node.getStepExpression().asText();
			} else {
				// if step expression is null, default value is 1
				stepExpr = "1";
			}
			multistack.push(variable, new ValueWrapper(startExpr));
			while (multistack.peek(variable).numCompare(endExpr) <= 0) {
				for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
					node.getChild(i).accept(this);
				}
				ValueWrapper variableWrapper = multistack.peek(variable);
				variableWrapper.add(stepExpr);
			}
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();

			for (Element element : node.getElements()) {
				if (element instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) element).getValue());
				} else if (element instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) element).getValue());
				} else if (element instanceof ElementString) {
					stack.push(((ElementString) element).getValue());
				} else if (element instanceof ElementVariable) {
					ValueWrapper variableWrapper = multistack.peek(((ElementVariable) element).getName());
					stack.push(variableWrapper.getValue());
				} else if (element instanceof ElementOperator) {
					ValueWrapper first = new ValueWrapper(stack.pop());
					Object second = stack.pop();
					String symbol = ((ElementOperator) element).getValue();
					switch (symbol) {
					case "+":
						first.add(second);
						stack.push(first.getValue());
						break;
					case "-":
						first.subtract(second);
						stack.push(first.getValue());
						break;
					case "*":
						first.multiply(second);
						stack.push(first.getValue());
						break;
					case "/":
						first.divide(second);
						stack.push(first.getValue());
						break;
					default:
						throw new SmartScriptEngineException("Operator not supported");
					}
				} else if (element instanceof ElementFunction) {
					String function = ((ElementFunction) element).getValue();
					switch (function) {
					case "sin":
						Object x = stack.pop();
						x = Math.sin(Math.toRadians(Double.parseDouble(x.toString())));
						stack.push(x);
						break;
					case "decfmt":
						DecimalFormat df = new DecimalFormat(stack.pop().toString());
						stack.push(df.format(Double.parseDouble(stack.pop().toString())));
						break;
					case "dup":
						stack.push(stack.peek());
						break;
					case "swap":
						Object a = stack.pop();
						Object b = stack.pop();
						stack.push(a);
						stack.push(b);
						break;
					case "setMimeType":
						requestContext.setMimeType(stack.pop().toString());
						break;
					case "paramGet":
						Object defValue = stack.pop();
						Object name = stack.pop();
						Object value = requestContext.getParameter(name.toString());
						stack.push(value == null ? defValue : value);
						break;
					case "pparamGet":
						defValue = stack.pop();
						name = stack.pop();
						value = requestContext.getPersistentParameter(name.toString());
						stack.push(value == null ? defValue : value);
						break;
					case "pparamSet":
						name = stack.pop();
						value = stack.pop();
						requestContext.setPersistentParameter(name.toString(), value.toString());
						break;
					case "pparamDel":
						name = stack.pop();
						requestContext.removePersistentParameter(name.toString());
						break;
					case "tparamGet":
						defValue = stack.pop();
						name = stack.pop();
						value = requestContext.getTemporaryParameter(name.toString());
						stack.push(value == null ? defValue : value);
						break;
					case "tparamSet":
						name = stack.pop();
						value = stack.pop();
						requestContext.setTemporaryParameter(name.toString(), value.toString());
						break;
					case "tparamDel":
						name = stack.pop();
						requestContext.removeTemporaryParameter(name.toString());
						break;
					default:
						throw new SmartScriptEngineException("Function not supported");
					}
				}
			}
			for (Object item : stack) {
				try {
					requestContext.write(item.toString());
				} catch (IOException e) {
					throw new SmartScriptEngineException(e.getMessage());
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}

	};

	/**
	 * Constructor.
	 * 
	 * @param documentNode document node of document
	 * @param requestContext request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Execute the document.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
