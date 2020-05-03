package ua.nure.teslenko.SummaryTask4.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Data base connector 
 * @author Mykhailo Teslenko
 *
 */
public class DBManager {
	private DataSource ds;
	private static DBManager instance;
	public static final String DB_NAME = "jdbc/FT4test";
	static Logger log = LogManager.getLogger();
	
	/**
	 * Getting DataSource object from context
	 * @throws AppException
	 */
	private DBManager() throws AppException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup(DB_NAME);
		} catch (NamingException ex) {
			ex.printStackTrace();
			throw new AppException("db.failToConnect", ex);
		}
	}
	
	/**
	 * Singleton pattern
	 * @return
	 * @throws Mykhailo Teslenko
	 */
	public static DBManager getInstance() throws AppException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/**
	 * Connection to DB
	 * @return connection to DB
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	

	/**
	 * Static method for connection closing.
	 * @param conn
	 * @throws AppException
	 */
	public static void closeConn(Connection conn) throws AppException {
		if (conn != null) {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException("db.closeConnection", e);
			}
		}
	}
	
	/**
	 * Static method for rollback
	 * @param conn
	 * @throws AppException
	 */
	public static void rollback(Connection conn) throws AppException {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new AppException("db.rollback", ex);
			}
		}
	}

}
