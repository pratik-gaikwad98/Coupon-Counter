package com.couponcounter.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.couponcounter.dto.CouponDto;
import com.couponcounter.dto.FilterFeildsDto;
import com.couponcounter.dto.OrderDto;
import com.couponcounter.dto.ProductDiscountDto;
import com.couponcounter.dto.ProductDto;
import com.couponcounter.dto.RequestCouponDto;
import com.couponcounter.entity.Coupon;
import com.couponcounter.entity.Orders;
import com.couponcounter.entity.Product;
import com.couponcounter.entity.Users;
import com.couponcounter.exceptions.ExcelFileException;
import com.couponcounter.exceptions.InvalidCouponException;
import com.couponcounter.exceptions.InvalidFeildsException;
import com.couponcounter.exceptions.InvalidProductException;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.helper.CsvHelper;
import com.couponcounter.requests.Data;
import com.couponcounter.requests.JsonRequest;

@Service
public class AppService extends ServiceHelper {
	Logger log = LoggerFactory.getLogger(AppService.class);

	/*
	 * Method To Generate Coupon-
	 * 
	 * generateCoupon(JsonRequest request) - takes response in dto used mapper to
	 * map entity to dao and adds data in database before adding it validates all
	 * fields date format, coupon type,coupon is unique. fetches the saved coupon
	 * maps in dto and returns the response
	 * 
	 */
	public CouponDto generateCoupon(JsonRequest request) throws InvalidUserException {
		log.info("generateCoupon Method called and processing request");
		log.debug("Post request received for coupon {} ", request.getData().getCoupon());
		if (isCouponDetailsValid(request.getData().getCoupon())) {
			log.info("coupon is valid");
			Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			coupon = new Coupon(request.getData().getCoupon().getCouponNo(),
					Integer.parseInt(request.getData().getCoupon().getDiscount()),
					request.getData().getCoupon().getType(),
					LocalDate.parse(request.getData().getCoupon().getValidity()), user,
					setCoupounStatus(LocalDate.parse(request.getData().getCoupon().getValidity())));
			couponRepo.save(coupon);
			log.info("coupon saved succesfully");
			coupon = couponRepo.findByCouponNo(coupon.getCouponNo());
			mapper.updateCouponToCouponDto(coupon, couponDto);
		}
		return couponDto;
	}

