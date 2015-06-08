package se.good_omens.xmlModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.good_omens.xmlModel.XmlNode.NODE_FIELDS;
import se.good_omens.xmlModel.exceptions.XmlModelException;


/**
 * Provide a XmlModel or a XmlNode this will then proceed to work over
 * the nodes with any query you may have.
 * 
 * @author Werner Johansson(tux)
 * @date Feb 14, 2012
 */
public class XmlNodeTraverser {

	private final XmlNode rootNode;
	
	public XmlNodeTraverser( XmlNode rootNode ) {
		this.rootNode = rootNode;
	}
	
	public List<XmlNode> getNodesByName( String name ) {
		if( name.contains( ":" ) ) {
			if( name.endsWith( ":" ) ) {
				return getNodeByField( name.substring( 0, (name.length() - 1 )), NODE_FIELDS.PREFIX );
			} else if( name.startsWith( ":" ) ) {
				return getNodeByField( name.substring( 1 ), NODE_FIELDS.NAME );
			} else {
				return getNodeByField( name, NODE_FIELDS.FULL_NAME );
			}
		} else {
			return getNodeByField(name, NODE_FIELDS.NAME );
		}
	}
	
	public List<XmlNode> getNodesByPrefix( String name ) {
		return getNodeByField( name, NODE_FIELDS.PREFIX );
	}
	
	public List<XmlNode> getNodesByParentsName( String name ) {
		if( name.contains( ":" ) ) {
			if( name.endsWith( ":" )) {
				return getNodeByField( name.substring( 0, (name.length() -1) ), NODE_FIELDS.PARENT_PREFIX );
			} else if( name.startsWith(":") ) {
				return getNodeByField( name.substring( 1 ), NODE_FIELDS.PARENT_NAME );
			} else {
				return getNodeByField( name, NODE_FIELDS.PARENT_FULLNAME );
			}
		} else {
			return getNodeByField( name, NODE_FIELDS.PARENT_NAME );
		}
	}
	
	public List<XmlNode> getNodesByParentsPrefix( String name ) {
		return getNodeByField( name, NODE_FIELDS.PARENT_PREFIX );
	}
	
	public List<XmlNode> getNodesByContainingTextValue( String name ) {
		return getNodeByField( name, NODE_FIELDS.TEXT_VALUE );
	}
	
	public List<XmlNode> getNodesByAttributeName( String name ) {
		return getNodeByField( name, NODE_FIELDS.ATTRIBUTE_NAME );
	}
	
	public List<XmlNode> getNodesByAttributeValue( String name ) {
		return getNodeByField( name, NODE_FIELDS.ATTRIBUTE_VALUE );
	}
	
	/**
	 * This will return a list of node matching the path provided.
	 * '/' signifies a parent/child relationship. 
	 * If a path is started with a '/' it signifies that the path is considered 
	 *    to start at the root element.
	 * '@' signifies that the node in question should contain an attribute named 
	 *    as the text directly following it.
	 * '#' signifies that a value of an attribute node attached to the node in 
	 *    question should have the key name directly following the it.
	 * @note Path provided should be deterministic enough to select one node only.
	 * 
	 * @param path
	 * @return
	 * @throws XmlModelException
	 */
	public XmlNode getNodeByPath( String path ) throws XmlModelException {
		if( path != null && !path.isEmpty() ) {
			List<XmlNode> list = getNodesOnPath( path );
			if( list.size() == 0 ) {
				return null;
			} else if( list.size() == 1 ) {
				return list.get( 0 );
			} else {
				String xml = "";
				for( XmlNode node : list ) {
					xml += node.toString();
					xml += " === NODE ===================== "+ System.getProperty( "line.separator" );
				}
				throw new XmlModelException( xml, "Found multiple nodes matching provided path. Please study provided xml, number of nodes are: "+ list.size() );
			}
		}
		throw new IllegalArgumentException( "Path may not be null or empty." );
	}
	
