
package com.librarymanagement;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchBook {

	public static void searchBook() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter book title to search: ");
		String title = sc.nextLine();

		String sql = "SELECT * FROM books WHERE title = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, title);

			try (ResultSet rs = ps.executeQuery()) {

				boolean found = false;

				while (rs.next()) {
					found = true;

					System.out.println();
					System.out.println("Book ID	: " + rs.getInt("book_id"));
					System.out.println("Title	: " + rs.getString("title"));
					System.out.println("Author	: " + rs.getString("author"));
					System.out.println("Category: " + rs.getString("category"));
					System.out.println("Available Copies: " + rs.getInt("available_copies"));
					System.out.println("----------------------------");
				}

				if (!found) {
					System.out.println("Book not found");
				}
			}

		} catch (SQLException e) {
			System.out.println("Error searching for book");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		searchBook();
	}
}
