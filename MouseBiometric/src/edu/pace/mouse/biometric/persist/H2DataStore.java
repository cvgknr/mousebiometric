package edu.pace.mouse.biometric.persist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pace.mouse.biometric.core.FeatureResult;

public class H2DataStore implements DataStore {
	private static final int NO_ENTRY_IN_USER_TABLE=-1;

	@Override
	public void saveFeatures(ArrayList<FeatureResult[]> frs,String firstName,String lastName) {
		validate(frs, firstName, lastName);
		Connection conn = null;
		Statement stmt =null;
		try {
			conn = PersistentStore.getConnection();

			// Assume a valid connection object conn
			conn.setAutoCommit(false);
			//get user id for this user
			int userId=getUserId(conn,firstName,lastName);
			stmt = conn.createStatement();
			for(FeatureResult[] fr:frs){
				for (int i = 0; i < fr.length; i++) {
					stmt.executeUpdate(createFeatureInsertSql(fr[i], userId));
				}
			}
			conn.commit();
			System.out.println("COMMITTED!!!");
		} catch (Exception se) {
			// If there is any error.
			try {
				if(conn!=null){
					conn.rollback();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.printStackTrace();
			throw new RuntimeException("Error in saveFeatures:"+se.getMessage());
		} finally {
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException ignore) {
					// TODO Auto-generated catch block
					ignore.printStackTrace();
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public void clearDataForThisUser(String firstName,String lastName){
		validate( firstName, lastName);
		Connection conn = null;
		Statement stmt =null;
		try {
			conn = PersistentStore.getConnection();

			// Assume a valid connection object conn
			conn.setAutoCommit(false);
			//get user id for this user
			int userId=getUserId(conn,firstName,lastName);
			stmt = conn.createStatement();
			
			stmt.executeUpdate("delete from ms_feature where user_id="+userId);
			
			conn.commit();
			System.out.println("COMMITTED!!!");
		} catch (Exception se) {
			// If there is any error.
			try {
				if(conn!=null){
					conn.rollback();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.printStackTrace();
			throw new RuntimeException("Error in clearDataForUser:"+se.getMessage());
		} finally {
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException ignore) {
					// TODO Auto-generated catch block
					ignore.printStackTrace();
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	//=======private====================
	private void validate(ArrayList<FeatureResult[]> frs,String firstName,String lastName){
		if(frs==null){
			throw new RuntimeException("Feature Results cannot be null");
		}
		validate(firstName, lastName);
	}
	private void validate(String firstName,String lastName){
		
		if(firstName==null){
			throw new RuntimeException("First Name cannot be null");
		}
		if(lastName==null){
			throw new RuntimeException("Last Name cannot be null");
		}
	}
	private int getUserId(Connection conn,String firstName,String lastName) {
		int userId=NO_ENTRY_IN_USER_TABLE;
		Statement stmt=null;
		ResultSet rs=null;
		try{
		
		stmt = conn.createStatement();
		rs=stmt.executeQuery(createSelectUserIdSql(firstName, lastName));
		if(rs.next()){
			userId=rs.getInt("id");
		}else{
			stmt.executeUpdate(createUserInsertSql(firstName, lastName));
			rs=stmt.executeQuery(createCurrSeqNumSql());
			if(rs.next()){
				userId=rs.getInt(1);
				if(userId<=0){
					throw new RuntimeException("Unable to get a user id from database");
				}
			}
			
		}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Error in getUserId():"+ex.getMessage());
		}finally{
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException ignore) {
					// TODO Auto-generated catch block
					ignore.printStackTrace();
				}
			}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException ignore) {
					// TODO Auto-generated catch block
					ignore.printStackTrace();
				}
			}
		}
		return userId;
	}
	
	
	
	private String createFeatureInsertSql(FeatureResult fr,int userId){
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO MS_FEATURE(id,value,classname,unit,label,user_id)");
		sb.append("values((SELECT NEXTVAL('MS_SEQ')),"+qt(fr.getValue())+","+qt(fr.getClassName())+","+qt(fr.getUnit())+","+qt(fr.getLabel())+","+userId+")");
		System.out.println(sb);
		return sb.toString();
	}
	private String createUserInsertSql(String firstName,String lastName){
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO MS_USER(id,first_name,last_name)");
		sb.append("values((SELECT NEXTVAL('MS_SEQ')),"+qt(firstName)+","+qt(lastName)+")");
		System.out.println(sb);
		return sb.toString();
	}
	private String createSelectUserIdSql(String firstName,String lastName){
		StringBuilder sb=new StringBuilder();
		sb.append("select id from MS_USER where UPPER(first_name)="+qt(firstName.toUpperCase()));
		sb.append(" and UPPER(last_name)="+qt(lastName.toUpperCase()));
		System.out.println(sb);
		return sb.toString();
	}
	private String createCurrSeqNumSql(){
		StringBuilder sb=new StringBuilder();
		sb.append("select currval('MS_SEQ')");
		
		System.out.println(sb);
		return sb.toString();
	}
	
	private String qt(String x){
		return "'"+x+"'";
	}

}
