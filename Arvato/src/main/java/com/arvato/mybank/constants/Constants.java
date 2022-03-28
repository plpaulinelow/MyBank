package com.arvato.mybank.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.classes.User;
/**
 * Constants Class
 * @author paulinelow
 *
 */
public class Constants {
	private String userFilePath = "/Users/paulinelow/Desktop";
	private String accountFilePath = "/Users/paulinelow/Desktop";
	private String userExcelFile = "/user.xlsx";
	private String accountExcelFile = "/account.xlsx";
	
	Logger logger = Logger.getLogger(Constants.class.getName());
	public static String ERROR_EXCEL_FAIL_FILE = "EXCEL FAIL on file!";
	public static String ERROR_EXCEL_FAIL_WORKBOOK = "EXCEL FAIL on workbook!";
	
	public static int INT_ZERO_VALUE=0;
	public static int INT_ONE_VALUE=1;
	public static int INT_TWO_VALUE=2;
	
	public static String CONSTANTS_ID="constantsId";
	public static String USERNAME ="username";
	public static String ACCOUNT_ID="accountId";
	public static String DEPOSIT = "deposit";
	public static String DEPOSIT_AMOUNT = "depositAmount";
	public static String BALANCE= "balance";
	public static String CHECK_BALANCE = "checkBalance";
	public static String ACCOUNT ="account";
	public static String WITHDRAWAL = "withdrawal";
	public static String WITHDRAWAL_AMOUNT = "withdrawalAmount";
	public static String TRANSFER = "transfer";
	public static String LOGOUT = "logout";
	public static String PASSWORD = "password";
	public static String TRANSFER_ACCOUNT_ID = "transferAccountId";
	public static String TRANSFER_AMOUNT = "transferAmount";
	
	public static String HOME_PAGE = "homePage";
	public static String HOME_PAGE_JSP = "HomePage.jsp";
	public static String DEPOSIT_PAGE_JSP ="DepositPage.jsp";
	public static String CHECK_BALANCE_PAGE_JSP = "CheckBalancePage.jsp";
	public static String WITHDRAWAL_PAGE_JSP = "WithdrawalPage.jsp";
	public static String TRANSFER_PAGE_JSP="TransferPage.jsp";
	public static String INDEX_PAGE_JSP = "index.jsp";
	
	public static String ERROR = "Error";
	public static String MESSAGE_DEPOSIT_AMOUNT_CANNOT_BE_EMPTY= "Deposit amount cannot be empty..";
	public static String MESSAGE_DEPOSIT_AMOUNT_MUST_BE_GREATER_THAN_ZERO = "Deposit amount must be greater than zero..";
	public static String MESSAGE_INPUT_CANNOT_BE_EMPTY = "Input cannot be empty";
	public static String MESSAGE_USER_DOES_NOT_EXIST = "User does not exist!";
	public static String MESSAGE_TRANSFER_AMOUNT_CANNOT_BE_EMPTY= "Transfer amount cannot be empty..";
	public static String MESSAGE_ACCOUNT_ID_MUST_BE_ENTERED= "Account id must be entered";
	public static String MESSAGE_THIRD_PARTY_ACCOUNT_DOES_NOT_EXISTS= "Third Party Account does not exist..";
	public static String MESSAGE_CURRENT_ACCOUNT_DOES_NOT_EXIST="Current Account does not exist..";
	public static String MESSAGE_UNABLE_TO_TRANSFER_TO_SAME_ACOUNT="Unable to transfer to the same account id..";
	public static String MESSAGE_INSUFFICENT_BALANCE_TO_TRANSFER= "Insufficient balance to proceed with transfer..";
	public static String MESSAGE_TRANSFER_AMOUNT_MUST_BE_GREATER_THAN_ZERO ="Transfer amount must be greater than zero..";
	public static String MESSAGE_WITHDRAWAL_AMOUNT_CANNOT_BE_EMPTY = "Withdrawal amount cannot be empty..";
	public static String MESSAGE_WITHDRAWAL_AMOUNT_MUST_BE_GREATER_THAN_ZERO="Withdrawal amount must be greater than zero..";
	public static String MESSAGE_INSUFFICIENT_BALANCE_FOR_WITHDRAWAL="Insufficient balance to proceed with withdrawal..";
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
			@SuppressWarnings("resource")
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
			// Return first sheet from the XLSX workbook 
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			// Get iterator to all the rows in current sheet 
			Iterator<Row> rowIterator = mySheet.iterator(); 
			// Traversing over each row of XLSX file 
			
			while (rowIterator.hasNext()) {
				User newUser = new User();
				Integer stringExists = INT_ZERO_VALUE;
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
            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_FILE);
			e.printStackTrace();
		} catch (IOException e) {
            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_WORKBOOK);
			e.printStackTrace();
		} 
	}
	
	private void populateAccountList() {
		File myFile = new File(getAccountFilePath()+getAccountExcelFile()); 
		FileInputStream fis;
		try {
			fis = new FileInputStream(myFile);
			@SuppressWarnings("resource")
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
			XSSFSheet mySheet = myWorkBook.getSheetAt(INT_ZERO_VALUE);
			Iterator<Row> rowIterator = mySheet.iterator(); 
			
			while (rowIterator.hasNext()) {
				Account newAcc = new Account();
				Integer numExists = INT_ZERO_VALUE;
				Boolean stringExists = false;

				Row row = rowIterator.next(); 
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
				if(!stringExists && numExists==INT_TWO_VALUE) {
					this.accountList.add(newAcc);
				}
			}
		} catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_FILE);
			e.printStackTrace();
		} catch (IOException e) {
            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_WORKBOOK);
			e.printStackTrace();
		} 
	}
	
}
