package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/absence")
public class ControllerAbsence {


	@RequestMapping(method = RequestMethod.POST, path = "/demande")
	public String ajoutAbsence() {
		

		return "redirect:accuiel";
	}
}
