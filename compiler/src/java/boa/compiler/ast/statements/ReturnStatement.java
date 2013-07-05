package boa.compiler.ast.statements;

import boa.compiler.ast.expressions.Expression;
import boa.compiler.visitors.AbstractVisitor;
import boa.compiler.visitors.AbstractVisitorNoArg;

/**
 * 
 * @author rdyer
 */
public class ReturnStatement extends Statement {
	protected Expression expr;

	public boolean hasExpr() {
		return expr != null;
	}

	public Expression getExpr() {
		return expr;
	}

	public ReturnStatement() {
	}

	public ReturnStatement(final Expression expr) {
		expr.setParent(this);
		this.expr = expr;
	}

	/** {@inheritDoc} */
	@Override
	public <A> void accept(final AbstractVisitor<A> v, A arg) {
		v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(final AbstractVisitorNoArg v) {
		v.visit(this);
	}

	public ReturnStatement clone() {
		final ReturnStatement s;
		if (hasExpr())
			s = new ReturnStatement(expr.clone());
		else
			s = new ReturnStatement();
		copyFieldsTo(s);
		return s;
	}
}