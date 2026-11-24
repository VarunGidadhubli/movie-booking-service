
package com.movie.booking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

	private static final String DB_URL = "jdbc:mysql://localhost:3307/movie_booking";

	private static final String DB_USERNAME = System.getProperty("username", System.getenv("DB_USERNAME"));

	private static final String DB_PASSWORD = System.getProperty("password", System.getenv("DB_PASSWORD"));

	// Establish database connection
	public static Connection getConnection() throws SQLException {

		if (DB_USERNAME == null || DB_PASSWORD == null) {
			throw new IllegalStateException("Database username or password is not configured");
		}

		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	// Retrieve role name using its generated ID
	public static String getRoleNameById(Long roleId) throws SQLException {

		String query = "SELECT name FROM roles WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setLong(1, roleId);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {
					return resultSet.getString("name");
				}

				return null;
			}
		}
	}

	// Check whether a role exists
	public static boolean roleExists(Long roleId) throws SQLException {

		String query = "SELECT 1 FROM roles WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setLong(1, roleId);

			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next();
			}
		}
	}
}