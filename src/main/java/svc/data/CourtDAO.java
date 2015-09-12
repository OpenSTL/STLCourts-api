package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

import svc.logging.LogSystem;
import svc.models.*;

import javax.sql.DataSource;

@Repository
public class CourtDAO
{
	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { CourtDAO.jdbcTemplate = new JdbcTemplate(dataSource); }
	
	public Court getByCourtId(int courtId)
	{
		try 
		{
			String sql = "SELECT * FROM courts WHERE id = ?";
			Court court = jdbcTemplate.queryForObject(sql,
													 new CourtSQLMapper(),
													 courtId);
			
			return court;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class CourtSQLMapper implements RowMapper<Court>
	{
		public Court mapRow(ResultSet rs, int i)
		{
			Court court = new Court();
			try
			{	
				court.id = rs.getInt("id");
				court.latitude = new BigDecimal(rs.getString("latitude"));
				court.longitude = new BigDecimal(rs.getString("longitude"));
				court.municipality = rs.getString("municipality");
				court.address = rs.getString("address");
				court.city = rs.getString("city");
				court.state = rs.getString("state");
				court.zip_code = rs.getString("zip_code");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return court;
		}
	}
}
