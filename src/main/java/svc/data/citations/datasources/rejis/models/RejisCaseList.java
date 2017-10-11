package svc.data.citations.datasources.rejis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisCaseList {

	@JsonProperty("PageNum")
	public Integer pageNumber;

	@JsonProperty("TotalPages")
	public Integer totalPages;
	
	@JsonProperty("TotalRows")
	public Integer totalRows;

	@JsonProperty("Message")
	public String message;

	@JsonProperty("CaseIndexRows")
	public List<RejisPartialCitation> caseIndexRows;
}
