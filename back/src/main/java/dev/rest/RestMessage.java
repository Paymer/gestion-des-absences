package dev.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.repository.RepositoryMessageErreur;

@RestController
@RequestMapping("/message")
public class RestMessage {
	
	@Autowired
	private RepositoryMessageErreur repoMessages;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String getMessages(){
		JSONArray jsonArrayMessage = new JSONArray();
		repoMessages.findAll().forEach(currentMessage -> {
			JSONObject jsonCurrentMessage = new JSONObject();
			jsonCurrentMessage.put("id", currentMessage.getId())
				.put("date", currentMessage.getDate())
				.put("service", currentMessage.getServiceOrigine())
				.put("message", currentMessage.getMessage());
			jsonArrayMessage.put(jsonCurrentMessage);
		});
		return jsonArrayMessage.toString();
	}
	
}
