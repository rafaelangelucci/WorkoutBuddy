<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['w_id'])){		
		$wid = $_POST['w_id'];
		$data = array('name', 'username', 'type', 'description', 'e_id');
		$where = array('w_id' => $wid);
		$result = $database->select('Exercise', $data, $where);
		
		echo json_encode($result);
	}
	else{
		echo 'fail';
	}
		

?>