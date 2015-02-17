<?php

class FFDB_Functions {

    private $ffdb;

    //put your code here
    // constructor
    function __construct() {
        include_once './ffdb_connect.php';
        // connecting to database
        $this->ffdb = new FFDB_Connect();
        $this->ffdb->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeEntry($Entry) {
        // Insert entry into database
        $result = mysql_query("INSERT INTO food_entries(FoodType) VALUES('$Entry')");
		
        if ($result) {
			return true;
        } else {			
			// For other errors
			return false;
		}
    }
	 /**
     * Getting all entries
     */
    public function getAllEntries() {
        $result = mysql_query("select * FROM food_entries");
        return $result;
    }
	/**
     * Get Yet to Sync row Count
     */
    public function getRowCount() {
        $result = mysql_query("SELECT * FROM food_entries");//  WHERE syncsts = FALSE");
        return $result;
    }
	
	public function updateSyncSts($type, $sts){
		$result = mysql_query("UPDATE food_entries SET syncsts = $sts WHERE Type = $type");
		return $result;
	}
}

?>