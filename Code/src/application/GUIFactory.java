package application;

import java.io.IOException;
import java.sql.SQLException;

import application.Admin.AdminMenuController;
import application.Doctor.DoctorMenuController;
import application.LabTechnician.LabTechnicianMenuController;
import application.Nurse.NurseMenuController;
import application.Pharmacist.Pharmacist_menuController;
import application.Receptionist.Receptionist_menuController;
import hospital.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import manager.AdminManager;
import manager.AdmitPatientManager;
import manager.CheckupPatientManager;
import manager.DischargePatientManager;
import manager.MedicineSaleManager;
import manager.NurseManager;
import manager.PatientVisitManager;
import manager.PerformTestManager;

public class GUIFactory
{
	private static GUIFactory factory = null; 
	
	private GUIFactory()
	{
	}
	public static GUIFactory getFactory()
	{
		if(factory == null)
		{
			factory = new GUIFactory();
		}
		return factory;
	}
	
	public Parent loadMenu(User user) throws IOException, ClassNotFoundException, SQLException
	{
		FXMLLoader loader = null;
		Parent parent = null;
		switch(user.getUsername().charAt(0))
		{
		case 'd':
			loader = new FXMLLoader(getClass().getResource("/application/Doctor/Doctor_menu.fxml"));
            parent = loader.load();
            DoctorMenuController controller1 = loader.getController();
            controller1.setCheckupPatientManager(new CheckupPatientManager()); 
            controller1.setUser(user);
            controller1.loadDetails();
            break;
		case 'n':
			loader = new FXMLLoader(getClass().getResource("/application/Nurse/NurseMenu.fxml"));
            parent = loader.load();
            NurseMenuController controller2 = loader.getController();
            controller2.setNurseManager(new NurseManager());  
            controller2.setUser(user);
            controller2.loadDetails();
            break;
		case 'p':
			loader = new FXMLLoader(getClass().getResource("/application/Pharmacist/Pharmacist_menu.fxml"));
            parent = loader.load();
            Pharmacist_menuController controller3 = loader.getController();
            controller3.setMedicineSaleManager(new MedicineSaleManager());  
            controller3.setUser(user);
            controller3.loadDetails();
            break;
		case 'l':
			loader = new FXMLLoader(getClass().getResource("/application/LabTechnician/LabTechnician_Menu.fxml"));
            parent = loader.load();
            LabTechnicianMenuController controller4 = loader.getController();
            controller4.setPerformTestManager(new PerformTestManager());
            controller4.setUser(user);
            controller4.loadDetails();
            break;
		case 'a':
			loader = new FXMLLoader(getClass().getResource("/application/Admin/AdminMenu.fxml"));
            parent = loader.load();
            AdminMenuController controller = loader.getController();
            controller.setAdminManager(new AdminManager()); 
            controller.setUser(user);
            controller.loadDeatails();
            break;
		case 'r':
			loader = new FXMLLoader(getClass().getResource("/application/Receptionist/Receptionist_menu.fxml"));
            parent = loader.load();
            Receptionist_menuController controller5 = loader.getController();
            controller5.setAdmitPatientManager(new AdmitPatientManager());
            controller5.setDischargePatientManager(new DischargePatientManager());
            controller5.setPatientVisitManager(new PatientVisitManager());
            controller5.setUser(user);
            controller5.loadDetails();
            break;
			
		}
		return parent;

	}
	
}