package jreactive.controller;

import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jreactive.dao.ProductDao;
import jreactive.model.Product;

@Controller
public class WelcomeController {

	@Autowired
	ProductDao productDao;

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		boolean ex = productDao.exists(1L);

		model.put("time", new Date());
		model.put("message", this.message);
		model.put("ex", ex);

		return "welcome";
	}
	
	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}

}
