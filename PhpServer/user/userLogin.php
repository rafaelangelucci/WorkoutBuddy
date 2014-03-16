<?php
	// Include Medoo
	require_once '../medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');

	if(isset($_POST['username'], $_POST['password'])){
        $username = $_POST['username'];
        $password = $_POST['password'];
        $data = array('username');
        $where = array('AND' => array('username' => $username, 'password' => $password));
        $response = $database->select('User', $data, $where);
        if($response == null){
            echo 'fail';
        }else{
            echo json_encode($response);
        }
    }
    else{
        echo 'fail';
    }
?>