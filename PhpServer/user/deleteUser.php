<?php

    // Include Medoo
    require_once 'medoo.php';
    // Initialize
    $database = new medoo('workoutbuddy_01');
    $salt = "uiucworkoutbuddysalt";

    if(isset($_POST['username'], $_POST['password'])){
        $username = $_POST['username'];
        $password = $_POST['password'];
        if(strlen($password)==0){
            echo 'password can not be empty';
        }else{
            $password = hash('sha512', $password.$salt);
            $data = array('username');
            $where = array('username' => $username);
            $response = $database->select('User', $data, $where);

            if(count($response)==0){
                echo 'username is not in the database';
            }else{
                $where = array('AND' => array('username' => $username, 'password' => $password));
                $response = $database->select('User', $data, $where);
                if(count($response)==0){
                    echo 'password is not correct';
                }else{
                    $response = $database->delete('User',$where);
                    echo 'successfully deleted';
                }
            }
        }
    }
    else{
        echo 'fail';
    }

?>