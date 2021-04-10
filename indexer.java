import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class indexer {
	HashMap<String, ArrayList<String>> makeInvertedFile(String filePath) throws Exception {
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		ArrayList<String> word = new ArrayList<String>();
		File file = new File(filePath);
		String[] index = new String[5];
		HashMap<String, int[]> documentMap = new HashMap<>();
		HashMap<String, double[]> documentMap2 = new HashMap<>();
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		//String으로 변환
		String str = Files.readString(file.toPath());
		StringBuffer sb = new StringBuffer(); 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(str));
		Document doc = db.parse(is);
		for (int i = 0; i < 5; i++) {
			index[i] = doc.getElementsByTagName("body").item(i).getChildNodes().item(0).getNodeValue();
		}
		for(int i = 0; i < 5; i++) {
			String[] tokens = index[i].split("#");
			String[] arr = null;
			for (int j = 0; j < tokens.length; j++) {
				if(tokens[j].length()>=1) {
					arr = tokens[j].split(":"); 
					word.add(arr[0]);
					int cnt = Integer.parseInt(arr[1]);
					if(!documentMap.containsKey(arr[0])) {
						int[] list = new int[5];
						list[i] = cnt;
						documentMap.put(arr[0], list);
					}else {
						int[] list = documentMap.get(arr[0]);
						list[i] = cnt;
						documentMap.put(arr[0], list);
					}
				}
			}	

		}
		
		
		for (int i = 0; i < word.size(); i++) {
			int[] tf = documentMap.get(word.get(i));
			double[] tf_idf = new double[5];
			int dx = 0;
			for (int j = 0; j < tf.length; j++) {
				if(tf[j]!=0)
					dx++;
			}
			for (int j = 0; j < tf.length; j++) {
				Double w = tf[j] * Math.log((double)5/dx);
				tf_idf[j] = Math.round(w*100)/100.0;
			}
			documentMap2.put(word.get(i), tf_idf);
		}
		
		for (int i = 0; i < word.size(); i++) {
			ArrayList<String> list = new ArrayList<String>();
			double[] ans = documentMap2.get(word.get(i));
			for (int j = 0; j < ans.length; j++) {
				if(ans[j]!=0) {
					list.add(Integer.toString(j));
					list.add(Double.toString(ans[j]));
				}
			}
			map.put(word.get(i), list);
		}
		return map;

	}

	void makePost(String fileName, HashMap<String, ArrayList<String>> map) throws Exception {
		FileOutputStream fileStream = new FileOutputStream(fileName+".post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		objectOutputStream.writeObject(map);
		objectOutputStream.close();
		
	}
	
	void readPost(String fileName) throws Exception {
		FileInputStream fileStream = new FileInputStream(fileName+".post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap = (HashMap)object;
		Iterator<String> it = hashMap.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			ArrayList<String> value = (ArrayList<String>)hashMap.get(key);
			System.out.println(key+" -> "+value);
		}
		
	}


}
