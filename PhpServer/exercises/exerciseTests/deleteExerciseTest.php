<?php

	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteExercise.php';
			$ch = curl_init($url);
 			$data = array('e_id' => '7');
				
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
			$response = curl_exec($ch);
			echo $response;
			curl_close($ch);
		}
	
	
	}


?>