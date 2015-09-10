package svc.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.email.Email;
import svc.email.EmailManager;

@RestController
@EnableAutoConfiguration
@RequestMapping("inveo-api/emails")
public class EmailController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    void add()
    {
    	try
    	{
    		EmailManager mgr = new EmailManager();
    		Email mail = new Email("teaminveo@gmail.com", "abobwhite89@gmail.com", null, null, "Email Body!!", "Email Subject", null, null);
    		mgr.SendEmail(mail);
    	}
    	catch (Exception ex)
    	{
    		System.out.println(ex);
    	}
    	finally
    	{
    		
    	}
    	
        return;
    }
	
}
