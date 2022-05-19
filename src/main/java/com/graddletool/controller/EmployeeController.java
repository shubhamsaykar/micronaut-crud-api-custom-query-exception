package com.graddletool.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import com.graddletool.exception.CustomException;
import com.graddletool.exception.CustomExceptionHandler;
import com.graddletool.model.Employee;
import com.graddletool.service.Service;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

@Controller
public class EmployeeController {

	@Inject
	private Service service;

	

//	public EmployeeController(Service service) {
//		this.service = service;
//	}

	

	@Get("/getconn")
	public String dbConnection() {
		this.service.doConnection();
		return "Connected to db";
	}

	@Post("/postdata")
	public Employee insertEmpData(@Body Employee employee) throws SQLException {
		this.service.postEmpData(employee);
		return employee;

	}

	@Get("/getdata")
	public ArrayList<Employee> getAllEmpData() throws SQLException {
		ArrayList<Employee> employees = this.service.getEmpInfo();
		return employees;
	}

	@Get("/getbyid/{id}")
	public HttpResponse<Employee> getEmpDataById(@PathVariable(value = "id") int id) throws SQLException, CustomException {
		
		
		return HttpResponse.ok(this.service.dataByEmpId(id));
		

	}

	@Put("/update/{id}")
	public Employee updatEmpData(@PathVariable(value = "id") int id, @Body Employee employee) throws SQLException {
		this.service.updateEmpData(employee, id);
		return employee;
	}

	@Delete("/delete/{id}")
	public int deleteEmpDataById(@PathVariable(value = "id") int id) throws SQLException {
		this.service.deleteEmpData(id);
		return id;
	}

}
