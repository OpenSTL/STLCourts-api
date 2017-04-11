package svc.data.textMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

@RunWith(MockitoJUnitRunner.class)
public class SMSSpamProtectionTest {
	MockHttpSession session;
	
	@Before
    public void setup() {
	 	session = new MockHttpSession();
    }
	
	@Test
	public void returnsFalseForLockedOut(){
		Boolean lockedOut = SMSSpamProtection.isLockedOut(session);
		assertEquals(lockedOut,false);
	}
	
	@Test
	public void addsErrorsAndLocksOut(){
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		Boolean lockedOut = SMSSpamProtection.isLockedOut(session);
		assertEquals(lockedOut,true);
	}
	
	@Test
	public void clearsErrorsCorrectly(){
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.addError(session);
		SMSSpamProtection.clearErrors(session);
		SMSSpamProtection.addError(session);
		Boolean lockedOut = SMSSpamProtection.isLockedOut(session);
		assertEquals(lockedOut,false);
	}
	
}
