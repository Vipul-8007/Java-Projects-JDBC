package com.studentmanagement;

import java.util.Scanner;

public class MainMenu {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {

			System.out.println("\n----- STUDENT MANAGEMENT SYSTEM -----");
			System.out.println("1. View Students");
			System.out.println("2. Add Student");
			System.out.println("3. Update Student");
			System.out.println("4. Delete Student");
			System.out.println("5. Enroll Student in Course");
			System.out.println("6. Mark Attendance");
			System.out.println("7. Add Marks");
			System.out.println("8. Generate Student Result");
			System.out.println("0. Exit");

			System.out.print("Enter your choice: ");

			try {
				choice = Integer.parseInt(scanner.nextLine());

				switch (choice) {
				case 1:
					ViewStudents.displayStudents();
					break;
				case 2:
					AddStudent.addStudent();
					break;
				case 3:
					UpdateStudent.updateStudent();
					break;
				case 4:
					DeleteStudent.deleteStudent();
					break;
				case 5:
					EnrollStudent.enrollStudent();
					break;
				case 6:
					MarkAttendance.markAttendance();
					break;
				case 7:
					AddMarks.addMarks();
					break;
				case 8:
					GenerateResult.generateResult();
					break;
				case 0:
					System.out.println("Exiting Student Management System");
					break;
				default:
					System.out.println("Invalid choice \nPlease try again");
				}

			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number");
			}
		}

		scanner.close();
	}
}
