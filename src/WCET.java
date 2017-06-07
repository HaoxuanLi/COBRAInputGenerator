import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

class Collect extends Thread{

	private String directory;
	private Boolean appendflag;
	
	public Collect(String directory, Boolean appendflag) throws IOException{
		this.directory = directory;		
		this.appendflag = appendflag;
	}
	
	public void run(){
		WCETAnalysis collect;
			try {
				collect = new WCETAnalysis(this.directory);
				collect.CollectResults(100, 4, this.appendflag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		      
	}
}
	
class WCET{
	
	public static void main(String[] args) throws Exception {
		
		
		Properties config = new WCETAnalysis().getConfig("config.properties");
		String pathtargetprogram = config.getProperty("pathtargetprogram");
		String directory = pathtargetprogram + "\\Inputs";
	
		if(!new File(directory).exists())
			new File(directory).mkdir();
		
		String blockfilepath = config.getProperty("pathtargetprogram") + "\\HPA_source";
		String targetfile =  config.getProperty("targetfile");
		String annonationfile = config.getProperty("annonationfile");
		

/*		long inputlength = 100;
		
		for (File file : new File(blockfilepath).listFiles()) {
			String blockfile = file.getAbsolutePath(); 
			Boolean logflag = true;
			new InputsGenerator(config.getProperty("pathtargetprogram"), blockfile, targetfile, annonationfile, inputlength, logflag);
	}

*/	System.out.println("Inputs generation done!");

	
		
/*	File rootdirectory = new File(pathtargetprogram+"\\Inputs");
	
	for(File blockdirectory: rootdirectory.listFiles()){
		File file = blockdirectory;
		Boolean appendflag = false;
		
	//	if(!blockdirectory.getName().contains("ReturnBlock")){
		Collect c1=new Collect(file.getAbsolutePath(), appendflag);
		c1.start();
		new ProcessorTemplateGenerator(file.getAbsolutePath());
		new MakefileGenerator(file.getAbsolutePath());	
		WCETAnalysis WCETanalysis = new WCETAnalysis(file.getAbsolutePath());
		WCETanalysis.cleanprogramfile(); 
		WCETanalysis.makeprogramfile();
		WCETanalysis.eraseprogram();
		WCETanalysis.deployprogram();
		c1.join();
		
	//	}
	}
	
	System.out.println("Results collection done!");
	*/	
		
	/*	File rootdirectory = new File(pathtargetprogram+"\\Inputs");
		
		for(File blockdirectory: rootdirectory.listFiles()){
			File file = blockdirectory;
			
			WCETAnalysis WCETanalysis = new WCETAnalysis(file.getAbsolutePath());
			WCETanalysis.clearData();
			
		
		}	
		*/

long inputlength = 100;


String blockfile = config.getProperty("pathtargetprogram") + "\\HPA_source" + "\\countnegative_main_block_r131.c"; 
Boolean logflag = true;
new InputsGenerator(config.getProperty("pathtargetprogram"), blockfile, targetfile, annonationfile, inputlength, logflag);

	
/*			File file = new File("C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\TEST\\countnegative\\Inputs\\blockR131");
			WCETAnalysis a1 = new WCETAnalysis(file.getAbsolutePath());
			//a1.AnalyzeResults();
			new ProcessorTemplateGenerator(file.getAbsolutePath());
			new MakefileGenerator(file.getAbsolutePath());	
			Collect c1=new Collect(file.getAbsolutePath(), true);
			c1.start();
			a1.cleanprogramfile(); 
			a1.makeprogramfile();
			a1.eraseprogram();
			a1.deployprogram();
*/			return;	
 
		
/*		for (File file : new File(directory).listFiles()) {
			if(file.isDirectory()){
				System.out.print(file.getName() + "\n");
				WCETAnalysis a = new WCETAnalysis(file.getAbsolutePath());
				new ProcessorTemplateGenerator(file.getAbsolutePath());
				nsew MakefileGenerator(file.getAbsolutePath());
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
