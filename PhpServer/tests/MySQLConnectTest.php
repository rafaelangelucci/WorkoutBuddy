<?php
include '../MySQLConnect.php';


class MySQLConnectTest extends PHPUnit_Framework_TestCase
{
	/*
	 * Tests the database connecter
	 */
	public function testSuccessfulConnection(){
		$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
		$db_user = 'workoutbuddy_ad';
		$db_pwd = 'admin';
		$db_name = 'workoutbuddy_01';
	
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		//AssertFalse to make sure that $db is not false
		$this->assertFalse(!$db);
	}
	
	public function testWorkoutList(){
		$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
		$db_user = 'workoutbuddy_ad';
		$db_pwd = 'admin';
		$db_name = 'workoutbuddy_01';
		$username = 'usernameA';
	
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		
		$result = $mysql->listWorkout($db, $username);
		$resultArray = json_decode($result);
		$this->assertEquals($resultArray[0]->{'name'}, 'WorkoutA');
		$this->assertEquals($resultArray[0]->{'date'}, '03-4-2014');
		$this->assertEquals($resultArray[0]->{'description'}, 'desc');
		
	}
	
	public function testExerciseList(){
		$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
		$db_user = 'workoutbuddy_ad';
		$db_pwd = 'admin';
		$db_name = 'workoutbuddy_01';
		$username = 'usernameA';
	
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		
		$result = $mysql->listExercises($db, $username);
		$resultArray = json_decode($result);
		$this->assertEquals($resultArray[0]->{'name'}, 'ExerciseA');
		$this->assertEquals($resultArray[0]->{'type'}, 'strength');
		$this->assertEquals($resultArray[0]->{'description'}, 'desc');	
	}
	
	public function testAddExercise(){
		$host_name = 'engr-cpanel-mysql.engr.illinois.edu';
		$db_user = 'workoutbuddy_ad';
		$db_pwd = 'admin';
		$db_name = 'workoutbuddy_01';
		$username = 'usernameA';
		$name = 'ExerciseC';
		$type = 'strength';
		$description = 'desc';
		
		$mysql = new MySQLTools();
		$db = $mysql->connect($host_name, $db_user, $db_pwd, $db_name);
		
		$mysql->addExercise($db, $username, $name, $type, $description);
		
	}
}
	
?>
