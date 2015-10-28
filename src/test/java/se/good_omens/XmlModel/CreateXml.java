package se.good_omens.XmlModel;



import org.testng.Assert;
import org.testng.annotations.Test;

import se.good_omens.xmlModel.XmlModel;
import se.good_omens.xmlModel.XmlNode;
import se.good_omens.xmlModel.exceptions.XmlModelException;
import se.good_omens.xmlModel.exceptions.XmlNodeException;

/**
 * This is to test the creation of a fully fledged XML
 * @author tux
 *
 */
public class CreateXml {

	public static final String lb = System.getProperty("line.separator");
	public static final boolean baseLineTests = true;
	public static final boolean reversibleXmlTest = true;

	@Test(enabled=baseLineTests)
	public void test_create_a_node_and_print() {
		XmlNode node = new XmlNode("crap");
		System.out.println(node);
	}

	@Test(enabled=baseLineTests)
	public void test_create_a_simple_node_and_xmlPrint() {
		XmlNode node = new XmlNode("crap");
		System.out.println(node.xmlPrint());
		Assert.assertEquals("<crap />"+lb, node.xmlPrint());
	}

	@Test(enabled=baseLineTests)
	public void test_create_a_singleNode_with_Text() {
		XmlNode node = new XmlNode("crap");
		node.setTextValue("Gustaf");
		Assert.assertEquals("<crap>Gustaf</crap>"+lb, node.xmlPrint());
	}

	@Test(enabled=baseLineTests)
	public void test_create_a_singleNode_with_Attribute() {
		XmlNode node = new XmlNode("crap");
		node.addAttribute("name", "Gustaf");
		Assert.assertEquals("<crap name=\"Gustaf\" />"+lb, node.xmlPrint());
	}

	@Test(enabled=baseLineTests)
	public void test_create_a_singleNode_with_Attribute_and_Text() {
		XmlNode node = new XmlNode("crap");
		node.addAttribute("name", "Gustaf");
		node.setTextValue("Monday");
		Assert.assertEquals("<crap name=\"Gustaf\">Monday</crap>"+lb, node.xmlPrint());
	}

	@Test(enabled=baseLineTests , expectedExceptions={XmlNodeException.class})
	public void test_create_a_singleNode_with_text_then_Child_toFail() {
		XmlNode node = new XmlNode("exception");
		node.setTextValue("failure");
		node.addChildNode(new XmlNode("killer"));
		System.out.println(node.xmlPrint());
	}

	@Test(enabled=baseLineTests, expectedExceptions={XmlNodeException.class})
	public void test_create_a_singleNode_with_childNode_toFail() {
		XmlNode node = new XmlNode("exception");
		node.addChildNode(new XmlNode("killer"));
		node.setTextValue("failure");
		System.out.println(node.xmlPrint());
	}

	@Test(enabled=baseLineTests)
	public void test_create_root_with_child_node_and_XmlPrint() {
		XmlNode parent = new XmlNode("crap", "crock");
		parent.addChildNode(new XmlNode("shit").setTextValue("up a river"));
		System.out.println(parent.xmlPrint());
	}

	@Test(enabled=reversibleXmlTest)
	public void test_parseProducedXml() {
		XmlNode parent = new XmlNode("crock");
		parent.addChildNode(new XmlNode("shit").setTextValue("up a river"));
		try {
			XmlModel base = new XmlModel(parent.xmlPrint());
			System.out.println(base.getXml());
		}
		catch (XmlModelException e) {
			e.printStackTrace();
		}
	}
}
