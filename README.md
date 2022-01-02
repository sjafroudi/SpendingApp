# CPSC 210 Term Project

## My Wallet

For the term project, I have decided to create an application which will allow users to track their finances. In my experience using banking applications, I have found that they do a decent job of classifying your expenses into categories (i.e., restaurants, groceries, gas). However, for this application, I would like to allow the user to be able to decide their *exact categories of spending*. For instance, if someone is interested in knowing how much they spend on Chinese takout in particular, they could create a category called *Chinese Takeout*. Or if someone would like to know how much they spend on parking in a month, they could create a category for that as well. I would also like to add functionality which allows you to assess your spending in these particular categories over time. This would allow the application user to track their spending behaivor. 

## User Stories

* As a user, I want to be able to create a new spending categories and add it to a list of spending categories
* As a user, I want to be able to select a spending category and add a transaction
* As a user, I want to be able to view a list of transactions for each spending category
* As a user, I want to be able to view the total balance of each spending category
* As a user, I want to be able to delete transactions
* As a user, I want to be able to save all newly added categories and transactions to a file
* As a user, I want to be able to load my spending data from a file

## Phase 4: Task 3

In order to improve the design of my application, I would refactor my gui class into multiple classes to reflect each unique panel. As such, I would draw associations from each gui class to each of the classes in my model folder accordingly. I think this would result in a simpler UML diagram and a more organized structure for my application.