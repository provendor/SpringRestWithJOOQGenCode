package org.database.cubrid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CubridDBAccess {

	public static Connection connection = null;
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public static Connection getConnection() {
		if(connection==null) {
			try {
			
				Class.forName("cubrid.jdbc.driver.CUBRIDDriver");
				connection = DriverManager.getConnection("jdbc:cubrid:localhost:30000:demodb:::?charset=UTF-8", "dba", "");
				connection.setAutoCommit(false);
				connection = getConnection();
				stmt = connection.createStatement();
				
			} catch (Exception e) {
				System.err.println("SQLException : " + e.getMessage());
			}
		}
		return connection;
	}

	@SuppressWarnings("unchecked")
	public static String convertResultSetToJson(ResultSet resultSet) throws SQLException
	{
	    JSONArray json = new JSONArray();
	    ResultSetMetaData metadata = resultSet.getMetaData();
	    int numColumns = metadata.getColumnCount();

	    while(resultSet.next())             //iterate rows
	    {
	        JSONObject obj = new JSONObject();      //extends HashMap
	        for (int i = 1; i <= numColumns; ++i)           //iterate columns
	        {
	            String column_name = metadata.getColumnName(i);
	            obj.put(column_name, resultSet.getObject(column_name));
	        }
	        json.add(obj);
	    }
	    return json.toJSONString();
	}
	
	public static String getCompleteTables() throws SQLException {
		rs = stmt.executeQuery("select class_name from _db_class");
		return convertResultSetToJson(rs);
	}
	
	public static String getCompleteTableData(String tableName) throws SQLException {
		rs = stmt.executeQuery("select * from "+tableName);
		return convertResultSetToJson(rs);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		stmt.close();
		conn.close();
	}
}