package simpleIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {
	void getSimilarity(String filePath, String query) throws Exception {
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		File file = new File(filePath+"\\index.post");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true);
		for (int j = 0; j < kl.size(); j++) {
			Keyword kwrd = kl.get(j);
			map.put(kwrd.getString(), 1);
		}
		HashMap hashMap = readPost(filePath);
		Iterator<String> it = hashMap.keySet().iterator();
		double[] id = new double[5];
		while(it.hasNext()) {
			String key = it.next();
			ArrayList<String> value = (ArrayList<String>)hashMap.get(key);
			int[] wq = new int[5];
			double[] q = new double[5];
			if(map.containsKey(key)) {
				for (int i = 0; i < 5; i++) {
					if(value.contains(Integer.toString(i)))
					{
						wq[i] = map.get(key);
						int idx = value.indexOf(Integer.toString(i));
						q[i] = Double.parseDouble(value.get(idx+1));
						id[i] += wq[i]*q[i];;
					}
						
				}
			}
			
		}
		for (int i = 0; i < 5; i++) {
			id[i] = Math.round(id[i]*100)/100.0;
			System.out.println(id[i]);
		}
	}
	

	HashMap readPost(String fileName) throws Exception {
		FileInputStream fileStream = new FileInputStream(fileName+"\\index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

		Object object = objectInputStream.readObject();
		objectInputStream.close();

		HashMap hashMap = (HashMap)object;
		return hashMap;


	}
}
