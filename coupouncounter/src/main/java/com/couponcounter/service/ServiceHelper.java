package com.couponcounter.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.couponcounter.dto.CouponDto;
import com.couponcounter.dto.ProductDto;
import com.couponcounter.dto.RequestCouponDto;
import com.couponcounter.dto.UsersDto;
import com.couponcounter.entity.Coupon;
import com.couponcounter.entity.Orders;
import com.couponcounter.entity.Product;
import com.couponcounter.entity.Users;
import com.couponcounter.exceptions.InvalidCouponException;
import com.couponcounter.exceptions.InvalidFeildsException;
import com.couponcounter.exceptions.InvalidProductException;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.helper.ObjectMapper;
import com.couponcounter.repository.CouponRepository;
import com.couponcounter.repository.OrderRepo;
import com.couponcounter.repository.ProductRepository;
import com.couponcounter.repository.UsersRepo;
import com.couponcounter.requests.JsonRequest;

/*
 * Helper class contains method needed by other class
 *
 */
public class ServiceHelper {
	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	protected Coupon coupon;

	@Autowired
	protected CouponDto couponDto;

	@Autowired
	protected CouponRepository couponRepo;

	@Autowired
	protected Product product;

	@Autowired
	protected ProductDto productDto;

	@Autowired
	protected ProductRepository productRepo;

	@Autowired
	protected Orders order;

	@Autowired
	protected OrderRepo orderRepo;

	@Autowired
	protected Environment env;

	@Autowired
	protected JdbcTemplate jdbc;

	@Autowired
	protected Users users;

	@Autowired
	protected UsersDto userDto;

	@Autowired
	protected UsersRepo usersRepo;

	Logger log = LoggerFactory.getLogger(ServiceHelper.class);

	/*
	 * method checkCouponDiscount() - Validated Coupon discount is numeric and does
	 * not contains string
	 */
	protected boolean checkCouponDiscount(RequestCouponDto requestCouponDto) {
		String pattern = "^[0-9]+$";
		if (requestCouponDto.getDiscount() == null) {
			throw new InvalidCouponException("coupon discount is 0");
		} else if (requestCouponDto.getDiscount().matches(pattern)) {
			log.info(" Coupon Discount {} is valid", couponDto.getDiscount());
			return false;
		} else {
			log.info("Checking Discount {} is Invalid", couponDto.getDiscount());
		}
		return true;
	}

