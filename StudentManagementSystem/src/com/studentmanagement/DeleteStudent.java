
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudent {

	public static void main(String[] args) {
		deleteStudent();
	}

	public static void deleteStudent() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID to delete : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		try (Connection connection = DBConnection.getConnection()) {

			connection.setAutoCommit(false);

			try {
				deleteRecords(connection, "DELETE FROM enrollments WHERE student_id = ?", studentId);

				deleteRecords(connection, "DELETE FROM attendance WHERE student_id = ?", studentId);

				deleteRecords(connection, "DELETE FROM marks WHERE student_id = ?", studentId);

				int rowsDeleted = deleteRecords(connection, "DELETE FROM students WHERE student_id = ?", studentId);

				if (rowsDeleted > 0) {
					connection.commit();
					System.out.println("Student and related records deleted successfully!");
				} else {
					connection.rollback();
					System.out.println("Student ID not found");
				}

			} catch (SQLException e) {
				connection.rollback();
				System.out.println("Deletion failed \nAll changes were rolled back");
				e.printStackTrace();
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Database connection failed");
			e.printStackTrace();
		}
	}

	private static int deleteRecords(Connection connection, String query, int studentId) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, studentId);
			return statement.executeUpdate();
		}
	}
}
