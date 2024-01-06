package zone.framework.model.base;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 * 
 * @author linux liu
 * 
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserPO user;

	public UserPO getUser() {
		return user;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return user.getLoginName();
	}

}
