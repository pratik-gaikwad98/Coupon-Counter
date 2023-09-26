package com.couponcounter.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.couponcounter.dto.CouponDto;
import com.couponcounter.dto.OrderDto;
import com.couponcounter.dto.ProductDiscountDto;
import com.couponcounter.dto.ProductDto;
import com.couponcounter.dto.RequestCouponDto;
import com.couponcounter.entity.Coupon;
import com.couponcounter.entity.Orders;
import com.couponcounter.exceptions.ExcelFileException;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.service.AppService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/demo")
public class WebAppController {

	@Autowired
	AppService appService;

	@GetMapping("/get_coupon")
	public String getCoupon(Model model) throws InvalidUserException {
		CouponDto coupon = appService.getCoupon();
		model.addAttribute("coupon", coupon);
		model.addAttribute("authorization", appService.getUserRole());
		return "coupon";
	}

	@GetMapping("/index")
	public String landingPage(Model model, Authentication authentication, HttpSession httpSession) {
		httpSession.setAttribute("loggedInPerson", authentication.getName());
		model.addAttribute("authorization", appService.getUserRole());
		return "index";
	}

	@GetMapping("/products_list")
	public String productsList(Model model) {
		List<ProductDto> productsDto = appService.getAllProducts();
		model.addAttribute("productDto", productsDto);
		model.addAttribute("authorization", appService.getUserRole());
		return "products_list";
	}

	@GetMapping("/apply_coupon")
	public String getProductByDisCountCoupon(Model model) {
		model.addAttribute("productDiscountDto", new ProductDiscountDto());
		model.addAttribute("authorization", appService.getUserRole());
		return "apply_coupon";
	}

	@PostMapping("/apply_coupon/product_details")
	public String applyCoupon(@ModelAttribute ProductDiscountDto productDiscountDto, Model model, HttpSession session) {
		try {
			ProductDiscountDto discountedProduct = appService
					.getProductByApplyingCoupon(productDiscountDto.getProductId(), productDiscountDto.getCouponId());
			session.setAttribute("productId", productDiscountDto.getProductId());
			session.setAttribute("couponId", productDiscountDto.getCouponId());
			model.addAttribute("productDiscountDto", discountedProduct);
			model.addAttribute("authorization", appService.getUserRole());
			return "product_after_applying_coupon";
		} catch (Exception e) {
			model.addAttribute("productDiscountDto", new ProductDiscountDto());
			model.addAttribute("error", "Request Failed! : " + e.getMessage());
			model.addAttribute("authorization", appService.getUserRole());
			return "apply_coupon";
		}
	}

	@GetMapping("/add_coupon")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String mainPage(Model model) {
		//model.addAttribute("couponDto", new CouponDto());
		model.addAttribute("requestCouponDto", new RequestCouponDto());
		model.addAttribute("authorization", appService.getUserRole());
		return "AddCoupon";
	}

	@PostMapping("/add_coupon")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addCoupon(@ModelAttribute RequestCouponDto couponDto, Model model)
			throws InvalidUserException {
		try {
			appService.createCoupon(couponDto);
			model.addAttribute("message", "Coupoun Added Succesfully");
			model.addAttribute("authorization", appService.getUserRole());

			return "AddCoupon";
		} catch (Exception e) {
			model.addAttribute("error", "Request Failed! : " + e.getMessage());
			model.addAttribute("couponDto", new RequestCouponDto());
			model.addAttribute("authorization", appService.getUserRole());
			return "AddCoupon";
		}
	}

	@GetMapping("/order")
	public String getOrder(Model model, HttpSession session) {
		OrderDto orderDto = appService.viewOrder((int) session.getAttribute("orderId"));
		model.addAttribute("OrderDto", orderDto);
		model.addAttribute("authorization", appService.getUserRole());
		return "order";
	}

	@PostMapping("/add_order")
	public String getOrder(@ModelAttribute OrderDto orders, Model model, HttpSession session) {
		try {
			System.out.println(
					(int) session.getAttribute("productId") + "     ||      " + (int) session.getAttribute("couponId"));
			OrderDto OrderDto = appService.addOrder((int) session.getAttribute("productId"),
					(int) session.getAttribute("couponId"));
			System.err.println(OrderDto);
			model.addAttribute("OrderDto", OrderDto);
			model.addAttribute("authorization", appService.getUserRole());
			session.setAttribute("orderId", OrderDto.getId());
			return "redirect:/demo/order";
		} catch (Exception e) {
			model.addAttribute("error", "Request Failed! : " + e.getMessage());
			model.addAttribute("order", new OrderDto());
			model.addAttribute("authorization", appService.getUserRole());
			return "product_after_applying_coupon";
		}
	}

	@GetMapping("/get_order")
	public String getUserOrder(Model model) {
		List<Orders> orders = appService.getUserOrder();
		model.addAttribute("order", orders);
		model.addAttribute("authorization", appService.getUserRole());
		return "user_orders";
	}

	@GetMapping("/get_all_coupons")
	public String getcoupons(Model model) {
		List<Coupon> coupons = appService.getAllCoupon();
		model.addAttribute("coupons", coupons);
		model.addAttribute("authorization", appService.getUserRole());
		return "coupons";
	}

	@GetMapping("/import_csv")
	public String upload_csv(Model model) throws ExcelFileException, IOException {
		model.addAttribute("authorization", appService.getUserRole());
		return "order_csv";
	}
	
	@PostMapping("/import_csv")
	public String uploadFile(@RequestParam MultipartFile file,Model model) throws ExcelFileException, IOException {
		try {
			Map<String, List<ProductDto>> productList = appService.save(null, file);
			model.addAttribute("productList",productList );
			model.addAttribute("authorization", appService.getUserRole());
			model.addAttribute("message", "file uploaded succesfully");
			//return "redirect:/demo/product_result";
			return "productresult";
		} catch (Exception e) {
			model.addAttribute("error", "Request Failed! : " + e.getMessage());
			model.addAttribute("authorization", appService.getUserRole());		
			return "order_csv";
		}			
	}
	
	@GetMapping("/get_all_order")
	public String getAllOrder(Model model) {
		List<Orders> orders = appService.getAllOrders();
		model.addAttribute("order", orders);
		model.addAttribute("authorization", appService.getUserRole());
		return "user_orders";
	}

}