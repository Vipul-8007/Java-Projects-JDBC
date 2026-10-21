
package com.studentmanagement;

import java.sql.Connection;

public class TestDBConnection {

	public static void main(String[] args) {

		try (Connection connection = DBConnection.getConnection()) {

			if (connection != null && !connection.isClosed()) {
				System.out.println("MySQL database connection successful!");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC driver not found!");
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("Database connection failed!");
			e.printStackTrace();
		}
	}
}
