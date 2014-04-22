<?php
	// Include Medoo
	require_once '../../helperFunctions.php';

	$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php';
	
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		
		public function testAddTemplateWO(){
			global $url;
			$data = array('operation' => 'add',
				'name' => 'test',
				'description' => 'desc',
				'username' => 'usernameA');
 
			$response = curlHelper($url, $data);
			$this->assertTrue($response>0);
			return $response;
		}
			
		/**
		 * @depends testAddTemplateWO
		 */
		 public function testGetTemplateWO($tid){
		 	global $url;
			$data = array('t_id' => $tid, 'operation' => 'get');

			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'name'}, 'test');
			$this->assertEquals($responseArray[0]->{'description'}, 'desc');
			$this->assertEquals($responseArray[0]->{'username'}, 'usernameA');
		 }
		 
		/**
		 * @depends testAddTemplateWO
		 */
		 public function testUpdateTemplateWO($tid){
		 	global $url;
			$data = array('operation' => 'update',
				't_id' => $tid,
				'name' => 'changed name',
				'description' => 'changed desc');
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
			
			//check changes were made
			$data = array('operation' => 'get', 't_id' => $tid);
			
			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'name'}, 'changed name');
			$this->assertEquals($responseArray[0]->{'description'}, 'changed desc');
		 }
		
		/**
		 * @depends testAddTemplateWO
		 */
		 public function testDeleteTemplateWO($tid){
		 	global $url;
		 	$data = array('operation' => 'delete', 't_id' => $tid);
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
		 }

	}


?>