package com.worksap.bootcamp.spring.bookstore.impl.controllers;

import static com.worksap.bootcamp.spring.bookstore.impl.controllers.ResponseUtil.redirect;
import static com.worksap.bootcamp.spring.bookstore.impl.controllers.ResponseUtil.view;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.worksap.bootcamp.spring.bookstore.impl.view.CartView;
import com.worksap.bootcamp.spring.bookstore.impl.view.ItemListView;
import com.worksap.bootcamp.spring.bookstore.impl.view.OrderView;
import com.worksap.bootcamp.spring.bookstore.impl.view.StockShortageView;
import com.worksap.bootcamp.spring.bookstore.impl.view.ThanksView;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartAddResult;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Customer;
import com.worksap.bootcamp.spring.bookstore.spec.dto.ItemStock;
import com.worksap.bootcamp.spring.bookstore.spec.services.CartService;
import com.worksap.bootcamp.spring.bookstore.spec.services.FlashService;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService.StockShortage;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService.StockShortageException;
import com.worksap.bootcamp.spring.bookstore.spec.services.ServiceFactory;

@Path("/bookstore")
@Produces(MediaType.TEXT_HTML)
public class BookStoreController {
	private static final Charset MS932 = Charset.forName("MS932");
	private static final String FLASH_ORDERED_CART = "orderedCart";
	private static final String FLASH_STOCK_SHORTAGE = "stockShortage";

	@Context
	private HttpServletRequest request;

	private final ServiceFactory serviceFactory;

	public BookStoreController() {
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
	@Path("/")
	public Response showList() {
		Iterable<ItemStock> items = serviceFactory.getItemService().getOnSale();
		return view("/index.jsp", new ItemListView(items));
	}

	@POST
	@Path("/addCart")
	public Response addCart(@FormParam("item-id") int itemId,
			@FormParam("amount") int amount) {
		CartService cartService = serviceFactory.getCartService();
		List<String> messages = new ArrayList<String>();

		String userId = request.getSession().getId();
		CartAddResult result = cartService.addCart(userId, itemId, amount);

		if (result.getOverflowed() > 0 ){
			messages.add("カートに入れられる最大数は" + result.getMaxCapacity() + "です。");
		}

		if (result.getShortage() > 0 ){
			messages.add("在庫数が "+result.getShortage()+"個 不足しています。");
		}

		return showCart(messages);
	}

	private Response showCart(List<String> messages) {
		CartService cartService = serviceFactory.getCartService();
		String userId = request.getSession().getId();
		Cart cart = cartService.getCart(userId);

		return view("/cart.jsp", new CartView(cart, messages));
	}

	@GET
	@Path("/showCart")
	public Response showCart() {
		return showCart(new ArrayList<String>());
	}

	@POST
	@Path("/removeFromCart")
	public Response removeFromCart(@FormParam("item-id") int itemId) {
		CartService cartService = serviceFactory.getCartService();
		String userId = request.getSession().getId();
		cartService.removeItem(userId, itemId);

		return showCart();
	}

	@POST
	@Path("/clearCart")
	public Response clearCart() {
		CartService cartService = serviceFactory.getCartService();
		String userId = request.getSession().getId();
		cartService.clear(userId);

		return showCart();
	}

	@GET
	@Path("/prepareOrder")
	public Response prepareOrder() {
		CartService cartService = serviceFactory.getCartService();
		String userId = request.getSession().getId();
		Cart cart = cartService.getCart(userId);

		if (cart.getTotal() == 0) {
			return redirect("/bookstore/");
		}

		return view("/order.jsp", new OrderView(cart, new Customer("", ""), new HashMap<String, String>()));
	}

	@POST
	@Path("/order")
	public Response order(@FormParam("name") String name, @FormParam("address") String address ) {
		Map<String, String> messages = validate(name, address);

		String userId = request.getSession().getId();

		CartService cartService = serviceFactory.getCartService();
		Cart cart = cartService.getCart(userId);

		if (messages.isEmpty()) {
			OrderService orderService = serviceFactory.getOrderService();
			FlashService flashService = serviceFactory.getFlashService();

			try {
				orderService.order(userId, name, address);
				flashService.put(FLASH_ORDERED_CART, cart);
				return redirect("/bookstore/thanks");
			} catch (StockShortageException e) {
				flashService.put(FLASH_STOCK_SHORTAGE , e.getStockShortage());
				return redirect("/bookstore/stockShortage");
			}
		}

		return view("/order.jsp", new OrderView(cart, new Customer(name, address), messages));
	}

	private Map<String,String> validate(String name, String address) {
		Map<String, String> messages = new HashMap<String, String>();

		String addressMessage = validateAddress(address);
		if (addressMessage != null) {
			messages.put("address", addressMessage);
		}

		String nameMessage = validateName(name);
		if (nameMessage != null) {
			messages.put("name", nameMessage);
		}

		return messages;
	}

	private String validateName(String name){
		if (name == null || name.isEmpty()) {
			return "氏名が入力されていません";
		}

		if (name.getBytes(MS932).length > 40) {
			return "氏名は40Byte以内で入力してください";
		}

		return null;
	}

	private String validateAddress(String address) {
		if (address == null || address.isEmpty()) {
			return "住所が入力されていません";
		}

		if (address.getBytes(MS932).length > 200) {
			return "住所は200Byte以内で入力してください";
		}

		return null;
	}

	@GET
	@Path("/thanks")
	public Response thanks() {
		FlashService flashService = serviceFactory.getFlashService();
		Cart orderHeaderId = flashService.get(FLASH_ORDERED_CART, Cart.class);

		if (orderHeaderId == null) {
			throw new IllegalStateException();
		}

		return view("/thanks.jsp", new ThanksView(orderHeaderId));
	}

	@GET
	@Path("/stockShortage")
	public Response showRecoveredCart() {
		FlashService flashService = serviceFactory.getFlashService();

		@SuppressWarnings("unchecked")
		List<StockShortage> shortage = flashService.get(FLASH_STOCK_SHORTAGE, List.class);

		return view("/stockShortage.jsp", new StockShortageView(shortage));
	}
}
