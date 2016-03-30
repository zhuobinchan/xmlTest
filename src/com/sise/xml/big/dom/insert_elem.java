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
import org.w3c.dom.Text;
/**
 * 
* 项目名称：xmlTest   
* 类名称：insert_elem   
* 类描述：   
* 创建人：潘正军  
* 创建时间：2014-10-23 下午04:12:38   
* 修改人：Administrator   
* 修改时间：2014-10-23 下午04:12:38   
* 修改备注：   
* @version 1.0.0   
*
 */
public class insert_elem {
	public static void main(String[] args){
		try{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("traversal.xml");
			doc.normalize();
			Element root=doc.getDocumentElement();
			///////////////////////////////////////
			Element team=doc.createElement("Team");
			Element country=doc.createElement("Country");
			Text country_text=doc.createTextNode("Spanish");
			country.appendChild(country_text);
			
			Element teamname=doc.createElement("Teamname");
			Text team_text=doc.createTextNode("Real Madrid");
			teamname.appendChild(team_text);
			
			Element member=doc.createElement("Member");			
			Attr age=doc.createAttribute("Age");
			Attr sex=doc.createAttribute("Sex");	
			Text member_text=doc.createTextNode("Ronaldo");
			member.setAttribute(age.getNodeName(), "30");
			member.setAttribute(sex.getNodeName(), "Male");	
			member.appendChild(member_text);
			
			team.appendChild(country);
			team.appendChild(teamname);	
			team.appendChild(member);	
			root.insertBefore(team, doc.getElementsByTagName("Team").item(0));
			
			if(doc!=null)
				printNode(root);
			/////////////////////////////////////////////
			TransformerFactory tf=TransformerFactory.newInstance();
			Transformer tansformer=tf.newTransformer();
			DOMSource source=new DOMSource(doc);
			StreamResult result=new StreamResult(new File("traversal.xml"));
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
