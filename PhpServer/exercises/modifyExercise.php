<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['e_id'], $_POST['username'], $_POST['name'], $_POST['type'], $_POST['description'])){
		$eid = $_POST['e_id'];
		$data = array(
			'name' => $_POST['name'],
			'username' => $_POST['username'], 
			'type' => $_POST['type'], 
			'description' => $_POST['description']);
		$where = array('e_id' => $eid);
		echo $database->update('Exercise', $data, $where);
	}
	else{
		echo 'fail';
	}
		

?>
	
