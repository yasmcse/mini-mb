# Moneybox Technical Task

## The Brief…
Create a screen that allows a user to login to their moneybox account and display their total plan value.

**Duration: 2 hours**

## What we are looking for…
- A fully working Android application
- Clean coding style
- Demonstration of architecture choices
- UI considerations including validation
- Unit Tests

## The Project…
The project includes the networking code and models to complete the task, you will just need to integrate them and implement the logic.

- The `Networking` class contains the **OkHttp** and **Retrofit** instances with the required headers for an unauthenticated request.
- The `models` package contains the request and response models with all the fields required for the rest of the task.

## Part 1 - Login Screen…
We would like to allow the user to enter their email address and password and login to our app.  Please design your screen according to the wireframes below:

![Login](/images/login.png =100x20)

To login please use the endpoint and credentials below:
```
Endpoint: @POST("users/login")
Email: jaeren+androidtest@moneyboxapp.com
Password: P455word12
```
You can use `LoginRequest` to make your request and `LoginResponse to parse your response.

## Part 2 - Display Plan Value
Once you have logged in, please create a simple screen that displays the user's `TotalPlanValue`.  Please design your screen according to the wireframe below:

![Accounts](/images/accounts.png =100x20)

To retrieve this data please use the endpoint with an additional authorization header, replacing {BEARER_TOKEN_HERE} with the bearer token you retrieved when logging in:

```
Endpoint: @GET("investorproducts")
Header name: Authorization
Header value: Bearer {BEARER_TOKEN_HERE}

```
You can use the `AllProductsResponse` to parse this response and retrieve the plan value.

## How to submit your test…
Please push your project to Github and invite android@moneyboxapp.com to view your project.