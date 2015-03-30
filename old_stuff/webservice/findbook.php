<?php
//findbook.php
//Start a session that carries a session variable
session_start();
//load and connect to MySQL database stuff
require("config.inc.php");

//if posted data is not empty
if (!empty($_POST)){
	//if the search text is empty, the page will die.
	if(empty($_POST['contents'])){
		//Create some data that will be the JSON response
		$response["success"] = 0;
		$response["message"] = "Please enter a title, author, or ISBN.";
		//die will kill the page and not execute any code below
		die(json_encode($response));	
	}

	//if the page hasnt died, we call isbndb
	header('Content-Type: application/json');
	//take input from textbox
	$contents = $_POST["contents"];
	//store json output from isbndb into output var
	$output = file_get_contents('https://www.googleapis.com/books/v1/volumes?q='
		.urlencode($contents).'&startIndex=0&maxResults=10&key='.$key.'');

	//echo json_encode($output);
	echo $output;

	//decode json into an object to make some logic out of results
	//$objJson = json_decode($output);

	//if there are any results
/*	if($objJson->result_count > 0){
		$response["success"] = 1;
		$response["message"] = "Successfully searched!";
		//$response["begin"] = $output;
		//$response["begin"] = json_encode($output);
		//echo $output;
		echo json_encode($response);
		echo $output;
		
		//echo json_encode($output);
	}*/
	//else no results
	/*else{
		//maybe do a success = 0 here???
		$response["success"] = 1;
		$response["message"] = "No Results Found";
		echo json_encode($response);
	}*/

}

else{
?>
<!--Take the user input and store it into a session variable that gets sent to search.php-->
		<h1>Find Book</h1> 

		<form action="findbook.php" method="post"> 
		    Search:<br /> 
		    <input type="text" size="64" name="contents" placeholder="Search for Title, Author, or ISBN" /> 
		    <br /><br /> 
		    <input type="submit" value="Search" /> 
		</form> 
<?php

}

?>
