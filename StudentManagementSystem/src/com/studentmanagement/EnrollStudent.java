
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class EnrollStudent {

	public static void main(String[] args) {
		enrollStudent();
	}

	public static void enrollStudent() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter course ID : ");
		int courseId = Integer.parseInt(scanner.nextLine());

		String studentQuery = "SELECT student_id FROM students WHERE student_id = ?";

		String courseQuery = "SELECT course_id FROM courses WHERE course_id = ?";

		String enrollmentQuery = "INSERT INTO enrollments " + "(student_id, course_id, enrollment_date) "
				+ "VALUES (?, ?, ?)";

		try (Connection connection = DBConnection.getConnection()) {

			// Check the student exists
			try (PreparedStatement statement = connection.prepareStatement(studentQuery)) {

				statement.setInt(1, studentId);

				try (ResultSet result = statement.executeQuery()) {
					if (!result.next()) {
						System.out.println("Student ID not found");
						return;
					}
				}
			}

			// Check the course exists
			try (PreparedStatement statement = connection.prepareStatement(courseQuery)) {

				statement.setInt(1, courseId);

				try (ResultSet result = statement.executeQuery()) {
					if (!result.next()) {
						System.out.println("Course ID not found");
						return;
					}
				}
			}

			// Insert the enrollment
			try (PreparedStatement statement = connection.prepareStatement(enrollmentQuery)) {

				statement.setInt(1, studentId);
				statement.setInt(2, courseId);
				statement.setDate(3, new Date(System.currentTimeMillis()));

				int rowsInserted = statement.executeUpdate();

				if (rowsInserted > 0) {
					System.out.println("Student enrolled successfully!");
				}
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to enroll student");
			e.printStackTrace();
		}
	}
}
