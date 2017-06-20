package jreactive.test;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "jreactive.dao"})
@EnableTransactionManagement
public class ServiceTestConfiguration {
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
	public JpaTransactionManager jpaTransMan(){
		JpaTransactionManager jtManager = new JpaTransactionManager(
				getEntityManagerFactoryBean().getObject());
		return jtManager;
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setDataSource(dataSource());
		lcemfb.setPersistenceUnitName("localContainerEntity");
		return lcemfb;
	}

}