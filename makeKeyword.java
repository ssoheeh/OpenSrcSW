
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class makeKeyword {

	Document kkma(String fileName) throws Exception{
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		File file = new File(fileName);
		String[] index = new String[5];
		//String으로 변환
		String str = Files.readString(file.toPath());
		StringBuffer sb = new StringBuffer();
		//아래 5줄은 xml파일을 Document로 생성하는 과정
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(str));
		Document doc = db.parse(is);
		for (int i = 0; i < 5; i++) {
			String st = doc.getElementsByTagName("body").item(i).getChildNodes().item(0).getNodeValue();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(st, true);
			for (int j = 0; j < kl.size(); j++) {
				Keyword kwrd = kl.get(j);
				if (i != kl.size() - 1){
					sb.append(kwrd.getString()+":"+kwrd.getCnt()+"#");

				}else{
					sb.append(kwrd.getString()+":"+kwrd.getCnt());
				}
			}
			index[i] = sb.toString();
			sb.delete(0, sb.toString().length());
		}
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		for (int i = 0; i < index.length; i++) {
			Element edoc = doc.createElement("doc");
			docs.appendChild(edoc);
			edoc.setAttribute("id", ""+i+"");
			Element title = doc.createElement("title");
			Element body = doc.createElement("body");
			title.appendChild(doc.createTextNode(food[i]));
			body.appendChild(doc.createTextNode(index[i]));
			edoc.appendChild(title);
			edoc.appendChild(body);
		}

		return doc;

	}



}
