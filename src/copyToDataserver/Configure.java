package copyToDataserver;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configure {
	private String path;
	private static final String node0 = "Configure";
	private static final String nodeSA = "SourceAddress";
	private static final String nodeDA = "DestinationAddress";
	private static final String nodeLMT = "LastModifyTime";
	/**
	 * 
	 */
	public Configure() {
		super();
		path = "user.xml";
	}

	/**
	 * @param path
	 */
	public Configure(String path) {
		super();
		this.path = path;
	}
	
	public String[] getxmlContent() {
		File xmlFile = new File(this.path);
		if(!xmlFile.exists()) {
			createXML();
		}
		
		String sa = "";
		String da = "";
		String lmt = "";
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.path);
			sa = doc.getElementsByTagName(nodeSA).item(0).getTextContent();
			da = doc.getElementsByTagName(nodeDA).item(0).getTextContent();
			lmt = doc.getElementsByTagName(nodeLMT).item(0).getTextContent();
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String[] {sa,da,lmt};
	}

	public void refreshModifyTime(long newtime) {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.path);
			NodeList con = doc.getElementsByTagName(nodeLMT);
			Node modifytime = con.item(0);
			modifytime.setTextContent(String.valueOf(newtime));
			savexml(doc);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createXML() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element configure = doc.createElement(node0);
			Element source = doc.createElement(nodeSA);
			Element destination = doc.createElement(nodeDA);
			Element lastmodify = doc.createElement(nodeLMT);
			
			doc.appendChild(configure);
			configure.appendChild(source);
			configure.appendChild(destination);
			configure.appendChild(lastmodify);
			
			source.setTextContent("D:\\Work");
			destination.setTextContent("\\\\dataserver03\\Userdata\\fengwei.qi\\Work");
			lastmodify.setTextContent("0");
			
			doc.setXmlVersion("1.0");
			savexml(doc);
			
		}
		catch(Exception e) {
			System.out.println("xml文件创建失败");
			e.printStackTrace();
		}
	}
	
	private void savexml(Document doc) {
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty("encoding", "utf-8");
			
			DOMSource ds = new DOMSource(doc);
			StreamResult sr = new StreamResult(new File(this.path));
			
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(ds, sr);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
