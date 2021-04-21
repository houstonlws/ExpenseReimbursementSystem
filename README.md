This is a management system that handles company reimbursement requests. There are two account types that are available: Finance Manager (admin) and Employee. An Employee is able to submit a reimbursement and a Finance Manager is able to approve or deny it. 

This ia we full stack web application that utilizes Java, JDBC, PostgresSQL, Javalin, HTML, CSS and JavaScript. Below are some of the webpages and their features.

Login:
	-user is able to log in to their account or navigate to registration page

Register: 
	- user is able to sign up for a new account

Admin (Finance Manager)  Dashboard:
	- must be logged in to access
	- only visible to finance managers
	- can view all accounts and account type
	- can set account type
	- can see all requests (Approved, Denied, and Pending)
	- can approve or deny pending requests
	- can view a list of requests by request status
	- can filter through each list by request id
	- can view images attached to requests

Employee Dashboard:
	- must be logged in to access
	- visible to both finance managers and employees
	- can submit requests for reimbursements
	- can attach images to requests
	- can view all previous requests and filter by status
	- can filter though lists by request id
