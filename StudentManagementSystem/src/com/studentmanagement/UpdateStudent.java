
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateStudent {

	public static void main(String[] args) {
		updateStudent();
	}

	public static void updateStudent() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter new phone number : ");
		String phone = scanner.nextLine();

		String query = "UPDATE students SET phone = ? WHERE student_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, phone);
			statement.setInt(2, studentId);

			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Student updated successfully!");
			} else {
				System.out.println("Student ID not found");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to update student");
			e.printStackTrace();
		}
	}
}
