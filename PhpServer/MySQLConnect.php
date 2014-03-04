<?php

class MySQLTools
{
	/**
	 * makes a call to the mysql_connect function
	 * 
	 * @param $host_name name of the host of the database
	 * @param $db_user user login for the database
	 * @param $db_pwd password for the user
	 * 
	 * @return the database reference (False if connection failed)
	 */
	function connect($host_name, $db_user, $db_pwd){
		$link = mysql_connect($host_name, $db_user, $db_pwd);
		return $link;
		mysql_close($link);
	}
	
}
?>
