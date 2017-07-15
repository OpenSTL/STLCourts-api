package svc.data.citations.datasources.rejis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisCaseList {

	@JsonProperty
	public Integer PageNum;

	@JsonProperty
	public Integer TotalPages;
	
	@JsonProperty
	public Integer TotalRows;

	@JsonProperty
	public String Message;

	@JsonProperty
	public List<RejisPartialCitation> CaseIndexRows;
}
