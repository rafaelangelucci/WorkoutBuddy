<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['e_id'])){
		$eid = $_POST['e_id'];
		$data = array('name', 'username', 'type', 'description');
		$where = array('e_id' => $eid);
		$response = $database->select('Exercise', $data, $where);
		echo json_encode($response);
	}
	else{
		echo 'fail';
	}
		

?>
	
