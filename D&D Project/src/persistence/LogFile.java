package persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class LogFile {

	public static void writeLog(String error) {
		boolean check = false;
		BufferedWriter writer = null;
		try {
			String path = "";
			File file = new File("resource");
			StringTokenizer stk = new StringTokenizer(file.getAbsolutePath(), File.separator);
			for(int i = 0; i < 4; i++) {
				path += stk.nextToken() + File.separator;
			}
			file = new File(path + "MithGenesis-Log.txt");
			if(!file.exists()) {
				file.createNewFile();
				check = true;
			}
			if(file.exists())
				System.out.println(file.getAbsolutePath());
			writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		Date today = calendar.getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		try {
			if(check)
				writer.write("########## LOGFILE MithGenesis ##########");
			writer.write("\n" + dateFormat.format(today) + "\n#############################################################################################################\n");
			writer.write(error);
			writer.write("\n#############################################################################################################\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
