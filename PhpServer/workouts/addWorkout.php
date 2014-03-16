<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	if(isset($_POST['username'], $_POST['name'], $_POST['date'], $_POST['description'])){
		$username = $_POST['username'];
		$name = $_POST['name'];
		$date = $_POST['date'];
		$description = $_POST['description'];
		$data = array(
			'username' => $username,
			'name' => $name,
			'date' => $date,
			'description' => $description);
		echo $database->insert('Workout', $data);
	}
	else{
		echo 'fail';
	}
		

?>
	
