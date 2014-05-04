<?php
/**
 * Helper function to making curl calls much shorter and less repetative.
 */
function curlHelper($url, $data) {
	$ch = curl_init($url);

	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

	$response = curl_exec($ch);
	curl_close($ch);

	return $response;
}
?>