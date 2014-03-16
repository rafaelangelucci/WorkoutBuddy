<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['w_id'])){
		$wid = $_POST['w_id'];
		$where = array('w_id' => $wid);
		echo $database->delete('Workout', $where);
	}
	else{
		echo 'fail';
	}
		

?>
	
