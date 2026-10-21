
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class AddStudent {

	public static void main(String[] args) {
		addStudent();
	}

	public static void addStudent() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter first name : ");
		String firstName = scanner.nextLine();

		System.out.print("Enter last name : ");
		String lastName = scanner.nextLine();

		System.out.print("Enter email : ");
		String email = scanner.nextLine();

		System.out.print("Enter phone : ");
		String phone = scanner.nextLine();

		String query = "INSERT INTO students " + "(first_name, last_name, email, phone, admission_date) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, phone);
			statement.setDate(5, new Date(System.currentTimeMillis()));

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Student added successfully!");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to add student");
			e.printStackTrace();
		}
	}
}
