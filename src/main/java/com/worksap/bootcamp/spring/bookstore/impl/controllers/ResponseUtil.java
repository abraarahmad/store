package com.worksap.bootcamp.spring.bookstore.impl.controllers;

import java.net.URI;


import org.springframework.web.servlet.ModelAndView;

class ResponseUtil {
	  public static ModelAndView view(String jsp, Object it) {
	    return new ModelAndView(jsp, "it", it);
	  }

	  public static ModelAndView redirect(String uri) {
	    return new ModelAndView("redirect:" + URI.create(uri));
	  }
	}
