package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

import io.qameta.allure.Step;

public class CustomerProductDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDao.class);

	private CustomerProductDao() {

	}

	private static final String CUSTOMER_PRODUCT_QUERY = """
			SELECT
				id,
				tr_customer_id,
				mst_model_id,
				dop,
				popurl,
				imei2,
				imei1,
				serial_number
			from tr_customer_product
			where id = ?
			""";

	@Step("Retrieving the customer product data from database for the specific customer product id")
	public static CustomerProductDBModel getCustomerProductInfo(int tr_customer_product) {

		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		CustomerProductDBModel customerProductDBModel = null;

		try {
			LOGGER.info("Getting the connection from the Database Manager");
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			preparedStatement.setInt(1, tr_customer_product);
			LOGGER.info("Executing the SQL Query {}", CUSTOMER_PRODUCT_QUERY);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("popurl"), resultSet.getString("imei2"),
						resultSet.getString("imei1"), resultSet.getString("serial_number"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the Result Set to the CustomerProductDBModel", e);
			e.printStackTrace();
		}

		return customerProductDBModel;

	}

}
