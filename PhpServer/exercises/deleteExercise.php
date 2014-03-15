<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['e_id'])){
		$eid = $_POST['e_id'];
		$where = array('e_id' => $eid);
		echo $database->delete('Exercise', $where);
	}
	else{
		echo 'fail';
	}
		

?>
	
