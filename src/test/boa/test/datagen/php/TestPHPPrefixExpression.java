package boa.test.datagen.php;

import java.io.IOException;

import org.junit.Test;

public class TestPHPPrefixExpression extends PHPBaseTest {

	@Test
	public void testPrefixExpression() throws IOException, Exception{
		nodeTest(load("test/datagen/PHP/PreFixExpressionUnpack.boa"),
				load("test/datagen/PHP/PreFixExpressionUnpack.php"));
	}
	
}
