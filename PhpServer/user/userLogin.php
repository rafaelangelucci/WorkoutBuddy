<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
    $salt = "uiucworkoutbuddysalt";

	if(isset($_POST['username'], $_POST['password'])){
        $username = $_POST['username'];
        $password = $_POST['password'];
        $password = hash('sha512', $password.$salt);
        $data = array('username','password');
        $where = array('AND' => array('username' => $username, 'password' => $password));
        $response = $database->select('User', $data, $where);
        if($response == null){
            echo 'fail';
        }else{
            echo 'success';
        }
    }
    else{
        echo 'fail';
    }
?>