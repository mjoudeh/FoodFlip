<?php

class FFDB_Functions {

    private $ffdb;

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
	 * Get user
	 */
	public function getUser($id) {
		$result = mysql_query("SELECT * FROM users WHERE id = '$id'");
		return $result;
	}
	
	/**
	 * Store new user
	 */
	public function storeUser($id) {
		$result = mysql_query("INSERT INTO users(id, karma) VALUES('$id', '0')");
		
		if ($result) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * Storing new entry
     * returns entry details
     */
    public function storeEntry($building, $location, $foodCategory, $foodType, $foodDescription) {
        // Insert entry into database
        $result = mysql_query("INSERT INTO food_entries(Building, Location, FoodCategory, FoodType, FoodDescription) VALUES('$building', '$location', '$foodCategory', '$foodType', '$foodDescription')");
		
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
        $result = mysql_query("SELECT * FROM food_entries");
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