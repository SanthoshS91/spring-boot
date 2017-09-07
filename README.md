# Java Coding - Santhosh #

Objective
=========
A fictional client has an existing Micro Service to fetch order and product information. The job is to add a new `Search REST API` to display a list of potential matches. 

Classes
===========
1. SearchResource.java - Controller for all the search functionalities
2. SearchService.java - Service layer to call the respective Repositories
3. All the classes under the test package(src.test.java) - Junit/Spring Integartion Test Cases
4. OrderRepository.java - Added methods for the first three criteria
5. ProductRepository.java - Added a method for the fourth criteria

How to compile and run the application?
======================================
1. Import the package from the git.
2. Goto the repository path /java-assessment-solution/ from the command line.
3. using the "mvn spring-boot:run" command to run the application.

OR

==
            
1. Import the package from the git.
2. Goto the repository path /java-assessment-solution/ from the command line.
3. Give the following commands
	a. mvn clean install
4. Import the workspace in STS.
5. Right click on the orders application and run as spring-boot

Task 1) ###
Goal is to add a new generic `Search REST API` to retrieve all relevant information matching the following criteria:

#### Criteria 1: Filter all the orders which are shipped. ####
Use the below url to run this criteria

http://localhost:8088/search/orders/{status}  - where status = shipped (http://localhost:8088/search/orders/shipped)

#### Criteria 2: Filter all the orders where discount has been applied. ####
Use the below url to run this criteria

http://localhost:8088/search/orders/discounted

#### Criteria 3: Filter all the orders having more that two products in the transaction. ####
Use the below url to run this criteria

http://localhost:8088/search/orders?productCount=2

value of productCount represents the number of products greater than the given inputed number 
productCount value is dynamic - you can try with other numbers to check various conditions

#### Criteria 4: Filter all the products whose price is more than $30. ####
Use the below url to run this criteria

http://localhost:8088/search/products?minPrice=30

value of minPrice represents the number of products greater than the given inputed number 
minPrice value is dynamic - you can try with other numbers to check various conditions


How to run the suite of automated tests?
========================================
1. Import the workspace in STS 
2. Goto the test package (src.test.java)
3. Right click on the pacakge and "Run as Junit cases".
4. Install EClEmma Plugin from Eclipse MarketPlace.
5. Right click on the same package and select "coverage as junit" to view the overall coverage.


Other API Endpoints
======================
1) List Orders:
   **[GET]** `http://localhost:8088/orders`

2) Fetch Order Details:
   **[GET]** `http://localhost:8088/orders/{order_id}`

3) List Products:
   **[GET]** `http://localhost:8088/products`

4) Fetch Product Details:
   **[GET]** `http://localhost:8088/products/{product_id}`

Technical Information
=====================
 * Java 8.x
 * Maven 3.x
 * Spring Framework 4.x
 * Spring Boot 1.5.6
 * Hibernate
 * JPA
 * H2 database
 * JUnit 4.x
 * Mockito 2.x
 * Hamcrest
 * Spring Integration Tests
