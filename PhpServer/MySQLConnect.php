<?php

class MySQLTools
{
	/**
	 * makes a call to the mysql_connect function
	 * 
	 * @param $host_name name of the host of the database
	 * @param $db_user user login for the database
	 * @param $db_pwd password for the user
	 * @param $db_name name of the database
	 * 
	 * @return the database reference (False if connection failed)
	 */
	function connect($host_name, $db_user, $db_pwd, $db_name){
		$mysqli = new mysqli($host_name, $db_user, $db_pwd, $db_name);
		if ($mysqli->connect_errno) {
    		echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
		}
		return $mysqli;
	}
	
	
	
}
?>
