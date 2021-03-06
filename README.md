# New-generation-puppies
## Telerik Academy final project assignment - Bright Consulting
*****

NGP Payment System is a software product, build for the needs of large Bulgarian telecom. It allows banks and other financial institutions to pay the monthly bills of their customers. Connecting with the app is possible through GUI or through REST interface so that the clients of that application could make use of its APIs and generate automatic payments through their own systems without any human interactions.
	The project is the final assignment at Telerik Academy Alpha – Java course. It is a result of collaboration between two Telerik trainees – Atanas Velev and Deyan Georgiev, a.k.a. Doctor Octopus Team.
	In details, the project is divided in two modules – backend implementation and GUI.
	
1.	The backend logic of the app is developed as a REST API, using the Spring MVC framework. It is designed in 3 parts:
-	Public part (login panel) – accessible without authentications;
-	Private part (user panel) – accessible only for registered users;
-	Administrative part (admin panel) – accessible only for administrators;
 
 As a logged in user, the client is able to process several actions, such as:
- paying a bill of a subscriber;
- receiving a variety of reports – listing all its subscribers, listing all unpaid bills of a particular subscriber, listing personal info about a subscriber – used services, paid amounts for these services, listing max and average amount paid by a subscriber for defined period of time, listing the top 10 subscribers due to the amounts paid for the telecom, listing all payments of its subscribers.

As a logged in administrator, the user will be able to process several other actions, such as:
-	creating user’s accounts and other admin accounts;
-	managing all user’s personal data, except passwords;
-	deleting user accounts;
-	issuing particular bill for a subscriber;

For security reasons, the app is developed using the JWT technology for managing all processes with authentication and authorization issues. 

2.	The UI of the projects is implemented as an independent part of the app in such a way that it could be deployed on separate server. 
It is communicating with the Business module through a REST API with JSON responses.
For serving the UI, he team had used HTML, CSS, Java Script and jQuery.
The two pannels /user’s and admin’s/ are developed as a single-page app.

Snapshots:

![alt text](https://github.com/DoctorOctopusTeam/New-generation-puppies/blob/master/snap1.jpg "Login page")
![alt text](https://github.com/DoctorOctopusTeam/New-generation-puppies/blob/master/snap2.jpg "Admin module")
![alt text](https://github.com/DoctorOctopusTeam/New-generation-puppies/blob/master/snap3.png "User module")

******

## DOCUMENTATION for NGP REST API:
### User Module

 ******
 
| Methods        | HTTP Request | Description  |
| :------------- |:-------------:| :-----:|
| getSubscriberInfo      | GET  /user/info/{phoneNumber} | Gets information about a user – phone number, name, EGN  |
| getAllPayments      | GET /user/payments      |   Gets a list of all payments made by client’s subscribers ordered by the pay date in descending order  |
| getMaxPaidFromSubscriber      | GET /user/reports/max/{phoneNumber}/ {startDate}/{endDate} | Gets the biggest amount paid by a subscriber for service for defined period  |
| getAveragePaidFromSubscriber      | GET /user/reports/average/{phoneNumber}/ {startDate}/{endDate} | Gets the average sum paid from a subscriber for services for defined period  |
| getBiggestAmountsPaidBySubscribers| GET /user/reports/10biggest-amounts | Gets a list of the top 10 subscribers based on the paid amounts for services  |
| usedServicesFromSubscriber      | GET /user/services/ {phoneNumber} | Gets a list of all used services from a subscriber  |
 | getUnpaidBillsBySubscriber      | GET /user/unpaid/ {phoneNumber} | Gets a list of all unpaid bills from a subscriber  |
 | payBill      | POST /user/pay/{id} | Pay a particular bill for a subscribers  |
 
 *******
 
 ### Admin Module:
 
 *********
 
| Methods        | HTTP Request | Description  |
| :------------- |:-------------:| :-----:|
| registerUser      | POST  /admin/registeruser | Creates an account for a client – user with all needed info  |
| registerAdmin      | POST /admin/registeradmin | Creates an account for other admin user – with all needed info  |
| updateClient      | POST /admin/updatecreds | Updates data about a particular user /without password/  |
| changePassword      | POST /admin/changepassword | Changes own password on first logging in  |
| deleteUser      | POST /admin/delete | Deletes a user  |
| issueNewBill      | POST /admin/issuebill | Creates a new bill for a client  |
| listAll      | GET /admin/listAll/{role} |Gets a list of all users, based on the role they have  |
| listAllSubscribers      | GET /admin/subscribers | Gets a list of all subscribers  |




