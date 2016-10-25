package com.ait.grindsregister;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SubjectDAO {
	private Connection conn;
	public List<Subject> findAllSubjects() {
		List<Subject> list = new ArrayList<Subject>();
		conn = null;
		String sql = "SELECT * FROM subjects ORDER BY subject";
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processSubject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return list;
	}
	
	public Subject findById(int id){
		Subject subject = null;
		conn = null;
		String sql = "SELECT * FROM subjects WHERE sub_id = "+id;
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				subject = processSubject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return subject;
	}
	
	protected Subject processSubject(ResultSet rs) throws SQLException {
		Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("sub_Id"));
        subject.setSubject(rs.getString("subject"));
        return subject;
    }
}
