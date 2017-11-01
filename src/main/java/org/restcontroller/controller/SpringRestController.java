package org.restcontroller.controller;

import java.sql.SQLException;
import org.database.cubrid.CubridDBAccess;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRestController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tables", method = RequestMethod.GET,headers="Accept=application/json")
	public String getCompleteTables() throws SQLException
	{
		CubridDBAccess.getConnection();
		return CubridDBAccess.getCompleteTables();
	}
	
	@RequestMapping(value = "/table/{tableName}", method = RequestMethod.GET,headers="Accept=application/json")
	public String getTableData(@PathVariable String tableName) throws SQLException
	{
		CubridDBAccess.getConnection();
		return CubridDBAccess.getCompleteTableData(tableName);
	}

}
