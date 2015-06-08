package se.good_omens.xmlModel;

import java.util.List;

import org.w3c.dom.Document;

import se.good_omens.xmlModel.exceptions.XmlModelException;

/**
 * A parsed piece of XML essentially. Once the XML is put in it will be parsed
 * and then be ready for querying. The model in its self isn't much more than
 * the xml converted into several linked XmlNodes with related attributes and
 * values
 *
 * @author Werner Johansson(tux)
 * @date Feb 13, 2012
 */
public class XmlModel {

	private final XmlDocumentConverter parser;
	private final XmlNode model;
	private final XmlNodeTraverser traverser;

	public XmlModel(String xml) throws XmlModelException {
		parser = new XmlDocumentConverter(xml);
		model = parser.getRoot();
		traverser = new XmlNodeTraverser(model);
	}

	public Document getDocument() {
		return parser.getDoc();
	}

	public XmlNode getModel() {
		return model;
	}

	/**
	 * This will return a list of node matching the path provided. '/' signifies a
	 * parent/child relationship. If a path is started with a '/' it signifies
	 * that the path is considered to start at the root element. '@' signifies
	 * that the node in question should contain an attribute named as the text
	 * directly following it. '#' signifies that a value of an attribute node
	 * attached to the node in question should have the key name directly
	 * following the it.
	 *
	 * @note Path provided should be deterministic enough to select one node only.
	 *
	 * @throws IllegalStateException
	 *           - If multiple nodes are found as start or ending point.
	 * @throws IllegalArgumentException
	 *           - if the path is at some point found to be malformed.
	 * @param path
	 * @return
	 */
	public XmlNode getNodeByPath(String path) throws XmlModelException {
		return traverser.getNodeByPath(path);
	}

	/* NODE SEARCHING METHODS */

	public List<XmlNode> getNodesByAttributeName(String attributeName) {
		return traverser.getNodesByAttributeName(attributeName);
	}

	public List<XmlNode> getNodesByAttributeValue(String attributeValue) {
		return traverser.getNodesByAttributeValue(attributeValue);
	}

	public List<XmlNode> getNodesByContainingTextValue(String textValue) {
		return traverser.getNodesByContainingTextValue(textValue);
	}

	public List<XmlNode> getNodesByName(String name) {
		return traverser.getNodesByName(name);
	}

	public List<XmlNode> getNodesByParentsName(String parent) {
		return traverser.getNodesByParentsName(parent);
	}

	public List<XmlNode> getNodesByParentsPrefix(String parentPrefix) {
		return traverser.getNodesByParentsPrefix(parentPrefix);
	}

	/**
	 * This will return a list of node matching the path provided. '/' signifies a
	 * parent/child relationship. If a path is started with a '/' it signifies
	 * that the path is considered to start at the root element. '@' signifies
	 * that the node in question should contain an attribute named as the text
	 * directly following it. '#' signifies that a value of an attribute node
	 * attached to the node in question should have the key name directly
	 * following the it.
	 *
	 * @note Path provided should be deterministic enough to select one node only.
	 *
	 *
	 * @throws IllegalStateException
	 *           - If multiple nodes are found as start point.
	 * @throws IllegalArgumentException
	 *           - if the path is at some point found to be malformed.
	 * @param path
	 * @return
	 */
	public List<XmlNode> getNodesByPath(String path) {
		return traverser.getNodesOnPath(path);
	}

	public List<XmlNode> getNodesByPrefix(String prefix) {
		return traverser.getNodesByPrefix(prefix);
	}

	public String getXml() {
		return parser.getXml();
	}

	/**
	 * Returns the model as a textString;
	 */
	@Override
	public String toString() {
		return model.toString();
	}
}
