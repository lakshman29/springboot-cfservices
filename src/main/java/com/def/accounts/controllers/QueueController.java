package com.def.accounts.controllers;




import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.def.accounts.domain.Message;
@Controller
public class QueueController {
	 @Autowired
		private RabbitTemplate rabbitTemplate;
	 
	  @RequestMapping(value = "/")
	    public String home(Model model) {
	        model.addAttribute(new Message());
	        //return "WEB-INF/jsp/home.jsp";
	        return "home";
	    }
	  // @Scheduled(fixedDelay = 1000, initialDelay = 500)
	    @RequestMapping(value = "/publish", method=RequestMethod.POST)
	    public String publish(Model model, Message message) {
	        // Send a message to the "messages" queue
	    	rabbitTemplate.convertAndSend("test", message.getValue());
	        model.addAttribute("published", true);
	        return home(model);
	    }

	    @RequestMapping(value = "/get", method=RequestMethod.POST)
	    public String get(Model model) {
	        // Receive a message from the "messages" queue
	        String message = (String)rabbitTemplate.receiveAndConvert("test");
	        if (message != null)
	            model.addAttribute("got", message);
	        else
	            model.addAttribute("got_queue_empty", true);

	        return home(model);
	    }
	    
	    

	/*	@Scheduled(fixedDelay = 1000L)
		public void send() {
			this.rabbitTemplate.convertAndSend("foo", "hello");
	}*/
}
