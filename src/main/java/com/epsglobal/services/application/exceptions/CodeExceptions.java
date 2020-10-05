package com.epsglobal.services.application.exceptions;

import java.util.HashMap;
import java.util.Map;

public class CodeExceptions {
	public static final int ERROR = 1;
	
	public static final int PASSWORD_INVALID = 50;
	public static final int USER_INACTIVE = 51;
	public static final int USER_BLOCKED = 52;
	public static final int MUST_CHANGE_PASSWORD = 53;
	
	public static final int CUSTOMER_NOT_FOUND = 100;
	
	public static final int LOCATION_NOT_FOUND = 200;
	public static final int LOCATION_EXISTS = 201;
	
	public static final int WAREHOUSE_NOT_FOUND = 300;
	public static final int WAREHOUSE_EXISTS = 301;
	public static final int WAREHOUSE_USER_NOT_FOUND = 302;
	
	public static final int WAREHOUSE_CARRIER_NOT_FOUND = 303;
	public static final int WAREHOUSE_CARRIER_MINIMIUM = 304;
	public static final int WAREHOUSE_CARRIER_MAXIMIUM = 305;
	public static final int WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND = 306;
	public static final int WAREHOUSE_CARRIER_ORIGIN_NOT_FOUND = 307;
	public static final int WAREHOUSE_CARRIER_DESTINATION_NOT_FOUND = 308;
	
	public static final int WAREHOUSE_DIRECT_NOT_FOUND = 309;
	public static final int WAREHOUSE_DIRECT_MINIMIUM = 310;
	public static final int WAREHOUSE_DIRECT_MAXIMIUM = 311;
	public static final int WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND = 312;
	public static final int WAREHOUSE_DIRECT_ORIGIN_NOT_FOUND = 313;
	public static final int WAREHOUSE_DIRECT_DESTINATION_NOT_FOUND = 314;
	
	public static final int WAREHOUSE_ADAPTER_NOT_FOUND = 315;
	public static final int WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND = 316;
	public static final int WAREHOUSE_ADAPTER_ORIGIN_NOT_FOUND = 317;
	public static final int WAREHOUSE_ADAPTER_DESTINATION_NOT_FOUND = 318;
	
	public static final int WAREHOUSE_DEVICE_NOT_FOUND = 319;
	public static final int WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND = 320;
	public static final int WAREHOUSE_DEVICE_ORIGIN_NOT_FOUND = 321;
	public static final int WAREHOUSE_DEVICE_DESTINATION_NOT_FOUND = 322;
	
	
	public static final int MANUFACTER_NOT_FOUND = 400;
	public static final int MANUFACTER_EXISTS = 401;
	
	public static final int CARRIER_NOT_FOUND = 500;
	public static final int CARRIER_EXISTS = 501;
	
	public static final int DIRECT_NOT_FOUND = 600;
	public static final int DIRECT_EXISTS = 601;
	
	public static final int ADAPTER_NOT_FOUND = 700;
	public static final int ADAPTER_EXISTS = 701;
	
	public static final int DEVICE_NOT_FOUND = 800;
	public static final int DEVICE_EXISTS = 801;
	
	public static final int FILE_NOT_FOUND = 900;
	
	public static final int USER_NOT_FOUND = 1000;
	public static final int USER_EXISTS = 1001;
	
	public static final int ROLE_NOT_FOUND = 1100;
	public static final int ROLE_EXISTS = 1101;
	
	public static final int PERMISSION_NOT_FOUND = 1100;
	public static final int PERMISSION_EXISTS = 1101;
	
	public static final int USER_ROLE_NOT_FOUND = 1200;
	public static final int USER_ROLE_EXISTS = 1201;
	
	public static final int ROL_PERMISSION_NOT_FOUND = 1300;
	public static final int ROL_PERMISSION_EXISTS = 1301;
	
	
	public static final int BACKUP_NOT_FOUND = 1400;
	public static final int BACKUP_EXISTS = 1401;
	public static Map<Integer, String> ERRORS = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(ERROR, "Error");
			
			put(PASSWORD_INVALID, "The password is invalid");
			put(USER_INACTIVE, "Thes user is inactive");
			put(USER_BLOCKED, "The user is blocked");
			put(MUST_CHANGE_PASSWORD, "You must change the password");
			
