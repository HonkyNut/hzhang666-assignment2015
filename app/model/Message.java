package model;
import play.libs.Json;
import java.util.*;
import java.util.regex.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message{
	//UUID
	private String uuid;
	//Email
	private String email;
	//content
	private String content;
	//tags
	private List<String> tags;

    public Message(){
    }
    
    public Message(String uuid,String email,String content){
    	this.uuid = uuid;
    	this.email = email;
		this.content = content;
		this.tags = new ArrayList<String>();
setTags(content);
    }
	
	public void setTags(String content){
		Pattern p = Pattern.compile("#\\w+");
		Matcher m = p.matcher(content);
		while (m.find()) {
			tags.add(m.group());
		}
	}
	
	public List<String> getTags(){
		return tags;
	}
    
    public void setUuid(String uuid){
    	this.uuid = uuid;
    }
    
    public void setEmail(String email){
    	this.email = email;
    }
	
	public void setContent(String content){
    	this.content = content;
    }

	
	public String getUuid(){
    	return uuid;
    }
    
    public String getEmail(){
    	return email;
    }
    
    public String getContent(){
    	return content;
    }
    
    public String toString(){
		ObjectNode message = Json.newObject();
		message.put("uuid",uuid);
		message.put("email",email);
		message.put("content",content);
    	return message.toString();
    }
}
