package com.arvato.mybank.impl.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.arvato.mybank.api.services.TransactionServices;
import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;

public class TransactionServicesImpl implements TransactionServices{

	public TransactionServicesImpl() {
		super();
	}

	public Double checkBalance(Integer accountId) {
		Constants constants = new Constants();
		if(accountId != null && accountId>0) {
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
	
	public void updateAccountList(Integer accountId, Double balance, Constants constants) {
		File myFile = new File(constants.getAccountFilePath()+constants.getAccountExcelFile()); 
		FileInputStream fis;
		XSSFWorkbook myWorkBook =null; 
		try {
			fis = new FileInputStream(myFile);
			myWorkBook = new XSSFWorkbook (fis); 
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			myWorkBook.removeSheetAt(0);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(myWorkBook!=null) {
			XSSFSheet mySheet = myWorkBook.createSheet("account");
			//create header row
			XSSFRow rowHead = mySheet.createRow(0);
			rowHead.createCell(0).setCellValue("accountId");
			rowHead.createCell(1).setCellValue("balance");
			
			//create row data
			for(int i=0;i<constants.getAccountList().size();i++) {
				XSSFRow row = mySheet.createRow(i+1);
				row.createCell(0).setCellValue(constants.getAccountList().get(i).getAccountId());
				row.createCell(1).setCellValue(constants.getAccountList().get(i).getBalance());
			}
			
			try {
				FileOutputStream fileOut = new FileOutputStream(constants.getAccountFilePath()+ constants.getAccountExcelFile());
				myWorkBook.write(fileOut);
				fileOut.close();
				System.out.println("Excel is written successfully");    

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
