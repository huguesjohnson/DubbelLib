/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example;

import java.util.ArrayList;
import java.util.List;

/* 
 * This came up on an actual interview for a job that required zero coding.
 * Whatever.
 * 
 * The request was to write an example of program that mapped a route to a response.
 * It would have one method to add a route, like:
 *   addRoute("/foo/","response")
 * And another to lookup a route, like:
 *   doRoute("/foo/") ==> "response"
 *   
 * I originally wrote it and the unit tests in 5 minutes using a HashMap.
 * 
 * I was then asked to make it support wildcards, like:
 *   addRoute("/fo*","response")
 *   doRoute("/foo/") ==> "response"
 * For that I went with a search tree of characters. It took more than 5 minutes but works. 
 * There's probably a better way to do this but given limited time it's alright.
 * The original HashMap implementation is left commented in for reference (although it's very simple).
 * 
 * This was not the first interview that asked about implementing a tree. 
 * Not sure why implementing a tree proves you can code or do anything else competently.
 * At best it means you finished the first semester of CS undergrad or read half a book on algorithms.
 *   
 * If you are asked about any enhancements/considerations, a few suggestions:
 *   typed exceptions
 *   additional constructor that accepts <String,String> of routes
 *   update route method
 *   delete route
 *   addRoute - if a route already exists is that an exception or should the method return a boolean as a success code
 *   depending on how this is used - synchronization could matter
 *   will we ever care about query strings?
 */
public class WildcardRouterExample{
	/* original implementation
	 * private Map<String,String> routes;
	 */
	private SimpleCharTreeNode rootNode;
	
	/**
	 * Default constructor - initializes an empty list
	 */
	public WildcardRouterExample(){
		/* original implementation
		 * this.routes=new HashMap<String,String>();
		 */
		this.rootNode=new SimpleCharTreeNode('/');
	}
	
	/**
	 * Adds a route to the route table
	 * @param route The relative route to add - i.e. "/foo" or "/f*"
	 * @param response The response (likely a redirect in real code) i.e. "https://whatever"
	 * @throws Exception If the route already exists unless the requirements are to override it I guess
	 */
	public void addRoute(String route,String response) throws Exception{
		/* original implementation
		 * if(routes.containsKey(route)){
		 * throw(new Exception("The route "+route+" already exists."));
		 * }
		 * this.routes.put(route,response);
		 */
		//start at the root node
		SimpleCharTreeNode currentNode=rootNode;
		//go through this character-by-character
		char[] rarray=route.toCharArray();
		for(char c:rarray){
			int branchIndex=currentNode.indexOfBranch(c);
			if(branchIndex<0){
				//add a new branch
				currentNode.branches.add((new SimpleCharTreeNode(c)));
				currentNode=currentNode.branches.get(currentNode.branches.size()-1);
			}else{
				//traverse down the tree
				currentNode=currentNode.branches.get(branchIndex);
			}
		}
		//currentNode should now be pointing at the last node in the chain
		if(currentNode.response!=null){
			throw(new Exception("The route "+route+" already exists with response "+response));
		}
		currentNode.response=response;
	}
	
	/**
	 * Looks up a route
	 * @param route The route to lookup - i.e. "/foo"
	 * @return The corresponding response 
	 * @throws Exception If the route is not found
	 */
	public String doRoute(String route) throws Exception{
		/* original implementation
		 * String response=routes.get(route);
		 * if(response!=null){
		 * return(response);
		 * }
		 * throw(new Exception("The route "+route+" was not found."));
		 */
		//start at the root node
		SimpleCharTreeNode currentNode=rootNode;
		//go through this character-by-character
		char[] rarray=route.toCharArray();
		for(char c:rarray){
			int branchIndex=currentNode.indexOfBranch(c);
			if(branchIndex>=0){
				//traverse down the tree
				currentNode=currentNode.branches.get(branchIndex);
			}else{
				//there are no nodes with the current character, is there a wildcard?
				branchIndex=checkWildcardRoute(currentNode);
				return(currentNode.branches.get(branchIndex).response);
			}
		}
		//check for exact match
		if(currentNode.response!=null){
			return(currentNode.response);
		}
		int branchIndex=checkWildcardRoute(currentNode);
		return(currentNode.branches.get(branchIndex).response);
	}
	
	/**
	 * checks if a node has a wildcard route
	 * @param currentNode The node to check
	 * @return index of the wildcard route, -1 if not 
	 * @throws Exception If the route is not found
	 */
	private int checkWildcardRoute(SimpleCharTreeNode node) throws Exception{
		int branchIndex=node.indexOfBranch('*');
		if(branchIndex<0){
			throw(new Exception("Wildcard match not found."));
		}
		return(branchIndex);
	}
	
	/**
	 * Extremely simple tree node structure.
	 * Java TreeMap would of course work but the request was to implement this.
	 */
	class SimpleCharTreeNode{
		char c;//one letter in the route
		String response;//if this is a terminal node then this is the response value
		List<SimpleCharTreeNode> branches;//branches - this is not a btree so there are many
		
		/**
		 * Create a non-terminal node
		 * @param c the character for this node
		 */
		SimpleCharTreeNode(char c){
			this.c=c;
			this.response=null;
			this.branches=new ArrayList<SimpleCharTreeNode>();
		}

		/**
		 * Test if this node has a branch for a character
		 * @param c the character to look for
		 * @return the index of the branch, -1 if not found
		 */
		int indexOfBranch(char c){
			if(this.branches==null){return(-1);}//this only happens if I mess up somewhere else
			for(int i=0;i<this.branches.size();i++){
				if(this.branches.get(i).c==c){
					return(i);
				}
			}
			return(-1);
		}
	}
	
}