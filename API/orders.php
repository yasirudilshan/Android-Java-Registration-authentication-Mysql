
<?php 
 
 /*
 * Created by Belal Khan
 * website: www.simplifiedcoding.net 
 * Retrieve Data From MySQL Database in Android
 */
 
 //database constants
 define('DB_HOST', 'localhost');
 define('DB_USER', 'root');
 define('DB_PASS', '');
 define('DB_NAME', 'androidloginregistration');
 
 //connecting to database and getting the connection object
 $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
 //creating a query
 //$stmt = $conn->prepare("SELECT userID, adress, order_status, note FROM order_details;");
 $stmt = $conn->prepare("SELECT userID,adress,order_status,note FROM order_details;");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($id, $adress, $order_status, $note);
 //$stmt->bind_result($id);
 
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['id'] = $id; 
 $temp['adress'] = $adress; 
 $temp['order_status'] = $order_status; 
 $temp['note'] = $note; 

 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 //echo json_encode(['orders'=>$products]);
 //echo json_encode($products,flags:JSON_PRETTY_PRINT);

 echo json_encode($products);