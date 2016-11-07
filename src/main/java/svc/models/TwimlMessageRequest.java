package svc.models;

public class TwimlMessageRequest {
	//params listed at: https://www.twilio.com/docs/api/twiml/sms/twilio_request
	private String MessageSid;
	private String SmsSid;
	private String AccountSid;
	private String MessagingServiceSid;
	private String From;  //phone numbers stored in E.164  so: +14155554345
	private String To;
	private String Body;
	private int NumMedia;
	private String[] MediaContentType;
	private String[] Mediaurl;
	private String FromCity;
	private String FromState;
	private String FromZip;
	private String FromCountry;
	private String ToCity;
	private String ToState;
	private String ToZip;
	private String ToCountry;
	
	public String getMessageSid() {return MessageSid;}
	public void setMessageSid(String MessageSid){this.MessageSid = MessageSid;}
	
	public String getSmsSid() {return SmsSid;}
	public void setSmsSid(String MessageSid){this.SmsSid = SmsSid;}
	
	public String getAccountSid() {return AccountSid;}
	public void setAccountSid(String AccountSid){this.AccountSid = AccountSid;}
	
	public String getMessagingServiceSid() {return MessagingServiceSid;}
	public void setMessagingServiceSid(String MessagingServiceSid){this.MessagingServiceSid = MessagingServiceSid;}
	
	public String getFrom() {return From;}
	public void setFrom(String From){this.From = From;}
	
	public String getTo() {return To;}
	public void setTo(String To){this.To = To;}
	
	public String getBody() {return Body;}
	public void setBody(String Body){this.Body = Body;}
	
	public int getNumMedia() {return NumMedia;}
	public void setNumMedia(int NumMedia){this.NumMedia = NumMedia;}
	
	public String[] getMediaContentType() {return MediaContentType;}
	public void setMediaContentType(String[] MediaContentType){this.MediaContentType = MediaContentType;}
	
	public String[] getMediaurl() {return Mediaurl;}
	public void setMediaurl(String[] Mediaurl){this.Mediaurl = Mediaurl;}
	
	public String getFromCity() {return FromCity;}
	public void setFromCity(String FromCity){this.FromCity = FromCity;}
	
	public String getFromState() {return FromState;}
	public void setFromState(String FromState){this.MessageSid = FromState;}
	
	public String getFromZip() {return FromZip;}
	public void setFromZip(String FromZip){this.FromZip = FromZip;}
	
	public String getFromCountry() {return FromCountry;}
	public void setFromCountry(String MessageSid){this.FromCountry = FromCountry;}
	
	public String getToCity() {return ToCity;}
	public void setToCity(String ToCity){this.ToCity = ToCity;}
	
	public String getToState() {return ToState;}
	public void setToState(String ToState){this.MessageSid = ToState;}
	
	public String getToZip() {return ToZip;}
	public void setToZip(String ToZip){this.ToZip = ToZip;}
	
	public String getToCountry() {return ToCountry;}
	public void setToCountry(String MessageSid){this.ToCountry = ToCountry;}

}
