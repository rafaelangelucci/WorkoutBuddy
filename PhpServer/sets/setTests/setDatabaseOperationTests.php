<?php
// Include Medoo
require_once '../../helperFunctions.php';

$url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php';

class SetDatabaseOperationTests extends PHPUnit_Framework_TestCase {
	/**
	 * Adds a set to the database and checks if it was added correctly
	 */
	public function testAddSet() {
		global $url;
		$data = array('operation' => 'add', 'reps' => '10', 'weight' => '135', 'priority' => '1', 'e_id' => '1', 'w_id' => '1');

		$response = curlHelper($url, $data);
		$this -> assertTrue($response > 0);
		return $response;
	}

	/**
	 * @depends testAddSet
	 * Makes a call to get a the set from the database. Checks if all fields are correct
	 */
	public function testGetSet($sid) {
		global $url;
		$data = array('s_id' => $sid, 'operation' => 'get');

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'reps'}, 10);
		$this -> assertEquals($responseArray[0] -> {'weight'}, 135);
		$this -> assertEquals($responseArray[0] -> {'priority'}, 1);
		$this -> assertEquals($responseArray[0] -> {'e_id'}, 1);
		$this -> assertEquals($responseArray[0] -> {'w_id'}, 1);
	}

	/**
	 * @depends testAddSet
	 * calls a function to update sets and then makes sure all the fields
	 * were properly updated
	 */
	public function testUpdateSet($sid) {
		global $url;
		$data = array('operation' => 'update', 's_id' => $sid, 'reps' => '11', 'weight' => '136', 'priority' => '2', 'e_id' => '1', 'w_id' => '1');
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);

		//check changes were made
		$data = array('operation' => 'get', 's_id' => $sid);

		$response = curlHelper($url, $data);
		$responseArray = json_decode($response);

		$this -> assertEquals($responseArray[0] -> {'reps'}, 11);
		$this -> assertEquals($responseArray[0] -> {'weight'}, 136);
		$this -> assertEquals($responseArray[0] -> {'priority'}, 2);
		$this -> assertEquals($responseArray[0] -> {'e_id'}, 1);
		$this -> assertEquals($responseArray[0] -> {'w_id'}, 1);
	}

	/**
	 * @depends testAddSet
	 * Calls delete on the set table and makes sure it was properly deleted.
	 */
	public function testDeleteSet($sid) {
		global $url;
		$data = array('operation' => 'delete', 's_id' => $sid);
		$response = curlHelper($url, $data);
		$this -> assertTrue($response == 1);
	}

}
?>