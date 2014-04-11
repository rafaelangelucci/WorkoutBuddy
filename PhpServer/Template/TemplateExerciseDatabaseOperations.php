<?php
	// Include Medoo
	require_once 'medoo.php';
	// Initialize
	$database = new medoo('workoutbuddy_01');
	
	//Sets all the post variables
	if(isset($_POST['te_id'])){
		$teid = $_POST['te_id'];
	}
	if(isset($_POST['t_id'])){
		$tid = $_POST['t_id'];
	}
	if(isset($_POST['priority'])){
		$priority = $_POST['priority'];
	}
	if(isset($_POST['e_id'])){
		$eid = $_POST['e_id'];
	}
	if(isset($_POST['numSets'])){
		$numSets = $_POST['numSets'];
	}
	if(isset($_POST['reps'])){
		$reps = $_POST['reps'];
	}
	if(isset($_POST['operation'])){
		$operation = $_POST['operation'];
	}
	
	//Performs the get operation
	if($operation === 'get'){
		$columns = array('te_id', 't_id', 'priority', 'e_id', 'numSets', 'reps');
		$where = array('te_id' => $teid);
		$response = $database->select('Template_Exercise', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the getlist operation
	elseif($operation === 'getlist'){
		$columns = array('te_id', 't_id', 'priority', 'e_id', 'numSets', 'reps');
		$where = array('t_id' => $tid);
		$response = $database->select('Template_Exercise', $columns, $where);
		echo json_encode($response);
	}
	
	//Performs the add operation
	elseif($operation === 'add'){
		$data = array(
			'te_id' => $teid,
			't_id' => $tid,
			'priority' => $priority,
			'e_id' => $eid,
			'numSets' => $numSets,
			'reps' => $reps);
		echo $database->insert('Template_Exercise', $data);
	}
	
	//Performs the delete operation
	elseif($operation === 'delete'){
		$where = array('te_id' => $teid);
		echo $database->delete('Template_Exercise', $where);
	}
	
	//Performs the update operation
	elseif($operation === 'update'){
		$data = array('priority' => $priority,
			'e_id' => $eid,
			'numSets' => $numSets,
			'reps' => $reps);
		$where = array('te_id' => $teid);
		echo $database->update('Template_Exercise', $data, $where);
	}
	
	//no operation defined
	else{
		echo 'fail';
	}
		

?>
	
