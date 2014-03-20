<?php
//include
require_once '../../helperFunctions.php';

class MySQLConnectTest extends PHPUnit_Framework_TestCase
{
    public function testBasic(){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $response = curlHelper($url, $data);
        $this->assertEquals($response, 'success');

        //Curl for login user
        $getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php';
        $getData = array('username' => 'validUser',
            'password' => 'validPassword');
        $getResponse = curlHelper($getUrl, $getData);
        $this->assertEquals($getResponse, 'success');

        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $deleteResponse = curlHelper($deleteUrl, $deleteData);
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function testInvalidPasswordLogin(){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $response = curlHelper($url, $data);
        $this->assertEquals($response, 'success');

        //Curl for login user
        $getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php';
        $getData = array('username' => 'validUser',
            'password' => 'invalidPassword');
        $getResponse = curlHelper($getUrl, $getData);
        $this->assertEquals($getResponse, 'fail');

        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $deleteResponse = curlHelper($deleteUrl, $deleteData);
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function testInvalidUserLogin(){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $response = curlHelper($url, $data);
        $this->assertEquals($response, 'success');

        //Curl for login user
        $getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php';
        $getData = array('username' => 'invalidUser',
            'password' => 'validPassword');
        $getResponse = curlHelper($getUrl, $getData);
        $this->assertEquals($getResponse, 'fail');

        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $deleteResponse = curlHelper($deleteUrl, $deleteData);
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function testInvalidUserAdd(){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $response = curlHelper($url, $data);
        $this->assertEquals($response, 'success');

        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $response = curlHelper($url, $data);
        $this->assertEquals($response, 'username already exists');

        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => 'validUser',
            'password' => 'validPassword');
        $deleteResponse = curlHelper($deleteUrl, $deleteData);
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }


}


?>