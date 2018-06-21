package boa.test.datagen.js;

import java.io.IOException;

import org.junit.Test;

public class TestVariableDeclarationNode extends JavaScriptBaseTest {

	@Test
	public void varaibleDeclTest1() throws IOException {
		nodeTest(load("test/datagen/javascript/VariableDeclarationNode.boa"),
				load("test/datagen/javascript/VariableDeclarationNode.js"));
	}

	@Test
	public void varaibleDeclTest2() throws IOException {
		nodeTest(load("test/datagen/javascript/VariableDeclarationNode2.boa"),
				load("test/datagen/javascript/VariableDeclarationNode2.js"));
	}

	@Test
	public void varaibleDeclTest3() throws IOException {
		nodeTest(load("test/datagen/javascript/VariableDeclarationNode3.boa"),
				load("test/datagen/javascript/VariableDeclarationNode3.js"));
	}

	@Test
	public void varaibleDeclTest4() throws IOException {
		nodeTest(load("test/datagen/javascript/VariableDeclarationNode4.boa"),
				load("test/datagen/javascript/VariableDeclarationNode4.js"));
	}

	@Test
	public void arrayLiteralTest() throws IOException {
		nodeTest(load("test/datagen/javascript/ArrayLiteralNode.boa"),
				load("test/datagen/javascript/ArrayLiteralNode.js"));
	}

	@Test
	public void varaibleDeclTest5() throws IOException {
		nodeTest(load("test/datagen/javascript/VariableDeclarationNode5.boa"),
				load("test/datagen/javascript/VariableDeclarationNode5.js"));
	}
}
