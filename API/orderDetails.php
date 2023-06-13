<?php
if(!empty($_POST['item'])){
    $item=$_POST['item'];
    
    $result=array();

    $con = mysqli_connect(hostname:"localhost" , username:"root" , password:"", database:"loginregister");

    if($con){
            $sql="select * from order_details where userID ='".$item."'";
            $res=mysqli_query($con,$sql);

            if(mysqli_num_rows($res)!=0){
                $row=mysqli_fetch_assoc($res);
  
                        //$result=array("status"=>"success","message"=>"Login successful");
                        //$result=array("status"=>"success","message"=>"Login successful","name"=>$nme);
                        //$result=array("status"=>"success","message"=>"Login successful","name"=>$name,"email"=>$email,"apikey"=>$apiKey);
                        $result=array("status"=>"success","message"=>"Login successful","id"=>$row['userID'],"address"=>$row['adress'],"order_status"=>$row['order_status'],"note"=>$row['note']);
                        //$result=array("status"=>"success","message"=>"Login successful");
                        //$result=array("status"=>"success","message"=>"Login successful","name"=>$row['name'],"email"=>$row['email']);
                    
                
            }
            else $result=array("status"=>"failed","message"=>"Retry with correct email and password");
    }
    else $result=array("status"=>"failed","message"=>"Database connection failed");
}
else $result=array("status"=>"failed","message"=>"All fields are required");

echo json_encode($result,flags:JSON_PRETTY_PRINT);
//echo $result;