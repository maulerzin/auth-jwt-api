# Client Register and Authentication API
Description: With this API developers will be able to register and Authenticate clients in a system.
>Version: 1.0.0.

>Technologies: For this API i've used: 

>springboot framework with java 11.

>PostgreSQL to store DATA. 

>For security essues i choose to use spring security and JWT.

# Testing
>This application is avaiable in Heroku with the base URL: https://auth-jwt-api.herokuapp.com

# Resources
> POST(/api/auth/signup)

>Description: This resource rigerters a client and store the rececived data in a table "clients".

> Payload Example :

 {

	"email":"user10@test.com",

	"username": "user10",

	"password":"123456789"

}

> POST(/api/auth/login)

>Description: This resource logs in the system.

> Payload Example :

 {

	"username": "usuario2",

	"password":"123456789"

}
