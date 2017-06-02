package com.worksap.bootcamp.spring.bookstore.impl.controllers;

import static com.worksap.bootcamp.spring.bookstore.impl.controllers.ResponseUtil.view;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.worksap.bootcamp.spring.bookstore.impl.view.OrderDetailView;
import com.worksap.bootcamp.spring.bookstore.impl.view.OrderHeaderView;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ServiceFactory;

@Path("/administrator")
@Produces(MediaType.TEXT_HTML)
public class AdministratorController {
	private ServiceFactory serviceFactory = null;

	public AdministratorController(){
		try {
			serviceFactory = (ServiceFactory) Class.forName(
							"com.worksap.bootcamp.spring.bookstore.impl.services.DefaultServiceFactory")
					.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	@GET
	@Path("/orderHeader")
	public Response showOrdered() {
		ManageOrderService manageOrderService = serviceFactory.getManageOrderService();
		List<OrderHeader> headers = manageOrderService.getOrderHeaders();

		return view("/orderHeader.jsp", new OrderHeaderView(headers));
	}

	@GET
	@Path("/orderDetail/{headerId}")
	public Response showOrderDetails(@PathParam("headerId") int id) {
		ManageOrderService manageOrderService = serviceFactory.getManageOrderService();
		Order order = manageOrderService.getOrder(id);

		return view("/orderDetail.jsp", new OrderDetailView(order));
	}
}
