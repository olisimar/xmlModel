package se.good_omens.xmlModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;

import se.good_omens.xmlModel.exceptions.XmlNodeException;

/**
 * This class is to represent a single node within an XML tree. It should be
 * able to deal with prefix, names, attributes as well as node values in various
 * ways.
 *
 * @author Werner Johansson(tux)
 * @date Feb 13, 2012
 */
public class XmlNode {

	/**
	 * This is used to describe a key value pair most often found during parsing
	 * attributes. It is also used to describe a value node found during parsing
	 * of a node.
	 *
	 * @author Werner Johansson(tux)
	 * @date Feb 13, 2012
	 */
	public class Attribute {
		private final String key;
		private final String value;

		public Attribute(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return key + " : " + value;
		}
	}
	protected enum NODE_FIELDS {
		PREFIX, NAME, FULL_NAME, PARENT_NAME, PARENT_PREFIX, PARENT_FULLNAME, TEXT_VALUE, ATTRIBUTE_NAME, ATTRIBUTE_VALUE
	}
	private List<Attribute> attributeList = null;
	private final String prefix;

	private final String name;

	private String textValue = null;
	private Node w3cNode = null;
	private XmlNode parent = null;

	private List<XmlNode> children = null;;

	protected String lb = System.getProperty("line.separator");

	public XmlNode(Node node) {
		if ((node.getLocalName() == null) || node.getLocalName().trim().isEmpty()) {
			throw new XmlNodeException(
					"Value Node passed in as a proper node, please correct code. Belongs to: "
							+ node.getParentNode().getNodeName() + " this node being '"
							+ node.getTextContent() + "'");
		}
		this.w3cNode = node;
		this.prefix = node.getPrefix();
		this.name = node.getLocalName();
	}

	/* CONSTRUCTORS */
	public XmlNode(String name) {
		this(null, name);
	}

	/* GET */

