<?php
	// Include Medoo
	require_once '../../helperFunctions.php';
	
	$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php';
	class MySQLConnectTest extends PHPUnit_Framework_TestCase
	{
		public function testAddGetDeleteSet(){
			global $url;
			//test add set
			$data = array('operation' => 'add',
				'reps' => '10',
				'weight' => '135',
				'priority' => '1',
				'e_id' => '1',
				'w_id' => '1');
 
			$response = curlHelper($url, $data);
			$sid = $response;
			$this->assertTrue($response>0);
		
			//test getset
			$data = array('s_id' => $sid, 'operation' => 'get');
			
			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);
			
			$this->assertEquals($responseArray[0]->{'reps'}, 10);
			$this->assertEquals($responseArray[0]->{'weight'}, 135);
			$this->assertEquals($responseArray[0]->{'priority'}, 1);
			$this->assertEquals($responseArray[0]->{'e_id'}, 1);
			$this->assertEquals($responseArray[0]->{'w_id'}, 1);
		
			//Test delete set
		 	$data = array('operation' => 'delete', 's_id' => $sid);
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
		 }
	
	}


?>