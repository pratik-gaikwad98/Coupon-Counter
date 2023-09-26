package com.couponcounter.service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.couponcounter.dto.FilterFeildsDto;
import com.couponcounter.exceptions.InvalidFeildsException;
import com.couponcounter.requests.JsonRequest;

@Service
public class OrderService {
	@Autowired
	protected JdbcTemplate jdbc;

	Logger log = LoggerFactory.getLogger(ServiceHelper.class);

	public List<FilterFeildsDto> getAllOrder(JsonRequest request) throws InvalidFeildsException {
	    log.info("getAllOrder method processing");
	    String orderQuery = buildOrderQuery(request);
	    System.out.println(orderQuery);
	    List<FilterFeildsDto> feildsDto = jdbc.query(orderQuery, new BeanPropertyRowMapper<>(FilterFeildsDto.class));
	    return feildsDto;
	}

	private String buildOrderQuery(JsonRequest request) throws InvalidFeildsException {
	    String fieldsQuery = buildFieldsQuery(request);
	    String brandQuery = buildBrandQuery(request);
	    String sortingQuery = buildSortingQuery(request);
	    String limitOffsetQuery = buildLimitOffsetQuery(request);

	    return "SELECT " + fieldsQuery +
	            " FROM ORDERS o " +
	            "JOIN product p ON o.product_id = p.product_id " +
	            "JOIN coupon c ON o.coupon_id = c.coupon_id " +
	            brandQuery +
	            sortingQuery +
	            limitOffsetQuery;
	}

	private String buildFieldsQuery(JsonRequest request) throws InvalidFeildsException {
	    List<String> fields = request.getData().getParamData().get(0).getFields();
	    List<String> allFields = Arrays.asList("coupon_id", "coupon_no", "discount", "type", "validity",
	            "product_id", "product_name", "brand", "order_id", "product_id", "coupon_id",
	            "mrp", "created_at", "discount_value", "offered_discount");

	    log.debug("Creating fields query");

	    if (fields.isEmpty()) {
	        throw new InvalidFeildsException("Columns name are required, write 'all' in paramdata fields");
	    }

	    if (fields.contains("all")) {
	        log.info("Query creating for all fields: " + allFields);
	        fields = allFields;
	    } else {
	        log.info("Query creating for specific fields: " + fields);
	    }

	    List<String> formattedFields = fields.stream()
	            .map(t -> {
					try {
						return checkField(t);
					} catch (InvalidFeildsException e) {
						e.printStackTrace();
					}
					return t;
				})
	            .collect(Collectors.toList());

	    return String.join(", ", formattedFields);
	}

	private String buildBrandQuery(JsonRequest request) {
	    List<String> brandNames = request.getData().getParamData().get(0).getFilterParams().getBrandName();
	    log.debug("Checking brand names and generating brand query");

	    if (!brandNames.isEmpty()) {
	        String formattedBrandNames = brandNames.stream()
	                .map(name -> "'" + name.toLowerCase() + "'")
	                .collect(Collectors.joining(", "));

	        return "WHERE p.brand IN (" + formattedBrandNames + ")";
	    } else {
	        return "";
	    }
	}

	private String buildSortingQuery(JsonRequest request) throws InvalidFeildsException {
	    String sort = request.getData().getParamData().get(0).getSorting().getCreatedAt();
	    log.debug("Checking sorting sequence: " + sort);

	    if (sort.equalsIgnoreCase("desc") || sort.equalsIgnoreCase("asc")) {
	        log.debug("Sorting sequence is " + sort);
	        return "ORDER BY o.created_at " + sort + " ";
	    } else {
	        log.debug("Invalid sort sequence: " + sort);
	        throw new InvalidFeildsException("Only DESC or ASC is allowed");
	    }
	}

	private String buildLimitOffsetQuery(JsonRequest request) {
	    int limit = request.getData().getParamData().get(0).getLimit();
	    int offset = request.getData().getParamData().get(0).getOffset();
	    String query = "";

	    if (limit != 0) {
	        log.debug("Setting limit " + limit + " in query");
	        query += "LIMIT " + limit;
	    }

	    if (offset != 0) {
	        log.debug("Setting offset " + offset + " in query");
	        query += " OFFSET " + offset;
	    }

	    return query;
	}

	private String checkField(String field) throws InvalidFeildsException {
	    List<String> couponFields = Arrays.asList("coupon_id", "coupon_no", "discount", "type", "validity");
	    List<String> productFields = Arrays.asList("product_id", "product_name", "brand");
	    List<String> orderFields = Arrays.asList("order_id", "product_id", "coupon_id", "mrp", "created_at",
	            "discount_value", "offered_discount");

	    String formattedField = null;

	    if (couponFields.contains(field)) {
	        formattedField = "c." + field;
	    } else if (productFields.contains(field)) {
	        formattedField = "p." + field;
	    } else if (orderFields.contains(field)) {
	        formattedField = "o." + field;
	    }

	    if (formattedField == null) {
	        log.debug("Param Data Field doesn't match: " + field);
	        throw new InvalidFeildsException("Param Data Field " + field + " doesn't match");
	    }

	    return formattedField;
	}

}
