# Ecommerce Application
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
    * Api for user to fetch random single coupon.
    * Once coupon fetched bu user , user cant fetch another coupoun until it is expired or used.
    * admin can see list of user remaining.
  
3.   verifying coupon: api/verify_coupon
     * user verifies non expired or used coupon is used again
     * used soft delete to verify.

4.  importing product: api/verify_coupon.
     *  only admin is allowed to import products.
     *  admin can import product one by one or bulk import by csv.

  5.  add order : api/add_order
      *  One can add Order in cart.
      *  place single order or bulk order

  6.    get orders list
        * user will get his order list while admin can see all order done by user on same api.

  Used Spring security 6 , Later added QR CODE for payment.
    
   
