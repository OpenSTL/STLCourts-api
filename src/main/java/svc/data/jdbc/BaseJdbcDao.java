package svc.data.jdbc;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

public abstract class BaseJdbcDao implements ResourceLoaderAware {
	protected NamedParameterJdbcTemplate jdbcTemplate;
    private ResourceLoader resourceLoader;
	
	@Inject
	public void setDataSource(DataSource dataSource) { 
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource); 
	}

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Override to define any SimpleJdbcInsert objects needed by the DAO.
    protected void createSimpleJdbcInserts(DataSource dataSource) {
    	
    }

	protected String getSql(String resourceFilePath) {
		String outStr = "";

		try {
			Resource e = resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/" + resourceFilePath);
			InputStream is = e.getInputStream();
			if(is != null) {
				InputStreamReader isr = new InputStreamReader(is);

				String str;
				for(BufferedReader br = new BufferedReader(isr); (str = br.readLine()) != null; outStr = outStr + " " + str) {
					;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outStr;
	}
}
