package com.sise.xml.big.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 *
 * 类名称：DomWrite
 * 类描述：
 * 创建人：潘正军
 * 修改人：Administrator
 * 修改时间：2014-8-15 下午05:00:46
 * 修改备注：
 * @version 1.0.0
 *
 */
public class DomWrite {

	/**
	 * main
	 * @param args
	 *void
	 * @throws Exception 
	 * @exception
	 * @since  1.0.0
	 */
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		//两种获取文档对象的方式都可以，一个传参，一个文件实例化
		Document doc=db.parse(new File("writeSample.xml"));//Document doc=db.parse(args[0]);
		Element root=doc.getDocumentElement();
		Element team_member=doc.createElement("team_member");
		root.appendChild(team_member);
		creatChildElement(doc, "empno", "value", "30772");
		creatChildElement(doc, "name", "value", "Manjeet singh");
		creatChildElement(doc, "designation", "value", "Team_leader");
		creatChildElement(doc, "email", "value", "Manjeet@abc.com");
		doc.normalize();
		if (doc!=null) {
			printNode(root);
			/*利用Transformer将处理的文档信息通过XSLT转换返回给源文档。如果不加该段代码，程序对信息的处理只表现在内存中，无法
			反映在XML文档上。*/
			TransformerFactory tff=TransformerFactory.newInstance();
			Transformer transformer=tff.newTransformer();
			DOMSource domSource=new DOMSource(doc);
			StreamResult sResult=new StreamResult(new File("writeSample.xml"));
			//StreamResult sResult=new StreamResult(args[0]);
			transformer.transform(domSource, sResult);
		}
	}
	/**
	 * creatChildElement
	 * @param doc
	 * @param s1
	 * @param s2
	 * @param s3
	 *void
	 * @exception
	 * @since  1.0.0
	 */
	private static void creatChildElement(Document doc,String s1,String s2,String s3){
		Element element=doc.createElement(s1);
		Attr attr=doc.createAttribute(s2);
		element.setAttribute(attr.getNodeName(), s3);
		doc.getElementsByTagName("team_member").item(0).appendChild(element);
	}
	static void printNode(Element element){
		//获取子节点和属性节点
		NodeList children=element.getChildNodes();
		NamedNodeMap attr=element.getAttributes();
		//获取子节点个数
		//int r=nodeList.getLength();
		//处理属性节点
		String tagName=element.getNodeName();
		if (attr!=null) {
			
			System.out.print("<"+tagName);
			for (int i = 0; i < attr.getLength(); i++) {
				//注意使用转义字符处理属性引号
				System.out.print("  "+attr.item(i).getNodeName()+"=\""+attr.item(i).getNodeValue()+"\"");
				
			}
			System.out.print(">");
		}else if (attr==null) {
			System.out.print("<"+element.getNodeName()+">");
		}
		//递归处理子节点
		if (element.hasChildNodes()) {
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i).getNodeType()==Node.ELEMENT_NODE) {
					printNode((Element)children.item(i));
				} 
				else if(children.item(i).getNodeType()==Node.TEXT_NODE) {
					System.out.print(children.item(i).getNodeValue());
				}
				//思考如果有注释如何输出？
				else if (children.item(i).getNodeType()==Node.COMMENT_NODE) {
					System.out.print("<!--");
					Comment comment=(Comment)children.item(i);
					System.out.print(comment.getData());
					System.out.print("-->");
				}
			}
			System.out.print("<"+tagName+"/>");
		}
		
	}

}
