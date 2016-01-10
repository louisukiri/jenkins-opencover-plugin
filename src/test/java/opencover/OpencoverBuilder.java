package opencover;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import junit.framework.Assert;


public class OpencoverBuilder {
	@Test
	public void test() {

			String test = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
"<CoverageSession xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
"<Summary numSequencePoints=\"731\" visitedSequencePoints=\"683\" numBranchPoints=\"193\" visitedBranchPoints=\"176\" sequenceCoverage=\"93.43\" branchCoverage=\"91.19\" maxCyclomaticComplexity=\"5\" minCyclomaticComplexity=\"1\" visitedClasses=\"28\" numClasses=\"29\" visitedMethods=\"123\" numMethods=\"133\" />"+
"  <Modules>"+
"    <Module skippedDueTo=\"Filter\" hash=\"E7-54-44-89-91-58-FF-D3-61-E4-82-5C-21-8D-AA-7D-AB-6A-D8-FB\">"+
"      <FullName>C:\\WINDOWS\\assembly\\GAC_64\\mscorlib\\2.0.0.0__b77a5c561934e089\\mscorlib.dll</FullName>"+
"      <ModuleName>mscorlib</ModuleName>"+
"      <Classes />"+
"    </Module></Modules></CoverageSession>";
			
			Pattern p = Pattern.compile("branchCoverage=\"([^\"]+)");
			Matcher m = p.matcher(test);
			String Val = "";
			while(m.find())
			{
				Val = m.group(1);
			}
			assertEquals("91.19", Val);
//			assertTrue(p.matches(".*?branchCoverage=\"([^\"]+?).*?", test));
	}

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
