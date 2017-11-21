/*
 * JDBCAbstract.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

package tigase.util;

import tigase.annotations.TigaseDeprecated;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The class has been deprecated in favor of <code>DataRepository</code> class. Please update your code accordingly.
 * <br>
 * <br>
 * Created: Mon Mar  3 10:43:44 2008
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 * @deprecated
 */
@Deprecated
@TigaseDeprecated(since = "3.3.11", removeIn = "4.1.0")
public abstract class JDBCAbstract {

	public static final String SP_STARTS_WITH = "{ call";
	public static final String DERBY_CONNVALID_QUERY = "values 1";
	public static final String JDBC_CONNVALID_QUERY = "select 1";
	/**
	 * Private logger for class instance.
	 */
	private static final Logger log = Logger.getLogger(JDBCAbstract.class.getName());

	/**
	 * Database active connection.
	 */
	private Connection conn = null;

	/**
	 * Prepared statement for testing whether database connection is still working. If not connection to database is
	 * recreated.
	 */
	private PreparedStatement conn_valid_st = null;

	/**
	 * Connection validation helper.
	 */
	private long connectionValidateInterval = 1000 * 60;

	/**
	 * Database connection string.
	 */
	private String db_conn = null;
	private boolean derby_mode = false;
	/**
	 * Connection validation helper.
	 */
	private long lastConnectionValidated = 0;

	/**
	 * <code>initRepository</code> method is doing lazy initialization with database. Connection to database will be
	 * established during the first authentication request.
	 *
	 * @param conn_str a <code>String</code> value of database connection string. The string must also contain database
	 * user name and password if required for connection.
	 * @param params
	 *
	 * @throws SQLException if an error occurs during access database. It won't happen however as in this method we do
	 * simple variable assigment.
	 */
	public abstract void initRepository(String conn_str, Map<String, String> params) throws SQLException;

	/**
	 * <code>getResourceUri</code> method returns database connection string.
	 *
	 * @return a <code>String</code> value of database connection string.
	 */
	public String getResourceUri() {
		return db_conn;
	}

	public void setResourceUri(String uri) {
		db_conn = uri;
	}

	public CallableStatement prepareCallable(String query) throws SQLException {
		return conn.prepareCall(query);
	}

	public PreparedStatement prepareQuery(String query) throws SQLException {
		if (query.startsWith(SP_STARTS_WITH)) {
			return conn.prepareCall(query);
		} else {
			return conn.prepareStatement(query);
		}
	}

	public PreparedStatement prepareStatement(String query) throws SQLException {
		return conn.prepareStatement(query);
	}

	/**
	 * <code>checkConnection</code> method checks database connection before any query. For some database servers (or
	 * JDBC drivers) it happens the connection is dropped if not in use for a long time or after certain timeout passes.
	 * This method allows us to detect the problem and reinitialize database connection.
	 *
	 * @return a <code>boolean</code> value if the database connection is working.
	 *
	 * @throws SQLException if an error occurs on database query.
	 */
	protected boolean checkConnection() throws SQLException {
		try {
			synchronized (conn_valid_st) {
				long tmp = System.currentTimeMillis();

				if ((tmp - lastConnectionValidated) >= connectionValidateInterval) {
					conn_valid_st.executeQuery();
					lastConnectionValidated = tmp;
				}    // end of if ()
			}
		} catch (Exception e) {
			initRepo();
		}        // end of try-catch

		return true;
	}

	/**
	 * <code>initPreparedStatements</code> method initializes internal database connection variables such as prepared
	 * statements.
	 *
	 * @throws SQLException if an error occurs on database query.
	 */
	protected void initPreparedStatements() throws SQLException {
		String query = (derby_mode ? DERBY_CONNVALID_QUERY : JDBC_CONNVALID_QUERY);

		conn_valid_st = conn.prepareStatement(query);
	}

	protected void release(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) {
			}
		}
	}

	/**
	 * <code>initRepo</code> method initializes database connection and data repository.
	 *
	 * @throws SQLException if an error occurs on database query.
	 */
	private void initRepo() throws SQLException {
		synchronized (db_conn) {
			derby_mode = db_conn.startsWith("jdbc:derby");
			conn = DriverManager.getConnection(db_conn);
			conn.setAutoCommit(true);
			initPreparedStatements();
		}
	}
}

