package se.good_omens.xmlModel;

import java.nio.charset.Charset;

import se.good_omens.xmlModel.exceptions.XmlNodeException;

/**
 * Will produce an XML Document.
 * This will be taking a XmlNode and make it into a properly document.
 * It presumes the header will be applied with version 1.0 and encoding UTF-8 
 * encoding. If no header is needed or unwanted it can be switched off.
 * However the encoding will still be enforced.
 *
 * @author tux
 */
public class XmlDocument {

	private final XmlNode root;
	private String version  = "1.0";
	private String encoding = "UTF-8";
	private boolean applyHeader = true;
	
	public XmlDocument(XmlNode rootNode) {
		if(rootNode == null) {
			throw new XmlNodeException("Root node for the document was found to be NULL. Unable to proceed in an orderly fashion.");
		}
		this.root = rootNode;
	}
	
	/*  GET/SET  */
	
	public XmlNode getRootNode() {
		return root;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}	
	public String getVersion() {
		return this.version;
	}
	
	public void setEncoding(String encoding) {
		if(!Charset.isSupported(encoding)) {
			throw new IllegalArgumentException("Unsupported Charset for this platform. Please update your request.");
		}
		this.encoding = encoding;
	}
	public String getEncoding() {
		return this.encoding;
	}
	
	public boolean willApplyHeader() {
		return this.applyHeader;
	}
	public void setApplyHeader(boolean applyHeader) {
		this.applyHeader = applyHeader;
	}
	
	/**
	 * Will produce a String that forms a properly formatted document.
	 */
	public String toString() {
		String toReturn = "";
		if(applyHeader) {
			toReturn = "<?xml version=\""+ this.version +"\" encoding=\""+ this.encoding +"\" ?>"+ System.getProperty("line.separator");
		}
		toReturn += root.xmlPrint();
		byte[] tmp = toReturn.trim().getBytes();
		toReturn = new String(tmp, Charset.forName(encoding)).trim();
		return toReturn;
	}
}
