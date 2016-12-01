package svc.data.textMessages;

import java.util.List;

import svc.models.Citation;
import svc.util.DatabaseUtilities;

public class ListCitationsTextMessage {
	List<Citation> citations;
	
	public ListCitationsTextMessage(List<Citation> citations){
		this.citations = citations;
	}
	
	public String toTextMessage(){
		String message = "";
		
		String ticketWord = (citations.size()>1)?" tickets were ":" ticket was ";
		if (citations.size() > 0){
			message = citations.size()+ticketWord+"found";
			
			int counter = 1;
			for(Citation citation : citations){
				message += "\n"+counter+") ticket from: "+DatabaseUtilities.convertDatabaseDateToUS(citation.citation_date);
				counter++;
			}
			message += "\nReply with the ticket number you want to view.";
		}
		
		return message;
	}
	
}
