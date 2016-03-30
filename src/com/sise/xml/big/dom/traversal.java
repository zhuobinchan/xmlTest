package com.sise.xml.big.dom;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
* 项目名称：xmlTest   
* 类名称：traversal   
* 类描述：   
* 创建人：潘正军  
* 创建时间：2014-10-23 下午04:13:19   
* 修改人：Administrator   
* 修改时间：2014-10-23 下午04:13:19   
* 修改备注：   
* @version 1.0.0   
*
 */
public class traversal {
	public static void main(String[] args){
		try{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(args[0]);
			doc.normalize();
			Element root=doc.getDocumentElement();
			if(doc!=null)
				printNode(root);
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
