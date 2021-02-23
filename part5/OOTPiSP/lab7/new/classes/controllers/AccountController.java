package classes.controllers;

import classes.User;

/**
 * @author Vladik
 * @version 1.0
 * @created 11-Dec-2019 12:12:53
 */
public class AccountController {

	private AccountProcessor accountProcessor;

	public AccountController(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param user
	 */
	public boolean changeUserInfo(User user){
		return false;
	}

	/**
	 * 
	 * @param user
	 */
	public boolean deleteUser(User user){
		return false;
	}

	/**
	 * 
	 * @param login
	 * @param hash
	 */
	public boolean logIn(String login, String hash){
		return false;
	}

	public boolean logOut(){
		return false;
	}

	/**
	 * 
	 * @param login
	 * @param hash
	 */
	public boolean signIn(String login, String hash){
		return false;
	}
}//end AccountController