package com.sise.xml.big.dom;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
* 项目名称：xmlTest   
* 类名称：remove_elem   
* 类描述：   
* 创建人：潘正军  
* 创建时间：2014-10-23 下午04:13:00   
* 修改人：Administrator   
* 修改时间：2014-10-23 下午04:13:00   
* 修改备注：   
* @version 1.0.0   
*
 */
public class remove_elem {
	public static void main(String[] args){
		try{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(args[0]);
			doc.normalize();
			Element root=doc.getDocumentElement();
			///////////////////////////////////////
			Element member=(Element)root.getElementsByTagName("Member").item(0);
			Node text=member.getFirstChild();
			member.removeChild(text);
			
			if(doc!=null)
				printNode(root);
			/////////////////////////////////////////////
			TransformerFactory tf=TransformerFactory.newInstance();
			Transformer tansformer=tf.newTransformer();
			DOMSource source=new DOMSource(doc);
			StreamResult result=new StreamResult(new File(args[0]));
			tansformer.transform(source,result);
		}catch(Exception e){
			e.printStackTrace();
		}
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
