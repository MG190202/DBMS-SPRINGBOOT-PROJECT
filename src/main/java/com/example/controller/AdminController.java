package com.example.controller;

import com.example.entity.Catalogue;
import com.example.entity.Patient;
import com.example.entity.Room;
import com.example.entity.Staff;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private MedicalreportRepository medicalreportRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CatalogueRepository catalogueRepository;


    @RequestMapping("/index/{name}")
    public String dashboard(Model model,@PathVariable("name")String name){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        return "ADMIN/admin_dashboard";
    }

    //view profile
    @GetMapping("/profile/{name}")
    public String showProfile(@PathVariable("name")String name , Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        return "ADMIN/profile";
    }

    //show all patients
    @GetMapping("/show-patients/{name}")
    public String showPatients(@PathVariable("name")String name ,Model model){
        List<Patient> patients = this.patientRepository.findAll();
        model.addAttribute("patients", patients);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        return "ADMIN/show_patients";
    }

    //show rooms
    @GetMapping("/show-rooms/{name}")
    public String showRooms( @PathVariable("name")String name ,Model model){
        List<Room> rooms = this.roomRepository.findAll();
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("rooms", rooms);
        return "ADMIN/show_rooms";
    }

    //show catalogue items
    @GetMapping("/show-catalogue/{name}")
    public String showCatalogue(Model model,@PathVariable("name")String name){
        List<Catalogue> catalogues = this.catalogueRepository.findAll();
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("catalogues", catalogues);
        return "ADMIN/show_catalogue";
    }
    //show all staff
    @GetMapping("/show-staff/{name}")
    public String showStaff(Model model,@PathVariable("name")String name){
        List<Staff> staffs = this.staffRepository.findAll();
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("staffs", staffs);
        return "ADMIN/show_staff";
    }

    //open add room
    @GetMapping("/add-room/{name}")
    public String openAddRoomForm(Model model,@PathVariable("name")String name){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("room",new Room());
        return "ADMIN/add_room_form";
    }

    //processing add room form
    @PostMapping("/process-room/{name}")
    public String processRoom(@ModelAttribute("room") Room room,Model model,@PathVariable("name")String name){
        roomRepository.save(room);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        return "ADMIN/add_room_form";
    }


    //open add staff
    @GetMapping("/add-staff/{name}")
    public String openAddStaffForm(Model model,@PathVariable("name")String name){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);

        model.addAttribute("stafff",new Staff());
        return "ADMIN/add_staff_form";
    }

    //processing add staff form
    @PostMapping("/process-staff/{name}")
    public String processStaff(@ModelAttribute("stafff") Staff stafff,@PathVariable("name")String name,Model model){
        stafff.setPassword("ghilodsnlhms");
        staffRepository.save(stafff);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);

        return "ADMIN/add_staff_form";
    }

    //open add catalogue form
    @GetMapping("/add-catalogue/{name}")
    public String openAddCatalogueForm(Model model,@PathVariable("name")String name){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("catalogue",new Catalogue());
        return "ADMIN/add_catalogue_form";
    }

    //processing add catalogue form
    @PostMapping("/process-catalogue/{name}")
    public String processCatalogue(@ModelAttribute("catalogue") Catalogue catalogue,@PathVariable("name")String name,Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        catalogueRepository.save(catalogue);

        return "ADMIN/add_catalogue_form";
    }




    //DELETES
    @GetMapping("/show-patients/{sname}/deletepatient/{name}")
    public String deletePatient(@PathVariable("sname") String staffname,@PathVariable("name") String name,Model model){
        Patient patient =this.patientRepository.getPatientByName(name);
        this.patientRepository.delete(patient);
        String redirectUrl = "/admin/show-patients/" + staffname;
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/show-staff/{sname}/deletestaff/{name}")
    public String deleteStaff(@PathVariable("sname") String staffname,@PathVariable("name") String name,Model model){
        Staff staff =this.staffRepository.getStaffByName(name);
        this.staffRepository.delete(staff);
        String redirectUrl = "/admin/show-staff/" + staffname;
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/show-rooms/{sname}/deleteroom/{roomno}")
    public String deleteRoom(@PathVariable("sname") String staffname,@PathVariable("roomno") Integer roomno,Model model){
        Room room =this.roomRepository.getRoomByRoomno(roomno);
        this.roomRepository.delete(room);
        String redirectUrl = "/admin/show-rooms/" + staffname;
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/show-catalogue/{sname}/deletecatalogue/{id}")
    public String deleteCatalogue(@PathVariable("sname") String sname,@PathVariable("id") Integer id,Model model){
        Catalogue catalogue = this.catalogueRepository.getCatalogueByAccessID(id);
        this.catalogueRepository.delete(catalogue);
        String redirectUrl = "/admin/show-catalogue/" + sname;
        return "redirect:" + redirectUrl;
    }


    //UPDATES
    /////////////////////CATALOGUE
    @GetMapping("/show-catalogue/{name}/updatecatalogue/{id}")
    public String updateCatalogue(@PathVariable("id")Integer id,@PathVariable("name") String name,Model model){
      //  Catalogue catalogue = this.catalogueRepository.getCatalogueByAccessID(id);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("catalogue",new Catalogue());
        model.addAttribute("id",id);
        return "ADMIN/update_catalogue_form";
    }

    @PostMapping("/processup-catalogue/{name}/{id}")
    public String processupCatalogue(@ModelAttribute("catalogue") Catalogue catalogue,@PathVariable("id")Integer id,@PathVariable("name")String name,Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        Catalogue cat = this.catalogueRepository.getCatalogueByAccessID(id);
        cat.setAccessName(catalogue.getAccessName());
        cat.setQuantity(catalogue.getQuantity());
        catalogueRepository.save(cat);

        return "ADMIN/update_catalogue_form";
    }
    /////////////////////////////////////////////////////ROOMS

    @GetMapping("/show-rooms/{name}/updateroom/{id}")
    public String updateroom(@PathVariable("id")Integer id,@PathVariable("name") String name,Model model){
        //  Catalogue catalogue = this.catalogueRepository.getCatalogueByAccessID(id);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("room",new Room());
        model.addAttribute("id",id);
        return "ADMIN/update_room_form";
    }

    @PostMapping("/processup-room/{name}/{id}")
    public String processuproom(@ModelAttribute("room") Room room,@PathVariable("id")Integer id,@PathVariable("name")String name,Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        Room ro = this.roomRepository.getRoomByRoomno(id);
        ro.setRoomtype(room.getRoomtype());
        ro.setMaxoccup(room.getMaxoccup());
        ro.setCharge(room.getCharge());
        roomRepository.save(ro);

        return "ADMIN/update_room_form";
    }

    //////////////////////////////////////////////////PATIENTS
    @GetMapping("/show-patients/{name}/updatepatient/{pn}")
    public String updatepatient(@PathVariable("pn")String pn,@PathVariable("name") String name,Model model){
        //  Catalogue catalogue = this.catalogueRepository.getCatalogueByAccessID(id);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("patient",new Patient());
        model.addAttribute("pn",pn);
        return "ADMIN/update_patient_form";
    }

    @PostMapping("/processup-patient/{name}/{pn}")
    public String processuppatient(@ModelAttribute("patient") Patient patient,@PathVariable("pn")String pn,@PathVariable("name")String name,Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        Patient pat = this.patientRepository.getPatientByName(pn);
        pat.setName(patient.getName());
        pat.setDOB(patient.getDOB());
        pat.setBloodgroup(patient.getBloodgroup());
        pat.setGender(patient.getGender());
        pat.setMobileNo(patient.getMobileNo());
        patientRepository.save(pat);

        return "ADMIN/update_patient_form";
    }

    /////////////////////////////////////////////////STAFF
    @GetMapping("/show-staff/{name}/updatestaff/{sn}")
    public String updatestaff(@PathVariable("sn")String sn,@PathVariable("name") String name,Model model){
        //  Catalogue catalogue = this.catalogueRepository.getCatalogueByAccessID(id);
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        model.addAttribute("staff1",new Staff());
        model.addAttribute("sn",sn);
        return "ADMIN/update_staff_form";
    }

    @PostMapping("/processup-staff/{name}/{sn}")
    public String processupstaff(@ModelAttribute("staff1") Staff staff1,@PathVariable("sn")String sn,@PathVariable("name")String name,Model model){
        Staff staff = staffRepository.getStaffByName(name);
        model.addAttribute("staff", staff);
        Staff st = staffRepository.getStaffByName(sn);
        st.setName(staff1.getName());
        st.setDOB(staff1.getDOB());
        st.setMobileno(staff1.getMobileno());
        st.setDesignation(staff1.getDesignation());
        st.setDepartment(staff1.getDepartment());
        st.setSalary(staff1.getSalary());
        staffRepository.save(st);

        return "ADMIN/update_staff_form";
    }


}
