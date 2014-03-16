<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['w_id'], $_POST['username'], $_POST['name'], $_POST['date'], $_POST['description'])){
		$wid = $_POST['w_id'];
		$data = array(
			'name' => $_POST['name'],
			'username' => $_POST['username'], 
			'date' => $_POST['date'], 
			'description' => $_POST['description']);
		$where = array('w_id' => $wid);
		$response = $database->update('Workout', $data, $where);
		echo json_encode($response);
	}
	else{
		echo 'fail';
	}
		

?>
	
