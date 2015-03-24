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

7. In **MainActivity**, **SubmitScreenActivity**, and **SearchScreenActivity** change the *client.post* IP address to the address of your local machine. There are 2 *client.post*'s in MainActivity and 1 in each of the other classes.
8. Create a folder in xampp/htdocs named *foodflip* and copy the contents of the php folder to xampp/htdocs/foodflip.
9. In XAMPP click on *Shell*.
10. In the bash that opens up, type *mysql -u root -p*. When it asks for a password, just press ENTER.
11. You should now see 'mysql>' on the left. Type the following, pressing ENTER after each input:

	* use mysql;
	* UPDATE mysql.user
	* SET Password=PASSWORD("admin")
	* WHERE User="root";
	* FLUSH PRIVILEGES;
	* exit
	
12. Back in XAMPP click on *Config* for *Apache*, then click *phpMyAdmin (config.inc.php)*.
13. Find the line that says "$cfg['Servers'][$i]['password'] = '';" and change it to "$cfg['Servers'][$i]['password'] = 'admin';".
	

