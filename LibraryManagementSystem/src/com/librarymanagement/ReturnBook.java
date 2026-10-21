
package com.librarymanagement;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ReturnBook {

	public static void returnBook() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Issue ID : ");
		int issueId = sc.nextInt();

		System.out.print("Enter return date (YYYY-MM-DD) : ");
		String returnDate = sc.next();

		String sql = "SELECT * FROM book_issues WHERE issue_id = ?";

		try (Connection con = DBConnection.getConnection()) {

			con.setAutoCommit(false);

			try {
				int bookId;
				Date dueDate;

				// Find the issue record
				try (PreparedStatement ps = con.prepareStatement(sql)) {

					ps.setInt(1, issueId);

					try (ResultSet rs = ps.executeQuery()) {

						if (!rs.next()) {
							System.out.println("Issue record not found");
							con.rollback();
							return;
						}

						bookId = rs.getInt("book_id");
						dueDate = rs.getDate("due_date");

						Date existingReturnDate = rs.getDate("return_date");

						if (existingReturnDate != null) {
							System.out.println("This book has already been returned");
							con.rollback();
							return;
						}
					}
				}

				// Calculate the fine
				Date actualReturnDate = Date.valueOf(returnDate);

				long difference = actualReturnDate.getTime() - dueDate.getTime();

				long lateDays = difference / (1000 * 60 * 60 * 24);

				double fine = 0;

				if (lateDays > 0) {
					fine = lateDays * 10;
				}

				System.out.println("Late days: " + Math.max(lateDays, 0));
				System.out.println("Fine amount : " + fine);

				// Update the issue record
				String updateSql = "UPDATE book_issues " + "SET return_date = ?, fine_amount = ? "
						+ "WHERE issue_id = ? AND return_date IS NULL";

				try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {

					updatePs.setDate(1, actualReturnDate);
					updatePs.setDouble(2, fine);
					updatePs.setInt(3, issueId);

					int rows = updatePs.executeUpdate();

					if (rows != 1) {
						throw new SQLException("Issue record could not be updated");
					}
				}

				// Increase available copies
				String bookSql = "UPDATE books " + "SET available_copies = available_copies + 1 " + "WHERE book_id = ?";

				try (PreparedStatement bookPs = con.prepareStatement(bookSql)) {

					bookPs.setInt(1, bookId);

					int rows = bookPs.executeUpdate();

					if (rows != 1) {
						throw new SQLException("Book availability could not be updated");
					}
				}

				// Save both changes together
				con.commit();

				System.out.println("Book returned successfully!");

			} catch (SQLException | IllegalArgumentException e) {

				con.rollback();

				System.out.println("Return failed : " + e.getMessage());
			}

		} catch (SQLException e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		returnBook();
	}
}
