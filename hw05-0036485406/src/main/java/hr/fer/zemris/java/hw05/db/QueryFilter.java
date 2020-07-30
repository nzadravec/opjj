package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryFilter implements IFilter {

	private List<ConditionalExpression> exprList;

	public QueryFilter(List<ConditionalExpression> exprList) {
		super();
		this.exprList = exprList;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		if(record == null) {
			throw new NullPointerException();
		}
		
		for (ConditionalExpression expr : exprList) {
			if (!expr.evaluate(record)) {
				return false;
			}
		}
		
		return true;
	}

}
