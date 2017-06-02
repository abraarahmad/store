package com.worksap.bootcamp.spring.bookstore.impl.controllers;

import java.net.URI;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

class ResponseUtil {
	public static Response view(String jsp, Object it) {
		return Response.ok().entity(new Viewable(jsp, it)).build();
	}

	public static Response redirect(String uri) {
		return Response.seeOther(URI.create(uri)).build();
	}
}
