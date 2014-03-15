<?php
	//include
	require_once '../../helperFunctions.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testAddExercise(){
			//Curl for adding exercise
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addExercise.php';
 			$data = array(
 				'username' => 'usernameA',
				'name' => 'ExerciseD',
				'description' => 'desc',
				'type' => 'strength');
				
			//Curl for getting exercise
			$eid = curlHelper($url, $data);
			$getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExercise.php';
			$getData = array('e_id' => $eid);
			$getResponse = curlHelper($getUrl, $getData);
			$responseArray = json_decode($getResponse);
			
			$this->assertEquals($responseArray[0]->{'name'}, 'ExerciseD');
			
			//Curl for updating exercise
			$updateUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyExercise.php';
			$updateData = array(
				'e_id' => $eid,
 				'username' => 'usernameA',
				'name' => 'ExerciseE',
				'description' => 'Changed exercise',
				'type' => 'strength');
			$updateResponse = curlHelper($updateUrl, $updateData);
			
			
		}
	
	
	}


?>