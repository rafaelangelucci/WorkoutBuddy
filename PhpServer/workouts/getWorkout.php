<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['w_id'])){
		$wid = $_POST['w_id'];
		$columns = array('name', 'username', 'date', 'description');
		$where = array('w_id' => $wid);
		$response = $database->select('Workout', $columns, $where);
		echo json_encode($response);
	}
	else{
		echo 'fail';
	}
		

?>
	
