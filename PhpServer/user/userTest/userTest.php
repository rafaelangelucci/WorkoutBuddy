<?php
//include
require_once '../../helperFunctions.php';

class MySQLConnectTest extends PHPUnit_Framework_TestCase
{
    public function testUser(){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'usernameA',
            'password' => 'passwordA');
        $response = curlHelper($url, $data);

        //Curl for login user
        $getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php';
        $getData = array(
            'username' => 'usernameA',
            'password' => 'passwordA');
        $getResponse = curlHelper($getUrl, $getData);
        $responseArray = json_decode($getResponse);

        $this->assertEquals($responseArray[0]->{'username'}, 'usernameA');
        //$this->assertEquals($responseArray[0]->{'password'}, 'passwordA');

        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => 'usernameA',
            'password' => 'passwordA');
        $deleteResponse = curlHelper($deleteUrl, $deleteData);


    }


}


?>