
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class MarkAttendance {

	public static void main(String[] args) {
		markAttendance();
	}

	public static void markAttendance() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID : ");
		int studentId = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter attendance status (Present/Absent) : ");
		String status = scanner.nextLine().trim();

		if (!status.equalsIgnoreCase("Present") && !status.equalsIgnoreCase("Absent")) {

			System.out.println("Invalid status \nEnter Present or Absent");
			return;
		}

		String query = "INSERT INTO attendance " + "(student_id, attendance_date, status) " + "VALUES (?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, studentId);
			statement.setDate(2, new Date(System.currentTimeMillis()));
			statement.setString(3, status);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Attendance marked successfully!");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Unable to mark attendance");
			e.printStackTrace();
		}
	}
}
