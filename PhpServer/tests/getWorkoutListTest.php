<?php

	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkoutList.php';
			$ch = curl_init($url);
 
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, 'username=' . urlencode('usernameA'));
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
			$response = curl_exec($ch);
			echo $response;
			curl_close($ch);
		}
	
	
	}


?>