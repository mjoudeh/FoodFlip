# Food Flip

# Database Setup

1. Install XAMPP.
2. In XAMPP, start Apache and MySQL.
3. Log in to phpMyAdmin by navigating to http://localhost/phpmyadmin/ in your browser.
4. Create a database in phpMyAdmin named "ffdb".
5. Create a table in ffdb named *food_entries* with seven columns:

	* Building (varchar(255))
	* Location (varchar(255))
	* FoodCategory (varchar(10))
	* FoodType (varchar(255))
	* FoodDescription (varchar(255))
	* Votes (int - default 0)
	* syncsts (tinyint - default value 0) - this will possibly be used for syncing new entries.
	
6. Create another table in ffdb named *users* with 2 columns:

	* id (varchar(255))
	* karma (int)

7. In **SubmitScreenActivity** and **SearchScreenActivity** change the *client.post* IP address to the address of your local machine.
8. Create a folder in xampp/htdocs named *foodflip* and copy the contents of the php folder to xampp/htdocs/foodflip.

