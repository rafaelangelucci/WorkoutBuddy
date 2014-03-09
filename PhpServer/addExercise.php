<?php
	include 'MySQLConnect.php';

	
	$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
	$db_user = 'workoutbuddy_ad';
	$db_pwd = 'admin';
	$db_name = 'workoutbuddy_01';
	if(isset($_POST['username'], $_POST['name'], $_POST['type'], $_POST['description'])){
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		
		$username = $_POST['username'];
		$name = $_POST['name'];
		$type = $_POST['type'];
		$description = $_POST['description'];
		$result = $mysql->addExercise($db, $username, $name, $type, $description);
		
		echo $result;
	}
	else{
		echo 'fail';
	}
		

?>
	