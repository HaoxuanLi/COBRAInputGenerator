import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import VariableClasses.Declaration;

public class MapFileReader{
	String avrFilesOutputDirectoryAbsolutePath = "";
	
	
	public MapFileReader(WCETTargetInfo wcetTargetInfo){
		readWcetTargetInfo(wcetTargetInfo);
	}
	
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
	}

	
	public File getMapFile() throws FileNotFoundException {
		File mapFile = null;
		for (File file : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
			if (file.getName().matches(".*\\.map$"))
				mapFile = file;
		}
		return mapFile;
	}

	public BufferedReader getMapFileReader() throws FileNotFoundException {
		File mapFile = getMapFile();
		BufferedReader mapFileReader = new BufferedReader(new FileReader(mapFile));
		return mapFileReader;
	}
	
	public String getAddress(String name) throws IOException{
		String address = "";
		String mapFileReaderline = "";
		BufferedReader mapFileReader = getMapFileReader();
		
		while ((mapFileReaderline = mapFileReader.readLine()) != null) {
			if (mapFileReaderline.matches("\\s*0x\\w*\\s*" + name+"$")) {
				String address_temp = mapFileReaderline.replaceAll(name + "$", "")
						.replaceAll("\\s", "");
				address = address_temp
						.substring(address_temp.length() - 4, address_temp.length());
				break;
			}
		}
		mapFileReader.close();
		return address;
	}
	
	public int getMemorySpaceSizeOfInput(Declaration input) throws IOException{
		int memorySpaceSizeOfInput = 0;
		String name = input.getDeclarationName();
		String mapFileReaderLine = "";
		BufferedReader mapFileReader = getMapFileReader();
		String address="";
		String addressNextLine = "";
		while ((mapFileReaderLine = mapFileReader.readLine()) != null) {
			if (mapFileReaderLine.matches("\\s*0x\\w*\\s*" + name+"$")) {
				String address_temp = mapFileReaderLine.replaceFirst("[^x]*x", "").replaceAll("\\s.*", "");
						
				address = address_temp
						.substring(address_temp.length() - 4, address_temp.length());
				
				String mapFileReaderLine_NextLine = mapFileReader.readLine();
				
				String addressNextLine_temp = mapFileReaderLine_NextLine.replaceFirst("[^x]*x", "").replaceAll("\\s.*", "");
				
				addressNextLine = addressNextLine_temp
						.substring(addressNextLine_temp.length() - 4, addressNextLine_temp.length());
				
				
				break;
			}
		}
		
		mapFileReader.close();
		memorySpaceSizeOfInput = Integer.parseInt(addressNextLine, 16) - Integer.parseInt(address, 16);
		
		return memorySpaceSizeOfInput;
	}
	
}