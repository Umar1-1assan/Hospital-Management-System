package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBManager 
{
	private static DBManager dbManager = null;
    private String jdbcUrl;
    private String driverClass;

    private DBManager() 
    {
        this.jdbcUrl =  "jdbc:sqlserver://DESKTOP-DF0DJ4G\\SQLEXPRESS;databaseName=Hospital;IntegratedSecurity=True;trustServerCertificate=true";
        this.driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    /*public DBManager(String jdbcUrl, String username, String password, String driverClass) 
    {
        this.jdbcUrl = jdbcUrl;
        this.driverClass = driverClass;
    }*/
    public static DBManager getDBManager()
	{
		if(dbManager == null)
		{
			dbManager = new DBManager();
			return dbManager;
		}
		else return dbManager;
	}

    public Connection connect() throws SQLException, ClassNotFoundException 
    {
        if (driverClass != null) {
            Class.forName(driverClass);
        }
        return DriverManager.getConnection(jdbcUrl);
    }

    public String getJdbcUrl() 
    {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }


    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}