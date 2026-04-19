package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemsDBModel;

public class MapJobProblemsDao {

	private MapJobProblemsDao() {

	}

	private static final String PROBLEMS_QUERY = """
			select * from map_job_problem where tr_job_head_id = ?
			""";

	public static MapJobProblemsDBModel getProblemInfoFromDB(int tr_job_head_id) {

		Connection conn;
		PreparedStatement preparedStatement;
		MapJobProblemsDBModel problemsDBModel = null;

		try {
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(PROBLEMS_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				problemsDBModel = new MapJobProblemsDBModel(resultSet.getInt("id"), resultSet.getInt("tr_job_head_id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return problemsDBModel;
	}

}
