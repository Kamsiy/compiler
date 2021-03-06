/*
 * Copyright 2017, Hridesh Rajan, Robert Dyer, Che Shian Hung
 *                 Iowa State University of Science and Technology
 *                 and Bowling Green State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package boa.compiler.ast.statements;

import boa.compiler.ast.Factor;
import boa.compiler.ast.expressions.Expression;
import boa.compiler.visitors.AbstractVisitor;
import boa.compiler.visitors.AbstractVisitorNoArgNoRet;
import boa.compiler.visitors.AbstractVisitorNoReturn;

/**
 * 
 * @author rdyer
 * @author hridesh
 * @author hungc
 */
public class AssignmentStatement extends Statement {
	protected Factor lhs;
	protected String op;
	protected Expression rhs;

	public Factor getLhs() {
		return lhs;
	}

	public String getOp() {
		return op;
	}

	public Expression getRhs() {
		return rhs;
	}

	public AssignmentStatement(final Factor lhs, final Expression rhs) {
		this(lhs, "=", rhs);
	}

	public AssignmentStatement(final Factor lhs, final String op, final Expression rhs) {
		if (lhs != null)
			lhs.setParent(this);
		if (rhs != null)
			rhs.setParent(this);
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	/** {@inheritDoc} */
	@Override
	public <T,A> T accept(final AbstractVisitor<T,A> v, A arg) {
		return v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public <A> void accept(final AbstractVisitorNoReturn<A> v, A arg) {
		v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(final AbstractVisitorNoArgNoRet v) {
		v.visit(this);
	}

	public AssignmentStatement clone() {
		final AssignmentStatement s = new AssignmentStatement(lhs.clone(), op, rhs.clone());
		copyFieldsTo(s);
		return s;
	}
}
