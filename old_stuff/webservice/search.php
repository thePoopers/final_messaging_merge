<?php
 session_start();
 require("config.inc.php");
 
// check that the contents is present

     
    $contents = 'kite runner'; 
 

    //echo "CONTENTS VAR CONTAINS ";
    //echo $contents;
    //echo " END";
    //store json output from isbndb into output var
    $output = file_get_contents('http://isbndb.com/api/v2/json/Q0DGGAQJ/books?q='.urlencode($contents).'');
    //decode json into an object to make some logic out of results
    $objJson = json_decode($output);
         
    //if there are any results
    if($objJson->result_count > 0){
        //$response["success"] = 1;
        //$response["message"] = "Successfully searched!";
        //$response["begin"] = $output;
        //
        //echo json_encode($response);
        echo $output;
    }
    //else no results
    else{
        $response["success"] = 1;
        $response["message"] = "No Results Found";
        echo json_encode($response);
    }
    

?>