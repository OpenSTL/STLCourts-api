package svc.data.jdbc;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

//TODO: Make ResourceLoaderAware so we can load sql statements from package vs inline
public abstract class BaseJdbcDao {
	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	@Inject
	public void setDataSource(DataSource dataSource) { 
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource); 
	}

    // Override to define any SimpleJdbcInsert objects needed by the DAO.
    protected void createSimpleJdbcInserts(DataSource dataSource) {
    	
    }
}
