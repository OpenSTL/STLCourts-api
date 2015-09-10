package svc.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.data.*;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("inveo-api/userData")
public class UserDataController {
	
	UserDataDAO dao = new UserDataDAO();
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	void GetUserData()
	{
		return;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	void updateUserData(@RequestBody UserData input) {
		
		if (dao.checkForEmail(input.email))
		{
			// do nothing
		}
		else
		{
			dao.addUserData(input);
		}
	}
}
