package ua.nure.teslenko.SummaryTask4.web.command;

import java.util.HashMap;
import java.util.Map;

import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandAddUserDoctor;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandPatientMenuAdmin;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRegisterAction;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRegisterActionPatient;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRegistration;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRemovePatient;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRemoveUser;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRemoveUserDoctor;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandShowDoctors;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandTestNewLogin;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandUserMenu;
import ua.nure.teslenko.SummaryTask4.web.command.autorization.CommandAuthorize;
import ua.nure.teslenko.SummaryTask4.web.command.autorization.CommandLogin;
import ua.nure.teslenko.SummaryTask4.web.command.autorization.CommandLogout;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandAddDiagnose;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandAddPrescription;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandDischarge;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandPatientMenuDoctor;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandExecutePrescription;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandRemovePrescription;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandShowDoctorPatients;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandShowAllUsers;
/**
 * Contains commands for most actions in application.
 * @author Mykhailo Teslenko
 *
 */

public class CommandContainer {
	private static Map<String, Command> commands = new HashMap<>();
	static {
		commands.put("login", new CommandLogin());
		commands.put("authorize", new CommandAuthorize());
		commands.put("logout", new CommandLogout());
		
		//admin commands
		commands.put("registration", new CommandRegistration());
		commands.put("registerUser", new CommandRegisterAction());
		commands.put("registerPatient", new CommandRegisterActionPatient());
		commands.put("showAllUsers", new CommandShowAllUsers());
		commands.put("showAllPatients", new CommandShowAllPatients());
		commands.put("userMenu", new CommandUserMenu());
		commands.put("addUserDoctor", new CommandAddUserDoctor());
		commands.put("removeUserDoctor", new CommandRemoveUserDoctor());
		commands.put("removeUser", new CommandRemoveUser());
		commands.put("selectDoctor", new CommandShowDoctors());
		commands.put("testNewLogin", new CommandTestNewLogin());
		commands.put("patientMenuAdmin", new CommandPatientMenuAdmin());
		commands.put("removePatient", new CommandRemovePatient());
		
		//doctor commands
		commands.put("addPrescription", new CommandAddPrescription());
		commands.put("removePrescription", new CommandRemovePrescription());
		commands.put("executePrescription", new CommandExecutePrescription());
		commands.put("showDoctorPatients", new CommandShowDoctorPatients());
		commands.put("showAllPatients", new CommandShowAllPatients());
		commands.put("userMenuAJAX", new CommandPatientMenuDoctor());
		commands.put("addDiagnose", new CommandAddDiagnose());
		commands.put("discharge", new CommandDischarge());
		commands.put("patientMenuDoctor", new CommandPatientMenuDoctor());
		
		//common
		commands.put("setLocale", new CommandSetLocale());
		
	
		
		
	}
	
	/**
	 * Returns appropriate command.
	 * @param commandName
	 * @return
	 */
	public static Command get(String commandName) {
		if(commandName == null || !commands.containsKey(commandName)) {
			return new CommandNoCommand();
		} 
		return commands.get(commandName);
	}
}
