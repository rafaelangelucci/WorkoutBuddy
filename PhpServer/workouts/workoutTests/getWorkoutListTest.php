<?php
	//include
	require_once '../../helperFunctions.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkoutList.php';
			$data = array('username' => 'usernameA');
 
			$response = curlHelper($url, $data);
			
			$resultArray = json_decode($response);
			$this->assertEquals('WorkoutA', $resultArray[0]->{'name'});
			$this->assertEquals('03-4-2014', $resultArray[0]->{'date'});
			$this->assertEquals('desc', $resultArray[0]->{'description'});
		}
	
	
	}


?>