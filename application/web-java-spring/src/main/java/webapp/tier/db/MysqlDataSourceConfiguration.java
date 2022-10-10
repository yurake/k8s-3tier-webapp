package webapp.tier.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "webapp.tier.db.mysql", entityManagerFactoryRef = "mysqlEntityManager", transactionManagerRef = "mysqlTransactionManager")
public class MysqlDataSourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	public DataSourceProperties mysqlProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@Autowired
	public DataSource mysqlDataSource(@Qualifier("mysqlProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	@Autowired
	public LocalContainerEntityManagerFactoryBean mysqlEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("mysqlDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("webapp.tier.db.mysql")
				.persistenceUnit("mysql")
				.build();
	}

	@Bean
	@Primary
	@Autowired
	public JpaTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManager") LocalContainerEntityManagerFactoryBean mysqlEntityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mysqlEntityManager.getObject());
		return transactionManager;
	}
}
