package webapp.tier.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "webapp.tier.db.postgres", entityManagerFactoryRef = "postgresEntityManager", transactionManagerRef = "postgresTransactionManager")
public class PostgresDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.postgres")
	public DataSourceProperties postgresProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Autowired
	public DataSource postgresDataSource(@Qualifier("postgresProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean postgresEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("postgresDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("webapp.tier.db.postgres")
				.persistenceUnit("postgres")
				.build();
	}

	@Bean
	@Autowired
	public JpaTransactionManager postgresTransactionManager(
			@Qualifier("postgresEntityManager") LocalContainerEntityManagerFactoryBean postgresEntityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(postgresEntityManager.getObject());
		return transactionManager;
	}
}
