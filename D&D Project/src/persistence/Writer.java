package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Writer {
	
	public void writeConf(String fileName, String type, String value) throws Exception{
		FileWriter fileWriter = null;
		BufferedReader buffReader = null;
		try {
			fileWriter = new FileWriter("resource/" + fileName + "/tmp.txt");
			buffReader = new BufferedReader(new FileReader("resource/" + fileName + "/config.txt"));
		} catch (IOException e1) {
			LogFile.writeLog(e1.toString());
		}

		String line;
		boolean made = false;
		try {
			while((line = buffReader.readLine()) != null) {
				StringTokenizer stk = new StringTokenizer(line);
				String tit = stk.nextToken(":").trim();
				if(tit.compareTo(type) == 0) {
					fileWriter.append(type + ":" + value + "\n");
					made = true;
				} else {
					String val = stk.nextToken().trim();
					fileWriter.append(tit + ":" + val + "\n");
				}
					
			}
			if(!made)
				fileWriter.append(type + ":" + value + "\n");
			
			fileWriter.close();
			buffReader.close();
			
			BufferedReader buff = new BufferedReader(new FileReader("resource/" + fileName + "/tmp.txt"));
			FileWriter file = new FileWriter("resource/" + fileName + "/config.txt");
			
			while((line = buff.readLine()) != null) {
				file.append(line + "\n");
			}
			buff.close();
			file.close();
			File tmp = new File("resource/" + fileName + "/tmp.txt");
			tmp.delete();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
	}
	
	public void writeText(String fileName, String imageName, String text) throws Exception{
		FileWriter file = null;
		BufferedReader buffReader = null;
		File fileTrue = null;
		File fileTmp = null;
		try {
			fileTrue = new File("resource/" + fileName + "/text.txt");
			fileTmp = new File("resource/" + fileName + "/tmp.txt");
			file = new FileWriter(fileTmp.getAbsolutePath());
			buffReader = new BufferedReader(new FileReader(fileTrue.getAbsolutePath()));
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		String line;
		boolean made = false;
		try {
			while((line = buffReader.readLine()) != null) {
				if(line.contains("###") && line.compareTo("###" + imageName) != 0) {
					file.append(line + "\n");
					while((line = buffReader.readLine()).compareTo("##END##") != 0)
						file.append(line + "\n");
					file.append("##END##\n");
				} else if(line.compareTo("###" + imageName) == 0){
					file.append(line + "\n");
					file.append(text + "\n");
					file.append("##END##");
					made = true;
				}
			}
			if(!made) {
				file.append("###" + imageName + "\n");
				file.append(text + "\n");
				file.append("##END##");
			}
			file.close();
			buffReader.close();
		} catch (IOException e1) {
			LogFile.writeLog(e1.toString());
		}
		try {
			file = new FileWriter(fileTrue.getAbsolutePath());
			buffReader = new BufferedReader(new FileReader(fileTmp.getAbsolutePath()));
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		try {
			file.write("");
			while((line = buffReader.readLine()) != null) {
				file.append(line + "\n");
			}
			file.close();
			buffReader.close();
			fileTmp.delete();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
	}

	public void writeFile(String type, String text) throws Exception{
		try {
			BufferedWriter buff = new BufferedWriter(new FileWriter(new File("resource/application/" + type), true));
			buff.write(text);
			buff.close();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
	}	
}
