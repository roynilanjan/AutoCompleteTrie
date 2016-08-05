package nr.trie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
*/
public class DictionaryTrie implements Dictionary {

	private TrieNode root;
	private int size;

	public DictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. 
	 */
	public boolean addWord(String word) {
		if (word.length() == 0 || word == null)
			return false;

		String wordLower = word.toLowerCase();
		TrieNode curr = root;

		for (Character c : wordLower.toCharArray()) {

			if (curr.getValidNextCharacters().contains(c)) {

				curr = curr.getChild(c);

				/*
				 * if(curr.endsWord()){ return false; }
				 */

			} else {
				curr = curr.insert(c);

				// curr.setText(c);

			}

		}
		if (curr.endsWord()) {
			return false;
		} else {
			curr.setEndsWord(true);

			curr.setText(word);
			size++;

		}
		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/** Returns whether the string is a word in the trie */


	/**
	 * * Returns up to the n "best" predictions, including the word itself, in
	 * terms of length If this string is not in the trie, it returns null.
	 *
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	//@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method  implements the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the
		// trie, return an empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem
		// to the back of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions

		Queue<TrieNode> q = new LinkedList<TrieNode>();

		List<String> completions = new LinkedList<String>();
		TrieNode element;
		TrieNode curr;

		if (!isWord(prefix)) {

			return completions;
		} else {

			//

			curr = root;

			for (Character c : prefix.toCharArray()) {

				curr = curr.getChild(c);

			}
			/*if (curr.endsWord()) {
				completions.add(curr.getText());
			}*/
			q.add(curr);
			// System.out.println("q.remove() --" + q.remove().getText());
			/*
			 * element = q.remove(); System.out.println("element.getText()---" +
			 * element.getText());
			 */
			while ((!q.isEmpty() && completions.size() < numCompletions )) {

				element = q.remove();
				if(element!=null){
				if (element.endsWord()){
					completions.add(element.getText());
					//System.out.println("added element --->" + element.getText());
				}
				Set<Character> keySet = element.getValidNextCharacters();
				/*System.out.println("element.getValidNextCharacters()-->" +
						element.getValidNextCharacters());
				*/for (Character c : keySet) {
					q.add(element.getChild(c));

				}
				}

			}

		}

		return completions;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;


		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}
	public static void main(String [] args){

		DictionaryTrie smallDict=new DictionaryTrie();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		List<String> res = smallDict.predictCompletions("he", 3);
		System.out.println("res = " + res);

	}

	public boolean isWord(String s) {

		char fchar = 0 ;
		if(s.length()>0)
		 fchar = s.charAt(0);
		if (s.length() == 0 || s == null || (fchar>65 && fchar<90))
			return false;

		// TODO: Implement this method.
		String wordLower = s.toLowerCase();
		TrieNode curr = root;
		// int len = wordLower.length();

		for (Character c : wordLower.toCharArray()) {

			if (curr.getValidNextCharacters().contains(c)) {

				curr = curr.getChild(c);

				/*
				 * if(curr.endsWord()){ return true; }
				 */

			} else {
				return false;
			}

		}
		return true;

	}

}
