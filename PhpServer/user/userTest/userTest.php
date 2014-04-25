<?php
//include
require_once '../../helperFunctions.php';

class MySQLConnectTest extends PHPUnit_Framework_TestCase
{
    /**
     * @dataProvider provider
     */
    public function testLogin($username,$password,$login,$result)
    {
        $response = $this->addUser('validUser','validPassword');
        $this->assertEquals($response, 'success');

        if($login){
            $getResponse = $this->login($username,$password);
        }else{
            $getResponse = $this->addUser($username,$password);
        }
        $this->assertEquals($getResponse, $result);

        $deleteResponse = $this->deleteUser('validUser','validPassword');
        $this->assertEquals($deleteResponse, 'successfully deleted');
    }

    public function provider()
    {
        return array(
            array('validUser','validPassword',true, 'success'),
            array('validUser','invalidPassword',true, 'fail'),
            array('invalidUser','invalidPassword',true, 'fail'),
            array('validUser','validPassword',false, 'username already exists')
        );
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
        $file = 'addUser.php';
        return $this->urlConnector($username, $password, $file);
    }

    private function login($username, $password){
        //Curl for login user
        $file = 'userLogin.php';
        return $this->urlConnector($username, $password, $file);
    }

    private function deleteUser($username, $password){
        //Curl for deleting user
        $file = 'deleteUser.php';
        return $this->urlConnector($username, $password, $file);
    }

    private function urlConnector($username, $password, $file)
    {
        $data = array(
            'username' => $username,
            'password' => $password);
        return curlHelper('http://workoutbuddy.web.engr.illinois.edu/PhpFiles/'.$file, $data);
    }


}

?>