package com.sise.xml.big.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Node {
	public int value;
	public Node left;
	public Node right;
	public int i;

	public Node(int i) {
		this.i = i;
	}

	public void store(int value) {
		if (value < this.value) {
			if (this.left == null) {
				this.left = new Node(i + 1);
				this.left.value = value;
			} else {
				this.left.store(value);
			}
		} else if (value > this.value) {
			if (this.right == null) {
				this.right = new Node(i + 1);
				this.right.value = value;
			} else {
				this.right.store(value);
			}
		}
	}

	


	public void middleList() {
		if (this.left != null) {
			left.middleList();
		}
		System.out.println(this.value + ",i=" + this.i);
		if (this.right != null) {
			right.middleList();
		}
	}



	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		int[] data = new int[20];
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) (Math.random() * 20) + 1;
			System.out.println(data[i] + ",");
		}

		System.out.println("=================");

		Node root = new Node(1);
		root.value = data[0];
		for (int i = 1; i < data.length; i++) {
			root.store(data[i]);
		}

		
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		//两种获取文档对象的方式都可以，一个传参，一个文件实例化
		Document doc=db.parse(new File("writeSample.xml"));//Document doc=db.parse(args[0]);
		Element droot=doc.getDocumentElement();
		Element team_member=doc.createElement("team_member");
		
		root.middleList();
	
	}
}
