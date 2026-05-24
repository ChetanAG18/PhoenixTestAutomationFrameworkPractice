package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.JobHeadDBModel;

import io.qameta.allure.Step;

public class JobHeadDao {
	
	private static final Logger LOGGER = LogManager.getLogger(JobHeadDao.class);

	private JobHeadDao() {

	}

	private static final String JOB_HEAD_QUERY = """
			select * from tr_job_head where tr_customer_id = ?
			""";

	@Step("Retrieving the job head information from database for the specific customer id")
	public static JobHeadDBModel getJobHeadDataFromDB(int tr_customer_id) {
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		JobHeadDBModel jobHeadDataFromDB = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(JOB_HEAD_QUERY);
			preparedStatement.setInt(1, tr_customer_id);
			LOGGER.info("Executing the SQL Query {}", JOB_HEAD_QUERY);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jobHeadDataFromDB = new JobHeadDBModel(resultSet.getInt("id"), resultSet.getString("job_number"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("tr_customer_product_id"),
						resultSet.getInt("mst_service_location_id"), resultSet.getInt("mst_platform_id"),
						resultSet.getInt("mst_warrenty_status_id"), resultSet.getInt("mst_oem_id"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the Result Set to the JobHeadDBModel", e);
			e.printStackTrace();
		}

		return jobHeadDataFromDB;
	}
}
