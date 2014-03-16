<?php
	// Include Medoo
	require_once '../../helperFunctions.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExerciseList.php';
			$data = array('username' => 'usernameA');
 
			$response = curlHelper($url, $data);
			
			$resultArray = json_decode($response);
			$this->assertEquals('ExerciseA', $resultArray[0]->{'name'});
			$this->assertEquals('strength', $resultArray[0]->{'type'});
			$this->assertEquals('desc', $resultArray[0]->{'description'});
		}
	
	
	}


?>