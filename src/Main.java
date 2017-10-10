import java.io.IOException;

import jssc.SerialPortException;

class Collect extends Thread {
	private WCETTargetInfo wcetTargetInfo;


	Collect(WCETTargetInfo wcetTargetInfo) throws IOException {
		this.wcetTargetInfo = wcetTargetInfo;
	}

	public void run() {
		try {
			new ResultsCollector(this.wcetTargetInfo).collectResults();
		} catch (IOException | SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class Main {
	
	public static void main(String[] args) throws Exception {

		
		WCETTargetInfo wcetTargetInfo = new WCETTargetInfo();
		wcetTargetInfo.setBlockFileName("iir_main_node_r140_r153");
		wcetTargetInfo.setGeneratedInputLength(20);
		wcetTargetInfo.setLogGeneratedInputsEnabledFlag(false);
	


		wcetTargetInfo.setStepNumber("5"); 
		new WCET(wcetTargetInfo);

	/*	wcetTargetInfo.setStepNumber("2"); 
		new WCET(wcetTargetInfo);

		wcetTargetInfo.setStepNumber("3"); 
		new WCET(wcetTargetInfo);

		wcetTargetInfo.setStepNumber("4"); 
		new WCET(wcetTargetInfo);

		wcetTargetInfo.setAppendMeasurementResultsEnabledFlag(false); 
		Collect c1 = new Collect(wcetTargetInfo); c1.start();
		
	
		wcetTargetInfo.setStepNumber("5"); 
		new WCET(wcetTargetInfo);
		c1.join();
		*/
	
		return;

	}
}
