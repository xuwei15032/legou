package test;

public class UserController {
	
	private UserService userService;
	
	
	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void addUser() {
		userService.addUser();
	}
}
