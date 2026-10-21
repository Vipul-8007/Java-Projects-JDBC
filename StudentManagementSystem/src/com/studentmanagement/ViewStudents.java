
package com.studentmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewStudents {

	public static void main(String[] args) {
		displayStudents();
	}

	public static void displayStudents() {

		String query = "SELECT * FROM students";

		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			System.out.println("Student Records :");
			System.out.println("------------------------------");

			while (resultSet.next()) {

				int id = resultSet.getInt("student_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String phone = resultSet.getString("phone");

				System.out.println("ID: " + id);
				System.out.println("Name: " + firstName + " " + lastName);
				System.out.println("Email: " + email);
				System.out.println("Phone: " + phone);
				System.out.println("------------------------------");
			}

		} catch (Exception e) {
			System.out.println("Unable to retrieve student records");
			e.printStackTrace();
		}
	}
}
