package persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class Reader {
	
	/*
	 * Restituisce il tipo di rpg in uso dal file resource/fileName/conf.txt
	 * 
	 * Parametro: fileName -> nome della cartella relativa al salvataggio scelto
	 */
	public String readConf(String fileName, String type) throws Exception{
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader("resource/" + fileName + "/config.txt"));
		} catch (FileNotFoundException e) {
			LogFile.writeLog(e.toString());
		}
		String line;
		try {
			while((line = buffReader.readLine()) != null) {
				StringTokenizer stk = new StringTokenizer(line);
				String title = stk.nextToken(":").trim();
				if(title.compareTo(type) == 0) {
					buffReader.close();
					return stk.nextToken().trim();
				}
			}
			buffReader.close();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		return null;
	}
	
	public String readText(String fileName, String imageName) throws Exception{
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader("resource/" + fileName + "/text.txt"));
		} catch (FileNotFoundException e1) {
			LogFile.writeLog(e1.toString());
		}
		String line, result = "";
		boolean found = false;
		try {
			while((line = buff.readLine()) != null) {
				if(line.compareTo("###" + imageName) == 0) {
					found = true;
				} else {
					if(found) {
						if(line.compareTo("##END##") == 0)
							found = false;
						else 
							result += line + "\n";
					}
				}
			}
			buff.close();
			return result;
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		return null;
	}
	
	public Collection<String> readFileList(String fileName) throws Exception{
		Collection<String> result = new ArrayList<String>();
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			LogFile.writeLog(e.toString());
			return null;
		}
		String line;
		try {
			while((line = buff.readLine()) != null) {
				if(line.contains("###")) {
					result.add((String)line.subSequence(3, line.length()));
				}
			}
			buff.close();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		return result;
	}

	public String readFile(String fileName, String search) throws Exception{
		String result = "";
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			LogFile.writeLog(e.toString());
		}
		String line;
		try {
			while((line = buff.readLine()) != null) {
				if(line.contains("###" + search)) {
					while((line = buff.readLine()).compareTo("##END##") != 0) {
						result += line + "\n";
					}
					break;
				}
			}
			buff.close();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		return result;
	}
}
