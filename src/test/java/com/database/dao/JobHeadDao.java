package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.JobHeadDBModel;

public class JobHeadDao {

	private JobHeadDao() {

	}

	private static final String JOB_HEAD_QUERY = """
			select * from tr_job_head where tr_customer_id = ?
			""";

	public static JobHeadDBModel getJobHeadDataFromDB(int tr_customer_id) {
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		JobHeadDBModel jobHeadDataFromDB = null;
		try {
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(JOB_HEAD_QUERY);
			preparedStatement.setInt(1, tr_customer_id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jobHeadDataFromDB = new JobHeadDBModel(resultSet.getInt("id"), resultSet.getString("job_number"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("tr_customer_product_id"),
						resultSet.getInt("mst_service_location_id"), resultSet.getInt("mst_platform_id"),
						resultSet.getInt("mst_warrenty_status_id"), resultSet.getInt("mst_oem_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jobHeadDataFromDB;
	}
}
