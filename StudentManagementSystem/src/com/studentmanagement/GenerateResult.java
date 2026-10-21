
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GenerateResult {

	public static void main(String[] args) {
		generateResult();
	}

	public static void generateResult() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		String query = "SELECT students.first_name, students.last_name, "
				+ "courses.course_name, marks.marks_obtained, " + "marks.total_marks, "
				+ "(marks.marks_obtained * 100.0 / marks.total_marks) " + "AS percentage " + "FROM students "
				+ "INNER JOIN marks " + "ON students.student_id = marks.student_id " + "INNER JOIN courses "
				+ "ON marks.course_id = courses.course_id " + "WHERE students.student_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, studentId);

			try (ResultSet result = statement.executeQuery()) {

				if (!result.isBeforeFirst()) {
					System.out.println("No marks found for this student");
					return;
				}

				while (result.next()) {

					String firstName = result.getString("first_name");
					String lastName = result.getString("last_name");
					String courseName = result.getString("course_name");

					int marksObtained = result.getInt("marks_obtained");

					int totalMarks = result.getInt("total_marks");
					double percentage = result.getDouble("percentage");

					String status = percentage >= 35 ? "Pass" : "Fail";

					System.out.println("------------------------------");
					System.out.println("Student: " + firstName + " " + lastName);
					System.out.println("Course: " + courseName);
					System.out.println("Marks: " + marksObtained + "/" + totalMarks);
					System.out.printf("Percentage: %.2f%%%n", percentage);
					System.out.println("Result: " + status);
					System.out.println("------------------------------");
				}
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to generate student result");
			e.printStackTrace();
		}
	}
}
