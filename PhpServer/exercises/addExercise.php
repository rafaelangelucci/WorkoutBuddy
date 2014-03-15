<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['username'], $_POST['name'], $_POST['type'], $_POST['description'])){
		$username = $_POST['username'];
		$name = $_POST['name'];
		$type = $_POST['type'];
		$description = $_POST['description'];
		$data = array(
			'username' => $username,
			'name' => $name,
			'type' => $type,
			'description' => $description);
		echo $database->insert('Exercise', $data);
	}
	else{
		echo 'fail';
	}
		

?>
	
