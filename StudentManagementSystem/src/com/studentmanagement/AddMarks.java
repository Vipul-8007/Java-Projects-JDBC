
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMarks {

	public static void main(String[] args) {
		addMarks();
	}

	public static void addMarks() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter course ID : ");
		int courseId = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter marks obtained : ");
		int marksObtained = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter total marks : ");
		int totalMarks = Integer.parseInt(scanner.nextLine());

		if (totalMarks <= 0 || marksObtained < 0 || marksObtained > totalMarks) {

			System.out.println("Invalid marks /nPlease check your input");
			return;
		}

		String query = "INSERT INTO marks " + "(student_id, course_id, marks_obtained, total_marks) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.setInt(3, marksObtained);
			statement.setInt(4, totalMarks);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Student marks added successfully!");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to add student marks");
			e.printStackTrace();
		}
	}
}
