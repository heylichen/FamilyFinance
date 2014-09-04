package lab.test.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lab.web.config.spring.SpringConfig;
import lab.web.config.spring.WebAppInitializer;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
@ActiveProfiles("dev") 

//using spring testcontext framework 4.0
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration//because SpringConfig has @EnableWebMvc, you need this 
@TransactionConfiguration(defaultRollback = false)
public @interface TransactionDevTest {

}