	/**
	 * This will return a list of node matching the path provided.
	 * '/' signifies a parent/child relationship. 
	 * If a path is started with a '/' it signifies that the path is considered to start at the root element.
	 * '@' signifies that the node in question should contain an attribute named as the text directly following it.
	 * '#' signifies that a value node attached to the node in question should have the key name directly following the it.
	 * @param path
	 * @return
	 */
	public List<XmlNode> getNodesByPath( String path ) {
		if( path != null && !path.isEmpty() ) {
			return getNodesOnPath( path );
		}
		throw new IllegalArgumentException( "Path may not be null or empty." );
	}

	
	public List<XmlNode> getNodesOnPath(String path) {
		if( path.startsWith( "/" ) ) {
			String[] parts = path.substring(1).split( "/", 3 );
			if( parts.length == 1 ) {
				List<XmlNode> root = new LinkedList<XmlNode>();
				root.add(rootNode);
				return parseNodeListForMatches(root, parts[0] );
			} else if( parts.length == 2 ) {
				validateRootNodeValidity( parts[1].trim() );
				return parsePathNode( rootNode, parts[1].trim() );
			} else if( parts.length > 2 ) {
				validateRootNodeValidity( parts[1].trim() );
				return parsePathNode( rootNode, parts[1].trim()+"/"+parts[2].trim() );
			} else {
				throw new IllegalStateException( "Path provided did not break down into anything useful. Path provided was: "+ path +" which broke down into pieces of "+ parts.length );
			}
		} else if( path.startsWith( "@" ) ) { // Can be refactored with #, 
			String[] parts = path.substring(1).split( "[@#]" );
			List<XmlNode> root = this.getNodesByAttributeName( parts[0] );
			System.out.println( "Found: "+ root.size() );
			if( parts.length == 2 ) {
				List<XmlNode> subList = this.getNodesByAttributeValue( parts[1] );
				for( XmlNode node : subList ) {
					if( !root.contains( node ) ) {
						root.remove( node );
					}
				}
			}
			return root;
		} else if( path.startsWith( "#" ) ) {
			String[] parts = path.substring(1).split( "[@#]" );
			List<XmlNode> root = this.getNodesByAttributeValue( parts[0] );
			if( parts.length == 2 ) {
				List<XmlNode> subList = this.getNodesByAttributeName( parts[1] );
				for( XmlNode node : subList ) {
					if( !root.contains( node ) ) {
						root.remove( node );
					}
				}
			}
			return root;
		} else {
			String[] parts = path.split( "/", 2 );
			List<XmlNode> relRoot = this.getNodesByName( parts[0].trim() );
			if( parts.length == 1 ) {
				return parseNodeListForMatches(relRoot, path );
			} else if( parts.length == 2 ) {
					return parsePathNode( relRoot.get( 0 ), parts[1].trim() );
			} else {
				throw new IllegalStateException( "Path provided did not break down into anything useful. Path provided was: "+ path +" which broke down into pieces of "+ parts.length );
			}
		}
	}
	
	private void validateRootNodeValidity( String nodeName) {
		List<XmlNode> relRoot = this.getNodesByName( nodeName );
		if( relRoot.size() > 1 ) {
			throw new IllegalArgumentException( "Found multiple root elements asked, please refine path for single point root. Possible root nodes found: "+ relRoot.size() );
		}

	}
	
	private List<XmlNode> parsePathNode( XmlNode node, String path ) {
		if( path != null && !path.isEmpty() ) {
			if( path.contains( "/" ) ) {
				String[] parts = path.split( "/", 2 );
				List<XmlNode> matches = parseNodeListForMatches(node.getChildren(), parts[0].trim() );
				List<XmlNode> matchingChildren = new LinkedList<XmlNode>();
				for( XmlNode child : matches ) {
					matchingChildren.addAll( parsePathNode( child, parts[1].trim() ) );
				}
				return matchingChildren;
			} else {
				return parseNodeListForMatches(node.getChildren(), path.trim() );
			}
		}
		return null;
	}
	
	private List<XmlNode> parseNodeListForMatches( List<XmlNode> nodes, String delimiter ) {
		List<XmlNode> toReturn = new LinkedList<XmlNode>();
		String[] parts = delimiter.trim().split( "[@#]" );
		
		if(delimiter.trim().isEmpty()) {
			toReturn.addAll(nodes);
		} else if( determineIfOnlyAttribute( delimiter ) ) {
			toReturn.addAll( nodes );
		} else {
			toReturn = returnNodesWithMatchingNames(nodes, parts[0].trim() );
		}
		if( delimiter.contains( "@" ) && !toReturn.isEmpty() ) {
			toReturn = removeNodesByDelimitingAttribute( nodes, toReturn, parts[ determineModfierOrder( delimiter, "@", "#" ) ].trim(), "@" );
		} 
		if( delimiter.contains( "#" ) && !toReturn.isEmpty() ) {
			toReturn = removeNodesByDelimitingAttribute( nodes, toReturn, parts[ determineModfierOrder( delimiter, "#", "@" ) ].trim(), "#" );
		} 
		return toReturn;
	}
	
