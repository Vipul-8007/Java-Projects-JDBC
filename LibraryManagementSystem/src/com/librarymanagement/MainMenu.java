package com.librarymanagement;

import java.util.Scanner;

public class MainMenu {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int choice;

		do {
			System.out.println("\n----- Library Management System -----");
			
			System.out.println("\n1. Add Book");
			System.out.println("2. Search Book");
			System.out.println("3. Issue Book");
			System.out.println("4. Return Book");
			System.out.println("5. Fine Calculation");
			System.out.println("6. View All Books");
			System.out.println("0. Exit");
			System.out.print("\nEnter your choice : ");
			choice = sc.nextInt();
			
			System.out.println();			

			switch (choice) {

			case 1:
				AddBook.addBook();
				break;

			case 2:
				SearchBook.searchBook();
				break;

			case 3:
				IssueBook.issueBook();
				break;

			case 4:
				ReturnBook.returnBook();
				break;

			case 5:
				FineCalculation.calculateFine();
				break;

			case 6:
				ViewAllBooks.viewAllBooks();
				break;

			case 0:
				System.out.println("Exiting Library Management System.");
				break;

			default:
				System.out.println("Invalid choice. Please try again.");
			}

		} while (choice != 0);

		sc.close();
	}
}
