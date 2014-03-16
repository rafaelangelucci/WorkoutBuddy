<?php
	//include
	require_once '../../helperFunctions.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testAddDeleteGetModifyWorkout(){
			//Curl for adding workout
			$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addWorkout.php';
 			$data = array(
 				'username' => 'usernameA',
				'name' => 'WorkoutD',
				'description' => 'desc',
				'date' => '03-20-2014');
			$wid = curlHelper($url, $data);
			
			//Curl for getting Workout
			$getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkout.php';
			$getData = array('w_id' => $wid);
			$getResponse = curlHelper($getUrl, $getData);
			$responseArray = json_decode($getResponse);
			
			$this->assertEquals($responseArray[0]->{'name'}, 'WorkoutD');
			$this->assertEquals($responseArray[0]->{'date'}, '03-20-2014');
			$this->assertEquals($responseArray[0]->{'username'}, 'usernameA');
			$this->assertEquals($responseArray[0]->{'description'}, 'desc');
			
			//Curl for updating workout
			$updateUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyWorkout.php';
			$updateData = array(
				'w_id' => $wid,
 				'username' => 'usernameA',
				'name' => 'WorkoutE',
				'description' => 'Changed workout',
				'date' => '03-21-2014');
			$updateResponse = curlHelper($updateUrl, $updateData);
			
			//get to check changes
			$getResponse = curlHelper($getUrl, $getData);
			$responseArray = json_decode($getResponse);
			$this->assertEquals($responseArray[0]->{'name'}, 'WorkoutE');
			$this->assertEquals($responseArray[0]->{'date'}, '03-21-2014');
			$this->assertEquals($responseArray[0]->{'username'}, 'usernameA');
			$this->assertEquals($responseArray[0]->{'description'}, 'Changed workout');
			
			//Curl for deleting workout
			$deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteWorkout.php';
			$deleteData = array('w_id' => $wid);
			$deleteResponse = curlHelper($deleteUrl, $deleteData);
			$this->assertEquals($deleteResponse[0], '1');
			
			
			
		}
	
	
	}


?>