package com.spring.repository;
import com.spring.entity.Employee;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	public List<Employee> findByName(String name);
	public Optional<Employee> findByEmail(String email);
	public List<Employee> findByDept(String dept);
	public List<Employee> findByGender(String gender);
	public List<Employee> findByDeptAndGender(String dept, String gender);
	public List<Employee> findByDeptOrGender(String dept, String gender);
	public List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
	public List<Employee> findBySalaryGreaterThan(double salary);
	public List<Employee> findBySalaryLessThan(double salary);
	@Transactional
	@Modifying
	public void deleteByEmail(String email);
	@Transactional
	@Modifying
	public void deleteByDept(String dept);
	@Transactional
	@Modifying
	public void deleteByDeptAndGender(String dept, String gender);
	@Transactional
	@Modifying
	public void deleteBySalaryLessThan(double salary);
	@Transactional
	@Modifying
	public void deleteBySalaryGreaterThan(double salary);
	 
}