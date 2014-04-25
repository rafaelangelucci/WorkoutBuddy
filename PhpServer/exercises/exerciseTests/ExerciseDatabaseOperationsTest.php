<?php
	// Include Medoo
	require_once '../../helperFunctions.php';

	$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		
		public function testAddExercise(){
			global $url;
			$data = array('operation' => 'add',
				'type' => 'strength',
				'name' => 'bench',
				'description' => 'desc',
				'username' => 'test');
 
			$response = curlHelper($url, $data);
			$this->assertTrue($response>0);
			return $response;
		}
			
		/**
		 * @depends testAddExercise
		 */
		 public function testGetExercise($eid){
		 	global $url;
			$data = array('e_id' => $eid, 'operation' => 'get');

			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'type'}, 'strength');
			$this->assertEquals($responseArray[0]->{'name'}, 'bench');
			$this->assertEquals($responseArray[0]->{'description'}, 'desc');
			$this->assertEquals($responseArray[0]->{'username'}, 'test');
		 }
		 
		/**
		 * @depends testAddExercise
		 */
		 public function testUpdateExercise($eid){
		 	global $url;
			$data = array('operation' => 'update',
				'e_id' => $eid,
				'type' => 'distance',
				'name' => 'run',
				'description' => 'desc changed',
				'username' => 'test');
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
			
			//check changes were made
			$data = array('operation' => 'get', 'e_id' => $eid);
			
			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'type'}, 'distance');
			$this->assertEquals($responseArray[0]->{'name'}, 'run');
			$this->assertEquals($responseArray[0]->{'description'}, 'desc changed');
			$this->assertEquals($responseArray[0]->{'username'}, 'test');
		 }
		
		/**
		 * @depends testAddExercise
		 */
		 public function testDeleteExercise($eid){
		 	global $url;
		 	$data = array('operation' => 'delete', 'e_id' => $eid);
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
		 }

	}


?>