	/*
	 * isDateValid() : Validates string format.
	 */
	protected boolean isDateValid(RequestCouponDto couponDto) {
		String pattern = "^(20[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		if (couponDto.getValidity() == null) {
			throw new InvalidCouponException("coupon validy is null");
		} else if (couponDto.getValidity().matches(pattern)
				&& couponDto.getValidity().matches("^(20[0-9][0-9])-(0[1-9]|1[012])- 30$") == false) {
			log.info("Coupon Date {} is valid", couponDto.getValidity());
			return false;
		}
		return true;
	}

	/*
	 * checkCouponType():
	 * 
	 * checks coupon type is value or percent is not than throws exception
	 */
	protected boolean checkCouponType(RequestCouponDto couponDto) {
		log.info("Checking Coupon Type {} is value or percent", couponDto.getType());
		if (couponDto.getType() == null) {
			throw new InvalidCouponException("coupon type is null");
		} else if (couponDto.getType().equals("value") || couponDto.getType().equals("percent")) {
			log.info(" Coupon Type is valid", couponDto.getType());
			return false;
		} else {
			log.info(" Coupon Type {} is invalid", couponDto.getType());
		}
		return true;
	}

	protected boolean checkCouponIsUnique(RequestCouponDto couponDto) {
		boolean flag = false;
		log.info("Checking Coupon no. {} is unique", couponDto.getCouponNo());
		Coupon coupon = couponRepo.findByCouponNo(couponDto.getCouponNo());
		if (couponDto.getCouponNo() == null) {
			throw new InvalidCouponException("coupon no. is empty");
		} else if (coupon != null) {
			log.info("Coupon no. {} is unique ", couponDto.getCouponNo());
			flag = true;
		} else {
			log.info("Coupon no. {} is not unique found in database ", couponDto.getCouponNo());
		}
		return flag;
	}

	/*
	 * checkProductIsUnique()
	 * 
	 * validates product is unique or not by fetching data from table. if not unique
	 * it wont allow user to add products
	 * 
	 */
	protected boolean checkProductIsUnique(Product product) {
		boolean flag;
		if (product.getProductName() == null) {
			throw new InvalidProductException("product name is empty");
		}
		Product pro = productRepo.findByProductName(product.getProductName());
		log.info("checking product name is unique");

		if (pro == null) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	/*
	 * Testing Api Method to add Product
	 * 
	 * Add single product in database
	 */

	public boolean generateProduct(ProductDto productDto) {
		log.info("service method processing ");
		Product product = new Product();
		mapper.updateProductDtoToProduct(productDto, product);
		productRepo.save(product);
		log.debug("Product {} generated in database");
		return true;
	}

	/*
	 * Method To get All products From Database
	 * 
	 * fetches all products from database
	 */
	public List<ProductDto> getAllProducts() {
		log.info("service method processing");

		List<Product> products = productRepo.findAllByOrderById();
		List<ProductDto> productsDto = new ArrayList<>();

		for (Product product : products) {

			ProductDto productDto = new ProductDto();

			mapper.updateProductToProductDto(product, productDto);

			productDto.setCreatedAt(product.getCreatedAt());

			productsDto.add(productDto);
		}
		return productsDto;
	}

	/*
	 * is coupon expired or not by checkin date and validy from the database
	 */
	protected void setCouponExpired(int couponId) {
		log.info("setting coupon expired in database");
		coupon = couponRepo.findById(couponId).orElse(null);
		coupon.setCouponValidity(false);
		coupon.setUpdatedBy(usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
		coupon.setUpdatedAt(LocalDateTime.now());
		couponRepo.save(coupon);
	}

	/*
	 * checks coupon is expired or not by checking date and validy from the database
	 */
	protected Boolean isCouponExpired(Coupon coupon) {
		log.debug("Checking coupon is expired");
		boolean flag = true;
		if (coupon.getCouponValidity()) {
			log.info("coupon is valid and can be used");
			flag = false;
		} else {
			log.info("coupon is Expired");
		}
		return flag;
	}

	/*
	 * isCouponNull() : validates coupon is null or not
	 */
	protected Boolean isCouponNull(Coupon coupon) {
		boolean flag = false;
		if (coupon == null) {
			log.info("");
			flag = true;
			throw new InvalidCouponException("No Coupon With This ID");
		} else {
		}
		return flag;
	}

	/*
	 * isProductNull() :
	 * 
	 * verifies product is null.
	 */
	protected Boolean isProductNull(Product product) {
		boolean flag = false;
		if (product == null) {
			flag = true;
			throw new InvalidProductException("No Product With This ID");
		} else {
			log.debug("product with id found in database" + product.getId());
		}
		return flag;
	}

	/*
	 * gets coupons by id
	 */
	protected CouponDto getCouponById(int couponId) {
		Coupon coupon = couponRepo.findById(couponId).orElse(null);

		if (isCouponNull(coupon) == false) {
			log.info("coupon is ", coupon);
			mapper.updateCouponToCouponDto(coupon, couponDto);
		}

		if (isCouponExpired(coupon) == false) {
			log.debug("coupon found with id ", coupon.getId());

			mapper.updateCouponToCouponDto(coupon, couponDto);
		} else {
			log.debug("coupon not found with id ", coupon.getId());
			throw new InvalidCouponException("Coupon Expired");
		}

		return couponDto;
	}

	/*
	 * calculates discount on product
	 * 
	 */
	protected int applyDiscount(CouponDto coupon, ProductDto product) {
		log.info("calculating discount on product ");
		int discountValue = coupon.getType().equalsIgnoreCase("value") ? discountValue = coupon.getDiscount()
				: (product.getMrp() * coupon.getDiscount()) / 100;
		log.debug("disccountValue is {}", discountValue);
		return discountValue;
	}

	/*
	 * method to check limit and offset and creates string
	 */
	protected String subquery4(JsonRequest request) throws InvalidFeildsException {
		int limit = request.getData().getParamData().get(0).getLimit();
		int offset = request.getData().getParamData().get(0).getOffset();
		String query = "";
		if (limit != 0) {
			log.debug("setting limit {} in sub query", limit);
			query += "limit " + limit;
		}

		if (offset != 0) {
			log.debug("setting offset {} in sub query", offset);
			query += " offset " + offset;
		}
		log.debug("subquery for select statement generated is {}", query);
		return query;
	}

	/*
	 * Method to return query based on sorting condition desc or asc
	 */
	protected String subquery3(JsonRequest request) throws InvalidFeildsException {
		String query = "";
		String sort = request.getData().getParamData().get(0).getSorting().getCreatedAt();
		log.debug("checking sorting sequence ", sort);
		if (sort.equalsIgnoreCase("desc") || sort.equalsIgnoreCase("asc")) {
			log.debug("sorting sequence is  {}", sort);
			query = "ORDER BY o.created_at " + sort;
		} else {
			log.debug("invalid sort sequence ", sort);
			throw new InvalidFeildsException("Only DESC or ASC is allowed ");
		}
		log.debug("subquery for select statement generated is {}", query);

		return query;
	}

	/*
	 * Method to return query based on filter condition
	 */
	protected String subquery2(JsonRequest request) throws InvalidFeildsException {
		List<String> brandName = request.getData().getParamData().get(0).getFilterParams().getBrandName();
		String query2 = "";
		log.info("checking brand names and generating sub query");
		if (!brandName.isEmpty()) {
			query2 = "where p.brand = '";
			for (String productFeild : brandName) {
				query2 += productFeild.toLowerCase() + "' OR p.brand = '";
			}
			query2 = query2.substring(0, query2.length() - 15);
			log.debug("sub query is generated succesfully ", query2);
		} else {
			log.debug("failed to generate sub query {} because invalid brand name {} ", query2, brandName);
		}
		log.debug("subquery for select statement generated is {}", query2);

		return query2;
	}

	/*
	 * creating select query for mention by user are valid or not
	 */
	protected String subquery(JsonRequest request) throws InvalidFeildsException {
		List<String> AllFeilds = Arrays.asList(("coupon_id"), ("coupon_no"), ("discount"), ("type"), ("validity"),
				("product_id"), ("product_name"), ("brand"), ("order_id"), ("product_id"), ("coupon_id"), ("mrp"),
				("created_at"), ("discount_value"), ("offered_discount"));
		log.debug(" Creating select sub query ");
		List<String> fields = request.getData().getParamData().get(0).getFields();

		if (fields.isEmpty()) {
			throw new InvalidFeildsException("Columns name are required , write 'all' in feilds");
		}

		String query1 = "";

		if (fields.get(0).equalsIgnoreCase("all")) {
			log.info("query creating for all feilds " + AllFeilds);
			log.debug("checking user feilds are valid ");

			for (String feild : AllFeilds) {
				query1 += checkFeild(feild.toLowerCase()) + ", ";

			}
		} else {
			log.info("query creating for specific feilds " + fields);

			for (String feild : fields) {
				query1 += checkFeild(feild.toLowerCase()) + ", ";

			}
		}
		String query = "SELECT " + query1.substring(0, query1.length() - 2)
				+ " FROM ORDERS o JOIN product p ON o.product_id = p.product_id JOIN coupon c ON o.coupon_id = c.coupon_id";
		log.debug("subquery for select statement generated is {}", query);
		return query;
	}

	@SuppressWarnings("null")
	protected String checkFeild(String feild) throws InvalidFeildsException {
		List<String> coupon = Arrays.asList(("coupon_id"), ("coupon_no"), ("discount"), ("type"), ("validity"));
		List<String> product = Arrays.asList(("product_id"), ("product_name"), ("brand"));
		List<String> order = Arrays.asList(("order_id"), ("product_id"), ("coupon_id"), ("mrp"), ("created_at"),
				("discount_value"), ("offered_discount"));
		String feilds = coupon.contains(feild) ? "c." + feild
				: product.contains(feild) ? "p." + feild : order.contains(feild) ? "o." + feild : null;

		if (feilds == null) {
			log.debug("Param Data Feilds doesnt match");
			throw new InvalidFeildsException("Param Data Feild " + feild + " doesnt match");
		}
		return feilds;
	}

	/*
	 * isFeildsValid()-
	 * 	
	 * check column name mention by user are valid or not
	 */
	protected boolean isFeildsValid(List<String> brandName) throws InvalidFeildsException {
		boolean flag = false;
		for (String brand : brandName) {
			List<Product> product = productRepo.findByBrandIgnoreCase(brand);
			if (product.isEmpty()) {
				log.debug("Brand Name Feild " + brand + " is not valid feild ");
				throw new InvalidFeildsException("Brand Name Feild " + brand + " is not valid feild");
			} else {
				log.debug("Brand Name Feild " + brand + " are valid feilds ");
				flag = true;
			}
		}
		return flag;
	}

	/*
	 * checks coupon feilds are valid
	 */
	protected Boolean isCouponDetailsValid(RequestCouponDto requestCouponDto) {

		log.info("checking coupon number is unique ");
		if (checkCouponIsUnique(requestCouponDto)) {
			throw new InvalidCouponException("Coupon " + requestCouponDto.getCouponNo() + " already Exists");
		}

		log.info("checking Discount number is Digit ");
		if (checkCouponDiscount(requestCouponDto)) {
			throw new InvalidCouponException("Coupon Discount should be numeric");
		}

		log.info("Checking coupon type is value or percent");
		if (checkCouponType(requestCouponDto)) {
			throw new InvalidCouponException(
					"Coupon Type should be value or percent only you entered " + requestCouponDto.getType());
		}

		log.info("Checking Date format ");
		if (isDateValid(requestCouponDto)) {
			throw new InvalidCouponException("invalid date, Date format should be YYYY-MM-DD ,But you entered "
					+ requestCouponDto.getValidity());
		}

		return true;
	}

	protected void updateUserDetail(Orders orders) {
		if (!isRoleAdmin()) {
			Users users = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			orders.setCreatedBy(users);
			users.setCoupon(null);
			usersRepo.save(users);
			orderRepo.save(orders);
		}
	}

	protected boolean validateUserHasActiveCoupon() throws InvalidUserException {
		Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (!isRoleAdmin() && user.getCoupon() != null && !isCouponExpired(user.getCoupon())) {
			return true;
		}
		return false;
	}

	protected boolean isRoleAdmin() {
		Collection<? extends GrantedAuthority> authority = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		boolean isAdmin = authority.stream().anyMatch(a -> a.toString().equals("ROLE_ADMIN"));
		return isAdmin;
	}

	protected void updateUserDetails(Coupon coupon) {
		if (!isRoleAdmin()) {
			Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			user.setCoupon(coupon);
			usersRepo.save(user);
		}
	}

	protected boolean setCoupounStatus(LocalDate validityTime) {
		return !validityTime.isBefore(LocalDate.now());
	}

	public List<Orders> getUserOrder() {
		List<Orders> orders = new ArrayList<>();
		Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		orders = orderRepo.findOrderByUserId(user.getUserId());
		return orders;
	}

	protected Map<String, List<ProductDto>> getAddedProductList(List<Product> products) {
		List<ProductDto> addedProduct = new ArrayList<>();
		List<ProductDto> nonAddedProduct = new ArrayList<>();
		Map<String, List<ProductDto>> productlist = new HashMap<>();
		for (Product product : products) {
			ProductDto productDto = new ProductDto();
			if (checkProductIsUnique(product)) {
				Users user = usersRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
				product.setCreatedBy(user);
				productRepo.save(product);
				mapper.updateProductToProductDto(product, productDto);
				addedProduct.add(productDto);
				log.info("All unique products are Imported Succesfully");
			} else {
				mapper.updateProductToProductDto(product, productDto);
				nonAddedProduct.add(productDto);
			}
		}
		productlist.put("Added Products", addedProduct);
		productlist.put("Products already Exist", nonAddedProduct);

		return productlist;
	}

	public List<String> getUserRole() {
		Collection<? extends GrantedAuthority> role = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		List<String> roleNames = role.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		return roleNames;
	}

}
