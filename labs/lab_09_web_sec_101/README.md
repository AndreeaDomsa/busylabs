# Web security 101
### Lab Description

Lab 09 contains code examples for:
1. XSS  (Cross-site script injection)
2. CSP  (Content security policy)
3. XSRF (Cross-site request forgery)
5. JWT  (Json Web Token)
6. DoS  (Denial of service)
7. SQLi & NoSQLi (Injection)

These examples are bad (academic purpose only). Really bad. Unbelievably bad. Yuge security risks. Don't copy paste them in your apps. Think first, then act.

## XSS
### Description
Cross-site script injection occurs because input / output is not being sanitized properly or at all. The attacker achieves remote code execution within other users browsers.

Both FE and BE can implement safeguards against it:
1. FE SHOULD sanitize output if necessary
2. FE can sanitize input
3. BE SHOULD sanitize input
4. BE can sanitize output

For our exercise, the following attack vector can be used:
```
<img src='test' onerror='(function(e){alert("Pwned")}).call(this)'>
```

### Resources
* [Code based on this](https://markatta.com/codemonkey/blog/2016/04/18/chat-with-akka-http-websockets/)
* [List of naughty strings](https://github.com/minimaxir/big-list-of-naughty-strings)
* [OWASP](https://www.owasp.org/index.php/Cross-site_Scripting_(XSS))

## CSP
### Description
The content-security-policy allows us to instruct the browser (if supported), how it should treat dynamic resources.

Our attack vector consists of:
* A 'malicious' css and a js resource injected via cdn
* An img with an 'onerror' event
* An inline script
### Resources
* [CSP Home](https://content-security-policy.com/)

## JWT ('jot')
### Description
A json web token is a way of representing certain claims between parties. Access the resource links for more informations. It has multiple advantages, such as:
* Self containment
* Small in size

We will be using PostMan for this exercise. Our attack vector consists of modifying the header containing the auth token and taking advantage of an unsafe usage case of a JWT library:
* Attack vector - eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IklvbiIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTUxNjIzOTAyMn0.
* Safe & valid token - eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IklvbiIsInJvbGUiOiJtZW1iZXIiLCJpYXQiOjE1MTYyMzkwMjJ9.kB1GuviR0sb-RqsfNwv8p0Og468zuYzWZT-tTFW_oUs
### Resources
* [JWT Home 1](https://jwt.io)
* [JWT Home 2](https://www.jsonwebtoken.io/)

## XSRF (sea surf)
### Description
Cross-site request forgery - Attackers making requests on your behalf (closely related to phising)

For our example, the attacker will succeed in transfering some money from our bank account.
### Resources
* [XSRF or CSRF](https://en.wikipedia.org/wiki/Cross-site_request_forgery)

## DDoS / DoS
### Description
A 'Denial of Service' attack consists in flooding a service with requests, denying any legitimate users access to it. 

For this exercise we will be performing a 'Distributed DoS' attack. We will be using an open sourced http load testing tool called [wrk](https://github.com/wg/wrk). Download it, unzip it and 'make' it, then let's deny the lab 9 backend:
```bash
./wrk  -t12 -c800 -d60s http://<LOCAL_IP>:10001/xss
```

The `/xss` endpoint was chosen because it also creates a WebSocket connection -> Extra load.
### Resources
* [DoS](https://www.digitalattackmap.com/understanding-ddos/)
* [Mirai IoT DDos attack](https://www.csoonline.com/article/3258748/security/the-mirai-botnet-explained-how-teen-scammers-and-cctv-cameras-almost-brought-down-the-internet.html)

## SQLi & NoSQLi
### Description
### Resources

# Tips & Resources
Please keep the following things in mind:
* Stay up-to-fucking-date with all your dependencies!
* Hide your shitty version headers or anything that exposes what technologies you're using
* Use HTTPS for everyting (no mixed content with HTTP) including web sockets
* Don't expose more than necessary
* Pay attention to your server / libraries configuration
* Log and monitor! Detect breaches & unexpected behaviour early

