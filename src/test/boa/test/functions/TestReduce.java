/*
 * Copyright 2017, Robert Dyer,
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
package boa.test.functions;

import static org.junit.Assert.assertEquals;
import static boa.functions.BoaAstIntrinsics.parseexpression;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import boa.functions.BoaNormalFormIntrinsics;
import boa.types.Ast.Expression;

/**
 * Test expression reduction.
 *
 * @author rdyer
 */
@RunWith(Parameterized.class)
public class TestReduce {
	@Parameters(name = "{index}][{0} = {1}")
	public static Collection<String[]> expressions() {
		return Arrays.asList(new String[][] {
			// literals
			{ "5", "5" },
			{ "8.0", "8.0" },

			{ "+2", "2" },
			{ "+(+2)", "2" },
			{ "+ +2", "2" },

			{ "-8.0", "-8.0" },
			{ "-2", "-2" },
			{ "-(-2)", "2" },
			{ "- -2", "2" },
			{ "- - -2", "-2" },
			{ "- - - -2", "2" },

			{ "+ -2", "-2" },
			{ "- +2", "-2" },
			{ "- + +2", "-2" },
			{ "+ - +2", "-2" },
			{ "+ + -2", "-2" },
			{ "- - +2", "2" },
			{ "+ - -2", "2" },
			{ "- + -2", "2" },
			{ "- + - +2", "2" },
			{ "+ - - +2", "2" },
			{ "+ (- - +2)", "2" },
			{ "+ + - +2", "-2" },

			// add operator
			{ "1 + 2", "3" },
			{ "5 + 2 + 1", "8" },
			{ "5.0 + 2 + 1", "8.0" },
			{ "-1 + -2", "-3" },

			{ "0.1 + 1 + 10 + 100", "111.1" },
			{ "-0.1 + 1 + 10 + 100", "110.9" },
			{ "0.1 + -1 + 10 + 100", "109.1" },
			{ "0.1 + 1 + -10 + 100", "91.1" },
			{ "0.1 + (1 + -10) + 100", "91.1" },
			{ "0.1 + 1 + 10 + -100", "-88.9" },
			{ "-0.1 + -1 + 10 + 100", "108.9" },
			{ "-0.1 + 1 + -10 + 100", "90.9" },
			{ "-0.1 + 1 + 10 + -100", "-89.1" },
			{ "0.1 + -1 + -10 + 100", "89.1" },
			{ "0.1 + -1 + 10 + -100", "-90.9" },
			{ "0.1 + 1 + -10 + -100", "-108.9" },
			{ "-0.1 + -1 + -10 + 100", "88.9" },
			{ "-0.1 + -1 + 10 + -100", "-91.1" },
			{ "-0.1 + 1 + -10 + -100", "-109.1" },
			{ "0.1 + -1 + -10 + -100", "-110.9" },
			{ "-0.1 + -1 + -10 + -100", "-111.1" },

			// subtract operator
			{ "2 - 1", "1" },
			{ "1 - 2 - 3", "-4" },
			{ "2 - -5 - 1", "6" },
			{ "5.0 - 2 - 1", "2.0" },
			{ "5 - (3 - 2)", "4" },
			{ "(5 - 3) - 2", "0" },

			{ "111.1 - 100 - 10 - 1 - 0.1", "0.0" },
			{ "-111.1 - 100 - 10 - 1 - 0.1", "-222.2" },
			{ "111.1 - -100 - 10 - 1 - 0.1", "200.0" },
			{ "111.1 - (-100 - 10) - 1 - 0.1", "220.0" },
			{ "111.1 - -(-100 - 10) - 1 - 0.1", "0.0" },
			{ "111.1 - -(-(-100 - 10) - 1) - 0.1", "220.0" },
			{ "111.1 - -(-(-(-100 - 10) -1) - 0.1)", "2.0" },

			// multiply operator
			{ "2 * 5 * 1", "10" },
			{ "5.0 * 2 * 1", "10.0" },
			{ "999 * 999 * 0", "0" },

			{ "1 * 1 * 1", "1" },
			{ "-1 * 2 * 3", "-6" },
			{ "1 * -2 * 3", "-6" },
			{ "1 * 2 * -3", "-6" },
			{ "-1 * -2 * 3", "6" },
			{ "1 * -2 * -3", "6" },
			{ "-1 * -2 * -3", "-6" },

			// divide operator
			{ "12 / 2 / 3", "2" },
			{ "10.0 / 2 / 1", "5.0" },

			{ "10 / 3", "3.3333333333333335" }, // FIXME should be "3"
			{ "10.0 / 3", "3.3333333333333335" },
			{ "5 / -1", "-5" },

			// with variables
			{ "+x", "x" },
			{ "+(+x)", "x" },
			{ "+ + +x", "x" },
			{ "+ + + +x", "x" },

			{ "-x", "-x" },
			{ "- -x", "x" },
			{ "- - -x", "-x" },
			{ "- - - -x", "x" },

			{ "x + 5.0 + 1", "6.0 + x" },
			{ "-x + 5.0 - 1", "4.0 + -x" }, // FIXME should be "4.0 - x"
			{ "-x + 1 - 5", "-4 + -x" }, // FIXME should be "-4 - x"
			{ "x * 5.0 * 1", "5.0 * x" },
			{ "x / 10.0 * 5", "5 * (x / 10.0)" }, // FIXME should be "x / 2.0"

			{ "5.0 + x + 1", "6.0 + x" },
			{ "5.0 - x - 1", "4.0 - x" },
			{ "1 - x - 5", "-4 - x" },
			{ "5.0 * x * 1", "5.0 * x" },
			{ "5.0 / x / 5", "1.0 / x" },

			{ "5.0 + 1 + x", "6.0 + x" },
			{ "5.0 - 1 - x", "4.0 - x" },
			{ " 1 - 5 - x", "-4 - x" },
			{ "5.0 * 1 * x", "5.0 * x" },
			{ "5.0 / 5 / x", "1.0 / x" },

			// identities
			{ "0 + x", "x" },
			{ "x + 0.0", "x" },
			{ "x + (1 - 1.0)", "x" },
			{ "x + (3 - 3)", "x" },
			{ "(3 - 3) + x", "x" },

			{ "0 - x", "-x" },
			{ "x - 0", "x" },
			{ "x - (3 - 3)", "x" },
			{ "(3 - 3) - x", "-x" },

			{ "1 * x", "x" },
			{ "x * 1", "x" },
			{ "x * (3 - 2)", "x" },
			{ "(3 - 2) * x", "x" },
			{ "-1 * x", "-x" },
			{ "x * -1", "-x" },

			{ "x / 1", "x" },
			{ "x / 1 / 1 / 1", "x" },

			{ "x + 1 - 1", "x" },
			{ "1 + x - 1", "x" },
			{ "1 - 1 + x", "x" }, 
			{ "-x + 1 - 1", "-x" },
			{ "1 + -x - 1", "-x" },
			{ "1 - 1 + -x", "-x" },
			{ "x * 2 / 2", "2 * x / 2" }, // FIXME should be "x"
			{ "2 * x / 2", "2 * x / 2" }, // FIXME should be "x"
			{ "2 / 2 * x", "x" },
			{ "-x * 2 / 2", "2 * -x / 2" }, // FIXME should be "-x"
			{ "2 * -x / 2", "2 * -x / 2" }, // FIXME should be "-x"
			{ "2 / 2 * -x", "-x" },

			{ "x / x * x", "x" },
			{ "x * x / x", "x * x / x" }, // FIXME should be "x"
			// FIXME
			// { "-x * x / x", "-x" },
			// { "x * -x / x", "-x" },
			// { "x * x / -x", "-x" },
			// { "-x * -x / x", "x" },
			// { "-x * x / -x", "x" },
			// { "x * -x / -x", "x" },
			// { "-x * -x / -x", "-x"},

			// elimination
			{ "0 * x", "0" },
			{ "x * 0", "0" },
			{ "x * (1 - 1)", "0" },
			{ "(1 - 1) * x", "0" },
			{ "x - x", "0" },
			{ "x / x", "1" },

			{ "x * 0 * 0", "0" },
			{ "0 * x * 0", "0" },
			{ "0 * 0 * x", "0" },
			{ "-x * 0 * 0", "0" },
			{ "0 * -x * 0", "0" },
			{ "0 * 0 * -x", "0" },
			{ "x * x * 0", "0" },
			{ "x * 0 * x", "0" },
			{ "0 * x * x", "0" },
			{ "x + x - x - x", "2 * x - 2 * x" }, // FIXME should be "0"
			{ "x - x + x - x", "0" },
			{ "x - x - x + x", "-x + x" }, // FIXME should be "0"
			{ "-x - x + x + x", "2 * -x + 2 * x" }, // FIXME should be "0"
			{ "-x + x - x + x", "2 * -x + 2 * x" }, // FIXME should be "0"
			{ "-x + x + x - x", "2 * -x + 2 * x" }, // FIXME should be "0"
			{ "x * x / x / x", "x * x / x / x" }, // FIXME should be "1"
			{ "x / x * x / x", "1" },
			{ "x / x / x * x", "1 / x * x" }, // FIXME should be "1"
			{ "-x / x * x / x", "-x / x * x / x" }, // FIXME should be "-1"
			{ "x / -x * x / x", "x / -x * x / x" }, // FIXME should be "-1"
			{ "x / x * -x / x", "-x / x" }, // FIXME should be "-1"
			{ "x / x * x / -x", "x / -x" }, // FIXME should be "-1"

			// with methods 
			{ "foo(x + 3, 2 + 1)", "foo(3 + x, 3)" },
			{ "3 + foo(2 + 1) - 1", "2 + foo(3)" },

			{ "foo(x + 1 + 1, y + 2 + 2)", "foo(2 + x, 4 + y)" },
			{ "foo(1 + x + 1, 2 + y + 2)", "foo(2 + x, 4 + y)" },
			{ "foo(1 + 1 + x, 2 + 2 + y)", "foo(2 + x, 4 + y)" },
			{ "foo(x + 1 - 1, y + 2 - 2)", "foo(x, y)" },
			{ "foo(x - 1 + 1, y - 2 + 2)", "foo(x, y)" },

			{ "foo() + 1 + 1", "2 + foo()" },
			{ "1 + foo() + 1", "2 + foo()" },
			{ "1 + 1 + foo()", "2 + foo()" },
			{ "foo() + 1 - 1", "foo()" },
			{ "foo() - 1 + 1", "foo()" },
			{ "1 + foo() - 1", "foo()" },
			{ "-1 + foo() + 1", "foo()" },
			{ "1 - 1 + foo()", "foo()" },
			{ "-1 + 1 + foo()", "foo()" },

			// complex expressions, multiple operators etc
			{ "-5 * -x", "5 * x" },
			{ "-y * -5 * -x", "-5 * y * x" },
			{ "-2 * -y * -5 * -x", "10 * y * x" },
			{ "5 - 3 + 2", "4" },
			{ "5 - (3 + 2)", "0" },
			{ "5.0 / x / 5 * 10.0 * x", "10.0 * (1.0 / x) * x" }, // FIXME should be 10
			{ "-5 * (5 - 10) + 1", "26" },
			{ "-5 * (5 - 5) + 1", "1" },
			{ "-5 / (3 + 2) + 1", "0" },
			{ "(-5 / (3 + 2)) + 1", "0" },
			{ "-5 / ((3 + 2) + 1)", "-0.8333333333333334" },
			{ "-6 / -3 + -5 * -3", "17" },
			{ "-6 / -3 + (-5 * -3)", "17" },
			{ "-2 / (-3 + -5) * -3", "-0.75" },
			{ "5.0 + 3.5 - 2 * a.length", "8.5 - 2 * a.length" },
			{ "5.0 + a.length * (3.5 - 2)", "5.0 + 1.5 * a.length" },
			{ "5 + x + 3 + x", "8 + 2 * x" },
			{ "5 + (x + 3) + x", "8 + 2 * x" },
			{ "5 - (x - 3) - x", "8 + -2 * x" },
			{ "(x - 3) - x", "-3" },
			{ "5.0 + +3.5 - -2 * a.length", "8.5 + 2 * a.length" },
			{ "(5.0 + 3.5) - -2 * a.length", "8.5 + 2 * a.length" },
			//{ "5.0 + 3.5 - -2 * a.length + x", "8.5 + 2 * a.length + x" }, // FIXME when parsed, the answer isnt flattened but should be
			{ "5.0 + 3.5 - (-2 * a.length + x)", "8.5 + 2 * a.length + -x" }, // FIXME should be 8.5 + 2 * a.length - x
			{ "5.0 + 3.5 - -2 * (a.length + x)", "8.5 + 2 * (a.length + x)" },
			{ "5 - m(a) + 0", "5 + -m(a)" }, // FIXME
			{ "5 - -m(a) - 0", "5 + m(a)" },
			{ "5 + +m(a) - 0", "5 + m(a)" },
			{ "5 + m() + -0", "5 + m()" },
			{ "5 + m(a).x * 0", "5" },
			{ "5 + m(a, b).x / -1", "5 + m(a, b).x / -1" }, // FIXME should be 5 - m(a, b).x
			{ "(5 + m(1 + 2, b).x) / -1", "(5 + m(3, b).x) / -1" }, // FIXME should be 5 - m(3, b).x

			{ "x / 0", "x / 0" }, // FIXME
			{ "x + 2 * 2", "4 + x" },
			{ "x * 2 + 2", "2 + 2 * x" },
			{ "x - 2 * 2", "x - 4" },
			{ "x * 2 - 2", "2 * x - 2" },
			{ "x + 6 / 3", "2 + x" },
			{ "x / 3 + 6", "6 + x / 3" },

			{ "1 + -x + 2 + 3", "6 + -x" },
			{ "1 + -x + 2 - 4", "-1 + -x" }, // FIXME shoulde be "-1 - x"
			{ "1 + -x - 4 + 2", "-1 + -x" },
			{ "5 + (x + 3) + x + 2 - 3", "7 + 2 * x" },
			{ "5 + (x + 3) + x - 2 + 3", "9 + 2 * x" },
			{ "5 + (x + 3) + x * 2 - 3", "5 + x + 2 * x" }, // FIXME should be "5 + 3 * x"
			{ "5 + (x + 3) - x + 2 - 3", "7 + x + -x" }, // FIXME should be "7"
			{ "5 + (x + 3) + x + 2 * 3", "14 + 2 * x" },
			{ "5 + (x + 3) + x - 2 * 3", "2 + 2 * x" }, // FIXME should be "2 + 2 * x"
			{ "2 + ((8 + x) - x) + 3", "13 + x + -x" }, // FIXME should be "13"
			{ "1 - 2 * x - 3 + -2", "-4 + -2 * x" },
			{ "((8 + x) - x)", "8 + x + -x" }, // FIXME should be "8"
			{ "8 - -x - 2 * x", "8 + -2 * x + x" }, //FIXME should be "8 - x"
			{ "x * ((-y) / z)", "x * (-y / z)"},
		});
	}

	private Expression e = null;
	private Expression reduced = null;

	public TestReduce(final String e, final String reduced) {
		this.e = parseexpression(e);
		this.reduced = parseexpression(reduced);
	}

	@Test
	public void testReduce() throws Exception {
		assertEquals(reduced, BoaNormalFormIntrinsics.reduce(e));
	}
}
