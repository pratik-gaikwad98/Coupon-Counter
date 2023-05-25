# Coupon-Counter
Created Coupon counter project using springboot, mvc, security, thymeleaf, postgresql,swagger.
user can see products, get coupon ,apply coupons to the products and order product.

### Created Api for following operations.
1. Creating coupons
2. getting coupon
3. verifying coupon
4. importing product
5. get product by id
6. to check discount on the coupon
7. add order
8. get orders list

### Api Description
1. Creating coupons: api/add_coupon
   * Api to add coupon to the database.
   * only admin is allowed to add coupons.
   * validating coupon fields before adding to database.
   * used jpa to save the coupon in postgresql
   * only admin is able to update coupons list.

 2. Getting coupon: api/request_coupon
    * Api for user to fetch random single coupon, once coupon is fetched .
    
   
