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
	
}
	
?>
