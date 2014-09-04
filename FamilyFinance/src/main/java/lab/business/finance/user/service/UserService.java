package lab.business.finance.user.service;

import lab.business.finance.user.dao.UserDAO;
import lab.business.finance.user.entity.User;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	public User getEntity(Long id) {
		return userDAO.getEntity(id);
	}

	public void deleteById(Long id) {
		userDAO.deleteById(id);
	}

	public void saveOrUpdate(User entity) {
		userDAO.saveOrUpdate(entity);
	}

	/**
	 * 保存
	 * @param u
	 */
	public void save(User u){
		this.cryptPassword(u);
		userDAO.save(u);
	}
	
	/**
	 * 加密用户密码
	 * @param u
	 */
	public void cryptPassword(User u) {
		if (u.getPassword().length() < 32) {
			String pass = DigestUtils.sha512Hex(u.getPassword());
			u.setPassword(pass);
		}
	}
	/**
	 * 判断输入的密码是否正确
	 * @param input
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String input, String password) {
		if (input == null) {
			if (password == null) {
				return true;
			} else {
				return false;
			}
		}
		if (password == null) { 
				return false; 
		}
		String hashed = DigestUtils.sha512Hex(input);
		if (hashed.equals(password)) {
			return true;
		} else {
			return false;
		} 
	}
	/**
	 * 判断输入的密码是否正确
	 * @param input
	 * @param userInDB
	 * @return
	 */
	public boolean checkPassword(User input, User userInDB){
		if(input==null||userInDB==null){
			return false;
		}
		return checkPassword(input.getPassword(), userInDB.getPassword());
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	 
	
}
