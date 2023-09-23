package test.org.fugerit.java.test.query.export.base;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.query.export.catalog.QueryConfigCatalog;
import org.fugerit.java.test.query.export.base.BasicUsage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBasicUsage {

	@Test
	public void basicTestOk() {
		BasicUsage.getCatalog( "sample-catalog" ).stream().forEach( c -> 
			SafeFunction.apply( () -> {
				log.info( "test -> {}", c );
				try ( Connection conn = getConnection() ) {
					QueryConfigCatalog.handle( conn , c );
					Assert.assertTrue( new File( c.getOutputFile() ).exists() );
				}
			} )
		 );
	}
	
	@Test
	public void basicTestKo() {
		BasicUsage.getCatalog( "fail-catalog" ).stream().forEach( c -> 
			SafeFunction.apply( () -> {
				log.info( "test -> {}", c );
				try ( Connection conn = getConnection() ) {
					Assert.assertThrows( IOException.class , () -> QueryConfigCatalog.handle( conn , c ) );
				}
			} )
		 );
	}
	
	private static boolean init = false;
	
	 /**
    * Database initialization for testing i.e.
    * <ul>
    * <li>Creating Table</li>
    * <li>Inserting record</li>
    * </ul>
    * 
    * @throws SQLException
    */
	@BeforeClass
   public synchronized static void initDatabase() throws SQLException {
		if ( !init ) {
	        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
	            statement.execute("CREATE TABLE test_export (id INT NOT NULL, name VARCHAR(50) NOT NULL,"
	                    + "email VARCHAR(50) NOT NULL, PRIMARY KEY (id))");
	            connection.commit();
	            statement.executeUpdate("INSERT INTO test_export VALUES (1001,'FieldA1', 'FieldB1')");
	            statement.executeUpdate("INSERT INTO test_export VALUES (1002,'FieldA2', 'FieldB2')");
	            statement.executeUpdate("INSERT INTO test_export VALUES (1003,'FieldA3', 'FieldB3')");
	            connection.commit();
	            init = true;
	        }			
		}
   }

   /**
    * Create a connection
    * 
    * @return connection object
    * @throws SQLException
    */
   protected static Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:hsqldb:mem:base_db", "testdb", "testdb");
   }
	
}
