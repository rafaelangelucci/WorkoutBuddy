<?php
	// Include Medoo
	require_once '../medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');

	if(isset($_POST['username'], $_POST['password'])){
        $username = $_POST['username'];
        $password = $_POST['password'];
        $data = array('username');
        $where = array('username' => $username,'password' =>$password);
        $response = $database->select('User', $data, $where);
        if(count($response)==0){
            echo 'fail';
        }else{
            echo 'success';
        }
    }
    else{
        echo 'fail';
    }

//echo $_POST['username'];
?>