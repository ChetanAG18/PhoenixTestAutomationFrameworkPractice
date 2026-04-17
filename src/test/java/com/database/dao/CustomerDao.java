package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAILS_QUERY = """
			select * from tr_customer where id =
			""";

	public static CustomerDBModel getCustomerInfo(int customerId) throws SQLException {
		Connection conn = DatabaseManager.getConnection();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(CUSTOMER_DETAILS_QUERY + customerId);
		CustomerDBModel customerDBModel = null;

		while (resultSet.next()) {
			customerDBModel = new CustomerDBModel(resultSet.getString("first_name"), resultSet.getString("last_name"),
					resultSet.getString("mobile_number"), resultSet.getString("mobile_number_alt"),
					resultSet.getString("email_id"), resultSet.getString("email_id_alt"));
		}

		return customerDBModel;
	}

}
