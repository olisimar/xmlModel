package se.good_omens.XmlModel;

import org.testng.annotations.Test;

import se.good_omens.xmlModel.XmlModel;
import se.good_omens.xmlModel.exceptions.XmlModelException;

/**
 * Baseline tests. Parse tests. 
 * @author tux
 */
public class BaselineTests {
	
	public static final boolean printResults = false;
	public static final boolean baseTests = true;

	
	@Test(enabled=baseTests)
	public void test_parse_singleElementXml_and_print() throws XmlModelException {
		XmlModel model = new XmlModel(singleElementXml);
		if(printResults) {
			System.out.println(model);
		}
	}
	
	@Test(enabled=baseTests)
	public void test_parse_namespaceXml_and_print() throws XmlModelException {
		XmlModel model = new XmlModel(namespaceXml);
		if(printResults) {
			System.out.println(model);
		}
	}
	
	@Test(enabled=baseTests)
	public void test_parse_simpleXml_and_print() throws XmlModelException {
		XmlModel model = new XmlModel(simpleXml);
		if(printResults) {
			System.out.println(model);
		}
	}
	
	@Test(enabled=baseTests)
	public void test_parse_breakfastmenuXml_and_print() throws XmlModelException {
		XmlModel model = new XmlModel(breakfastmenuXml);
		if(printResults) {
			System.out.println(model);
		}
	}
	
	@Test(enabled=baseTests)
	public void test_parse_complexXml_and_print() throws XmlModelException {
		XmlModel model = new XmlModel(complexXml);
		if(printResults) {
			System.out.println(model);
		}
	}

	
	/* ================================================================= */
	
	public static String singleElementXml =
			"<root />";
	
	/* ================================================================= */
	
	public static String simpleXml = 
			"<note> "+
				"<to>Tove</to> "+
				"<from>Jani</from> "+
				"<heading>Reminder</heading> "+
				"<body>Don't forget me this weekend!</body> "+
			"</note>";
	
	/* ================================================================= */
	
	public static String complexXml =
			"<?xml version='1.0' encoding='UTF-8'?>" +
			"<ref>" +
			"	<quote>" +
			"		<author>Kalle Anka </author>" +
			"		<location>Ducklane 13</location>" +
			"		<quote>QUAK!</quote>" +
			"		<extra type='witness'>Lukas Duck</extra>" +
			"		<extra type='witness'>Daisy Duck</extra>" +
			"		<reporter>" +
			"			<name reliability='3' type='hearsay'>Daffy Duck</name>" +
			"			<date type='ISO'>2013-07-03</date>" +
			"			<profession type='reporter' />" +
			"		</reporter>" +
			"	</quote>" +
			"	<publisher type='print'>" +
			"		<editor legality='true'>Goofy Dog</editor>" +
			"		<issued date='2013-07-03' />" +
			"		<extra>" +
			"			<image width='300px' height='400pc' type='byline' caption='Daffy Duck, dirtdigger'>DaffyDuck.png</image>" +
			"		</extra>" +
			"	</publisher>" +
			"</ref>";
	
	
	/* ================================================================= */
	
	public static String namespaceXml =
			"<root>" +
			"	<h:table xmlns:h='http://www.w3.org/TR/html4/'>" +
			"		<h:tr>" +
			"			<h:td style='color=red;'>Apples</h:td>" +
			"			<h:td style='color=yellow'>Bananas</h:td>" +
			"		</h:tr>" +
			"	</h:table>" +
			"	<f:table xmlns:f='http://www.w3schools.com/furniture'>" +
			"		<f:name>African Coffee Table</f:name>" +
			"		<f:width>80</f:width>" +
			"		<f:length>120</f:length>" +
			"	</f:table>" +
			"</root>";
	
	/* ================================================================= */

