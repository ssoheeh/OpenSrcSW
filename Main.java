package Main;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Main {

	public static void main(String[] args) throws Exception {

		String[] intro = new String[5];
		StringBuilder sb = new StringBuilder();
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		for (int i = 0; i < food.length; i++) {
			File file = new File("/Users/PC/OneDrive/바탕 화면/2주차 실습 html/2주차 실습 html/"+food[i]+".html");
			try{
				if(checkBeforeFile(file)){
					//FileReader를 인자로 하는 BufferedReader 객체 생성
					BufferedReader br = new BufferedReader(new FileReader(file));

					//파일을 한 문장씩 읽어온다.
					String str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();
					str = br.readLine();

					//EOF는 null문자를 포함하고 있다.
					while(str != null){
						//읽은 문자열을 출력한다.
						sb.append(removeTag(str));
						sb.append("\n");
						str = br.readLine();
					}
					//FileReader와는 다르게 사용 후 꼭 닫아주어야 한다.
					br.close();
				}else{
					System.out.println("파일에 접근할 수 없습니다.");
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
		Document doc = docBuilder.newDocument();
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


		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("collection.xml")));

		transformer.transform(source, result);
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

