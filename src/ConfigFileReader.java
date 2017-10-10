import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigFileReader{
	
	private Properties config;
	private String configFileName = "config.properties";
	
	ConfigFileReader() throws IOException{
		this.config = this.getConfigProperties();
	}
	
	public Properties getConfigProperties() throws IOException {
		Properties properities = new Properties();
		InputStream configFileStream = getClass().getClassLoader().getResourceAsStream(this.configFileName);
		
		if (configFileStream != null) {
			try {
				properities.load(configFileStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				configFileStream.close();
			}
		} else {
			throw new FileNotFoundException("property file '" + this.configFileName + "' not found in the classpath");

		}
		return (properities);
	}	
	
	
	public String getProgramName(){
		return(config.getProperty("programName"));
	}
	
	public String getTicStamp(){
		return(config.getProperty("ticStamp"));
	}
	
	public String getTocStamp(){
		return(config.getProperty("tocStamp"));
	}
	
	public String getToolName(){
		return(config.getProperty("toolName"));
	}
	
	public String getInterfaceName(){
		return(config.getProperty("interfaceName"));
	}
	
	public String getDeviceName(){
		return(config.getProperty("deviceName"));
	}
	
	public String getF_CPU(){
		return(config.getProperty("F_CPU"));
	}
	
	public String getMainContent(){
		return(config.getProperty("mainContent"));
	}
	
	public String getSerialPortNumber(){
		return(config.getProperty("serialPortNumber"));
	}

	public String getAtprogramPath(){
		return(config.getProperty("atprogramPath"));
	}
	
	public String getAvrPath(){
		return(config.getProperty("avrPath"));
	}
	
	public String getAvrGccPath(){
		return(config.getProperty("avrGccPath"));
	}
	
	public String getAvrincludePath(){
		return(config.getProperty("avrincludePath"));
	}
	
	public String getAvrsizePath(){
		return(config.getProperty("avrsizePath"));
	}
	
	public String getAvrobjdumpPath(){
		return(config.getProperty("avrobjdumpPath"));
	}
	
	public String getOriginalFileDirectoryAbsolutePath(){
		return(config.getProperty("originalFileDirectoryAbsolutePath"));
	}
	
	public String getWorkDirectoryRootAbsolutePath(){
		return(config.getProperty("workDirectoryRootAbsolutePath"));
	}
	
	public String getBlockFileDirectoryAbsolutePath(){
		return(config.getProperty("blockFileDirectoryAbsolutePath"));
	}
	
	public String getOriginalFileAbsolutePath(){
		return(config.getProperty("originalFileAbsolutePath"));
	}
	
	public String getAnnotationFileAbsolutePath(){
		return(config.getProperty("annotationFileAbsolutePath"));
	}
	
	public int getProcessorRegisterLength(){
		return(Integer.parseInt(config.getProperty("processorRegisterLength")));
	}
	
	
	
}