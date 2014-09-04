package lab;

import javax.transaction.Transactional;

import lab.business.finance.user.dao.UserDAO;
import lab.business.finance.user.entity.User;
import lab.business.finance.user.service.UserService;
import lab.test.common.TransactionDevTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionDevTest
public class ResourceTest {
	@Autowired
	private UserService userService;

	@Test
	@Transactional
	public void saveUser() {
		
		
		for(int i=0; i<40; i++){
			User u = new User();
			u.setName("张三");
			u.setPassword("123456");
			u.setUsername("zs");
			
			userService.save(u);
		}
		System.out.println("ok");
	}

}
