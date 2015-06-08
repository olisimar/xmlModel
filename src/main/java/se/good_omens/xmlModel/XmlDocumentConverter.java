package se.good_omens.xmlModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import se.good_omens.xmlModel.exceptions.XmlModelException;

/**
 * Putting it here since it might beneficial to extract it's behaviour later.
 * @author Werner Johansson(tux)
 * @date Feb 13, 2012
 */
public class XmlDocumentConverter {
	private final String xml;
	private final Document doc;
	private final XmlNode root;
	
	public XmlDocumentConverter( String xml ) throws XmlModelException {
		this.xml = xml;
		this.doc = createDocument();
		this.root = parseDocument();
	}
	
	public String getXml() {
		return xml;
	}

	public Document getDoc() {
		return doc;
	}

	public XmlNode getRoot() {
		return root;
	}

	private XmlNode parseDocument() {
		XmlNode root = new XmlNode( doc.getDocumentElement() );
		parseAttributes( root, doc.getDocumentElement() );
		parseChildNodes( root );
		return root;
	}

	private void parseAttributes( XmlNode xmlNode, Node node ) {
		if( node.getAttributes() != null && node.getAttributes().getLength() != 0 ) {
			for( int index = 0; index < node.getAttributes().getLength(); index++ ) {
				xmlNode.addAttribute(node.getAttributes().item(index).getNodeName(), node.getAttributes().item(index).getTextContent());
			}
		}
	}
	
	private List<XmlNode> parseChildNodes( XmlNode parent ) {
		List<XmlNode> children = new ArrayList<XmlNode>();
		List<Node> kids = getChildNodes(parent.getW3cNode());
		for( Node node : kids ) {
			XmlNode xmlNode = parseNode( node );
			if( xmlNode != null ) {
				parent.addChildNode( xmlNode );
			}
		}
		
		return children;
	}
	
	private XmlNode parseNode(Node node) {
		if(node != null) {
			if( node.getChildNodes().getLength() > 0 ) {
				XmlNode xmlNode = new XmlNode( node );
				parseAttributes(xmlNode, node);
				for( Node child : getChildNodes( node ) ) {
					if( isValueNode(child) ) {
						addValueNode( xmlNode, child );
					} else {
						xmlNode.addChildNode( parseNode( child ) );
					}
				}
				return xmlNode;
			} else { // At this point we simply hope that the node is not a value node, if it is we return null.
				try {
					XmlNode xmlNode = new XmlNode( node );
					parseAttributes(xmlNode, node);
					return xmlNode;
				} catch(RuntimeException e) {
					return null;
				}
			}
		} else {
			return null;
		}
	}
	
	
	/*  UTIL  */
	
	private void addValueNode(XmlNode xmlNode, Node child) {
		XmlNode valueNode = new XmlNode( child.getPrefix(), child.getLocalName() );
		valueNode.setTextValue( child.getTextContent() );
		parseAttributes(valueNode, child);
		xmlNode.addChildNode( valueNode );
	}

	private boolean isValueNode( Node node ) {
		if( node.hasChildNodes() ) {
			if( node.getChildNodes().getLength() == 1 ) {
				if( !node.getChildNodes().item( 0 ).hasChildNodes() ) {
					return true;
				}
			}
		}
		return false;
	}
	
	private List<Node> getChildNodes( Node node ) {
		List<Node> children = new LinkedList<Node>();
		for( int index = 0; index < node.getChildNodes().getLength(); index++) {
			children.add( node.getChildNodes().item( index ) );
		}
		return children;
	}
	
	
	private Document createDocument() throws XmlModelException {
    InputSource is= new InputSource(new StringReader(xml));
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    try {
	    return factory.newDocumentBuilder().parse(is);
		} catch (ParserConfigurationException e) {
			//e.printStackTrace();
			throw new XmlModelException(xml, "ParserConfiguration in DocumentBuilderFactory failed. See attached cause.", e );
		} catch (SAXException e) {
			//e.printStackTrace();
			throw new XmlModelException(xml, "SAX exception("+ e.getClass().getSimpleName()+"): "+ e.getMessage(), e );
		} catch (IOException e) {
			//e.printStackTrace();
			throw new XmlModelException(xml, e.getMessage(), e );
		}
	}
}
