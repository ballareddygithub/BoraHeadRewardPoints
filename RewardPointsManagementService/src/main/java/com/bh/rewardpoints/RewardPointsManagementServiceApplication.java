package com.bh.rewardpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
@RequestMapping( "/" )
public class RewardPointsManagementServiceApplication {

	private static Logger logger = LoggerFactory.getLogger( RewardPointsManagementServiceApplication.class );
	
	public static void main(String[] args) {
		SpringApplication.run(RewardPointsManagementServiceApplication.class, args);
	}
	
	/* Redirect to Actuator Links */
	@GetMapping
	public ModelAndView home(){
		return new ModelAndView("redirect:/actuator");
	}

}
