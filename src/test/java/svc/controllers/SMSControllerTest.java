package svc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.runners.MockitoJUnitRunner;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;

import svc.managers.CitationManager;
import svc.managers.SMSManager;
import svc.models.*;

@RunWith(MockitoJUnitRunner.class)
public class SMSControllerTest {

	@InjectMocks
	SMSController controller;
	
	@Mock
	SMSManager smsManagerMock;
	@Mock
	CitationManager citationManagerMock;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	
	
	@Before
	public void init() throws IOException{		
		Message sms = new Message.Builder().body(new Body("messageString")).build();
		MessagingResponse twimlResponse = new MessagingResponse.Builder().message(sms).build();
	    
		when(smsManagerMock.getTwimlResponse((TwimlMessageRequest)notNull(), (HttpServletRequest)notNull(), (HttpSession)notNull() )).thenReturn(twimlResponse);
	
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
	}
	
	@Test
	public void callsCorrectMethods() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		controller.GetMessage(twimlMessageRequest,request, response, session);
		verify(session).setMaxInactiveInterval(30*60);
		verify(response).setContentType("application/xml");
		verify(smsManagerMock).getTwimlResponse(twimlMessageRequest,request,session);
	}
	
}
