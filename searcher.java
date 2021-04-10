import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {
    void CalcSim(String filePath, String query) throws Exception {
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
        double v1,v2;
        double[] id = new double[5];
        while(it.hasNext()) {
            v1 = 0; v2 = 0;
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
                        v1 += Math.pow(wq[i],2);
                        v2 += Math.pow(q[i],2);
                    }

                }
                
            }

        }
        for (int i = 0; i < 5; i++) {
            id[i] = Math.round(id[i]*100)/100.0;
            System.out.println(id[i]);
        }
        double[] title = new double[5];
        for (int i = 0; i < title.length; i++) {
            title[i] = id[i];
        }
        Arrays.sort(title);
        String[] top3 = new String[3];
        int a = 0;
        for (int i = 4; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if(a==3)
                    break;
                if(title[i]==id[j]) {
                    if(!food[j].equals("")) {
                        top3[a] = food[j];
                        food[j] = "";
                        if(a<=2) {
                            a++;}
                    }
                }

            }
            if(a==3)
                break;
        }
        for (int i = 0; i < top3.length; i++) {
            System.out.println(top3[i]);
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
