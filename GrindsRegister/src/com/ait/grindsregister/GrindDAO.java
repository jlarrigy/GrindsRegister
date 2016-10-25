package com.ait.grindsregister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GrindDAO {
	private Connection conn;
	
	public List<Grind> findById(int id){
		List<Grind> list = new ArrayList<Grind>();
		conn = null;
		String sql = "SELECT grinds.g_id, grinds.tut_id, grinds.sub_id, tutor.tut_name, tutor.email, tutor.phone, grinds.price, grinds.description" +
					 " FROM grinds INNER JOIN tutor" +
					 " ON grinds.tut_id=tutor.tut_id AND sub_id = "+id;
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				list.add(processGrind(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return list;
	}// end findbyid
	
	
	public int getTutorId(int grindId){
		int tutorId = -1;
		conn = null;
		String sql = "SELECT tut_id FROM grinds WHERE g_id = "+grindId;
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				tutorId = rs.getInt(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return tutorId;
	}// end getTutorId
	
	public void saveGrind(Grind grind) {
		String sql = "INSERT INTO grinds (tut_id,sub_id,price,description)"+
					 "VALUES(?,?,?,?)";
		try{
			conn = ConnectionHelper.getConnection();
    		PreparedStatement ps = conn.prepareStatement(sql);
    		//ps.setInt(1, grind.getGrindId());
    		ps.setInt(1, grind.getTutorId());
    		ps.setInt(2, grind.getSubjectId());
    		ps.setString(3, grind.getPrice());
    		ps.setString(4, grind.getDescription());
    		
    		boolean rs = ps.execute();
    		//print this
    		System.out.println(ps.getUpdateCount());
    		System.out.println(rs);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
	}//end savegrind
	
	public List<Grind> findByTutorId(int id){
		List<Grind> list = new ArrayList<Grind>();
		conn = null;
		String sql = "SELECT grinds.g_id, grinds.tut_id, grinds.sub_id, tutor.tut_name, tutor.email, tutor.phone, grinds.price, grinds.description" +
					 " FROM grinds INNER JOIN tutor" +
					 " ON grinds.tut_id=tutor.tut_id AND grinds.tut_id="+id;
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				list.add(processGrind(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return list;
	}
	
	public Grind findGrindById(int id){
		Grind grind = null;
		conn = null;
		String sql = "SELECT grinds.g_id, grinds.tut_id, grinds.sub_id, tutor.tut_name, tutor.email, tutor.phone, grinds.price, grinds.description"+
					 " FROM grinds"+
					 " INNER JOIN tutor"+
					 " ON grinds.tut_id=tutor.tut_id AND grinds.g_id="+id; 
		try {
			conn = ConnectionHelper.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				grind = processGrind(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(conn);
		}
		return grind;
	}
	
    public void updateGrind(Grind grind) {
        Connection c = null;
        try {
        	//"UPDATE tutor SET tut_name='"+tutor.getName()+"', email='"+tutor.getEmail()+"', phone='"+tutor.getPhone()+"', username='"+tutor.getUsername()+"', password='"+tutor.getPassword()+"' WHERE tut_id='"+tutor.getId()+"'"
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE grinds SET tut_id=?, sub_id=?, price=?, description=? WHERE g_id=?");
            ps.setInt(1, grind.getTutorId());
            ps.setInt(2, grind.getSubjectId());
            ps.setString(3, grind.getPrice());
            ps.setString(4, grind.getDescription());
            ps.setInt(5, grind.getGrindId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        System.out.println("Grind updated");
    }
    
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM grinds WHERE g_id=?");
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
    
    public boolean removeAllGrinds(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM grinds WHERE tut_id IN(?)");
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
	
	protected Grind processGrind(ResultSet rs) throws SQLException {
		Grind grind = new Grind();
        grind.setGrindId(rs.getInt("g_id"));
        grind.setTutorId(rs.getInt("tut_id"));
        grind.setSubjectId(rs.getInt("sub_id"));
        grind.setTut_name(rs.getString("tut_name"));
        grind.setEmail(rs.getString("email"));
        grind.setPhone(rs.getString("phone"));
        grind.setPrice(rs.getString("price"));
        grind.setDescription(rs.getString("description"));
        return grind;
    }
}
