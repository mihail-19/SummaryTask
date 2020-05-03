package ua.nure.teslenko.SummaryTask4.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.util.Validator;

class TestValidator {

	@Test
	void validateLoginNull() {
		assertThrows(AppException.class, () -> Validator.validateLogin(null));
	}
	@Test
	void validateLoginSmall() {
		assertThrows(AppException.class, () -> Validator.validateLogin("f"));
	}
	@Test
	void validateLoginSymbols() {
		assertThrows(AppException.class, () -> Validator.validateLogin("login%"));
	}
	@Test
	void validateLoginCorrect() throws AppException {
			Validator.validateLogin("login_correct");
	}
	
	@Test
	void validatePasswordNull() {
		assertThrows(AppException.class, () -> Validator.validatePassword(null));
	}
	@Test
	void validatePasswordSmall() {
		assertThrows(AppException.class, () -> Validator.validatePassword("f"));
	}
	@Test
	void validatePasswordSymbols() {
		assertThrows(AppException.class, () -> Validator.validatePassword("p^w#%"));
	}
	@Test
	void validatePasswordCorrect() throws AppException {
			Validator.validatePassword("1rsef");
	}
	
	@Test
	void validateNameNull() {
		assertThrows(AppException.class, () -> Validator.validateName(null));
	}
	@Test
	void validateNameSymbols() {
		assertThrows(AppException.class, () -> Validator.validateName("login%"));
	}
	@Test
	void validateNameCorrect() throws AppException {
			Validator.validateName("John");
	}
	@Test
	void validateNameCyr() throws AppException {
			Validator.validateName("В'ячеслав");
	}
	
	@Test
	void isUniqueLoginNull() {
		assertThrows(AppException.class, () -> Validator.isUniqueLogin(null));
	}
}
