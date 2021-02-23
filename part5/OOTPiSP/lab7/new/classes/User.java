package classes;


/**
 * @author Asus
 * @version 1.0
 * @created 11-Dec-2019 12:12:54
 */
public class User {

	private String email;
	private int id;
	private String login;
	private int password_hash;
	private Role role;
	public Role m_Role;

	public User(){

	}

	public void finalize() throws Throwable {

	}
}//end User