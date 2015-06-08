package se.good_omens.XmlModel;

import junit.framework.Assert;

import org.testng.annotations.Test;

import se.good_omens.xmlModel.XmlNodeCDATA;

public class TestCDATANodes {

	@Test
	public void test_printEmptyCDATA() {
		System.out.println("Test 1");
		XmlNodeCDATA data = new XmlNodeCDATA();
		System.out.println(data.xmlPrint());
		Assert.assertEquals(null, data.getCDATA());
	}

	@Test
	public void test_printWithData() {
		String CDATA = "CRAP ON A BREADSTRING!";
		System.out.println("Test 2");
		XmlNodeCDATA data = new XmlNodeCDATA();
		System.out.println(data.setTextValue( CDATA ));
		System.out.println(data.xmlPrint());

		Assert.assertEquals(CDATA, data.getTextValue());
		Assert.assertEquals(CDATA, data.getCDATA());
	}
}
