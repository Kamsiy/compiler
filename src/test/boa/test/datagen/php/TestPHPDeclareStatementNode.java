package boa.test.datagen.php;

import java.io.IOException;

import org.junit.Test;

public class TestPHPDeclareStatementNode extends PHPBaseTest {

	@Test
	public void declareStatementNode() throws IOException, Exception{
		nodeTest(load("test/datagen/PHP/DeclareStatementNode.boa"),
				load("test/datagen/PHP/DeclareStatementNode.php"));
	}
	
}
