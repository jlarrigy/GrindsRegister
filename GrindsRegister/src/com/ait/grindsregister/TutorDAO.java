package com.ait.grindsregister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
import java.util.Random;

public class TutorDAO {
	private Connection conn;
	
	public List<Tutor> findAllTutors() {
		List<Tutor> list = new ArrayList<Tutor>();
		conn = null;
		String sql = "SELECT * FROM tutor";
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processTutor(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return list;
	}
	public Tutor findById(int id){
		Tutor tutor = null;
		conn = null;
		String sql = "SELECT * FROM tutor WHERE tut_id = "+id;
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				tutor = processTutor(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return tutor;
	}
	public int getNextId(){
		int id = -1;
		String sql = "SELECT MAX(tut_id) FROM tutor";
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1)+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return id;
	}


	public void saveTutor(Tutor tutor){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!! saveTutor !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!! "+tutor+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO tutor (tut_name, email, phone, username, password) VALUES (?, ?, ?, ?, ?)",
                new String[] { "ID" });
            ps.setString(1, tutor.getName());
            ps.setString(2, tutor.getEmail());
            ps.setString(3, tutor.getPhone());
            ps.setString(4, tutor.getUsername());
            ps.setString(5, tutor.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        
	}
	
    public void updateTutor(Tutor tutor) {
        Connection c = null;
        try {
        	//"UPDATE tutor SET tut_name='"+tutor.getName()+"', email='"+tutor.getEmail()+"', phone='"+tutor.getPhone()+"', username='"+tutor.getUsername()+"', password='"+tutor.getPassword()+"' WHERE tut_id='"+tutor.getId()+"'"
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE tutor SET tut_name=?, email=?, phone=?, username=?, password=? WHERE tut_id=?");
            ps.setString(1, tutor.getName());
            ps.setString(2, tutor.getEmail());
            ps.setString(3, tutor.getPhone());
            ps.setString(4, tutor.getUsername());
            ps.setString(5, tutor.getPassword());
            ps.setInt(6, tutor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        System.out.println("Tutor updated");
    }
    
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM tutor WHERE tut_id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
    }
    
    public void generateTempPassword(Tutor tutor) {
        Connection c = null;
        Random rand = new SecureRandom();
        int passwordLength = 8;
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
        
        String pw = "";
        
        for (int i=0; i<passwordLength; i++)
        {
            int index = (int)(rand.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }
        
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE tutor SET password=? WHERE tut_id=?");
            ps.setString(1, pw);
            ps.setInt(2, tutor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        System.out.println("Tutor updated...Password:" + pw);
    }
    
	protected Tutor processTutor(ResultSet rs) throws SQLException {
		Tutor tutor = new Tutor();
        tutor.setId(rs.getInt("tut_id"));
        tutor.setName(rs.getString("tut_name"));
        tutor.setEmail(rs.getString("email"));
        tutor.setPhone(rs.getString("phone"));
        tutor.setUsername(rs.getString("username"));
        tutor.setPassword(rs.getString("password"));
        return tutor;
    }
}
