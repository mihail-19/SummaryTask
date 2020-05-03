package ua.nure.teslenko.SummaryTask4.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminSelect;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Util class for validating registration data.
 * @author Mykhailo Teslenko
 *
 */
public final class Validator {
	static Logger log = LogManager.getLogger();
	private Validator() {
		
	}
	/**
	 * Validates user login. Login should not contain special symbols,
	 * except - and _
	 * @param login
	 * @throws AppException
	 */
	public static void validateLogin(String login) throws AppException {
		log.trace("validating login -> " + login);
		if(login == null || !login.matches("^[A-zА-яёЁіІїЇєЄ][A-zА-яёЁіІїЇєЄ0-9_\\-]{3,15}$")){
			log.error("incorrect login -> " + login);
			throw new AppException("validator.loginIncorrect");
		}
		log.debug("validation success for login -> " + login);
	}
	
	/**
	 * Validates password. It should contain only
	 * letters and digits.
	 * @param pwd
	 * @throws AppException
	 */
	public static void validatePassword(String pwd) throws AppException {
		log.trace("validating password -> " + pwd);
		if(pwd == null || !pwd.matches("^[A-zА-яёЁіІїЇєЄ0-9]{4,15}$")){
			log.error("incorrect password" + pwd);
			throw new AppException("validator.password");
		}
		log.trace("validation success for password -> " + pwd);
	}
	
	/**
	 * Validates name and surname. Letters only.
	 * @param name
	 * @throws AppException
	 */
	public static void validateName(String name) throws AppException {
		log.trace("validating name -> " + name);
		if(name == null || !name.matches("^[A-zА-яёЁіІїЇєЄ'-]{1,25}$")){
			log.error("incorrect name" + name);
			throw new AppException("validator.name");
		}
		log.trace("validation success for name -> " + name);
	}
	
	/**
	 * Tests login, comparing with existing logins in DB.
	 * @param newLogin
	 * @throws AppException
	 */
	public static void isUniqueLogin(String newLogin) throws AppException {
		log.trace("testing is login unique -> " + newLogin);
		if(newLogin == null) {
			throw new AppException("command.emptyUserLogin");
		}
		DaoAdminSelect dao = new DaoAdminSelect();
		List<String> logins = dao.getLoginsList();
		for(String login : logins) {
			if(newLogin.equals(login)) {
				log.error("login is not unique -> " + login);
				throw new AppException("validator.loginIncorrect");
			}
		}
	}
}
