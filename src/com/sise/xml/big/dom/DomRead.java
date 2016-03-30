package com.sise.xml.big.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 *
 * 类名称：DomRead
 * 类描述：
 * 创建人：潘正军
 * 修改人：Administrator
 * 修改时间：2014-8-15 下午05:00:56
 * 修改备注：
 * @version 1.0.0
 *
 */
public class DomRead {

	/**
	 * main(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param args
	 *void
	 * @throws Exception 
	 * @exception
	 * @since  1.0.0
	 */
	public static void main(String[] args) throws Exception {
		//step1:获得解析器工厂
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		//step2：由解析器工厂获得解析器
		DocumentBuilder db= dbf.newDocumentBuilder();
		//step3:由解析器解析具体的文档，获得文档对象，有多种方式，可参考具体的构造方法
		Document document=db.parse(new File("sample.xml"));
//		Document document=db.parse(args[0]);
		document.normalize();//去除多余文本节点
		//step4：获取文档的根元素
		Element root=document.getDocumentElement();
		//思考为何是5？可查jdk中Node的定义
		System.out.println(root.getChildNodes().getLength());
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			System.out.println(root.getChildNodes().item(i).getNodeName());
		}
		//step5：获取文档类型和实体节点(思考为什么是空值？)
		DocumentType dt=document.getDoctype();
		Node node=dt.getEntities().getNamedItem("begin");	
		if (node!=null) {
			System.out.println("begin的实体内容="+node.getTextContent());
		} else {
			System.out.println("node为空");
		}
		if (document!=null) {
			printNode(root);
//			TransformerFactory tff=TransformerFactory.newInstance();
//			Transformer transformer=tff.newTransformer();
//			DOMSource domSource=new DOMSource(document);
//			StreamResult sResult=new StreamResult(args[0]);
//			transformer.transform(domSource, sResult);
		}
		
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
