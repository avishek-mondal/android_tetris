<?php
	define("DB_HOST", getenv("DB_HOST"));
	define("DB_USER", getenv("DB_USER_ISIS"));
	define("DB_PASS", getenv("DB_PASS_ISIS"));
	define("DB_NAME", getenv("DB_NAME_ISIS"));
	
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }	
    ?>