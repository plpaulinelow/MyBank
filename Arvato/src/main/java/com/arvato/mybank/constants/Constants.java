package com.arvato.mybank.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.classes.User;

public class Constants {
	private String userFilePath = "/Users/paulinelow/Desktop";
	private String accountFilePath = "/Users/paulinelow/Desktop";
	private String userExcelFile = "/user.xlsx";
	private String accountExcelFile = "/account.xlsx";
	private List<User> userList = new ArrayList<>();
	private List<Account> accountList = new ArrayList<>();
	
	public String getUserExcelFile() {
		return userExcelFile;
	}
	public void setUserExcelFile(String userExcelFile) {
		this.userExcelFile = userExcelFile;
	}
	public String getAccountExcelFile() {
		return accountExcelFile;
	}
	public void setAccountExcelFile(String accountExcelFile) {
		this.accountExcelFile = accountExcelFile;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<Account> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public String getUserFilePath() {
		return userFilePath;
	}
	public void setUserFilePath(String userListFile) {
		this.userFilePath = userListFile;
	}
	public String getAccountFilePath() {
		return accountFilePath;
	}
	public void setAccountFilePath(String accountFilePath) {
		this.accountFilePath = accountFilePath;
	}
	public Constants() {
		super();
		init();
	}
	public void init() {
		//populate user and account list
		populateUserList();
		populateAccountList();
		
	}
	
	private void populateUserList() {
		File myFile = new File(getUserFilePath()+ getUserExcelFile()); 
		FileInputStream fis;
		try {
			fis = new FileInputStream(myFile);
			// Finds the workbook instance for XLSX file
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
			// Return first sheet from the XLSX workbook 
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			// Get iterator to all the rows in current sheet 
			Iterator<Row> rowIterator = mySheet.iterator(); 
			// Traversing over each row of XLSX file 
			
			while (rowIterator.hasNext()) {
				User newUser = new User();
				Integer stringExists = 0;
				boolean accountIdExists = false;

				Row row = rowIterator.next(); 
				// For each row, iterate through each columns 
				Iterator<Cell> cellIterator = row.cellIterator(); 

				while (cellIterator.hasNext()) { 
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) { 
						case STRING: 
						stringExists ++;
						if(stringExists==1) {
							newUser.setUsername(cell.getStringCellValue());
						}else if(stringExists==2) {
							newUser.setPassword(cell.getStringCellValue());
						}
						break; 
					case NUMERIC: 
						accountIdExists=true;
						newUser.setAccountId((int)cell.getNumericCellValue());
						break; 
					case BOOLEAN: 
						break; 
						default: 
								
					}
					
				} 
				if(stringExists==2 && accountIdExists) {
					this.userList.add(newUser);
				}
				System.out.println(""); 
			}
		} catch (FileNotFoundException e) {
			System.out.println("EXCEL FAIL on file!"); 
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("EXCEL FAIL on workbook!"); 
			e.printStackTrace();
		} 
	}
	
	private void populateAccountList() {
		File myFile = new File(getAccountFilePath()+getAccountExcelFile()); 
		FileInputStream fis;
		try {
			fis = new FileInputStream(myFile);
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator<Row> rowIterator = mySheet.iterator(); 
			
			while (rowIterator.hasNext()) {
				Account newAcc = new Account();
				Integer numExists = 0;
				Boolean stringExists = false;

				Row row = rowIterator.next(); 
				// For each row, iterate through each columns 
				Iterator<Cell> cellIterator = row.cellIterator(); 

				while (cellIterator.hasNext()) { 
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) { 
						case STRING: 
						stringExists = true;
						break; 
					case NUMERIC: 
						numExists++;
						if(numExists==1) {
							newAcc.setAccountId((int)cell.getNumericCellValue());
						}else if(numExists==2) {
							newAcc.setBalance(cell.getNumericCellValue());
						}
						break; 
					case BOOLEAN: 
						break; 
						default: 
								
					}
					
				} 
				if(!stringExists && numExists==2) {
					this.accountList.add(newAcc);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("EXCEL FAIL on file!"); 
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("EXCEL FAIL on workbook!"); 
			e.printStackTrace();
		} 
	}
	
}
