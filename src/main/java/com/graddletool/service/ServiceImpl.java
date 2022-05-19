package com.graddletool.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
//import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.graddletool.dao.DbConnection;
import com.graddletool.exception.CustomException;
import com.graddletool.exception.CustomExceptionHandler;
import com.graddletool.model.Employee;

@Singleton
public class ServiceImpl implements Service {

	// public DbConnection dbConnection = new DbConnection();

	@Inject
	public DbConnection dbConnection;

	@Inject
	private CustomExceptionHandler customExceptionHandler;

	ArrayList<Employee> al = new ArrayList<>();

	@Override
	public Connection doConnection() {
		this.dbConnection.connect();
		return null;
	}

	@Override
	public void postEmpData(Employee employee) throws SQLException {
		Connection conn = dbConnection.connect();
		String query = "SET IDENTITY_INSERT EmpData ON INSERT INTO EmpData (empId,empName,empAddress,designation,salary) values (?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setInt(1, employee.getEmpId());
		pstmt.setString(2, employee.getEmpName());
		pstmt.setString(3, employee.getEmpAddress());
		pstmt.setString(4, employee.getDesignation());
		pstmt.setLong(5, employee.getSalary());

		pstmt.execute();

		pstmt.close();

	}

	@Override
	public ArrayList<Employee> getEmpInfo() throws SQLException {
		Connection conn = dbConnection.connect();

		String query = "Select *from EmpData";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmpId(rs.getInt(1));
			emp.setEmpName(rs.getString(2));
			emp.setEmpAddress(rs.getString(3));
			emp.setDesignation(rs.getString(4));
			emp.setSalary(rs.getLong(5));

			al.add(emp);
		}

		return al;
	}

	@Override
	public Employee dataByEmpId(int id) throws SQLException, CustomException {
		Employee emp = new Employee();

		Connection conn = dbConnection.connect();
		String query = "select * FROM EmpData where empId =" + id;

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		if ((rs.next())) {

			emp.setEmpId(rs.getInt(1));
			emp.setEmpName(rs.getString(2));
			emp.setEmpAddress(rs.getString(3));
			emp.setDesignation(rs.getString(4));
			emp.setSalary(rs.getLong(5));
			al.add(emp);

		}

		return al.stream().filter(e -> e.getEmpId() == id).findFirst()
				.orElseThrow(() -> new CustomException("Employee", "empId", id));

	}

	@Override
	public Employee updateEmpData(Employee employee, int id) throws SQLException {

		Employee emp = new Employee();
		Connection conn = dbConnection.connect();

		List<Employee> li = new ArrayList<Employee>();

		String query = "select * FROM EmpData where empId =" + id;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		boolean data = false;

		while (rs.next()) {

			emp.setEmpId(rs.getInt(1));
			emp.setEmpName(rs.getString(2));
			emp.setEmpAddress(rs.getString(3));
			emp.setDesignation(rs.getString(4));
			emp.setSalary(rs.getLong(5));
			data = li.add(emp);
		}

		if (data) {

			String sql = "UPDATE EmpData SET empName=?, empAddress=?, designation=?, salary=? WHERE empId=?";
			PreparedStatement ptmt = conn.prepareStatement(sql);

			ptmt.setString(1, employee.getEmpName());
			ptmt.setString(2, employee.getEmpAddress());
			ptmt.setString(3, employee.getDesignation());
			ptmt.setLong(4, employee.getSalary());
			ptmt.setInt(5, employee.getEmpId());

			ptmt.executeUpdate();

		} else {
			throw (new CustomException("Empployee", "empId", id));
		}

		return employee;

	}

	@Override
	public void deleteEmpData(int id) throws SQLException {

		Connection conn = dbConnection.connect();
		String query = "Delete from EmpData where EmpId=?";
		PreparedStatement ptmt = conn.prepareStatement(query);

		ptmt.setInt(1, id);
		ptmt.execute();

	}

}