	public XmlNode(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	public XmlNode addAttribute(String name, String value) {
		if (name != null) {
			if (attributeList == null) {
				attributeList = new LinkedList<Attribute>();
			}
			if (value != null) {
				value = value.trim();
			}
			attributeList.add(new Attribute(name.trim(), value));
		}
		return this;
	}

	public XmlNode addChildNode(XmlNode xmlNode) {
		if (this.hasTextValue()) {
			throw new XmlNodeException(this.getName()
					+ " has a text value, invalid request.");
		}
		if (this.children == null) {
			this.children = new ArrayList<XmlNode>();
		}
		this.children.add(xmlNode);
		return this;
	}

	public XmlNode addChildNodes(List<XmlNode> children) {
		this.children = children;
		return this;
	}

	public List<Attribute> getAttributeList() {
		return this.attributeList;
	}

	public String getAttributeValue(String key) {
		if (attributeList != null) {
			for (Attribute attr : attributeList) {
				if (attr.getKey().equalsIgnoreCase(key)) {
					return attr.getValue();
				}
			}
		}
		throw new IllegalArgumentException(
				"Attribute Value was not present for key provided. Node: "
						+ this.getName() + " Recieved: " + key);
	}

	public List<XmlNode> getChildren() {
		return this.children;
	}

	/**
	 * Utility method to find a direct childNode by name. First found will be returned.
	 * There is no garuantee for a consistent result. Suggested to be used when a unique
	 * child is expected and not a list of children sharing the name.
	 * @param nameOfChild
	 * @return
	 */
	public XmlNode getChildByName(String nameOfChild) {
		if(nameOfChild == null) {
			throw new NullPointerException("getChildByName was provided with a NULL.");
		}
		if(this.hasChildren()) {
			for(XmlNode child : this.getChildren()) {
				if(child.getName().equalsIgnoreCase(nameOfChild.trim())) {
					return child;
				}
			}
		}
		return null;
	}

	private String getEmptySpaces() {
		XmlNode ref = parent;
		StringBuilder toReturn = new StringBuilder();
		int spaces = 0;
		while (ref != null) {
			spaces += 3;
			ref = ref.getParent();
		}
		while (spaces != 0) {
			toReturn.append(" ");
			spaces--;
		}
		return toReturn.toString();
	}

	/* SET */

	/**
	 * Gets the name without the prefix.
	 *
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public XmlNode getParent() {
		return this.parent;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getTextValue() {
		return this.textValue;
	}

	public Node getW3cNode() {
		return this.w3cNode;
	}

	public boolean hasAttributeKey(String key) {
		if (attributeList != null) {
			for (Attribute attr : attributeList) {
				if (attr.getKey().equalsIgnoreCase(key)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasAttributes() {
		if ((attributeList != null) && !attributeList.isEmpty()) {
			return true;
		}
		return false;
	}

	/* IS | HAS */

	public boolean hasChildren() {
		if ((children != null) && !children.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean hasTextValue() {
		if (this.getTextValue() != null) {
			return true;
		}
		return false;
	}

	protected boolean matchesRequest(String sought, NODE_FIELDS attributeName) {
		if ((sought == null) || sought.trim().isEmpty()) {
			return false;
		}
		switch (attributeName) {
			case NAME:
				if ((name != null) && name.equalsIgnoreCase(sought)) {
					return true;
				}
				return false;
			case FULL_NAME:
				String[] nameParts = sought.split(":");
				if (nameParts.length == 2) {
					if (matchesRequest(nameParts[0], NODE_FIELDS.PREFIX)
							&& matchesRequest(nameParts[1], NODE_FIELDS.NAME)) {
						return true;
					}
				}
				return false;
			case PREFIX:
				if ((prefix != null) && prefix.equalsIgnoreCase(sought)) {
					return true;
				}
				return false;
			case PARENT_NAME:
				if ((parent.getName() != null) && parent.getName().equalsIgnoreCase(sought)) {
					return true;
				}
				return false;
			case PARENT_PREFIX:
				if ((parent.getPrefix() != null)
						&& parent.getPrefix().equalsIgnoreCase(sought)) {
					return true;
				}
				return false;
			case PARENT_FULLNAME:
				String[] parentParts = sought.split(":");
				if (parentParts.length == 2) {
					if (parent.matchesRequest(parentParts[0], NODE_FIELDS.PREFIX)
							&& parent.matchesRequest(parentParts[1], NODE_FIELDS.NAME)) {
						return true;
					}
				}
				return false;
			case TEXT_VALUE:
				if ((textValue != null) && textValue.contains(sought)) {
					return true;
				}
				return false;
			case ATTRIBUTE_NAME:
				if (attributeList != null) {
					for (Attribute attr : attributeList) {
						if ((attr.getKey() != null) && attr.getKey().equalsIgnoreCase(sought)) {
							return true;
						}
					}
				}
				return false;
			case ATTRIBUTE_VALUE:
				if (attributeList != null) {
					for (Attribute attr : attributeList) {
						if ((attr.getValue() != null) && attr.getValue().contains(sought)) {
							return true;
						}
					}
				}
				return false;
			default:
				return false;
		}
	}

	public void setAttributeList(List<Attribute> attributeList) {
		this.attributeList = attributeList;
	}

	/* ************************************************** */

	/* UTIL */

	public XmlNode setParent(XmlNode parent) {
		this.parent = parent;
		return this;
	}

	public XmlNode setTextValue(String textValue) {
		if (textValue != null) {
			if (this.hasChildren()) {
				throw new XmlNodeException(
						"This node has children, invalid input. Failed at node "
								+ this.getName() + " with TextValue being: " + textValue);
			}
			textValue = textValue.replaceAll(lb, "");
			this.textValue = textValue.trim();
		}
		this.textValue = textValue;
		return this;
	}

	/**
	 * This is used with the model only, there is no need to even use this outside
	 * of the XmlModels requirements.
	 *
	 * @param w3cNode
	 */
	public void setW3cNode(Node w3cNode) {
		this.w3cNode = w3cNode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getEmptySpaces() + "|_____________________" + lb);
		builder.append(getEmptySpaces() + "| Node: <");
		if (prefix != null) {
			builder.append(prefix + " : ");
		}
		builder.append(name + ">" + lb);
		if (textValue != null) {
			builder.append(getEmptySpaces() + "| TextValue: " + textValue + lb);
		}
		if (parent == null) {
			builder.append("| Root Element" + lb);
		}
		if (attributeList != null) {
			builder.append(getEmptySpaces() + "+ Attributes: " + lb);
			for (Attribute attr : attributeList) {
				builder.append(getEmptySpaces() + "| +-" + attr + lb);
			}
		}

		if (children != null) {
			builder.append(getEmptySpaces() + "|- Children :" + name + lb);
			for (XmlNode child : children) {
				if (child != null) {
					builder.append(child.toString());
				} else {
					System.out.println("Null child attached, parser failed.");
				}
			}
			builder.append(getEmptySpaces() + "|-Children :" + name + lb);
		}

		return builder.toString();
	}

	public String xmlPrint() {
		return this.xmlPrint(0);
	}

	public String xmlPrint(int spaces) {
		StringBuilder toReturn = new StringBuilder();
		for (int count = 0; count < spaces; count++) {
			toReturn.append(" ");
		}
		toReturn.append("<");
		if (this.getPrefix() != null) {
			toReturn.append(this.getPrefix() + ":");
		}
		toReturn.append(this.getName());

		if (this.hasAttributes()) {
			toReturn = xmlPrintAddAttribute(toReturn);
		}

		if (this.getTextValue() != null) {
			toReturn = xmlPrintAddTextValue(toReturn);
		} else if (this.hasChildren()) {
			toReturn = xmlPrintAddChildNodes(spaces, toReturn);
		} else {
			toReturn.append(" />");
		}
		toReturn.append(lb);
		return toReturn.toString();
	}

	private StringBuilder xmlPrintAddAttribute(StringBuilder toReturn) {
		for (Attribute attr : this.getAttributeList()) {
			toReturn.append(" ");
			toReturn.append(attr.getKey());
			toReturn.append("=\"");
			toReturn.append(attr.getValue());
			toReturn.append("\"");
		}
		return toReturn;
	}

	private StringBuilder xmlPrintAddChildNodes(int spaces, StringBuilder toReturn) {
		toReturn.append(">" + lb);
		int newSpaces = spaces + 2;
		for (XmlNode child : this.getChildren()) {
			toReturn.append(child.xmlPrint(newSpaces));
		}
		for (int count = 0; count < spaces; count++) {
			toReturn.append(" ");
		}
		toReturn.append("</");
		if (this.getPrefix() != null) {
			toReturn.append(this.getPrefix() + ":");
		}
		toReturn.append(this.getName());
		toReturn.append(">");
		return toReturn;
	}

	private StringBuilder xmlPrintAddTextValue(StringBuilder toReturn) {
		toReturn.append(">");
		toReturn.append(this.getTextValue());
		toReturn.append("</");
		if (this.getPrefix() != null) {
			toReturn.append(this.getPrefix() + ":");
		}
		toReturn.append(this.getName());
		toReturn.append(">");
		return toReturn;
	}
}
