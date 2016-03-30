package com.sise.xml.big.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Node2 {
	public static void main(String[] args)throws Exception{
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		//两种获取文档对象的方式都可以，一个传参，一个文件实例化
		Document doc=db.parse(new File("Node.xml"));
		Element root=doc.getDocumentElement();
		Element Node=doc.createElement("Node");
		root.appendChild(Node);
		Node.setAttribute("id", "5");
		creatChildElement(doc, "LNode", "id", "1");
		creatChildElement(doc, "RNode", "id", "3");
		doc.normalize();
		if (doc!=null) {
			printNode(root);
			/*利用Transformer将处理的文档信息通过XSLT转换返回给源文档。如果不加该段代码，程序对信息的处理只表现在内存中，无法
			反映在XML文档上。*/
			TransformerFactory tff=TransformerFactory.newInstance();
			Transformer transformer=tff.newTransformer();
			DOMSource domSource=new DOMSource(doc);
			StreamResult sResult=new StreamResult(new File("Node.xml"));
			//StreamResult sResult=new StreamResult(args[0]);
			transformer.transform(domSource, sResult);
		}
		
		
		
		
	}
	
	
	
	
	private static void creatChildElement(Document doc,String s1,String s2,String s3){
		Element element=doc.createElement(s1);
		Attr attr=doc.createAttribute(s2);
		element.setAttribute(attr.getNodeName(), s3);
		doc.getElementsByTagName("Node").item(0).appendChild(element);
	}
	private static void printNode(Element element){
		NodeList children=element.getChildNodes();
		NamedNodeMap attr=element.getAttributes();
		int r=children.getLength();
		if(attr!=null){
			System.out.print("<"+element.getNodeName());
			for(int j=0;j<attr.getLength();j++){
				System.out.print(" "+attr.item(j).getNodeName()+"="+attr.item(j).getNodeValue()+" ");
			}
			System.out.println(">");
		}
		else if(attr==null){
			System.out.println("<"+element.getNodeName()+">");
		}
		if(element.hasChildNodes()){
			for(int k=0;k<r;k++){
				if(children.item(k).getNodeType()==Node.ELEMENT_NODE)
					printNode((Element)children.item(k));
				else if(children.item(k).getNodeType()==Node.TEXT_NODE){
					System.out.println(children.item(k).getNodeValue());
				}
			}
			System.out.println("</"+element.getNodeName()+">");
		}
	}

}
