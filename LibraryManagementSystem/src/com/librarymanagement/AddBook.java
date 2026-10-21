package com.librarymanagement;

import java.util.*;
import java.sql.*;

public class AddBook {
	public static void addBook() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter book title : ");
		String title = sc.nextLine();

		System.out.print("Enter author name : ");
		String author = sc.nextLine();

		System.out.print("Enter category : ");
		String category = sc.nextLine();

		System.out.print("Enter total copies : ");
		int totalCopies = sc.nextInt();

		String sql = "INSERT INTO books " + "(title, author, category, total_copies, available_copies) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, category);
			ps.setInt(4, totalCopies);
			ps.setInt(5, totalCopies);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("/nBook added successfully!");
			}
		} catch (SQLException e) {
			System.out.println("Error adding book.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		addBook();
	}
}
