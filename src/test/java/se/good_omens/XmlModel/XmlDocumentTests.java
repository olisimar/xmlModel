package se.good_omens.XmlModel;

import org.testng.annotations.*;

import se.good_omens.xmlModel.XmlDocument;
import se.good_omens.xmlModel.XmlNode;
import se.good_omens.xmlModel.exceptions.XmlNodeException;

public class XmlDocumentTests {

	public static final String lb = System.getProperty("line.separator");
	
	@Test
	public void test_print_basic_document() {
		XmlDocument doc = new XmlDocument(new XmlNode("base"));
		System.out.println("=== ============================"+ lb + doc + lb +"=== ============================");
	}
	
	@Test(expectedExceptions = {XmlNodeException.class})
	public void test_checkThatNullXmlNodeThrowsException() {
		XmlDocument doc = new XmlDocument(null);
		System.out.println(doc);
	}
	
	
}
