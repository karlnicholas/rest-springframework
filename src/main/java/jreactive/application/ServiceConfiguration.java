package jreactive.application;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "jreactive.dao"})
public class ServiceConfiguration {
/*
	@Bean
	public DriverManagerDataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem://standalone");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}
*/
	
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName("org.hsqldb.jdbcDriver");
        ds.setUrl("jdbc:hsqldb:mem://standalone");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
    }
	
	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(jpaDialect());

		return transactionManager;
	}

	@Bean
	public HibernateJpaDialect jpaDialect() {
		return new HibernateJpaDialect();
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();

		jpaVendor.setDatabase(Database.HSQL);
		jpaVendor.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		jpaVendor.setShowSql(true);
		return jpaVendor;

	}

   Properties additionalProperties() {
	      Properties properties = new Properties();
	      properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
	      properties.setProperty("hibernate.hbm2ddl.import_files", "/sql/import.sql");
	      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
	      return properties;
	   }

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setPersistenceUnitName("persistence");
		entityManagerFactory.setPackagesToScan("jreactive.model");
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setJpaDialect(jpaDialect());
		entityManagerFactory.setJpaProperties(additionalProperties());

		return entityManagerFactory;

	}

}