package com.librarymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAllBooks {

    public static void viewAllBooks() {

        String sql = "SELECT * FROM books";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Book ID	: " + rs.getInt("book_id"));
                System.out.println("Title	: " + rs.getString("title"));
                System.out.println("Author	: " + rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Total Copies: " + rs.getInt("total_copies"));
                System.out.println("Available Copies: "
                        + rs.getInt("available_copies"));
                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println("No books found");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying books");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        viewAllBooks();
    }
}