package com.spring.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.apierrorresponse.APIErrorResponse;
import com.spring.entity.Employee;
import com.spring.repository.EmployeeRepository;
import com.spring.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeService employeeService;

    EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
	
//	@PostMapping("/employees")
//	public ResponseEntity<Employee> saveEmployeeData(@Valid @RequestBody Employee employee){
//		
//		Employee emp = employeeService.saveEmployeeData(employee);
//		return ResponseEntity.status(HttpStatus.CREATED)
//				             .header("info", "employee data created suceessfully")
//				             .body(emp);
//	}
//	
    
    @PostMapping("/saveemployee")
	public ResponseEntity<EntityModel<Employee>> saveEmp(@Valid @RequestBody Employee emp) {
    	Employee employee = employeeService.saveEmployeeData(emp);
			EntityModel<Employee> entity=EntityModel.of(employee);
			entity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());
//	    // Add self link
//	    Link selfLink = linkTo(methodOn(EmpController.class).getById(employee.getId())).withSelfRel();
//	    employee.add(selfLink);
//	    
//	    Link getbygmail = linkTo(methodOn(EmpController.class).getEmployeeByEmail(employee.getEmail())).withSelfRel();
//	    employee.add(getbygmail);
//
//	    // Optional: Add a link to get all employees
//	    Link allEmpsLink = linkTo(methodOn(EmpController.class).getAllEmployee()).withRel("all-employees");
//	    employee.add(allEmpsLink);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .header("info", "data saved Successfully")
	            .body(entity);
	       }
	@PostMapping("/employees/bulk")
	public ResponseEntity<List<Employee>> saveAllEmployeeData(@RequestBody List<Employee> employees){
		List<Employee> emps = employeeService.saveAllEmployeeData(employees);
		return ResponseEntity.status(HttpStatus.CREATED)
							 .header("info", "Employee Data Created Successfully")
							 .body(emps);
	}
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployeesData(){
		List<Employee> empsData = employeeService.getAllEmployeeData();
		return empsData;
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id){
		Optional<Employee> optionalEmp = employeeService.getEmployeeById(id);
		if(optionalEmp.isPresent()) {
			Employee emp = optionalEmp.get();
			return ResponseEntity.status(HttpStatus.OK)
							     .header("Info", "Data Retrieved Successfully")
							     .body(emp);
		}
		else {
			APIErrorResponse apiErrorResponse = new APIErrorResponse();
			apiErrorResponse.setTimeStamp(LocalDateTime.now());
			apiErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiErrorResponse.setErrorMessage("data is not available with given id..." + id);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .header("info", "Data Not Found")
								 .body(apiErrorResponse);
		}
	}
	
	
	@GetMapping("/employees/name/{name}")
	public ResponseEntity<?> getEmployeeByName(@PathVariable("name") String name){
		List<Employee> optionalEmpName = employeeService.getEmployeeByName(name);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("Info", "data retrieved successfull")
							 .body(optionalEmpName);
		
	}
	
	@GetMapping("/employees/email/{email}")
	public ResponseEntity<?> getEmployeeByEmail(@PathVariable("email") String email){
		Optional<Employee> optEmpEmail = employeeService.getEmployeeByEmail(email);
		if(optEmpEmail.isPresent()) {
			Employee empEmail = optEmpEmail.get();
			return ResponseEntity.status(HttpStatus.OK)
					 .header("info", "data retrieved successfully")
					 .body(empEmail);
		}else {
			APIErrorResponse apiErrorResponse = new APIErrorResponse();
			apiErrorResponse.setTimeStamp(LocalDateTime.now());
			apiErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiErrorResponse.setErrorMessage("data not available" + email);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .header("info", "email not found")
								 .body(apiErrorResponse);
		}
	}
	
	@GetMapping("/employees/filter-by-dept")
	public ResponseEntity<List<Employee>> getEmployeeByDept(@RequestParam("dept") String dept){
		List<Employee> empDept = employeeService.getEmployeeByDept(dept);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "Data retrieved successfully")
							 .body(empDept);
	}
	
	@GetMapping("/employees/filter-by-gender")
	public ResponseEntity<List<Employee>> getEmployeeByGender(@RequestParam("gender") String gender){
		List<Employee> empGender = employeeService.getEmployeeGender(gender);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "Data retrieved successfully")
							 .body(empGender);
	}
	
	@GetMapping("/employees/filter-and")
	public ResponseEntity<List<Employee>> getEmpByDeptAndGender(@RequestParam("dept") String dept, @RequestParam("gender") String gender){
		List<Employee> empDeptAndGender = employeeService.getEmployeeByDeptAndGender(dept, gender);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "data retrived successfully")
							 .body(empDeptAndGender);
	}
	
	@GetMapping("/employees/filter-or")
	public ResponseEntity<List<Employee>> getEmpByDeptOrGender(@RequestParam("dept") String dept, @RequestParam("gender") String gender){
		List<Employee> empDeptOrGender = employeeService.getEmployeeByDeptOrGender(dept, gender);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "data retrived successfully")
							 .body(empDeptOrGender);
	}
	
	@GetMapping("/employees/salary-range")
	public ResponseEntity<List<Employee>> getEmployeeSalaryMinMax(@RequestParam("minSalary") double minSalary, @RequestParam("maxSalary") double maxSalary){
		List<Employee> getEmpSalMinMax = employeeService.getEmployeeMaxMin(minSalary, maxSalary);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "data retrived successfully")
							 .body(getEmpSalMinMax);
	}
	
	
	@GetMapping("/employees/salary-max")
	public ResponseEntity<List<Employee>> getEmployeesWithSalaryGreaterThan(@RequestParam("salary") double salary) {
	    List<Employee> employeesSalGreater = employeeService.getEmployeeSalaryGreaterThan(salary);
	    return ResponseEntity.status(HttpStatus.OK)
	                         .header("info", "Employees with salary greater than " + salary + " retrieved successfully")
	                         .body(employeesSalGreater);
	}
	
	
	@GetMapping("/employees/salary-min")
	public ResponseEntity<List<Employee>> getEmployeesWithSalaryLessThan(@RequestParam("salary") double salary) {
	    List<Employee> employeesSalLess = employeeService.getEmployeeSalaryLessThan(salary);
	    return ResponseEntity.status(HttpStatus.OK)
	                         .header("info", "Employees with salary less than " + salary + " retrieved successfully")
	                         .body(employeesSalLess);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmpById(@PathVariable("id") Long Id){
	    employeeService.deleteById(Id);
	    return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                         .header("info", "data not found")
	                         .build();
	}
	
	
	@DeleteMapping("/employees/email/{email}")
	public ResponseEntity<?> deleteEmpByEmail(@PathVariable("email") String email){
		employeeService.deleteByEmail(email);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .header("info", "email not found")
							 .build();
	}
	
	@DeleteMapping("/employees/del-by-dept")
	public ResponseEntity<?> deleteEmpByDept(@RequestParam("dept") String dept){
		employeeService.deleteEmployeeByDept(dept);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .header("info", "Dept not found")
							 .build();
						
	}
	
	@DeleteMapping("/employees/del-by-dept-gen")
	public ResponseEntity<?> deleteEmpByDeptAndGender(@RequestParam("dept") String dept, @RequestParam("gender") String gender){
		employeeService.deleteByDeptAndGender(dept, gender);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .header("info", "Dept not found")
							 .build();
	}
	
	@DeleteMapping("/employees/del-by-maxsal")
	public ResponseEntity<?> deleteEmployeeSalGreaterThan(@RequestParam("salary") double salary){
		employeeService.deleteByEmployeeSalaryGreaterThan(salary);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .header("info", "Dept not found")
							 .build();
	}
	
	@DeleteMapping("/employees/del-by-sal")
	public ResponseEntity<?> deleteEmployeeSalLess(@RequestParam("salary") double salary){
		employeeService.deleteByEmployeeSalaryLessThan(salary);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .header("info", "Dept not found")
							 .build();
	}
	
	@PutMapping("/employees/employeeupdate/{id}")
	public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Long id, @RequestBody Employee newEmployee){
		Employee updateEmployee = employeeService.updateEmployeeById(id, newEmployee);
		return ResponseEntity.status(HttpStatus.OK)
						     .header("info", "data retrived successfully")
						     .body(updateEmployee);
	}
	
	@PatchMapping("/employees/employeepartialupdate/{id}")
	public ResponseEntity<?> partialUpdateEmployeeById(@PathVariable("id") Long id, @RequestBody Map<String, Object> newEmployee){
		Employee partialUpdateEmp = employeeService.partialUpdateEmployee(id, newEmployee);
		return ResponseEntity.status(HttpStatus.OK)
							 .header("info", "Data updated sucessfully")
							 .body(partialUpdateEmp);
	}
	@GetMapping("employees/search")
	public ResponseEntity<List<Employee>> saveEmployee(
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String gender)
	{
		List<Employee> employees;
		if(dept!=null && gender!=null)
		{
			employees = employeeService.getEmployeeByDeptAndGender(dept,gender);
		}
		else if(dept!=null)
		{
			employees = employeeService.getEmployeeByDept(dept);
		}
		else if(gender!=null)
		{
			employees = employeeService.getEmployeeGender(gender);
			
		}
		else
		{
			employees = employeeService.getAllEmployeeData();
			
		}
		return ResponseEntity.status(HttpStatus.OK)
							.header("info","data retrieved successfully")
							.body(employees);
	}
	
}