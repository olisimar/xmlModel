package se.good_omens.xmlModel;

import java.util.List;

import org.w3c.dom.Node;

public class XmlNodeCDATA extends XmlNode {

	protected String cdata = null;

	public XmlNodeCDATA() {
		this("");
	}
	public XmlNodeCDATA(Node node) {
		super(node);
	}

	/**
	 * Ignoring the data sent and setting it to a <!--CDATA-->
	 * @param data
	 */
	public XmlNodeCDATA(String data) {
		super("![CDATA[");
	}

	@Override
	public XmlNode addAttribute(String name, String value) {
		return this;
	}

	/**
	 * This node type can't have child-nodes.
	 */
	@Override
	public XmlNode addChildNode(XmlNode xmlNode) {
		return this;
	}

	/**
	 * This node type cannot have child-nodes.
	 */
	@Override
	public XmlNode addChildNodes(List<XmlNode> children) {
		return this;
	}

	public String getCDATA() {
		return this.cdata;
	}

	@Override
	public String getTextValue() {
		return this.cdata;
	}

	/**
	 * This node type cannot have attributes.
	 */
	@Override
	public void setAttributeList(List<Attribute> attributeList) {
	}

	public XmlNodeCDATA setCDATA(String cdata) {
		this.cdata = cdata;
		super.setTextValue(cdata);
		return this;
	}

	@Override
	public XmlNode setTextValue(String data) {
		this.cdata = data;
		super.setTextValue(data);
		return this;
	}


	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String xmlPrint() {
		return this.xmlPrint(0);
	}

	/**
	 * This will accept any INT but will print with as if it received a 0(Zero) when it comes to the data.
	 */
	@Override
	public String xmlPrint(int spaces) {
		StringBuilder toReturn = new StringBuilder();
		for (int count = 0; count < spaces; count++) {
			toReturn.append(" ");
		}
		toReturn.append("<![CDATA[");
		toReturn.append(this.getCDATA());
		toReturn.append("]]>");
		toReturn.append(lb);
		return toReturn.toString();
	}
}
