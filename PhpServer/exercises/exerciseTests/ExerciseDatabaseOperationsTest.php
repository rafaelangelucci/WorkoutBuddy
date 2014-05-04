<?php
// Include Medoo
require_once '../../helperFunctions.php';

$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php';

class ExerciseDatabaseOperationsTest extends PHPUnit_Framework_TestCase {
	/**
	 * This function adds an Exercise to the database
	 * and checks the return value for its response
	 */
	public function testAddExercise() {
		global $url;
		$data = array('operation' => 'add', 'type' => 'strength', 'name' => 'bench', 'description' => 'desc', 'username' => 'test');

		$response = curlHelper($url, $data);
		$this -> assertTrue($response > 0);
		return $response;
	}

	/**
	 * @depends testAddExercise
	 * Tests get exercise and makes sure the correct exercise is returned
	 */
	public function testGetExercise($eid) {
		global $url;
		$data = array('e_id' => $eid, 'operation' => 'get');

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'type'}, 'strength');
		$this -> assertEquals($responseArray[0] -> {'name'}, 'bench');
		$this -> assertEquals($responseArray[0] -> {'description'}, 'desc');
		$this -> assertEquals($responseArray[0] -> {'username'}, 'test');
	}

	/**
	 * @depends testAddExercise
	 * Updates an exercise and calls get again to check that the fields were updated
	 */
	public function testUpdateExercise($eid) {
		global $url;
		$data = array('operation' => 'update', 'e_id' => $eid, 'type' => 'distance', 'name' => 'run', 'description' => 'desc changed', 'username' => 'test');
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);

		//check changes were made
		$data = array('operation' => 'get', 'e_id' => $eid);

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'type'}, 'distance');
		$this -> assertEquals($responseArray[0] -> {'name'}, 'run');
		$this -> assertEquals($responseArray[0] -> {'description'}, 'desc changed');
		$this -> assertEquals($responseArray[0] -> {'username'}, 'test');
	}

	/**
	 * @depends testAddExercise
	 * Deletes an exercise from the database and checks the return value to make sure
	 * it was deleted
	 */
	public function testDeleteExercise($eid) {
		global $url;
		$data = array('operation' => 'delete', 'e_id' => $eid);
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);
	}

}
?>