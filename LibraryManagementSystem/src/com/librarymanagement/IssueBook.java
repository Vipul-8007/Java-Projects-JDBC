
package com.librarymanagement;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class IssueBook {

	public static void issueBook() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Book ID : ");
		int bookId = sc.nextInt();

		System.out.print("Enter Member ID : ");
		int memberId = sc.nextInt();

		System.out.print("Enter issue date (YYYY-MM-DD) : ");
		String issueDate = sc.next();

		System.out.print("Enter due date (YYYY-MM-DD) : ");
		String dueDate = sc.next();

		String bookSql = "SELECT available_copies FROM books WHERE book_id = ?";

		String memberSql = "SELECT member_id FROM members WHERE member_id = ?";

		String issueSql = "INSERT INTO book_issues " + "(book_id, member_id, issue_date, due_date) "
				+ "VALUES (?, ?, ?, ?)";

		String updateSql = "UPDATE books " + "SET available_copies = available_copies - 1 " + "WHERE book_id = ?";

		try (Connection con = DBConnection.getConnection()) {

			con.setAutoCommit(false);

			try {
				// Check whether the member exists
				try (PreparedStatement memberPs = con.prepareStatement(memberSql)) {

					memberPs.setInt(1, memberId);

					try (ResultSet memberRs = memberPs.executeQuery()) {
						if (!memberRs.next()) {
							throw new SQLException("Member not found");
						}
					}
				}

				// Check whether the book exists and is available
				try (PreparedStatement bookPs = con.prepareStatement(bookSql)) {

					bookPs.setInt(1, bookId);

					try (ResultSet bookRs = bookPs.executeQuery()) {

						if (!bookRs.next()) {
							throw new SQLException("Book not found");
						}

						int availableCopies = bookRs.getInt("available_copies");

						if (availableCopies <= 0) {
							throw new SQLException("No copies available");
						}
					}
				}

				// Insert the issue record
				try (PreparedStatement issuePs = con.prepareStatement(issueSql)) {

					issuePs.setInt(1, bookId);
					issuePs.setInt(2, memberId);
					issuePs.setDate(3, Date.valueOf(issueDate));
					issuePs.setDate(4, Date.valueOf(dueDate));

					issuePs.executeUpdate();
				}

				// Reduce the available-copy count
				try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {

					updatePs.setInt(1, bookId);
					updatePs.executeUpdate();
				}

				// Save both changes together
				con.commit();

				System.out.println("Book issued successfully!");

			} catch (SQLException | IllegalArgumentException e) {

				con.rollback();

				System.out.println("Book issue failed : " + e.getMessage());
			}

		} catch (SQLException e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		issueBook();
	}
}
