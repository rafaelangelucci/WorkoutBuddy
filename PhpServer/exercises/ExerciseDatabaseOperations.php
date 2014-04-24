<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	//Sets all the post variables
	if(isset($_POST['e_id'])){
		$eid = $_POST['e_id'];
	}
	if(isset($_POST['name'])){
		$name = $_POST['name'];
	}
	if(isset($_POST['type'])){
		$type = $_POST['type'];
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
		$columns = array('e_id', 'name', 'type', 'description', 'username');
		$where = array('e_id' => $eid);
		$response = $database->select('Exercise', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the getlist operation
	elseif($operation === 'getlist'){
		$columns = array('e_id', 'name', 'type', 'description', 'username');
		$where = array('username' => $username);
		$response = $database->select('Exercise', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the add operation
	elseif($operation === 'add'){
		$data = array(
			'e_id' => $eid,
			'name' => $name,
			'type' => $type,
			'description' => $description,
			'username' => $username);
		echo $database->insert('Exercise', $data);
	}
	
	//Performs the delete operation
	elseif($operation === 'delete'){
		$where = array('e_id' => $eid);
		echo $database->delete('Exercise', $where);
	}
	
	//Performs the update operation
	elseif($operation === 'update'){
		$data = array('name' => $name,
			'type' => $type,
			'description' => $description);
		$where = array('e_id' => $eid);
		echo $database->update('Exercise', $data, $where);
	}
	
	//no operation defined
	else{
		echo 'fail';
	}
		

?>
	
