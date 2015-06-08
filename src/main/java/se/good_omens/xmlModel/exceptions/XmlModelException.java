package se.good_omens.xmlModel.exceptions;

/**
 * A basic exception to wrap a few of the various message that can pop up as
 * part of the rest.
 * @author werner.johansson
 * @date 15 feb 2012
 */
public class XmlModelException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String xml;
	
	public XmlModelException( String xml, String errorMessage ) {
		this( xml, errorMessage, null);
	}
	
	public XmlModelException( String xml, String message, Exception causingException ) {
		super( message, causingException );
		this.xml = xml;
	}

	public String getXml() {
		return xml;
	}

}
