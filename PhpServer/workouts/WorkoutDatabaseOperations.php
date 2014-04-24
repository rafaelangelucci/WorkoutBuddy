<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	//Sets all the post variables
	if(isset($_POST['w_id'])){
		$wid = $_POST['w_id'];
	}
	if(isset($_POST['name'])){
		$name = $_POST['name'];
	}
	if(isset($_POST['date'])){
		$date = $_POST['date'];
	}
	if(isset($_POST['description'])){
		$description = $_POST['description'];
	}
	if(isset($_POST['username'])){
		$username = $_POST['username'];
	}
	if(isset($_POST['operation'])){
		$operation = $_POST['operation'];
	}
	
	//Performs the get operation
	if($operation == 'get'){
		$columns = array('w_id', 'name', 'date', 'description', 'username');
		$where = array('w_id' => $wid);
		$response = $database->select('Workout', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the getlist operation
	elseif($operation === 'getlist'){
		$columns = array('w_id', 'name', 'date', 'description', 'username');
		$where = array('username' => $username);
		$response = $database->select('Workout', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the add operation
	elseif($operation === 'add'){
		$data = array(
			'w_id' => $wid,
			'name' => $name,
			'date' => $date,
			'description' => $description,
			'username' => $username);
		echo $database->insert('Workout', $data);
	}
	
	//Performs the delete operation
	elseif($operation === 'delete'){
		$where = array('w_id' => $wid);
		echo $database->delete('Workout', $where);
	}
	
	//Performs the update operation
	elseif($operation === 'update'){
		$data = array('name' => $name,
			'date' => $date,
			'description' => $description);
		$where = array('w_id' => $wid);
		echo $database->update('Workout', $data, $where);
	}
	
	//no operation defined
	else{
		echo 'fail';
	}
		

?>
	