	public static String catalogXml =
			"<CATALOG>" + 
			"	<CD>" + 
			"		<TITLE>Empire Burlesque</TITLE>" + 
			"		<ARTIST>Bob Dylan</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>Columbia</COMPANY>" + 
			"		<PRICE>10.90</PRICE>" + 
			"		<YEAR>1985</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Hide your heart</TITLE>" + 
			"		<ARTIST>Bonnie Tyler</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>CBS Records</COMPANY>" + 
			"		<PRICE>9.90</PRICE>" + 
			"		<YEAR>1988</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Greatest Hits</TITLE>" + 
			"		<ARTIST>Dolly Parton</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>RCA</COMPANY>" + 
			"		<PRICE>9.90</PRICE>" + 
			"		<YEAR>1982</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Still got the blues</TITLE>" + 
			"		<ARTIST>Gary Moore</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Virgin records</COMPANY>" + 
			"		<PRICE>10.20</PRICE>" + 
			"		<YEAR>1990</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Eros</TITLE>" + 
			"		<ARTIST>Eros Ramazzotti</ARTIST>" + 
			"		<COUNTRY>EU</COUNTRY>" + 
			"		<COMPANY>BMG</COMPANY>" + 
			"		<PRICE>9.90</PRICE>" + 
			"		<YEAR>1997</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>One night only</TITLE>" + 
			"		<ARTIST>Bee Gees</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Polydor</COMPANY>" + 
			"		<PRICE>10.90</PRICE>" + 
			"		<YEAR>1998</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Sylvias Mother</TITLE>" + 
			"		<ARTIST>Dr.Hook</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>CBS</COMPANY>" + 
			"		<PRICE>8.10</PRICE>" + 
			"		<YEAR>1973</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Maggie May</TITLE>" + 
			"		<ARTIST>Rod Stewart</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Pickwick</COMPANY>" + 
			"		<PRICE>8.50</PRICE>" + 
			"		<YEAR>1990</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Romanza</TITLE>" + 
			"		<ARTIST>Andrea Bocelli</ARTIST>" + 
			"		<COUNTRY>EU</COUNTRY>" + 
			"		<COMPANY>Polydor</COMPANY>" + 
			"		<PRICE>10.80</PRICE>" + 
			"		<YEAR>1996</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>When a man loves a woman</TITLE>" + 
			"		<ARTIST>Percy Sledge</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>Atlantic</COMPANY>" + 
			"		<PRICE>8.70</PRICE>" + 
			"		<YEAR>1987</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Black angel</TITLE>" + 
			"		<ARTIST>Savage Rose</ARTIST>" + 
			"		<COUNTRY>EU</COUNTRY>" + 
			"		<COMPANY>Mega</COMPANY>" + 
			"		<PRICE>10.90</PRICE>" + 
			"		<YEAR>1995</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>1999 Grammy Nominees</TITLE>" + 
			"		<ARTIST>Many</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>Grammy</COMPANY>" + 
			"		<PRICE>10.20</PRICE>" + 
			"		<YEAR>1999</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>For the good times</TITLE>" + 
			"		<ARTIST>Kenny Rogers</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Mucik Master</COMPANY>" + 
			"		<PRICE>8.70</PRICE>" + 
			"		<YEAR>1995</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Big Willie style</TITLE>" + 
			"		<ARTIST>Will Smith</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>Columbia</COMPANY>" + 
			"		<PRICE>9.90</PRICE>" + 
			"		<YEAR>1997</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Tupelo Honey</TITLE>" + 
			"		<ARTIST>Van Morrison</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Polydor</COMPANY>" + 
			"		<PRICE>8.20</PRICE>" + 
			"		<YEAR>1971</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Soulsville</TITLE>" + 
			"		<ARTIST>Jorn Hoel</ARTIST>" + 
			"		<COUNTRY>Norway</COUNTRY>" + 
			"		<COMPANY>WEA</COMPANY>" + 
			"		<PRICE>7.90</PRICE>" + 
			"		<YEAR>1996</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>The very best of</TITLE>" + 
			"		<ARTIST>Cat Stevens</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Island</COMPANY>" + 
			"		<PRICE>8.90</PRICE>" + 
			"		<YEAR>1990</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Stop</TITLE>" + 
			"		<ARTIST>Sam Brown</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>A and M</COMPANY>" + 
			"		<PRICE>8.90</PRICE>" + 
			"		<YEAR>1988</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Bridge of Spies</TITLE>" + 
			"		<ARTIST>T'Pau</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Siren</COMPANY>" + 
			"		<PRICE>7.90</PRICE>" + 
			"		<YEAR>1987</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Private Dancer</TITLE>" + 
			"		<ARTIST>Tina Turner</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>Capitol</COMPANY>" + 
			"		<PRICE>8.90</PRICE>" + 
			"		<YEAR>1983</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Midt om natten</TITLE>" + 
			"		<ARTIST>Kim Larsen</ARTIST>" + 
			"		<COUNTRY>EU</COUNTRY>" + 
			"		<COMPANY>Medley</COMPANY>" + 
			"		<PRICE>7.80</PRICE>" + 
			"		<YEAR>1983</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Pavarotti Gala Concert</TITLE>" + 
			"		<ARTIST>Luciano Pavarotti</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>DECCA</COMPANY>" + 
			"		<PRICE>9.90</PRICE>" + 
			"		<YEAR>1991</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>The dock of the bay</TITLE>" + 
			"		<ARTIST>Otis Redding</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>Atlantic</COMPANY>" + 
			"		<PRICE>7.90</PRICE>" + 
			"		<YEAR>1987</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Picture book</TITLE>" + 
			"		<ARTIST>Simply Red</ARTIST>" + 
			"		<COUNTRY>EU</COUNTRY>" + 
			"		<COMPANY>Elektra</COMPANY>" + 
			"		<PRICE>7.20</PRICE>" + 
			"		<YEAR>1985</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Red</TITLE>" + 
			"		<ARTIST>The Communards</ARTIST>" + 
			"		<COUNTRY>UK</COUNTRY>" + 
			"		<COMPANY>London</COMPANY>" + 
			"		<PRICE>7.80</PRICE>" + 
			"		<YEAR>1987</YEAR>" + 
			"	</CD>" + 
			"	<CD>" + 
			"		<TITLE>Unchain my heart</TITLE>" + 
			"		<ARTIST>Joe Cocker</ARTIST>" + 
			"		<COUNTRY>USA</COUNTRY>" + 
			"		<COMPANY>EMI</COMPANY>" + 
			"		<PRICE>8.20</PRICE>" + 
			"		<YEAR>1987</YEAR>" + 
			"	</CD>" + 
			"</CATALOG>";
	
