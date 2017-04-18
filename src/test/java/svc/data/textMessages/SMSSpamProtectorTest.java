package svc.data.textMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

@RunWith(MockitoJUnitRunner.class)
public class SMSSpamProtectorTest {
	MockHttpSession session;
	
	@Before
    public void setup() {
	 	session = new MockHttpSession();
    }
	
	@Test
	public void returnsFalseForLockedOut(){
		Boolean lockedOut = SMSSpamProtector.isLockedOut(session);
		assertEquals(lockedOut,false);
	}
	
	@Test
	public void addsErrorsAndLocksOut(){
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		Boolean lockedOut = SMSSpamProtector.isLockedOut(session);
		assertEquals(lockedOut,true);
	}
	
	@Test
	public void clearsErrorsCorrectly(){
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.addError(session);
		SMSSpamProtector.clearErrors(session);
		SMSSpamProtector.addError(session);
		Boolean lockedOut = SMSSpamProtector.isLockedOut(session);
		assertEquals(lockedOut,false);
	}
	
}
