<?php
//include
require_once '../../helperFunctions.php';

class MySQLConnectTest extends PHPUnit_Framework_TestCase
{
    /**
     * @dataProvider provider
     */
    public function testLogin($username,$password,$result)
    {
        $response = $this->addUser('validUser','validPassword');
        $this->assertEquals($response, 'success');

        $getResponse = $this->login($username,$password);
        $this->assertEquals($getResponse, $result);

        $deleteResponse = $this->deleteUser('validUser','validPassword');
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function provider()
    {
        return array(
            array('validUser','validPassword', 'success'),
            array('validUser','invalidPassword', 'fail'),
            array('invalidUser','invalidPassword', 'fail')
        );
    }

    public function testInvalidUserAdd(){
        $response = $this->addUser('validUser','validPassword');
        $this->assertEquals($response, 'success');

        $response = $this->addUser('validUser','validPassword');
        $this->assertEquals($response, 'username already exists');

        $deleteResponse = $this->deleteUser('validUser','validPassword');
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function testDeleteUser(){
        $deleteResponse = $this->deleteUser('validUser','validPassword');
        $this->assertEquals($deleteResponse, 'username is not in the database');

        $response = $this->addUser('validUser','validPassword');
        $this->assertEquals($response, 'success');

        $deleteResponse = $this->deleteUser('validUser','invalidPassword');
        $this->assertEquals($deleteResponse, 'password is not correct');

        $deleteResponse = $this->deleteUser('validUser','validPassword');
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    private function addUser($username, $password){
        //Curl for adding User
        $url = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php';
        $data = array(
            'username' => $username,
            'password' => $password);
        return curlHelper($url, $data);
    }

    private function login($username, $password){
        //Curl for login user
        $getUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php';
        $getData = array(
            'username' => $username,
            'password' => $password);
        return curlHelper($getUrl, $getData);
    }

    private function deleteUser($username, $password){
        //Curl for deleting user
        $deleteUrl = 'http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php';
        $deleteData = array(
            'username' => $username,
            'password' => $password);
        return curlHelper($deleteUrl, $deleteData);
    }


}

?>