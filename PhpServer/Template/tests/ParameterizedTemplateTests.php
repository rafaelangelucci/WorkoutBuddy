<?php
require_once '../../helperFunctions.php';

Class ParameterizedTemplateTest extends PHPUnit_Framework_TestCase {
	/**
	 * Function that outputs testing data
	 */
	public static function exerciseData() {
		$data1 = array( array('t_id' => '1', 'priority' => '1', 'e_id' => '1', 'numSets' => '2', 'reps' => '10'));
		$data2 = array( array('t_id' => '2', 'priority' => '2', 'e_id' => '2', 'numSets' => '4', 'reps' => '20'));
		$data3 = array( array('t_id' => '1', 'priority' => '2', 'e_id' => '3', 'numSets' => '3', 'reps' => '11'));
		$data4 = array( array('t_id' => '2', 'priority' => '1', 'e_id' => '10', 'numSets' => '4', 'reps' => '11'));

		return array($data1, $data2, $data3, $data4);
	}

	/**
	 * @dataProvider exerciseData
	 * Calls each of the database operations and makes sure they work correctly
	 */
	public function testAddGetDeleteTExercise($data) {
		$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php';
		//add the add operation to data
		$data['operation'] = 'add';
		//get te_id
		$response = curlHelper($url, $data);
		$this -> assertTrue($response > 0);

		//change operation to get to make sure it is in db
		$data['operation'] = 'get';
		$data['te_id'] = $response;

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'t_id'}, $data['t_id']);
		$this -> assertEquals($responseArray[0] -> {'priority'}, $data['priority']);
		$this -> assertEquals($responseArray[0] -> {'e_id'}, $data['e_id']);
		$this -> assertEquals($responseArray[0] -> {'numSets'}, $data['numSets']);
		$this -> assertEquals($responseArray[0] -> {'reps'}, $data['reps']);

		//change operation to delete from db
		$data['operation'] = 'delete';

		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);
	}

}
?>