			put(CUSTOMER_NOT_FOUND, "The client does not exist");
			
			put(LOCATION_NOT_FOUND, "The location does not exist");
			put(LOCATION_EXISTS, "The location exists");
			
			put(WAREHOUSE_NOT_FOUND, "The warehouse does not exist");
			put(WAREHOUSE_EXISTS, "The warehouse exists");
			put(WAREHOUSE_USER_NOT_FOUND, "The user does not exist");
			
			put(WAREHOUSE_CARRIER_NOT_FOUND, "The carrier does not exist");
			put(WAREHOUSE_CARRIER_MINIMIUM, "Minimum of elements not allowed");
			put(WAREHOUSE_CARRIER_MAXIMIUM, "Maximum of elements not allowed");
			put(WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND, "The transfer warehouse-carrier does not exist");
			put(WAREHOUSE_CARRIER_ORIGIN_NOT_FOUND, "The relationship warehouse-carrier of origin does not exist");
			put(WAREHOUSE_CARRIER_DESTINATION_NOT_FOUND, "The relationship warehouse-carrier of destination does not exist");
			
			put(WAREHOUSE_DIRECT_NOT_FOUND, "The direct does not exist");
			put(WAREHOUSE_DIRECT_MINIMIUM, "Minimum of elements not allowed");
			put(WAREHOUSE_DIRECT_MAXIMIUM, "Maximum of elements not allowed");
			put(WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND, "The transfer warehouse-direct does not exist");
			put(WAREHOUSE_DIRECT_ORIGIN_NOT_FOUND, "The relationship warehouse-direct of origin does not exist");
			put(WAREHOUSE_DIRECT_DESTINATION_NOT_FOUND, "The relationship warehouse-direct of destination does not exist");
			
			put(WAREHOUSE_ADAPTER_NOT_FOUND, "The adapter does not exist");
			put(WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND, "The transfer warehouse-adapter does not exist");
			put(WAREHOUSE_ADAPTER_ORIGIN_NOT_FOUND, "The relationship warehouse-adapter of origin does not exist");
			put(WAREHOUSE_ADAPTER_DESTINATION_NOT_FOUND, "The relationship warehouse-adapter of destination does not exist");
			
			put(WAREHOUSE_DEVICE_NOT_FOUND, "The device does not exist");
			put(WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND, "The transfer warehouse-device does not exist");
			put(WAREHOUSE_DEVICE_ORIGIN_NOT_FOUND, "The relationship warehouse-device of origin does not exist");
			put(WAREHOUSE_DEVICE_DESTINATION_NOT_FOUND, "The relationship warehouse-device of destination does not exist");
			
			put(MANUFACTER_NOT_FOUND, "The manufacturer does not exist");
			put(MANUFACTER_EXISTS, "The manufacturer exists");
			
			put(CARRIER_NOT_FOUND, "The carrier does not exist");
			put(CARRIER_EXISTS, "The carrier exists");
			
			put(DIRECT_NOT_FOUND, "The direct does not exist");
			put(DIRECT_EXISTS, "The direct exists");
			
			put(ADAPTER_NOT_FOUND, "The adapter does not exist");
			put(ADAPTER_EXISTS, "The adapter exists");
			
			put(DEVICE_NOT_FOUND, "The device does not exist");
			put(DEVICE_EXISTS, "The device exists");
			
			put(FILE_NOT_FOUND, "The file does not exist");
			
			put(USER_NOT_FOUND, "The user does not exist");
			put(USER_EXISTS, "The user exists");
			
			put(ROLE_NOT_FOUND, "The rol does not exist");
			put(ROLE_EXISTS, "The rol exists");
			
			put(PERMISSION_NOT_FOUND, "The permission does not exist");
			put(PERMISSION_EXISTS, "The permission exists");
			
			put(USER_ROLE_NOT_FOUND, "The user does not exist");
			put(USER_ROLE_EXISTS, "The user exists");
			
			put(ROL_PERMISSION_NOT_FOUND, "The permission does not exist");
			put(ROL_PERMISSION_EXISTS, "The permission exists");
			
			put(BACKUP_NOT_FOUND, "The backup does not exist");
			put(BACKUP_EXISTS, "The backup exists");			
		}
	};
}