	/* ================================================================= */
	
	public static String breakfastmenuXml =
			"<breakfast_menu>" +
			"	<food>" +
			"		<name>Belgian Waffles</name>" +
			"		<price>$5.95</price>" +
			"		<description>Two of our famous Belgian Waffles with plenty of real maple syrup</description>" +
			"		<calories>650</calories>" +
			"	</food>" +
			"	<food>" +
			"		<name>Strawberry Belgian Waffles</name>" +
			"		<price>$7.95</price>" +
			"		<description>Light Belgian waffles covered with strawberries and whipped cream</description>" +
			"		<calories>900</calories>" +
			"	</food>" +
			"	<food>" +
			"		<name>Berry-Berry Belgian Waffles</name>" +
			"		<price>$8.95</price>" +
			"		<description>Light Belgian waffles covered with an assortment of fresh berries and whipped cream</description>" +
			"		<calories>900</calories>" +
			"	</food>" +
			"	<food>" +
			"		<name>French Toast</name>" +
			"		<price>$4.50</price>" +
			"		<description>Thick slices made from our homemade sourdough bread</description>" +
			"		<calories>600</calories>" +
			"	</food>" +
			"	<food>" +
			"		<name calories='950'>Homestyle Breakfast</name>" +
			"		<price>$6.95</price>" +
			"		<description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description>" +
			"		<calories>950</calories>" +
			"	</food>" +
			"</breakfast_menu>";

}