	private boolean determineIfOnlyAttribute( String delimiter ) {
		return ( ( delimiter.contains( "@") && delimiter.indexOf( "@" ) == 0 ) || ( delimiter.contains( "#" ) && delimiter.indexOf( "#" ) == 0 ) );
	}
	
	private List<XmlNode> returnNodesWithMatchingNames( List<XmlNode> nodes, String name ) {
		List<XmlNode> toReturn = new LinkedList<XmlNode>();
		/**/
		for( XmlNode node : nodes ) {
			if( name.contains( ":" ) ) {
				if( name.endsWith(":") &&  node.matchesRequest( name.substring(0, ( name.length() -1 ) ), NODE_FIELDS.PREFIX ) ) {
					toReturn.add( node );
				} else if( name.startsWith(":") && node.matchesRequest( name.substring(1).trim(), NODE_FIELDS.NAME ) ) {
					toReturn.add( node );
				} else if( node.matchesRequest( name.trim(), NODE_FIELDS.FULL_NAME ) ) {
					toReturn.add( node );
				}
			} else {
				if( node.matchesRequest( name.trim(), NODE_FIELDS.NAME ) ) {
					toReturn.add( node );
				}
			}
		}
		/**/
		if( name.contains( ":" ) ) {
			for( XmlNode node : nodes ) {
				if( node.matchesRequest( name.trim(), NODE_FIELDS.FULL_NAME ) ) {
					toReturn.add( node );
				}
			}
		} else {
			for( XmlNode node : nodes ) {
				if( node.matchesRequest( name.trim(), NODE_FIELDS.NAME ) ) {
					toReturn.add( node );
				}
			}
		}
		/**/
		return toReturn;
	}
	
	private List<XmlNode> removeNodesByDelimitingAttribute( List<XmlNode> nodes, List<XmlNode> toReturn, String value, String find ) {
		List<XmlNode> subList = new LinkedList<XmlNode>();
		if( find.equalsIgnoreCase( "@" ) ) {
			subList = findNodeWithMatchingAttributeName(nodes, value.trim() );
		} else {
			subList = findNodeWithMatchingAttributeValue( nodes, value.trim() );
		}
		if( subList.isEmpty() ) {
			toReturn.clear();
		} else {
			for( XmlNode subNode : nodes ) {
				if( !subList.contains( subNode ) ) {
					toReturn.remove( subNode );
				}
			}
		}
		return toReturn;
	}
	
	private int determineModfierOrder( String delimiter, String askedFor, String theOther ) {
		if( delimiter.indexOf(askedFor) != -1 && delimiter.indexOf(theOther) != -1 ) {
			if( delimiter.indexOf(askedFor) > delimiter.indexOf(theOther) ) {
				return 2;
			} 
		}
		return 1;
	}
	
	
	private List<XmlNode> findNodeWithMatchingAttributeName( List<XmlNode> nodes, String attrName ) {
		List<XmlNode> toReturn = new LinkedList<XmlNode>();
		for( XmlNode node : nodes ) {
			if( node.matchesRequest( attrName, NODE_FIELDS.ATTRIBUTE_NAME ) ) {
				toReturn.add( node );
			}
		}
		return toReturn;
	}
	
	private List<XmlNode> findNodeWithMatchingAttributeValue( List<XmlNode> nodes, String attrName ) {
		List<XmlNode> toReturn = new LinkedList<XmlNode>();
		for( XmlNode node : nodes ) {
			if( node.matchesRequest( attrName, NODE_FIELDS.ATTRIBUTE_VALUE ) ) {
				toReturn.add( node );
			}
		}
		return toReturn;
	}
	

	private List<XmlNode> getNodeByField( String sought, NODE_FIELDS field ) {
		List<XmlNode> toReturn = new ArrayList<XmlNode>();
		if( rootNode.matchesRequest(sought, field)) {
			toReturn.add(rootNode);
		}
		if( rootNode.hasChildren() ) {
			toReturn.addAll( getNodesByField( sought, rootNode.getChildren(), field ) );
		}
		return toReturn;
	} 
	
	private List<XmlNode> getNodesByField( String sought, List<XmlNode> nodes, NODE_FIELDS field ) {
		List<XmlNode> toReturn = new ArrayList<XmlNode>();
		for( XmlNode node : nodes ) {
			if( node.matchesRequest( sought, field ) ) {
				toReturn.add(node);
			}
			if( node.hasChildren() ) {
				toReturn.addAll( getNodesByField( sought, node.getChildren(), field ) );
			}
		}
		return toReturn;
	}


}
