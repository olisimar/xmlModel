package se.good_omens.XmlModel;

import org.testng.annotations.Test;

import se.good_omens.xmlModel.XmlNode;

public class TestVisualPrintOutTests {

	@Test
	public void test_printBasicFragment() {
		XmlNode root = new XmlNode("root");
		System.out.println(root.xmlPrint());
	}
	
	@Test
	public void test_printBasicWithFirstChildFragment() {
		XmlNode root = new XmlNode("root");
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		System.out.println(root.xmlPrint());
	}
	
	@Test
	public void test_printBasicFirstSecondChildFragment() {
		XmlNode root = new XmlNode("root");
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		root.addChildNode(new XmlNode("secondChild").setTextValue("secondText"));
		System.out.println(root.xmlPrint());
	}
	
	@Test
	public void test_printBasicFirstFirstFragment() {
		XmlNode root = new XmlNode("root");
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		System.out.println(root.xmlPrint());
	}
	
	@Test
	public void test_printBasicFirstSecondAttributeFragment() {
		XmlNode root = new XmlNode("root");
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		root.addChildNode(new XmlNode("secondChild").addAttribute("firstAttr", "firstValue"));
		System.out.println(root.xmlPrint());
	}
	
	@Test
	public void test_printBasicFirstSecondFirstFragment() {
		XmlNode root = new XmlNode("root");
		root.addChildNode(new XmlNode("firstChild").setTextValue("firstText"));
		root.addChildNode(new XmlNode("secondChild").addChildNode(new XmlNode("firstChild").setTextValue("firstInnerChild")));
		System.out.println(root.xmlPrint());
	}
	
}
