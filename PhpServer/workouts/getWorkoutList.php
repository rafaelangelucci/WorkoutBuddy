<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['username'])){
		$username = $_POST['username'];
		$data = array('name', 'username', 'date', 'description');
		$where = array('username' => $username);
		$result = $database->select('Workout', $data, $where);		
		echo json_encode($result);
	}
	else{
		echo 'fail';
	}
		

?>
	