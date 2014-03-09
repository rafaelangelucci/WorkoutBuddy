<?php
	include 'MySQLConnect.php';

	
	$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
	$db_user = 'workoutbuddy_ad';
	$db_pwd = 'admin';
	$db_name = 'workoutbuddy_01';
	
	if(isset($_POST['username'])){
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		
		$username = $_POST['username'];
		$result = $mysql->listExercises($db, $username);
		
		echo $result;
	}
	else{
	echo 'fail';
	}
		

?>
	