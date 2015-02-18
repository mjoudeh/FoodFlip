# Food Flip

# Database Setup

1. Install XAMPP.
2. In XAMPP, start Apache and MySQL.
3. Log in to phpMyAdmin by navigating to http://localhost/phpmyadmin/ in your browser.
4. Create a database in phpMyAdmin named "ffdb".
5. Create a table in ffdb with three columns: 
		*FoodType (varchar(255))
		*FoodDescription (varchar(255))
		*syncsts (tinyint - default value 0) - this will possibly be used for syncing new entries.
6. In **SubmitScreenActivity** change the *client.post* IP address to the address of your local machine.

