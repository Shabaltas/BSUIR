package classes.controllers;

import classes.processors.FinancialProcessor;
import classes.Transaction;
import classes.Sale;

/**
 * @author Asus
 * @version 1.0
 * @created 11-Dec-2019 12:12:53
 */
public class FinancialController {

	private FinancialProcessor financialProcessor;

	public FinancialController(){

	}

	public void finalize() throws Throwable {

	}
	public String generateFullReport(){
		return "";
	}

	public String generateWeekReport(){
		return "";
	}

	/**
	 * 
	 * @param sale
	 */
	public Transaction paySale(Sale sale){
		return null;
	}
}//end FinancialController