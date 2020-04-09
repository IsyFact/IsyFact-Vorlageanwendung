package de.bund.bva.isyfact.terminfindung.persistence;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TeilnehmerDao;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TeilnehmerZeitraumDao;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TerminfindungDao;
import de.bund.bva.isyfact.terminfindung.persistence.dao.jpa.JpaTeilnehmerDao;
import de.bund.bva.isyfact.terminfindung.persistence.dao.jpa.JpaTeilnehmerZeitraumDao;
import de.bund.bva.isyfact.terminfindung.persistence.dao.jpa.JpaTerminfindungDao;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Konfiguriert Komponententests der Datenzugriffsschicht.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Configuration
@EnableTransactionManagement
public class TestPersistenceConfiguration {

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test-unit;MODE=Oracle;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean dbConfig = new DatabaseConfigBean();
        dbConfig.setDatatypeFactory(new H2DataTypeFactory());
        return dbConfig;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource,
        DatabaseConfigBean databaseConfigBean) {
        DatabaseDataSourceConnectionFactoryBean dbConnection =
            new DatabaseDataSourceConnectionFactoryBean(dataSource);
        dbConnection.setDatabaseConfig(databaseConfigBean);
        return dbConnection;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setPersistenceUnitName("hibernatePersistence");
        em.setDataSource(dataSource);
        em.setJpaDialect(new HibernateJpaDialect());

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabase(Database.H2);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public SharedEntityManagerBean entityManagerFactoryBean(EntityManagerFactory emf) {
        SharedEntityManagerBean sharedEntityManager = new SharedEntityManagerBean();
        sharedEntityManager.setEntityManagerFactory(emf);
        return sharedEntityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public TerminfindungDao terminfindungDao(EntityManager entityManager) {
        JpaTerminfindungDao dao = new JpaTerminfindungDao();
        dao.setEntityManager(entityManager);
        return dao;
    }

    @Bean
    public TeilnehmerDao teilnehmerDao(EntityManager entityManager) {
        JpaTeilnehmerDao dao = new JpaTeilnehmerDao();
        dao.setEntityManager(entityManager);
        return dao;
    }

    @Bean
    public TeilnehmerZeitraumDao teilnehmerZeitraumDao(EntityManager entityManager) {
        JpaTeilnehmerZeitraumDao dao = new JpaTeilnehmerZeitraumDao();
        dao.setEntityManager(entityManager);
        return dao;
    }

}
