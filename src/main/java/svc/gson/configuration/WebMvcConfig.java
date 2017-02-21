package svc.gson.configuration;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.http.converter.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import svc.hashids.Hashids;
import svc.logging.LogSystem;
import svc.models.Court;
import svc.models.Judge;
import svc.models.Municipality;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Inject
	Hashids municipalityHashids;
	@Inject
	Hashids judgeHashids;
	@Inject
	Hashids courtHashids;
	
	Gson gsonWithConverter;
	
	 @Override
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createGsonHttpMessageConverter());
        super.configureMessageConverters(converters);
	 }

	 private GsonHttpMessageConverter createGsonHttpMessageConverter() {
		 gsonWithConverter = new GsonBuilder()
			        		.registerTypeAdapter(Court.class, new CourtSerializer())
			        		.registerTypeAdapter(Judge.class, new JudgeSerializer())
			        		.registerTypeAdapter(Municipality.class, new MunicipalitySerializer())
			                .create();

        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gsonWithConverter);

        return gsonConverter;
	 }
	 
	 private class HashObject<T>{
		 private T classForHash;
		 private JsonObject jsonObj;
		 private Gson gson;
		 
		 public HashObject(T classForHash){
			 this.classForHash = classForHash;
			 gson = new Gson();
			 this.createJsonObject();
		 }
		 
		 private void createJsonObject(){
			 jsonObj = new JsonParser().parse(gson.toJson(this.classForHash)).getAsJsonObject(); 
		 }
		 
		 public void hashThisField(String fieldToHash,Hashids hashToUse){
			 try{
				 String hashedId = hashToUse.encode(Long.parseLong(jsonObj.get(fieldToHash).toString()));
				 jsonObj.addProperty(fieldToHash, hashedId);
			 }catch(Exception e){
				 LogSystem.LogEvent("Unhandled Exception - "+e.getMessage());
			 }
		 }
		 		 
		 public void addArrayField(String fieldToAdd, String jsonArrayString){
			 JsonArray jA = new JsonParser().parse(jsonArrayString).getAsJsonArray();
			 jsonObj.add(fieldToAdd, jA);
		 }
		 
		 public JsonObject getJsonObj(){
			 return jsonObj;
		 }
	 }
	 
	 private class CourtSerializer implements JsonSerializer<Court> {
		@Override
		public JsonElement serialize(Court court, Type arg1, JsonSerializationContext arg2) {
			HashObject<Court> courtHash = new HashObject<Court>(court);
			courtHash.hashThisField("id",courtHashids);
			courtHash.addArrayField("judges", gsonWithConverter.toJson(court.judges));
			return courtHash.getJsonObj();
		}
	}
	 
	 private class JudgeSerializer implements JsonSerializer<Judge> {
		@Override
		public JsonElement serialize(Judge judge, Type arg1, JsonSerializationContext arg2) {
			HashObject<Judge> judgeHash = new HashObject<Judge>(judge);
			judgeHash.hashThisField("id",judgeHashids);
			judgeHash.hashThisField("court_id",courtHashids);
			return judgeHash.getJsonObj();
		}
	}
	 
	 private class MunicipalitySerializer implements JsonSerializer<Municipality> {
		@Override
		public JsonElement serialize(Municipality municipality, Type arg1, JsonSerializationContext arg2) {
			HashObject<Municipality> municipalityHash = new HashObject<Municipality>(municipality);
			municipalityHash.hashThisField("id",municipalityHashids);
			municipalityHash.hashThisField("court_id",courtHashids);
			return municipalityHash.getJsonObj();
		}
	}
}