	/*
	 * Method To Get Coupon
	 * 
	 * getCoupon() - Used Random clas to fetch any random coupon from database
	 * mapped from entity to dto and returns in response
	 * 
	 */
	public CouponDto getCoupon() throws InvalidUserException {
		log.info("getCoupon() method processing");

		if (!validateUserHasActiveCoupon()) {
			Coupon coupon = couponRepo.getRandomCoupon();
			updateUserDetails(coupon);
			mapper.updateCouponToCouponDto(coupon, couponDto);
		} else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			mapper.updateCouponToCouponDto(usersRepo.findByEmail(username).getCoupon(), couponDto);
		}
		return couponDto;
	}

	/*
	 * method to Check Valid Coupons
	 * 
	 * verifyCoupon(int id)- checks is coupon is null or coupon is expired based on
	 * conditions it verfies the coupon and returns it.
	 * 
	 */
	public Map<Object, Object> verifyCoupon(int id) {
		log.info("verifyCoupon(int id) method processing");

		Map<Object, Object> map = new HashMap<Object, Object>();

		Coupon coupon = couponRepo.findById(id).orElse(null);
		log.debug("coupon is " + coupon);
		if (!isCouponNull(coupon) && !isCouponExpired(coupon)) {
			mapper.updateCouponToCouponDto(coupon, couponDto);
			log.info("coupon is verified on date is not expired and coupon is present in database");
			map.put("message", "Valid Coupon");

			map.put("coupon", couponDto);
		} else {
			log.info("coupon is expired");
			throw new InvalidCouponException("Coupon Expired");
		}
		return map;
	}

	/*
	 * Method to Import Csv file to Database
	 * 
	 * save(String file) - Take file name in request verifies name is present in
	 * directory and if it is present it reads the file it saves the products one at
	 * time. throws excetion if file not found in directory or different format is
	 * uploaded.
	 *
	 */

	public Map<String, List<ProductDto>> save(String fileName, MultipartFile file)
			throws ExcelFileException, IOException {
		log.info("save(String file) method processing, excel file path is \" + \"excel.file.path");
		Map<String, List<ProductDto>> productList = new HashMap<>();
		try {
			List<Product> products = new ArrayList<>();
			try {
				if (fileName != null) {
					products = CsvHelper.readCsvFileName(env.getProperty("excel.file.path") + fileName);
				} else {
					products = CsvHelper.readCsvMultipartFile(file);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("Products in excel sheet are " + products);
			productList = getAddedProductList(products);
		} catch (ExcelFileException e) {
			throw new ExcelFileException(
					"No File With Name " + file + " in directory" + env.getProperty("excel.file.path"));
		}
		return productList;
	}

	/*
	 * Method To get product by id From Database
	 * 
	 * getProductById(int productId) - Verifying product by id is present in
	 * database if product is not found throws exception else send product object in
	 * response
	 * 
	 */
	public ProductDto getProductById(int productId) {
		log.info("getProductById(int productId) method processing");
		Product product = productRepo.findById(productId).orElse(null);
		log.info("Fetched Product By id " + product);
		if (!isProductNull(product)) {
			mapper.updateProductToProductDto(product, productDto);
		} else {
			log.info("product is null in  getProductById(int productId) ");
			throw new InvalidProductException("NO Product with id " + productId);
		}
		return productDto;
	}

	/*
	 * Method to get product after applying coupon
	 * 
	 * getProductByApplyingCoupon(int productId, int couponId) - verifies coupon and
	 * product is valid calculates discount and offers discounted value to user.
	 */
	public ProductDiscountDto getProductByApplyingCoupon(int productId, int couponId) {
		log.info("getProductByApplyingCoupon method processing");

		ProductDiscountDto productDiscountDto = new ProductDiscountDto();
		ProductDto product = getProductById(productId);
		log.debug("Product is " + productId);

		CouponDto coupon = getCouponById(couponId);
		log.debug("coupon is " + coupon);

		int discount = applyDiscount(coupon, product);
		log.debug("After applying coupon={}, on product = {},  discount={} , discount type ={}", coupon, product,
				discount, coupon.getType());

		int discountOffered = product.getMrp() - discount;
		log.debug("After applying coupon={} on product = {}  offered amount ={}", coupon, product, discountOffered);

		productDiscountDto = new ProductDiscountDto(product.getId(), product.getProductName(), product.getBrand(),
				product.getMrp(), product.getCreatedAt(), coupon.getId(), coupon.getCouponNo(), coupon.getDiscount(),
				coupon.getType(), coupon.getValidity(), discount, discountOffered);

		return productDiscountDto;
	}

	/*
	 * Method to Add order in database
	 * 
	 * addOrder(int productId, int couponId) - Method to add products in Order List
	 * verifies coupon and product is valid applies discount updates the order table
	 * in database .
	 */

	public OrderDto addOrder(int productId, int couponId) {
		log.info("addOrder method processing");
		int discount = applyDiscount(getCouponById(couponId), getProductById(productId));
		log.debug("After applying coupon={}, on product = {},  discount={} , discount type ={}", coupon, product,
				discount, coupon.getType());
		Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int discountOffered = getProductById(productId).getMrp() - discount;
		log.debug("After applying coupon={} on product = {}  offered amount ={}", coupon, product, discountOffered);

		Orders orders = new Orders(productRepo.findById(productId).orElse(null),
				couponRepo.findById(couponId).orElse(null), getProductById(productId).getMrp(), discount,
				discountOffered, user);
		orderRepo.save(orders);
		log.info("order added succesfully");
		OrderDto orderDto = new OrderDto(orders.getOrderId(), orders.getProduct().getId(), orders.getCoupon().getId(),
				orders.getMrp(), orders.getCreatedAt(), orders.getDiscountValue(), orders.getOfferedDiscount(),
				orders.getCreatedBy().getUserId());
		updateUserDetail(orders);
		log.info("coupon used and expired coupon id ={} ", couponId);
		setCouponExpired(orders.getCoupon().getId());
		return orderDto;
	}

	/*
	 * Method to Get Order List
	 * 
	 * getAllOrder(JsonRequest request) - BAsed On conditions of user limits offset
	 * filter and user defined column it generated the query contains 4 sub query
	 * concatenates in single query and with help of jdbc executing it.
	 * 
	 */

	public List<FilterFeildsDto> getAllOrder(JsonRequest request) throws InvalidFeildsException {
		log.info("getAllOrder method processing ");
		String OrderQuery = subquery(request) + " " + subquery2(request) + " " + subquery3(request) + " "
				+ subquery4(request);
		List<FilterFeildsDto> FeildsDto = jdbc.query(OrderQuery, new BeanPropertyRowMapper<>(FilterFeildsDto.class));
		return FeildsDto;
	}

//	public CouponDto createCoupon(CouponDto couponDto) {
//		JsonRequest request = new JsonRequest();
//		Data data = new Data();
//		data.setCoupon(new RequestCouponDto(couponDto.getId(), couponDto.getCouponNo(), "" + couponDto.getDiscount(),
//				couponDto.getType(), couponDto.getValidity().toString()));
//		request.setData(data);
//		return generateCoupon(request);
//	}
	
	public CouponDto createCoupon(RequestCouponDto couponDto) {
		JsonRequest request = new JsonRequest();
		Data data = new Data();
		data.setCoupon(couponDto);
		request.setData(data);
		return generateCoupon(request);
	}

	public List<Coupon> getAllCoupon() {
		List<Coupon> coupon = couponRepo.getAllCoupon();
		return coupon;
	}

	public OrderDto viewOrder(int orderId) {
		Orders orders = orderRepo.findById(orderId).orElse(null);
		OrderDto orderDto = new OrderDto(orders.getOrderId(), orders.getProduct().getId(), orders.getCoupon().getId(),
				orders.getMrp(), orders.getCreatedAt(), orders.getDiscountValue(), orders.getOfferedDiscount(),
				orders.getCreatedBy().getUserId());
		return orderDto;
	}

	public List<Orders> getAllOrders() {
		List<Orders> orders = orderRepo.findAll();
		return orders;
	}
}
