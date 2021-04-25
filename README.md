# Expense Reimbursement System

## Project Description
This is a management system that handles company reimbursement requests. There are two account types that are available: Finance Manager (admin) and Employee. An Employee is able to submit a reimbursement and a Finance Manager is able to approve or deny it.

## Technologies Used
- Java
- JavaScript
- HTML
- CSS
- JDBC
- AWS RDS
- PostgreSQL
- Javalin

## Features
 **All Users** 
 - Sign up for a new account
  - Log into accounts

**Employee Account**
 - Submit categorized requests for reimbursements
 - Attach pictures of recepits to rreimbursement equests
 - View all previous reimbursement requests based on their status
 - Filter requests by their id number

**Finance Manager (Admin) Account** 
 - Approve or deny pending reimbursement requests
 - Change the account type of any registered account
- Filter reimbursements by id
- View attached images
 - Sort reimbursements by their status

# To-Do
- Add encryption to passwords before storing them in database
- Add a frame to display pictures, rather than opening them in a new tab
- Ability to update and delete account

## Getting Started

    git clone https://github.com/houstonlws/ExpenseReimbursementSystem

## Usage

1. **Login Page** - This is where a user can enter their credentials and login to their account, or they can navigate to the registration page to sign up for an account.

    ![Login](./screenshots/login-page.jpg)

2. **Registration Page** - This is where a user can sign up for an account. By default, all accounts are employee accounts. In order to obtain a finance manager account, another finance manager muust set the account type to admin.

    ![Register](./screenshots/registration-page.jpg)

3. **Employee Dashboard** - Once loggin into an employee account, the user is redirected to the Employee Dashboard. Their user information is displayed in the upper left portion of the screen and as well as some filtering options to display previous reimbursement requests by their status. At the top is a search bar will filter through all lists based on the id number of the request.

    ![Employee-Dashboard](/screenshots/employee-dashboard-home.jpg)
<!-- - A) Submit a new reimbursement
- B) View all submitted reimbursement requests
- C) View all approved reimbursement requests
- D) View all denied reimbursement requests
- E) View all pending reimbursement requests -->

4. **Finance Manager Dashboard** - 

    ![Admin-Dashboard](./screenshots/admin-all.jpg)