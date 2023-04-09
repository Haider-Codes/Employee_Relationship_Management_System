package com.springboot.app.controller;

import com.springboot.app.entity.Employee;
import com.springboot.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	// Since there is only one constructor @Autowired is optional
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService){
		employeeService = theEmployeeService;
	}

	// add mapping for "/list"
	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		List<Employee> theEmployees = employeeService.findAll();
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return "employees/list-employees";
	}

	// add mapping for "/showFormForAdd"
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel){

		Employee theEmployee = new Employee();

		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-form";

	}

	// add mapping for "/showFormForUpdate"
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel){

		// get the employee from the service
		Employee theEemployee = employeeService.findById(theId);

		// set the model attribute to pre-populate the form
		theModel.addAttribute("employee", theEemployee);

		// send over to our form
		return "employees/employee-form";

	}

	// Process the "employee-form" data
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){

		// save the Employee to the Database
		employeeService.save(theEmployee);

		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";

	}

	// add mapping for "delete"
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId){

		// delete the employee from the database
		employeeService.deleteById(theId);

		// redirect to the "/employees/list"
		return "redirect:/employees/list";

	}

}









