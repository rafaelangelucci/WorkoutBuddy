<?php
	require_once '../../helperFunctions.php';

	Class ParameterizedTest extends PHPUnit_Framework_TestCase{
		/**
		 * Function that outputs testing data
		 */
		 public static function exerciseData(){
		 	$data1 = array(array('type' => 'strength',
				'name' => 'pushups',
				'description' => 'get on floor',
				'username' => 'test'));
			$data2 = array(array('type' => 'distance',
				'name' => 'run',
				'description' => 'run...',
				'username' => 'user'));
			$data3 = array(array('type' => 'speed',
				'name' => 'sprint',
				'description' => 'run really fast',
				'username' => 'username'));
			$data4 = array(array('type' => 'strength',
				'name' => 'squat',
				'description' => 'sitting motion',
				'username' => 'new'));
			return array($data1, $data2, $data3, $data4);
	 	}
	 
		 /**
	  	* @dataProvider exerciseData
	  	*/
	 	public function testAddGetDeleteTExercise($data){
	 		$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php';
			//add the add operation to data
	 		$data['operation'] = 'add';
			//get te_id
	 		$response = curlHelper($url, $data);
			$this->assertTrue($response>0);
		
			//change operation to get to make sure it is in db
			$data['operation'] = 'get';
			$data['e_id'] = $response;
			
			$response = curlHelper($url, $data);
			$responseArray = json_decode($response);

			$this->assertEquals($responseArray[0]->{'type'}, $data['type']);
			$this->assertEquals($responseArray[0]->{'name'}, $data['name']);
			$this->assertEquals($responseArray[0]->{'description'}, $data['description']);
			$this->assertEquals($responseArray[0]->{'username'}, $data['username']);
		
			//change operation to delete from db
			$data['operation'] = 'delete';
		
			$response = curlHelper($url, $data);
			$this->assertTrue($response == 1);
	 	}
	 
	 
	}
?>