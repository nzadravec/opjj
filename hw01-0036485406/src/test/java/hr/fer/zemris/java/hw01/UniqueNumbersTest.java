package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {
	
	public TreeNode createTreeNode() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		
		return glava;
	}
	
	@Test
	public void addNodeTest() {
		TreeNode glava = createTreeNode();
		
		Assert.assertEquals(42, glava.value);
		Assert.assertEquals(21, glava.left.value);
		Assert.assertEquals(35, glava.left.right.value);
		Assert.assertEquals(76, glava.right.value);
	}
	
	@Test
	public void containsValueTest() {
		TreeNode glava = createTreeNode();
		
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 76));
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 21));
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 11));
	}
	
	@Test
	public void treeSizeTest() {
		TreeNode glava = null;
		Assert.assertEquals(0, UniqueNumbers.treeSize(glava));
		
		glava = createTreeNode();
		
		Assert.assertEquals(4, UniqueNumbers.treeSize(glava));
	}

}
