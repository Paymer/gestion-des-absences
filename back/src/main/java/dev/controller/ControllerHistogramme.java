package dev.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.histogramme.BarChartData;
import dev.service.ServiceHistogramme;



@RestController
@RequestMapping("/histogramme")
public class ControllerHistogramme{
	

	
	private ServiceHistogramme serHisto;

	
	@Autowired
	public ControllerHistogramme(ServiceHistogramme serHisto) {
		super();
		this.serHisto = serHisto;

	}
	

	@RequestMapping(value = "/{departement}/{year}/{month}", method = RequestMethod.GET, produces = "application/json")
	public BarChartData chartData(@PathVariable String departement, @PathVariable Integer year, @PathVariable Integer month){

		BarChartData barChartData =serHisto.chartData(departement, year, month);

			
		return barChartData;
	}
	
	
	
}
