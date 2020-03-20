## CreditCardProcessor
This project is build using spring boot 2.2.4-RELEASE and H2 as in-memory db.

### APIs
There are 2 APIs written to store card and get all cards from underlying DB

### Endpoints
1. GET : http://localhost:8080/api/v1/cards/

2. POST: http://localhost:8080/api/v1/cards/

   Request Body:<br/><code>
{
   "cardNumber":"3719 576543 21 0010",
   "cardHolderName" :"Gagan",
   "limit":"11.20"
}</code>

Also each credit card will be validated against Luh10 Algorithm before storing into DB

### Access H2-Console
http://localhost:8080/h2-console/

<code>
Username: gagan

Password: preet
</code>

### Front-end
React App: https://github.com/gaganpreetsingh/Card-React-Front-end.git

You can also test these APIs using front end build in React
