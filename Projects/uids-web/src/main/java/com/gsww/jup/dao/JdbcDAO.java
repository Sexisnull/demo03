package com.gsww.jup.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository("jdbcDAO")
public class JdbcDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	protected final Log logger = LogFactory.getLog(getClass());
	LobHandler lobHandler;

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public JdbcDAO() {
		super();
	}

	public int update(String sql, Object[] args) throws Exception {
		try {
			return jdbcTemplate.update(sql, args);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

/*	public int queryForInt(String sql, Object[] args) throws Exception {
		try {
			return jdbcTemplate.queryForInt(sql, args);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	public long queryForLong(String sql, Object[] args) throws Exception {
		try {
			return jdbcTemplate.queryForLong(sql, args);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}*/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object queryForObject(String sql, Object[] args, Class objClass)
			throws Exception {
		try {
			return jdbcTemplate.queryForObject(sql, args, objClass);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unused")
	public List<Map<String, Object>> queryForList(String sql, Object[] args)
			throws Exception {
		long start = System.currentTimeMillis();
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
					args);
			return list;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unused")
	public Map<String, Object> queryForMap(String sql)
			throws Exception {
		long start = System.currentTimeMillis();
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql);
			return map;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	public void execute(String sql) throws Exception {
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	public String queryForString(String sql, Object[] args) throws Exception {
		try {
			return (String) jdbcTemplate
					.queryForObject(sql, args, String.class);
		} catch (DataAccessException e) {
			// logger.warn(e.getMessage());
			// throw new Exception(e.getMessage(), e);
			// e.printStackTrace();
			return "";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryClobToString(String sql, Object[] args) throws Exception {
		List list = null;
		try {
			list = jdbcTemplate.query(sql, args, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String file = lobHandler.getClobAsString(rs, 1);
					return file;
				}
			});
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		if (list != null && list.size() > 0) {
			return String.valueOf(list.get(0));
		} else {
			return "";
		}
	}

	public void insertClobSql(String sql, final Object[] arg,
			final int[] clobIndex) throws Exception {

		jdbcTemplate.execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException {
						for (int i = 0; i < arg.length; i++) {

							for (int j = 0; j < clobIndex.length; j++) {
								if (i == clobIndex[j]) {
									lobCreator.setClobAsString(ps, i + 1,
											String.valueOf(arg[i]));
								} else {
									ps.setString(i + 1, String.valueOf(arg[i]));
								}
							}

						}
					}
				});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int callProcedure(String procedureName, final int parameter) {
		final String sql = "{call " + procedureName + "}";
		Object obj = jdbcTemplate.execute(sql, new CallableStatementCallback() {
			public Object doInCallableStatement(java.sql.CallableStatement cs)
					throws java.sql.SQLException, DataAccessException {

				if (parameter != 0) {
					cs.setInt(1, parameter);
				}
				// cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				return 0;
			}
		});
		return ((Integer) obj).intValue();
	}

	public List<Map<String, Object>> queryForList(String sql) throws Exception {
		try {
			return jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

	public void batchUpdate(String[] sql) throws Exception {
		try {
			jdbcTemplate.batchUpdate(sql);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
	}

}
