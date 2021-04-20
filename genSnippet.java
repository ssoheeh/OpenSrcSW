import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class genSnippet {

	void getFile(String fileName,String sentence) {
		File file = new File(fileName);
		String[] st = new String[5];
		int[] mostCnt = new int[5];
		int cnt = 0;
		try{
			if(checkBeforeFile(file)){
				BufferedReader br = new BufferedReader(new FileReader(file));

				String str = br.readLine();

				while(str != null){
					st[cnt] = str;
					str = br.readLine();
					if(cnt<4)cnt++;
				}
				br.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] arr = sentence.split(" ");
		for (int i = 0; i < st.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if(st[i].contains(arr[j])) {
					mostCnt[i]++;
				}
			}
		}
		int most = 0;
		for (int i = 0; i < arr.length; i++) {
			if(mostCnt[i]>most)
				most = i;
		}
		System.out.println(st[most]);
	}

	static boolean checkBeforeFile(File file){
		if(file.exists()){
			if(file.isFile() && file.canRead()){
				return true;
			}
		}
		return false;

	}
}
