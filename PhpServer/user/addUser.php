<?php
    // Include Medoo
    require_once '../medoo.php';
    // Initialize
    $database = new medoo('workoutbuddy_01');

    if(isset($_POST['username'], $_POST['password'])){
        $username = $_POST['username'];
        $password = $_POST['password'];
        $data = array(
            'username' => $username,
            'password' => $password);
        echo $database->insert('User', $data);
    }
    else{
        echo 'fail';
    }

?>