<h1>Server Side</h1>
Change Apache port to 80, mysql port to 3306. Copy the webservice folder and stick it into the (for mac its) /Applications/MAMP/htdocs in your computer or w/e the folder is that is default in your LAMP/XAMPP/WAMP/MAMP stack.

<h1>1.</h1>
This step you might not have to do, might be only for linux... Must edit DocumentRoot field which is found inside of the 000-default.conf (sometimes called default.conf). You probably have to google something like default.conf file location for YOUR-OS-HERE. Change the directory of DocumentRoot to the appropriate directory your web files are hosted.

Ex: For me (Ubuntu 14.10) it was: - /apache2/sites-enabled/000-default.conf - Changed DocumentRoot to my ~/Coding/Servers/www/webservice directory - Save

<h1>2.</h1>
Next we must edit the apache2.conf file (Its named apache2.conf for me, might be diff for you. Google it) Ex: for me it was: - /etc/apache2/apache2.conf - I added the following code below. (Note, the > that comes before Directory on the first and last lines should be a lessthan sign instead, so flip it.

>Directory ~/Coding/Server/www/webservice>
 Options Indexes FollowSymLinks
 AllowOverride None
  Require all granted
>/Directory>
<h1>3.</h1>
After this is done, reload apache and see if you can see default page.

<h1>4.</h1>
Configure conf.inc.php to connect to MYSQLDB using your information. The fields you probably need to change are: username password These are the username/password of your mysqldb.

<h1>5.</h1>
Start your server and visit http://localhost/lohin.php and play around with the different files inside of the webservice folder. If everything is updating, posting, etc then youre server is good to go and youre ready to configure your Android side.

<h1>Android Side</h1>
Save the MySQLTest folder to your computer and import it into android studio.

<h1>1.</h1>
Change the server location to your servers ipaddress (Ex: http://192.168.1.4/login.php) on the following files AddComment.java Login.java ReadComments.java Register.java

<h1>2.</h1>
Load up your emulator. If you use your phone, make sure its on the same network as your server.

<h1>3.</h1>
Compile, Run, and test the app.

If you got this far, youre ready to contribute code to the project. holla at ya boy
View the detailed readme on the googledoc
# BookIt
