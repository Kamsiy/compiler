package boa.test.datagen.xml;

import java.io.IOException;

import org.junit.Test;

public class TestAttribute extends XMLBaseTest {

	@Test
	public void testNameSpace() throws IOException, Exception{
		nodeTest(load("test/datagen/XML/Attribute.boa"),
				("test/datagen/XML/Attribute.XML"));
	}
	
}
