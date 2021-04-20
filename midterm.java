public class midterm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		genSnippet gs = new genSnippet();
		
		String fileName = args[1];
		if(args[0].equals("-f")) { 
			if(args[2].equals("-q")) {
				gs.getFile(fileName,args[3]);
			}
				
		}
	}

}
