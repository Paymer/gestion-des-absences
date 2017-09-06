package dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.service.ServiceTraitementNuit;

@Controller
@RequestMapping("/gestion-des-absences")
public class ControllerTraitementNuit {
	
	@Autowired
	private ServiceTraitementNuit serviceTraitementNuit;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView afficherBackOffice(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("gestion-des-absences");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView passerNuit(@RequestParam boolean traitementNuit){
		if(traitementNuit){
			serviceTraitementNuit.passerNuit();
		}
		return this.afficherBackOffice();
	}
	
}
