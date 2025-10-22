package edu.training.news_portal.dao.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {

	private static ConnectionPool instance = null;

	private BlockingQueue<Connection> availableConnections;
	private BlockingQueue<Connection> usedConnections;

	private ConnectionPool(String url, String user, String password, int poolSize) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		usedConnections = new ArrayBlockingQueue<Connection>(poolSize);
		availableConnections = new ArrayBlockingQueue<Connection>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			Connection connection = DriverManager.getConnection(url, user, password);
			PooledConnection pooledConnection = new PooledConnection(connection);
			availableConnections.add(pooledConnection);
		}
	}

	public static synchronized ConnectionPool getFirstInstance(String url, String user, String password, int poolSize)
			throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new ConnectionPool(url, user, password, poolSize);
		}
		return instance;
	}

	public static ConnectionPool getInstance() {

		return instance;
	}

	public Connection takeConnection() throws SQLException {
		Connection connection;
		try {
			connection = availableConnections.take();
			usedConnections.add(connection);
		} catch (InterruptedException e) {
			throw new SQLException("Error getting connection from pool", e);
		}
		return connection;
	}

	public void close() throws SQLException {
		Connection connection;
		while ((connection = usedConnections.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).getInnerConnection().close();
		}
		while ((connection = availableConnections.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).getInnerConnection().close();
		}
	}

	class PooledConnection implements Connection {

		Connection innerConnection;

		public PooledConnection(Connection connection) throws SQLException {
			innerConnection = connection;
			innerConnection.setAutoCommit(true);
		}

		public Connection getInnerConnection() {
			return innerConnection;
		}

		@Override
		public synchronized void close() throws SQLException {
			if (innerConnection.isClosed()) {
				throw new SQLException("Attempting to close a closed connection");
			}

			if (innerConnection.isReadOnly()) {
				innerConnection.setReadOnly(false);
			}

			if (!usedConnections.remove(this)) {
				throw new SQLException("Error removing connection from pool");
			}

			if (!availableConnections.offer(this)) {
				throw new SQLException("Error inserting connection into pool");
			}
		}

		@Override
		public void clearWarnings() throws SQLException {
			innerConnection.clearWarnings();
		}

		@Override
		public void commit() throws SQLException {
			innerConnection.commit();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return innerConnection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return innerConnection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return innerConnection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return innerConnection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return innerConnection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return innerConnection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return innerConnection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return innerConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return innerConnection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return innerConnection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return innerConnection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return innerConnection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return innerConnection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return innerConnection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return innerConnection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return innerConnection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return innerConnection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return innerConnection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return innerConnection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return innerConnection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return innerConnection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return innerConnection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return innerConnection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return innerConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return innerConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return innerConnection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return innerConnection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return innerConnection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return innerConnection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return innerConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return innerConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void rollback() throws SQLException {
			innerConnection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			innerConnection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			innerConnection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			innerConnection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			innerConnection.setHoldability(holdability);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			innerConnection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return innerConnection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return innerConnection.setSavepoint(name);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			innerConnection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return innerConnection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return innerConnection.unwrap(iface);
		}

		@Override
		public void abort(Executor arg0) throws SQLException {
			innerConnection.abort(arg0);

		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return innerConnection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return innerConnection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException {
			innerConnection.releaseSavepoint(arg0);
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			innerConnection.rollback(arg0);
		}

		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException {
			innerConnection.setClientInfo(arg0);
		}

		@Override
		public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
			innerConnection.setNetworkTimeout(arg0, arg1);
		}

		@Override
		public void setSchema(String arg0) throws SQLException {
			innerConnection.setSchema(arg0);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
			innerConnection.setTypeMap(arg0);
		}
	}
}