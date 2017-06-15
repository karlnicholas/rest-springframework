package jreactive.application;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jreactive.model.Product;
import jreactive.repository.ProductDao;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[0];
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{AppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
}