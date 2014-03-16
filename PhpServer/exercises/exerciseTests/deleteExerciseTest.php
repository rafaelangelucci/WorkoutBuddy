<?php
	// Include Medoo
	require_once '../../helperFunctions.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteExercise.php';
 			$data = array('e_id' => '20');
 
			$response = curlHelper($url, $data);
		}
	
	
	}


?>