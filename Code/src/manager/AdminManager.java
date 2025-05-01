package manager;
//import hospital.User;
//import hospital.DoctorQueue;
//import hospital.LabQueue;
//import hospital.Patient;
import hospital.*;
import dbhandler.*;

import java.sql.SQLException;
import java.util.ArrayList;
//import dbhandler.PatientDBHandler;

public class AdminManager
{
	//Add -> Save
	//Remove
	//Edit -> Get
	
	//Receptionist(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass)
	//Pharmacist(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass)
	//Nurse(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass, Room room)
	//LabTechnician(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass)
	//Doctor(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  String spec, String usernam, String pass)
	
	public AdminManager()
	{
		
	}
	
	//Doctor
	public boolean addUpdateDoctor(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  String spec, String usernam, String pass) throws ClassNotFoundException, SQLException
	{
		Doctor d = new Doctor(nam,add,cnic,phone,ag,sch,gen,spec,usernam,pass);
		return DoctorDBHandler.getDoctorDBHandler().saveDoctor(d);
	}
	public boolean removeDoctor(String username) throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().removeDoctor(username);
	}
	public Doctor getDoctor(String username) throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().getDoctor(username);
	}
	public ArrayList<Doctor> getDoctors() throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().getDoctors();
	}
	public ArrayList<Doctor> getDoctors(String name) throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().getDoctorViaName(name);
	
	}
	
	//LabTech
	public boolean addUpdateLabTechnician(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass) throws ClassNotFoundException, SQLException
	{
		LabTechnician l = new LabTechnician(nam,add,cnic,phone,ag,sch,gen,exp,usernam,pass);
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().saveLabTechnician(l);
	}
	public boolean removeLabTechnician(String username) throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().removeLabTechnician(username);
	}
	public LabTechnician getLabTechnician(String username) throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnician(username);
	}
	public ArrayList<LabTechnician> getLabTechs() throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnicians();
	}
	public ArrayList<LabTechnician> getLabTechs(String name) throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnicianViaName(name);
	}
	
	//Nurse
	public boolean addUpdateNurse(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass, int room) throws ClassNotFoundException, SQLException
	{
		Nurse n = new Nurse(nam,add,cnic,phone,ag,sch,gen,exp,usernam,pass,room);
		return NurseDBHandler.getNurseDBHandler().saveNurse(n);
	}
	public boolean removeNurse(String username) throws ClassNotFoundException, SQLException
	{
		return NurseDBHandler.getNurseDBHandler().removeNurse(username);
	}
	public Nurse getNurse(String username) throws ClassNotFoundException, SQLException
	{
		return NurseDBHandler.getNurseDBHandler().getNurse(username);
	}
	public ArrayList<Nurse> getNurses() throws ClassNotFoundException, SQLException
	{
		return NurseDBHandler.getNurseDBHandler().getNurses();
	}
	public ArrayList<Nurse> getNurses(String name) throws ClassNotFoundException, SQLException
	{
		return NurseDBHandler.getNurseDBHandler().getNurseViaName(name);
	}

	
	
	//Pharmacist
	public boolean addUpdatePharmacist(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass) throws ClassNotFoundException, SQLException
	{
		Pharmacist p = new Pharmacist(nam,add,cnic,phone,ag,sch,gen,exp,usernam,pass);
		return PharmacistDBHandler.getPharmacistDBHandler().savePharmacist(p);
	}
	public boolean removePharmacist(String username) throws ClassNotFoundException, SQLException
	{
		return PharmacistDBHandler.getPharmacistDBHandler().removePharmacist(username);
	}
	public Pharmacist getPharmacist(String username) throws ClassNotFoundException, SQLException
	{
		return PharmacistDBHandler.getPharmacistDBHandler().getPharmacist(username);
	}
	public ArrayList<Pharmacist> getPharmacists() throws ClassNotFoundException, SQLException
	{
		return PharmacistDBHandler.getPharmacistDBHandler().getPharmacists();
	}
	public ArrayList<Pharmacist> getPharmacists(String name) throws ClassNotFoundException, SQLException
	{
		return PharmacistDBHandler.getPharmacistDBHandler().getPharmacistViaName(name);
	}
	
	//Receptionist
	public boolean addUpdateReceprionist(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass) throws ClassNotFoundException, SQLException
	{
		Receptionist r = new Receptionist(nam,add,cnic,phone,ag,sch,gen,exp,usernam,pass);
		return ReceptionistDBHandler.getReceptionistDBHandler().saveReceptionist(r);
	}
	public boolean removeReceprionist(String username) throws ClassNotFoundException, SQLException
	{
		return ReceptionistDBHandler.getReceptionistDBHandler().removeReceptionist(username);
	}
	public Receptionist getReceptionist(String username) throws ClassNotFoundException, SQLException
	{
		return ReceptionistDBHandler.getReceptionistDBHandler().getReceptionist(username);
	}
	public ArrayList<Receptionist> getReceptionists() throws ClassNotFoundException, SQLException
	{
		return ReceptionistDBHandler.getReceptionistDBHandler().getReceptionists();
	}
	public ArrayList<Receptionist> getReceptionists(String name) throws ClassNotFoundException, SQLException
	{
		return ReceptionistDBHandler.getReceptionistDBHandler().getReceptionistViaName(name);
	}
	
	public ArrayList<Integer> getRooms() throws ClassNotFoundException, SQLException
	{
		return RoomDBHandler.getRoomDBHandler().getRooms();
	}
	
}