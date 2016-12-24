package it.vige.businesscomponents.injection.sql;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@DataSourceDefinitions({
		@DataSourceDefinition(name = "java:comp/ds1", className = "org.h2.jdbcx.JdbcDataSource", user = "sa", url = "jdbc:h2:./target/test_wildfly"),
		@DataSourceDefinition(name = "java:comp/ds2", className = "org.h2.jdbcx.JdbcDataSource", user = "sa", url = "jdbc:h2:./target/test_wildfly_2") })
@Stateless
public class DataBean {

	@Resource(lookup = "java:comp/ds1")
	private DataSource myData1;

	@Resource(lookup = "java:comp/ds2")
	private DataSource myData2;

	public DataSource getMyData1() {
		return myData1;
	}

	public DataSource getMyData2() {
		return myData2;
	}

}
