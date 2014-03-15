<?php

	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testWorkoutList(){
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExerciseList.php';
			$ch = curl_init($url);
 
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, 'username=' . urlencode('usernameA'));
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
			$response = curl_exec($ch);
			curl_close($ch);
			
			$resultArray = json_decode($response);
			$this->assertEquals('ExerciseA', $resultArray[0]->{'name'});
			$this->assertEquals('strength', $resultArray[0]->{'type'});
			$this->assertEquals('desc', $resultArray[0]->{'description'});
		}
	
	
	}


?>