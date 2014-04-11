<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	//Sets all the post variables
	if(isset($_POST['t_id'])){
		$tid = $_POST['t_id'];
	}
	if(isset($_POST['name'])){
		$name = $_POST['name'];
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
		$columns = array('t_id', 'name', 'description', 'username');
		$where = array('t_id' => $tid);
		$response = $database->select('Template_Workout', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the getlist operation
	elseif($operation === 'getlist'){
		$columns = array('t_id', 'name', 'description', 'username');
		$where = array('username' => $username);
		$response = $database->select('Template_Workout', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the add operation
	elseif($operation === 'add'){
		$data = array(
			't_id' => $tid,
			'name' => $name,
			'description' => $description,
			'username' => $username);
		echo $database->insert('Template_Workout', $data);
	}
	
	//Performs the delete operation
	elseif($operation === 'delete'){
		$where = array('t_id' => $tid);
		echo $database->delete('Template_Workout', $where);
	}
	
	//Performs the update operation
	elseif($operation === 'update'){
		$data = array('name' => $name,
			'description' => $description);
		$where = array('t_id' => $tid);
		echo $database->update('Template_Workout', $data, $where);
	}
	
	//no operation defined
	else{
		echo 'fail';
	}
		

?>
	
