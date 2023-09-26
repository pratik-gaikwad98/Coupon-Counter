package com.couponcounter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.couponcounter.dto.CouponDto;
import com.couponcounter.dto.FilterFeildsDto;
import com.couponcounter.dto.OrderDto;
import com.couponcounter.dto.ProductDiscountDto;
import com.couponcounter.dto.ProductDto;
import com.couponcounter.exceptions.ExcelFileException;
import com.couponcounter.exceptions.InvalidFeildsException;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.requests.JsonRequest;
import com.couponcounter.response.Responses;
import com.couponcounter.security.JwtRequestFilter;
import com.couponcounter.service.AppService;
import com.couponcounter.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/*
 * RestController class
 * handles all api on coupon counter applications 
 */
@RestController
@RequestMapping("/api")
public class 	AppController {

	Logger log = LoggerFactory.getLogger(AppController.class);

	@Autowired
	AppService service;

	@Autowired
	OrderService orderService;
	
	
	/*
	 * API 1-
	 * 
	 * @PostMapping("/add_coupon"): Controller To Add Coupon Taking Request in
	 * JsonRequest Class And Passing the object of coupon in service method
	 * generate Coupon returning Response
	 */
	
	@Operation(summary = "Generate Coupon")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Coupon Generated"),
			@ApiResponse(responseCode = "404", description = "Failed to generate") })
	@PostMapping("/add_coupon")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addCoupon(@RequestBody JsonRequest request) throws InvalidUserException {
		log.info("Api /api/add_coupon is running");
		CouponDto couponDto = service.generateCoupon(request);
		log.debug("couponDto {} is generated and fetched from database succesfully ", couponDto);
		return new ResponseEntity<>(new Responses("success", couponDto, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * API 2- request.getReqId(),	
	 * 
	 * @GetMapping("/request_coupon"): Api to request random valid coupon which
	 * Fetches single Random coupon from the database and Returns it in response
	 */
	@Operation(summary = "Request Coupon")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Coupon Fetched succesfully"), })
	@GetMapping("/get_coupon")
	public ResponseEntity<?> getCoupon(@RequestBody JsonRequest request) throws InvalidUserException {
		log.info("Api: /api/request_coupon is running");
		CouponDto coupon = service.getCoupon();
		log.info("sending coupon in response");
		return new ResponseEntity<>(new Responses("success", coupon, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * API 3-
	 * 
	 * @GetMapping("/verify_coupon/{coupon_id}"): Api to verify coupon verifying
	 * coupon on date and not used earlier
	 */
	@Operation(summary = "Verify Coupon")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Coupon Verified"),
			@ApiResponse(responseCode = "404", description = "Invalid Coupon") })
	@GetMapping("/verify_coupon/{coupon_id}")
	public ResponseEntity<?> verifyCoupon(@PathVariable("coupon_id") int couponId, @RequestBody JsonRequest request) {
		log.info("Api: /api/verify_coupon/{coupon_id} is running");
		Map<Object, Object> map = service.verifyCoupon(couponId);
		log.info("sending coupon in response");
		return new ResponseEntity<>(new Responses("success", map, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()), HttpStatus.OK);
	}

	/*
	 * API 4-
	 *
	 * @PostMapping("/import_product") : api to import products from excel sheet to
	 * database, only unique product names are added, and exceptions are handled
	 */
	@Operation(summary = "Add Products From CSV")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product added Successfull"),
			@ApiResponse(responseCode = "404", description = "Failed to add Product") })
	@PostMapping("/import_product")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> uploadFile(@RequestBody JsonRequest request) throws ExcelFileException, IOException {
		log.info("Api: /api/import_product is running");
		String fileName = request.getData().getFile();
		Map<String, List<ProductDto>> list = service.save(fileName,null);
		log.info("sending product in response");
		return new ResponseEntity<>(new Responses("success", list, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * API 5-
	 * 
	 * @GetMapping("/get_product/{product_id}"): Api to fetch product by id ,
	 * handled exception if product not found in database
	 * 
	 */
	@Operation(summary = "Get Product By id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Getting Product Successfully"),
			@ApiResponse(responseCode = "404", description = "Failed to get Product") })
	@GetMapping("/get_product/{product_id}")
	public ResponseEntity<?> getProduct(@PathVariable("product_id") int productId, @RequestBody JsonRequest request) {
		log.info("Api: /api/get_product/{product_id} is running");
		ProductDto product = service.getProductById(productId);
		log.info("sending product in response");
		return new ResponseEntity<>(new Responses("success", product, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * API 6 -
	 * 
	 * @GetMapping("/apply_coupon/{product_id}/{coupon_id}") : api applies coupon
	 * on product and gets discount on product. verifies coupon is valid and
	 * product is present in table handled exceptions
	 */
	@Operation(summary = "Apply coupon")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "coupon applied to product successfull"),
			@ApiResponse(responseCode = "404", description = "Failed to apply coupon") })
	@GetMapping("/apply_coupon/{product_id}/{coupon_id}")
	public ResponseEntity<?> getProductByDisCountCoupon(@PathVariable("product_id") int productId,
			@PathVariable("coupon_id") int couponId, @RequestBody (required=false) JsonRequest request ) {
		log.info("Api: /api/apply_coupon/{product_id} is running");
		ProductDiscountDto discountedProduct = service.getProductByApplyingCoupon(productId, couponId);
		log.info("sending product in response");
		return new ResponseEntity<>(new Responses("success", discountedProduct, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * API 7 -
	 * 
	 * @GetMapping("/add_order/{product_id}/{coupon_id}"): Add Orders In order Table
	 * Order table is Mapped with coupon id and product id from respective
	 * entities.
	 */
	@Operation(summary = "Add Order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "order added Successfull"),
			@ApiResponse(responseCode = "404", description = "Failed to add Product") })
	@GetMapping("/add_order/{product_id}/{coupon_id}")
	public ResponseEntity<?> addOrder(@PathVariable("product_id") int productId,
			@PathVariable("coupon_id") int couponId, @RequestBody JsonRequest request) {
		log.info("Api: /api/add_order/{product_id}/{coupon_id} is running");
		OrderDto order = service.addOrder(productId, couponId);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("Order", order);
		map.put("Message", "Order Added Succesfully");
		log.info("sending order detail in response");

		return new ResponseEntity<>(new Responses("success", map, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()), HttpStatus.OK);
	}

	/*
	 * API 8 -
	 * 
	 * @GetMapping("/list_orders"): Gets All the Orders Based On Requested inputs.
	 * 
	 */
	@Operation(summary = "list of Order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Getting order list"),
			@ApiResponse(responseCode = "404", description = "Failed to get order list") })
	@GetMapping("/list_orders")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getOrders(@RequestBody JsonRequest request) throws InvalidFeildsException {
		log.info("Api: /api/list_orders is running");
		List<FilterFeildsDto> FeildsDto = service.getAllOrder(request);
		log.info("sending order details in response");

		return new ResponseEntity<>(new Responses("success", FeildsDto, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	/*
	 * Extra for testing purpouse
	 * 
	 * @GetMapping("/getproducts") Testing Api to fetch products from table returns
	 * All the products present in the table
	 */
	@Operation(summary = "Get Products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Getting order list"),
			@ApiResponse(responseCode = "404", description = "Failed to get order list") })
	@GetMapping("/getproducts")
	public ResponseEntity<?> getAllProducts() {
		List<ProductDto> product = service.getAllProducts();
		
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	/*
	 * Extra for testing purpouse
	 * 
	 * @PostMapping("/add_product") Testing Api to add products from table and
	 * returns the products added recently in the table
	 */
	@Operation(summary = "Add Product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product Added"),
			@ApiResponse(responseCode = "404", description = "Failed to add product") })
	@PostMapping("/add_product")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addProduct(@RequestBody JsonRequest request) {
		ProductDto productDto = request.getData().getProduct();
		log.info("Api: /api/add_product is running");

		if (service.generateProduct(productDto)) {
			log.info("Product is Added in List");
		}
		return new ResponseEntity<>(new Responses("success", productDto, JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

}
