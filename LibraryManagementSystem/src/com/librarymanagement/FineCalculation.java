package com.librarymanagement;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FineCalculation {

	public static void calculateFine() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Issue ID: ");
		int issueId = sc.nextInt();

		String sql = "SELECT fine_amount FROM book_issues WHERE issue_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, issueId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					double fine = rs.getDouble("fine_amount");
					System.out.println("Fine Amount: ₹" + fine);
				} else {
					System.out.println("Issue record not found.");
				}

			}
		} catch (SQLException e) {
			System.out.println("Error retrieving fine.");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		calculateFine();
	}

}