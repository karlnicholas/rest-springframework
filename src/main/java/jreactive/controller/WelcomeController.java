package jreactive.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jreactive.dao.ProductDao;

@Controller
public class WelcomeController {

	@Autowired
	ProductDao productDao;

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Model model) {
		boolean ex = productDao.exists(1L);

		model.addAttribute("time", new Date());
		model.addAttribute("message", this.message);
		model.addAttribute("ex", ex);

		return "welcome";
	}
	
	@RequestMapping("/foo")
	public String foo(Model model) {
		throw new RuntimeException("Foo");
	}

}
