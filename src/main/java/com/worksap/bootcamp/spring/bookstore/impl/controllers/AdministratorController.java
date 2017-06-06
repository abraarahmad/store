package com.worksap.bootcamp.spring.bookstore.impl.controllers;

import static com.worksap.bootcamp.spring.bookstore.impl.controllers.ResponseUtil.view;

import java.util.List;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;







import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.worksap.bootcamp.spring.bookstore.impl.view.OrderDetailView;
import com.worksap.bootcamp.spring.bookstore.impl.view.OrderHeaderView;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ServiceFactory;

//@Path("/administrator")
//@Produces(MediaType.TEXT_HTML)
@Controller
@RequestMapping(value = "/administrator", produces = "text/html")
public class AdministratorController {
	

	@Autowired
	private ServiceFactory serviceFactory;

	public AdministratorController(){
//		try {
//			serviceFactory = (ServiceFactory) Class.forName(
//							"com.worksap.bootcamp.spring.bookstore.impl.services.DefaultServiceFactory")
//					.newInstance();
//		} catch (InstantiationException e) {
//			throw new IllegalStateException(e);
//		} catch (IllegalAccessException e) {
//			throw new IllegalStateException(e);
//		} catch (ClassNotFoundException e) {
//			throw new IllegalStateException(e);
//		}
	}

//	@GET
//	@Path("/orderHeader")
	@RequestMapping(value = "/orderHeader", method = RequestMethod.GET)
	public ModelAndView showOrdered() {
		ManageOrderService manageOrderService = serviceFactory.getManageOrderService();
		List<OrderHeader> headers = manageOrderService.getOrderHeaders();

		return view("/orderHeader.jsp", new OrderHeaderView(headers));
	}

//	@GET
//	@Path("/orderDetail/{headerId}")
	@RequestMapping(value = "/orderDetail/{headerId}", method = RequestMethod.GET)
	public ModelAndView showOrderDetails(@RequestParam("headerId") int id) {
		ManageOrderService manageOrderService = serviceFactory.getManageOrderService();
		Order order = manageOrderService.getOrder(id);

		return view("/orderDetail.jsp", new OrderDetailView(order));
	}
}
