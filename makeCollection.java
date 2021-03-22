package simpleIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {

	void makeXML(String fileName, Document doc) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File(fileName+".xml")));

		transformer.transform(source, result);
	}

	Document setXML(Document doc, String filePath) throws Exception {
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		String[] intro = new String[5];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < food.length; i++) {
			File file = new File(filePath+"/"+food[i]+".html");
			try{
				if(checkBeforeFile(file)){
					BufferedReader br = new BufferedReader(new FileReader(file));

					String str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();

					while(str != null){
						sb.append(removeTag(str));
						sb.append("\n");
						str = br.readLine();
					}
					br.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			intro[i] = sb.toString();
			sb.delete(0, sb.toString().length());
		}
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		for (int i = 0; i < food.length; i++) {
			Element edoc = doc.createElement("doc");
			docs.appendChild(edoc);
			edoc.setAttribute("id", ""+i+"");
			Element title = doc.createElement("title");
			Element body = doc.createElement("body");
			title.appendChild(doc.createTextNode(food[i]));
			body.appendChild(doc.createTextNode(intro[i]));
			edoc.appendChild(title);
			edoc.appendChild(body);
		}
		return doc;
	}
	
	
	static boolean checkBeforeFile(File file){
		if(file.exists()){
			if(file.isFile() && file.canRead()){
				return true;
			}
		}
		return false;

	}


	static String removeTag(String html) {
		return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}
}
