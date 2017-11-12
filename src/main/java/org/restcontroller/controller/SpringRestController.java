package org.restcontroller.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import provendordb.connection.CUBRIDDBConnection;
import provendordb.tables.Provuser;

@RestController
public class SpringRestController {
	
	@RequestMapping(value = "/Users", method = RequestMethod.GET,headers="Accept=application/json")
	public String getCompleteTables() throws SQLException
	{
		DSLContext dbConn = CUBRIDDBConnection.getConnection();
		Result<Record3<Integer, String, String>> recordsList = dbConn.select(Provuser.PROVUSER.PROVUSERID, Provuser.PROVUSER.PROVUSERFIRSTNAME, Provuser.PROVUSER.PROVUSERLASTNAME)
					.from(Provuser.PROVUSER).fetch();
		return convertResultSetToJson(recordsList.intoResultSet());
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
}
