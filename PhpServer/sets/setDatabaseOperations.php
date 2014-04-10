<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	//Sets all the post variables
	if(isset($_POST['s_id'])){
		$sid = $_POST['s_id'];
	}
	if(isset($_POST['reps'])){
		$reps = $_POST['reps'];
	}
	if(isset($_POST['weight'])){
		$weight = $_POST['weight'];
	}
	if(isset($_POST['time'])){
		$time = $_POST['time'];
	}
	if(isset($_POST['priority'])){
		$priority = $_POST['priority'];
	}
	if(isset($_POST['e_id'])){
		$eid = $_POST['e_id'];
	}
	if(isset($_POST['w_id'])){
		$wid = $_POST['w_id'];
	}
	if(isset($_POST['operation'])){
		$operation = $_POST['operation'];
	}
	
	//Performs the get operation
	if($operation === 'get'){
		$columns = array('s_id', 'reps', 'weight', 'time', 'priority', 'e_id', 'w_id');
		$where = array('s_id' => $sid);
		$response = $database->select('Sets', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the getlist operation
	elseif($operation === 'getlist'){
		$columns = array('s_id', 'reps', 'weight', 'time', 'priority', 'e_id', 'w_id');
		$where = array('AND'=> array('w_id' => $wid, 'e_id' => $eid));
		$response = $database->select('Sets', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the add operation
	elseif($operation === 'add'){
		$data = array(
			'reps' => $reps,
			'weight' => $weight,
			'time' => $time,
			'priority' => $priority,
			'e_id' => $eid,
			'w_id' => $wid);
		echo $database->insert('Sets', $data);
	}
	
	//Performs the delete operation
	elseif($operation === 'delete'){
		$where = array('s_id' => $sid);
		echo $database->delete('Sets', $where);
	}
	
	//Performs the update operation
	elseif($operation === 'update'){
		$data = array(
			'reps' => $reps,
			'weight' => $weight,
			'time' => $time,
			'priority' => $priority,
			'e_id' => $eid,
			'w_id' => $wid);
		$where = array('s_id' => $sid);
		echo $database->update('Sets', $data, $where);
	}
	
	//no operation defined
	else{
		echo 'fail';
	}
		

?>
	
