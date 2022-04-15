package com.testonline.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseAccess {

	protected Connection connect = null;
	protected PreparedStatement preparedStatement = null;
	protected ResultSet resultSet = null;
	protected String sql = "";
	
	protected void closeAccess() {
		try{
			if (this.connect != null)
				connect.close();
			if (this.preparedStatement != null)
				preparedStatement.close();
			if (this.resultSet != null)
				resultSet.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
