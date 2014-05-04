<?php
// Include Medoo
require_once '../../helperFunctions.php';

$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php';

class TemplateWorkoutTests extends PHPUnit_Framework_TestCase {
	/**
	 * Makes a call to add template workout to database. Checks if it was correctly added
	 */
	public function testAddTemplateWO() {
		global $url;
		$data = array('operation' => 'add', 'name' => 'test', 'description' => 'desc', 'username' => 'usernameA');

		$response = curlHelper($url, $data);
		$this -> assertTrue($response > 0);
		return $response;
	}

	/**
	 * @depends testAddTemplateWO
	 * Calls get on templateWorkout table and makes sure it correctly grabs the information
	 */
	public function testGetTemplateWO($tid) {
		global $url;
		$data = array('t_id' => $tid, 'operation' => 'get');

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'name'}, 'test');
		$this -> assertEquals($responseArray[0] -> {'description'}, 'desc');
		$this -> assertEquals($responseArray[0] -> {'username'}, 'usernameA');
	}

	/**
	 * @depends testAddTemplateWO
	 * Makes a call to update a template workout. Checks that all fields are correctly updated
	 */
	public function testUpdateTemplateWO($tid) {
		global $url;
		$data = array('operation' => 'update', 't_id' => $tid, 'name' => 'changed name', 'description' => 'changed desc');
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);

		//check changes were made
		$data = array('operation' => 'get', 't_id' => $tid);

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'name'}, 'changed name');
		$this -> assertEquals($responseArray[0] -> {'description'}, 'changed desc');
	}

	/**
	 * @depends testAddTemplateWO
	 * Makes a call to delete the template workout. Checks if it was properly deleted
	 */
	public function testDeleteTemplateWO($tid) {
		global $url;
		$data = array('operation' => 'delete', 't_id' => $tid);
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);
	}

}
?>