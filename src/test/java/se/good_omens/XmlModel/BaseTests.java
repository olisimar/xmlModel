package se.good_omens.XmlModel;

import java.nio.charset.Charset;
import java.util.SortedMap;

import org.testng.annotations.Test;

public class BaseTests {

	@Test
	public void getEncodings() {
		SortedMap<String, Charset> encodings = Charset.availableCharsets();
		for(Charset cs : encodings.values()) {
			System.out.println(cs.displayName() +" <===> "+ cs.toString());
		}
	}
}
