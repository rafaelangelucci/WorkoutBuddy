<?php
	require_once '../../helperFunctions.php';

	Class ParameterizedTest extends PHPUnit_Framework_TestCase{
		/**
		 * Function that outputs testing data
		 */
		 public static function workoutData(){
		 	$data1 = array(array('name' => 'Heavy Upper',
				'date' => '04/14/2014',
				'description' => 'Heavy day of lifts for the upper body',
				'username' => 'test'));
			$data2 = array(array('name' => 'Light Upper',
				'date' => '05/30/2013',
				'description' => 'Light day of lifts for the upper body',
				'username' => 'test'));
			$data3 = array(array('name' => 'Heavy Lower',
				'date' => '06/14/1992',
				'description' => 'Heavy day of lifts for the lower body',
				'username' => 'test'));
			$data4 = array(array('name' => 'Light Lower',
				'date' => '01/14/2014',
				'description' => 'Light day of lifts for the lower body',
				'username' => 'test'));
				
			return array($data1, $data2, $data3, $data4);
	 	}
	 
		 /**
	  	* @dataProvider workoutData
	  	*/
	 	public function testAddGetDeleteWorkout($data){
	 		$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php';
			//add the add operation to data
	 		$data['operation'] = 'add';
			//get te_id
	 		$response = curlHelper($url, $data);
			$this->assertTrue($response>0);
		
			//change operation to get to make sure it is in db
			$data['operation'] = 'get';
			$data['w_id'] = $response;
			
			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'name'}, $data['name']);
			$this->assertEquals($responseArray[0]->{'date'}, $data['date']);
			$this->assertEquals($responseArray[0]->{'description'}, $data['description']);
			$this->assertEquals($responseArray[0]->{'username'}, $data['username']);
		
			//change operation to delete from db
			$data['operation'] = 'delete';
		
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
	 	}
	}
?>