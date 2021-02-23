package classes;


/**
 * @author Asus
 * @version 1.0
 * @created 11-Dec-2019 12:12:54
 */
public class Sale {

	private int count;
	private int id;
	private Product product;
	private Transaction transaction;
	private User user;
	public User m_User;
	public Transaction m_Transaction;
	public Product m_Product;

	public Sale(){

	}

	public void finalize() throws Throwable {

	}
}//end Sale