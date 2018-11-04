<?php
	include 'var.php';
	$name = $_GET["name"];
	$score = $_GET["score"];
	$name = mysqli_real_escape_string($conn, $name);
	$score = mysqli_real_escape_string($conn, $score);
	$sql = "INSERT INTO highScore (Name, Score) VALUES ('$name', $score);";
	$data = $conn->query($sql);
?>