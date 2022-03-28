package com.arvato.mybank.impl.services;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.arvato.mybank.constants.Constants.INT_ZERO_VALUE;
import static com.arvato.mybank.constants.Constants.ERROR_EXCEL_FAIL_FILE;
import static com.arvato.mybank.constants.Constants.ERROR_EXCEL_FAIL_WORKBOOK;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.arvato.mybank.api.services.TransactionServices;
import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;
/**
 * TransactionServices implementation
 * @author paulinelow
 *
 */
public class TransactionServicesImpl implements TransactionServices{
	Logger logger = Logger.getLogger(TransactionServicesImpl.class.getName());

	public TransactionServicesImpl() {
		super();
	}

	public Double checkBalance(Integer accountId) {
		Constants constants = new Constants();
		if(accountId != null && accountId>INT_ZERO_VALUE) {
			for(Account account:constants.getAccountList()) {
				if(accountId.equals(account.getAccountId())) {
					return account.getBalance();
				}
			}
		}
		return null;
	}
	
	public void depositAmount(Integer accountId, Double depositAmount) {
		Constants constants = new Constants();
		for(Account account:constants.getAccountList()){
			if(accountId.equals(account.getAccountId())){
				account.setBalance(account.getBalance()+depositAmount);
				updateAccountList(accountId, account.getBalance(), constants);
				break;
			}
		}
	}
	
	public void withdrawalAmount(Integer accountId, Double withdrawalAmount) {
		Constants constants = new Constants();
		for(Account account:constants.getAccountList()){
			if(accountId.equals(account.getAccountId())){
				account.setBalance(account.getBalance()-withdrawalAmount);
				updateAccountList(accountId, account.getBalance(), constants);
				break;
			}
		}
	}
	
	public void transferAmount(Integer accountId, Integer transferAccountId, Double transferAmount) {
		withdrawalAmount(accountId, transferAmount);
		depositAmount(transferAccountId, transferAmount);
	}
	
	public void updateAccountBalance(Integer accountId, Double amount) {
		Constants constants = new Constants();
		for(Account account:constants.getAccountList()){
			if(accountId.equals(account.getAccountId())){
				account.setBalance(amount);
				updateAccountList(accountId, account.getBalance(), constants);
				break;
			}
		}
	}
	
	@SuppressWarnings("unused")
	public void updateAccountList(Integer accountId, Double balance, Constants constants) {
		File myFile = new File(constants.getAccountFilePath()+constants.getAccountExcelFile()); 
		FileInputStream fis;
		XSSFWorkbook myWorkBook =null; 
		try {
			fis = new FileInputStream(myFile);
			myWorkBook = new XSSFWorkbook (fis); 
			XSSFSheet mySheet = myWorkBook.getSheetAt(INT_ZERO_VALUE);
			myWorkBook.removeSheetAt(0);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(myWorkBook!=null) {
			XSSFSheet mySheet = myWorkBook.createSheet("account");
			XSSFRow rowHead = mySheet.createRow(INT_ZERO_VALUE);
			rowHead.createCell(0).setCellValue("accountId");
			rowHead.createCell(1).setCellValue("balance");
			
			for(int i=0;i<constants.getAccountList().size();i++) {
				XSSFRow row = mySheet.createRow(i+1);
				row.createCell(0).setCellValue(constants.getAccountList().get(i).getAccountId());
				row.createCell(1).setCellValue(constants.getAccountList().get(i).getBalance());
			}
			
			try {
				FileOutputStream fileOut = new FileOutputStream(constants.getAccountFilePath()+ constants.getAccountExcelFile());
				myWorkBook.write(fileOut);
				fileOut.close();

			} catch (FileNotFoundException e) {
	            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_FILE);
				e.printStackTrace();
			} catch (IOException e) {
	            logger.log(Level.SEVERE, ERROR_EXCEL_FAIL_WORKBOOK);
				e.printStackTrace();
			} 
			
		}
		
	}

}
