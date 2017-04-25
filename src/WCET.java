import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

class WCET {
	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException{
	 * 
	 * WCETSettings_GUI f =new WCETSettings_GUI();
	 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(600,900);
	 * f.setVisible(true);
	 * 
	 * }
	 */
	public static void main(String[] args) throws Exception {	
		long inputlength = 100;
		
		Properties config = new WCETAnalysis().getConfig("config.properties");
		String pathtargetprogram = config.getProperty("pathtargetprogram");
		String directory = pathtargetprogram + "\\Inputs";
	
		if(!new File(directory).exists())
			new File(directory).mkdir();
		
	
		String blockfilepath = config.getProperty("pathtargetprogram") + "\\HPA_source";
		String targetfile =  config.getProperty("targetfile");
		String annonationfile = config.getProperty("annonationfile");
/*		for (File file : new File(blockfilepath).listFiles()) {
		//	String blockfile = new File("C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\TEST\\quicksort\\HPA_source\\quicksort_vec_block_r205-206.c").getAbsolutePath();
			String blockfile = file.getAbsolutePath(); 
			new InputsGenerator(config.getProperty("pathtargetprogram"), blockfile, targetfile, annonationfile, inputlength);
			
		}

	*/

		
//	String blockfile = new File("C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\TEST\\bitonic\\HPA_source\\bitonic_return_block_r61-63.c").getAbsolutePath();
//	new InputsGenerator(config.getProperty("pathtargetprogram"), blockfile, targetfile, annonationfile, 1);
	
			File file = new File("C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\TEST\\bitonic\\Inputs\\blockR99");
			WCETAnalysis a = new WCETAnalysis(file.getAbsolutePath());
			new ProcessorTemplateGenerator(file.getAbsolutePath());
			new MakefileGenerator(file.getAbsolutePath());
			
						
	
				a.cleanprogramfile(); 
				a.makeprogramfile();
				a.eraseprogram();
				a.deployprogram();
			return;	
		
/*		for (File file : new File(directory).listFiles()) {
			if(file.isDirectory()){
				System.out.print(file.getName() + "\n");
				WCETAnalysis a = new WCETAnalysis(file.getAbsolutePath());
				new ProcessorTemplateGenerator(file.getAbsolutePath());
				new MakefileGenerator(file.getAbsolutePath());
				Thread collectresults = new Thread(){
					public void run() {
					try {
						a.CollectResults();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				};
				
				Thread deployprogram = new Thread(){
					public void run() {
					try {
						a.cleanprogramfile();
						a.makeprogramfile();
						a.eraseprogram();
						a.deployprogram();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				};
		
				collectresults.start();
				deployprogram.start();
				
				collectresults.join();
				deployprogram.join();
			}
		}
		
		
		*/
		
		//WCETAnalysis a = new WCETAnalysis();
		
		//a.AnalyzeResults();
			
	}

}
