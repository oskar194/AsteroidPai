package GameCore;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigParser {
	private int port;
	private String hostname;
	String path;
	
	public ConfigParser(String path) throws ParserConfigurationException, SAXException, IOException{
		this.path = path;
		File xmlFile = new File(path);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("config");
		
		Node nNode = nList.item(0);
		if(nNode.getNodeType() == Node.ELEMENT_NODE){
			Element eElement = (Element) nNode;
			this.port = Integer.parseInt(eElement.getElementsByTagName("port").item(0).getTextContent());
			this.hostname = eElement.getElementsByTagName("hostname").item(0).getTextContent();
		}
	}
	
	public String getHostname(){
		return this.hostname;
	}
	
	public int getPort(){
		return this.port;
	}
	

}
