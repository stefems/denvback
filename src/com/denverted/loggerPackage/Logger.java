package com.denverted.loggerPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	//=============================================================
	// CLASS FIELDS
	//=============================================================
	private BufferedWriter bwProperty = null;
	private static final String LINESEP =  "\n===================================================================\n";
	private static final String ERRORSEP = "\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n";
	private static final String GOODSEP =  "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
	//=============================================================

	public Logger() {
        File file = new File("/home/stefan/DtFiles/logDir/log.txt");
        try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			bwProperty = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Hah! We failed to open the logger.");
		}
	}
	
	public void beginLogEntry() {
		try {
			bwProperty.append(LINESEP);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			bwProperty.append("New Log for " + dateFormat.format(date));
			bwProperty.append(LINESEP);
			
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to begin new log entry.");
		}
	}
	
	public void endLogEntry() {
		try {
			bwProperty.append(LINESEP);
			bwProperty.append("End of Log.");
			bwProperty.append(LINESEP + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to add the end of a log entry.");
		}
	}
	
	public void addErrorMessage(String errorMessageArg) {
		try {
			bwProperty.append(ERRORSEP);
			bwProperty.append("ERROR:\n");
			bwProperty.append(errorMessageArg);
			bwProperty.append(ERRORSEP + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to add error message to log entry.");
		}
	}
	
	public void addGoodMessage(String goodMessageArg) {
		try {
			bwProperty.append(GOODSEP);
			bwProperty.append("GOOD:\n");
			bwProperty.append(goodMessageArg);
			bwProperty.append(GOODSEP + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to add good message to log entry.");
		}
	}
	
	public void closeLogger() {
		try {
			endLogEntry();
			bwProperty.close();
		} catch (IOException e) {
			System.out.println("Failed to close the logger! Oh no!");
		}
	}
	
	
	
	
}
