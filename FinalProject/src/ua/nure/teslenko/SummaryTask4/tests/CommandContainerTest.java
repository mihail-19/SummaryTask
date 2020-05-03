package ua.nure.teslenko.SummaryTask4.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ua.nure.teslenko.SummaryTask4.web.command.CommandContainer;
import ua.nure.teslenko.SummaryTask4.web.command.CommandNoCommand;
import ua.nure.teslenko.SummaryTask4.web.command.autorization.CommandLogin;

class CommandContainerTest {

	@Test
	void getNull() {
		CommandContainer.get(null);
	}
	
	@Test
	void getRealCommand() {
		Object expected = new CommandLogin().getClass();
		Object actual = CommandContainer.get("login").getClass();
		assertEquals(expected, actual);
	}
	
	@Test
	void getWrongCommand() {
		Object expected = new CommandNoCommand().getClass();
		Object actual = CommandContainer.get("someUnexistedCommand").getClass();
		assertEquals(expected, actual);
	}
}
