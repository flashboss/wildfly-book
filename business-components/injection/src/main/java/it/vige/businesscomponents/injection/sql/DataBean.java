package it.vige.businesscomponents.injection.sql;

import static java.util.logging.Logger.getLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@DataSourceDefinitions({
		@DataSourceDefinition(name = "java:comp/ds1", className = "org.h2.jdbcx.JdbcDataSource", user = "sa", databaseName = "testDB1", serverName = "luckydog", properties = {
				"databaseName=myDB", "databaseProp=doThis" }),
		@DataSourceDefinition(name = "java:comp/ds2", className = "org.h2.jdbcx.JdbcDataSource", user = "sa", databaseName = "testDB2", serverName = "luckydog", properties = {
				"databaseName=myDB", "databaseProp=doThis" }) })
@Stateless
public class DataBean {

	private static final Logger logger = getLogger(DataBean.class.getName());

	@Resource(lookup = "java:comp/ds1")
	private DataSource myData1;

	@Resource(lookup = "java:comp/ds2")
	private DataSource myData2;

	public void addMyData1(Data data) {
		add(myData1, data);
	}

	public void addMyData2(Data data) {
		add(myData2, data);
	}

	public List<Data> readAllMyData1() {
		return readAll(myData1);
	}

	public List<Data> readAllMyData2() {
		return readAll(myData2);
	}

	public void add(DataSource dataSource, Data data) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("insert into data(id,name,value) values (?,?,?)");
			statement.setInt(1, data.getId());
			statement.setString(2, data.getName());
			statement.setString(3, data.getValue());
			statement.execute();
		} catch (SQLException e) {
			logger.info("I cannot connect");
		}
	}

	public List<Data> readAll(DataSource dataSource) {
		List<Data> data = new ArrayList<Data>();
		try (Connection connection = dataSource.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from data");
			while (result.next()) {
				data.add(new Data(result.getInt("id"), result.getString("name"), result.getString("value")));
			}
		} catch (SQLException e) {
			logger.info("I cannot connect");
		}
		return data;
	}
}
