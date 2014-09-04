package lab.web.config.spring;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import lab.core.dao.proxy.ProxyDAOBeanFactoryPostProcessor;
import lab.web.resolver.JsonViewResolver;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
/* @PropertySource({ "classpath:persistence-hibernate.properties" }) */
@ComponentScan(basePackages = { "lab" })
public class SpringConfig extends WebMvcConfigurerAdapter {
	@Bean
	public BeanFactoryPostProcessor myBeanFactoryPostProcessor(){
		return new ProxyDAOBeanFactoryPostProcessor();
	}
	/*@Bean
	public AutowiredAnnotationBeanPostProcessor getpp() {
		return new AutowiredAnnotationBeanPostProcessor();
	}*/

	/**
	 * datasource
	 * 
	 * @return
	 */
	@Bean
	public DataSource restDataSource() {
		Properties env = hibernateProperties();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));

		return dataSource;
	}

	/**
	 * sessionFactory using hibernate
	 * 
	 * @return
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(restDataSource());
		sessionFactory.setPackagesToScan(new String[] { "lab" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		return sessionFactory;
	}

	/**
	 * 
	 * @param transactionManager
	 * @return
	 */
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties hibernateProperties() {
		Properties prop = new Properties();
		try {
			// load a properties file from class path, inside static method
			ClassPathResource r = new ClassPathResource("/persistence-hibernate.properties");
			prop.load( r.getInputStream());
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

		/*
		 * return new Properties() { {
		 * 
		 * setProperty("hibernate.dialect",
		 * env.getProperty("hibernate.dialect"));
		 * 
		 * setProperty("hibernate.hbm2ddl.auto",
		 * env.getProperty("hibernate.hbm2ddl.auto"));
		 * 
		 * } };
		 */
	}

	/**
	 * multi views
	 */
	@Bean(name = "jspViewResolver")
	public ViewResolver getJspViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean(name = "jsonViewResolver")
	public ViewResolver getJsonViewResolver() {
		JsonViewResolver resolver = new JsonViewResolver();

		return resolver;
	}

	/**
	 * Setup a simple strategy: 1. Only path extension is taken into account,
	 * Accept headers are ignored. 2. Return HTML by default when not sure.
	 */
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(false).defaultContentType(
				MediaType.TEXT_HTML);
	}

	/**
	 * Create the CNVR. Get Spring to inject the ContentNegotiationManager
	 * created by the configurer (see previous method).
	 */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");

	}
}
