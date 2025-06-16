package com.spring.service;


import com.spring.apierrorresponse.ResourceNotFoundException;
import com.spring.entity.Employee;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee saveEmployeeData(Employee employee) {
		Employee emp = employeeRepository.save(employee);
		return emp;
	}
	
	
	public List<Employee> saveAllEmployeeData(List<Employee> employee) {
		List<Employee> emp = employeeRepository.saveAll(employee);
		return emp;
	}
	
	public List<Employee> getAllEmployeeData(){
		List<Employee> emps = employeeRepository.findAll();
		return emps;
	}
	
	public Optional<Employee> getEmployeeById(Long id){
		Optional<Employee> optionalEmp = employeeRepository.findById(id);
		return optionalEmp;
	}
	
	public List<Employee> getEmployeeByName(String name){
		List<Employee> optEmpName = employeeRepository.findByName(name); 
		return optEmpName;
	}
	
	public Optional<Employee> getEmployeeByEmail(String email){
		Optional<Employee> getEmpEmail = employeeRepository.findByEmail(email);
		return getEmpEmail;
	}
	
	public List<Employee> getEmployeeByDept(String dept){
		List<Employee> getEmpDept = employeeRepository.findByDept(dept);
		return getEmpDept;
	}
	
	public List<Employee> getEmployeeGender(String gender){
		List<Employee> getEmpGender = employeeRepository.findByGender(gender);
		return getEmpGender;
	}
	
	public List<Employee> getEmployeeByDeptAndGender(String dept, String gender){
		List<Employee> getEmpDeptAndGender = employeeRepository.findByDeptAndGender(dept, gender);
		return getEmpDeptAndGender;
	}
	
	public List<Employee> getEmployeeByDeptOrGender(String dept, String gender){
		List<Employee> getEmpDeptOrGender = employeeRepository.findByDeptOrGender(dept, gender);
		return getEmpDeptOrGender;
	}
	
	public List<Employee> getEmployeeMaxMin(double minSalary, double maxSalary){
		List<Employee> getEmpMaxMin = employeeRepository.findBySalaryBetween(minSalary, maxSalary);
		return getEmpMaxMin;
	}
	
	public List<Employee> getEmployeeSalaryGreaterThan(double Salary){
		List<Employee> getEmpSalGreater = employeeRepository.findBySalaryGreaterThan(Salary);
		return getEmpSalGreater;
	}
	
	public List<Employee> getEmployeeSalaryLessThan(double Salary){
		List<Employee> getEmpSalLess = employeeRepository.findBySalaryLessThan(Salary);
		return getEmpSalLess;
	}
	
	
	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}
	
	public void deleteByEmail(String email) {
		employeeRepository.deleteByEmail(email);
	}
	
	public void deleteEmployeeByDept(String dept) {
		employeeRepository.deleteByDept(dept);
	}
	
	public void deleteByDeptAndGender(String dept, String gender) {
		employeeRepository.deleteByDeptAndGender(dept, gender);
	}
	
	public void deleteByEmployeeSalaryLessThan(double salary) {
		employeeRepository.deleteBySalaryLessThan(salary);
	}
	
	public void deleteByEmployeeSalaryGreaterThan(double salary) {
		employeeRepository.deleteBySalaryGreaterThan(salary);
	}
	
	public Employee updateEmployeeById(Long id, Employee employee) {
		Optional<Employee> optionalEmp = employeeRepository.findById(id);
		if(optionalEmp.isPresent()) {
			Employee emp = optionalEmp.get();
			emp.setName(employee.getName());
			emp.setSalary(employee.getSalary());
			emp.setDept(employee.getDept());
			emp.setEmail(employee.getEmail());
			emp.setGender(employee.getGender());
			
			return employeeRepository.save(emp);
		}else {
			throw new ResourceNotFoundException("employee with id"+ id + " not found"); 
		}
	}
	public Employee partialUpdateEmployee(Long id, Map<String, Object> newEmployee) {
		Optional<Employee> optEmp = employeeRepository.findById(id);
		
		if(optEmp.isPresent()) {
			Employee existingEmp = optEmp.get();
			if(newEmployee.containsKey("name")) {
				existingEmp.setName((String) newEmployee.get("name"));
			}
			if(newEmployee.containsKey("salary")) {
				existingEmp.setSalary((Double) newEmployee.get("salary"));
			}
			
			if(newEmployee.containsKey("dept")) {
				existingEmp.setDept((String) newEmployee.get("dept"));
			}
			
			if(newEmployee.containsKey("gender")) {
				existingEmp.setEmail((String) newEmployee.get("gender"));
			}
			
			if(newEmployee.containsKey("email")) {
				existingEmp.setEmail((String) newEmployee.get("email"));
			}
			
			return employeeRepository.save(existingEmp);
		}else {
			throw new ResourceNotFoundException("Employee with ID " + id + " not found");
		}
	}
}