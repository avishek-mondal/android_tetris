<?php
	include 'var.php';
	$sql = "SELECT Name, Score FROM highScore WHERE 1=1;";
	$data = $conn->query($sql);
	$dataToEcho = "";
	//var_dump($data);
	while ($row = $data->fetch_assoc()) {
		$dataToEcho = $dataToEcho . "$row[Name],$row[Score],";
	}
	echo substr_replace($dataToEcho, "", -1);
?>