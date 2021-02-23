package classes;


/**
 * @author Vladik
 * @version 1.0
 * @created 11-Dec-2019 12:12:54
 */
public class Transaction {

	private Data data;
	private int id;
	private String numberBankAccount;
	private int sum;

	public Transaction(){

	}

	public void finalize() throws Throwable {

	}
}//end Transaction