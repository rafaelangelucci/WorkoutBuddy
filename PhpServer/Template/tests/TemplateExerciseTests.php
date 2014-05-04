<?php
// Include Medoo
require_once '../../helperFunctions.php';

$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php';

class TemplateExerciseTests extends PHPUnit_Framework_TestCase {

	/**
	 * Calls add to templateExercise. Makes sure the object was correctly added
	 */
	public function testAddTemplateE() {
		global $url;
		$data = array('operation' => 'add', 't_id' => '1', 'priority' => '1', 'e_id' => '1', 'numSets' => '2', 'reps' => '10');

		$response = curlHelper($url, $data);
		$this -> assertTrue($response > 0);
		return $response;
	}

	/**
	 * @depends testAddTemplateE
	 * Tests that get templateExercise returns the correct data
	 */
	public function testGetTemplateE($teid) {
		global $url;
		$data = array('te_id' => $teid, 'operation' => 'get');

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'t_id'}, '1');
		$this -> assertEquals($responseArray[0] -> {'priority'}, '1');
		$this -> assertEquals($responseArray[0] -> {'e_id'}, '1');
		$this -> assertEquals($responseArray[0] -> {'numSets'}, '2');
		$this -> assertEquals($responseArray[0] -> {'reps'}, '10');
	}

	/**
	 * @depends testAddTemplateE
	 * Calls update and makes sure that all of the fields are correctly updated
	 */
	public function testUpdateTemplateE($teid) {
		global $url;
		$data = array('operation' => 'update', 'te_id' => $teid, 't_id' => '1', 'priority' => '2', 'e_id' => '2', 'numSets' => '3', 'reps' => '11');
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);

		//check changes were made
		$data = array('operation' => 'get', 'te_id' => $teid);
		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'t_id'}, '1');
		$this -> assertEquals($responseArray[0] -> {'priority'}, '2');
		$this -> assertEquals($responseArray[0] -> {'e_id'}, '2');
		$this -> assertEquals($responseArray[0] -> {'numSets'}, '3');
		$this -> assertEquals($responseArray[0] -> {'reps'}, '11');
	}

	/**
	 * @depends testAddTemplateE
	 * Makes a call to delete the template exercise and makes sure it is properly deleted
	 */
	public function testDeleteTemplateE($teid) {
		global $url;
		$data = array('operation' => 'delete', 'te_id' => $teid);
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);
	}

}
?>