package wargs.plugins.opencover;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class OpencoverFile {
	private double branchCoverage = -1;
	public OpencoverFile(String xmlPathString) throws ParserConfigurationException, SAXException, IOException
	{
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xmlPathString));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("Summary");
	        Element element = (Element)nodes.item(0);
	        
	        branchCoverage = Double.parseDouble(element.getAttribute("branchCoverage"));
	}
	public double getBranchCoverage()
	{
		return branchCoverage;
	}
}
