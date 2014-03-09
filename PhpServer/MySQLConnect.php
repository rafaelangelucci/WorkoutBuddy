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
	
	function listWorkout($mysqli, $username){
		$sql_query = "SELECT name, date, description FROM Workout WHERE username = ?";
		
		$statement = $mysqli->stmt_init();
		//prepare the statement
    	if($statement->prepare($sql_query)){
			//create prepared statement object
			$statement->bind_param('s', $username);
		
			//execute
			$statement->execute();
		
			//bind result
			$result = $statement->get_result();
			//close
			//$statement->close();
		
			$resultarray = array();
		
			while($row = $result->fetch_row()){
				$resultAssociative = array();
				$resultAssociative['name'] = $row[0];
				$resultAssociative['date'] = $row[1];
				$resultAssociative['description'] = $row[2];
				$resultarray[] = $resultAssociative;
			}
		
			return json_encode($resultarray);
		}
		return 'error';
	}
	
	function listExercises($mysqli, $username){
		$sql_query = "SELECT name, type, description FROM Exercise WHERE username = ?";
		
		$statement = $mysqli->stmt_init();
		//prepare the statement
    	if($statement->prepare($sql_query)){
			//create prepared statement object
			$statement->bind_param('s', $username);
		
			//execute
			$statement->execute();
		
			//bind result
			$result = $statement->get_result();
			//close
			//$statement->close();
		
			$resultarray = array();
		
			while($row = $result->fetch_row()){
				$resultAssociative = array();
				$resultAssociative['name'] = $row[0];
				$resultAssociative['type'] = $row[1];
				$resultAssociative['description'] = $row[2];
				$resultarray[] = $resultAssociative;
			}
		
			return json_encode($resultarray);
		}
		return 'error';
	}
	
	function addExercise($mysqli, $username, $name, $type, $description){
		$sql_query = "INSERT INTO `Exercise`(`type`, `name`, `description`, `username`) 
			VALUES (?,?,?,?)";
		$statement = $mysqli->stmt_init();
		//prepare the statement
    	if($statement->prepare($sql_query)){
    		//create prepared statement object
			$statement->bind_param('ssss', $type, $name, $description, $username);
			
			//execute
			$statement->execute();
    		}		
	}
	
}
?>
