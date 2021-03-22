package simpleIR;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class kuir {
	public static void main(String[] args) throws Exception {
		String filePath = args[1];

		makeCollection mc = new makeCollection();
		makeKeyword mk = new makeKeyword();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		if(args[0].equals("-c")) {
			doc = mc.setXML(doc, filePath);
			mc.makeXML("collection", doc);
		}
		else if(args[0].equals("-k")) {
			doc = mk.kkma(filePath);
			mc.makeXML("index", doc);
		}


	}
}
