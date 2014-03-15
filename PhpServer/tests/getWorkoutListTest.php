<?php

	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkoutList.php';
			$ch = curl_init($url);
			$data = array('username' => 'usernameA');
 
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
			$response = curl_exec($ch);
			curl_close($ch);
			
			$resultArray = json_decode($response);
			$this->assertEquals('WorkoutA', $resultArray[0]->{'name'});
			$this->assertEquals('03-4-2014', $resultArray[0]->{'date'});
			$this->assertEquals('desc', $resultArray[0]->{'description'});
		}
	
	
	}


?>