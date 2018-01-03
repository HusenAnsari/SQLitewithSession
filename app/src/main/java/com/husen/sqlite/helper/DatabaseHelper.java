package com.husen.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.husen.sqlite.model.Employee;
import com.husen.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gulamhusen on 21-11-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private Cursor cursor;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employee.db";

    //TABLE USER
    private static final String TABLE_USER = "user";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_ACTIVE = "userActive";

    //TABLE EMPLOYEE
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String EMPLOYEE_NAME = "employeeName";
    private static final String EMPLOYEE_PHONE = "employeePhone";
    private User user;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+"("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, "
                + USER_EMAIL + " TEXT UNIQUE, "
                + USER_PASSWORD + " TEXT, "
                + USER_ACTIVE + " INTEGER DEFAULT 0 "
                +")";
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_EMPLOYEE = "CREATE TABLE "+TABLE_EMPLOYEE+"("
                + EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMPLOYEE_NAME + " TEXT, "
                + EMPLOYEE_PHONE + " TEXT, "
                + USER_ID + " TEXT, "
                + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+USER_ID+")"
                +")";
        db.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS"+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS '" +TABLE_USER+"'");
        db.execSQL("DROP TABLE IF EXISTS '" +TABLE_EMPLOYEE+"'");
        onCreate(db);
    }

    public void addUser(User user){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getUserName());
        contentValues.put(USER_EMAIL, user.getUserEmail());
        contentValues.put(USER_PASSWORD, user.getUserPassword());
        db.insert(TABLE_USER, null,contentValues);
        db.close();
    }

    public void addEmployee(Employee employee){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_NAME, employee.getEmployeeName());
        contentValues.put(EMPLOYEE_PHONE, employee.getEmployeePhone());
        db.insert(TABLE_EMPLOYEE, null,contentValues);
        db.close();
    }

    //USING RAW QUERY
    public boolean isEmailExist(String strEmailAdd) {
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE "+ USER_EMAIL +" = '" + strEmailAdd + "'", null);
        boolean exist = (cursor.getCount() > 0);
        Log.e("count", String.valueOf(cursor.getCount()));
        cursor.close();
        db.close();
        return exist;
    }

   /* //USING DB QUERY
    public boolean isExist(String strEmailAdd){
        String whereClause = USER_EMAIL+"= ?";
        String[] whereArgs = new String[]{strEmailAdd};
        db = this.getWritableDatabase();
        cursor = db.query(TABLE_USER, null, whereClause, whereArgs, null, null, null);
        boolean exist = (cursor.getCount() > 0);
        Log.e("count", String.valueOf(cursor.getCount()));
        cursor.close();
        db.close();
        return exist;
    }*/


   public boolean checkUser(String email, String password) {
       String[] columns = {USER_ID};
       String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " = ?";
       String[] selectionArgs = new String[]{email, password};
       db = this.getReadableDatabase();
     /*  *
        * Here query function is used to fetch records from user table this function works like we use sql query.
        * SQL query equivalent to this query function is
        * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
        */
       cursor = db.query(TABLE_USER, //Table to query
               columns,                    //columns to return
               selection,                  //columns for the WHERE clause
               selectionArgs,              //The values for the WHERE clause
               null,                       //group the rows
               null,                       //filter by row groups
               null);                      //The sort order

       boolean isUser = (cursor.getCount() > 0);
       cursor.close();
       db.close();
       return isUser;
   }

   //GET USERNAME FROM EMAILID
   /* public String getEmployeeName(String email) {
       db = this.getReadableDatabase();
        String empName = "";
        try {
            cursor = db.rawQuery("SELECT "+USER_NAME+" FROM " + TABLE_USER + " WHERE "+ USER_EMAIL +" = '" + email + "'", null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            }
            return empName;
        }finally {
            cursor.close();
            db.close();
        }
    }*/

    public User getUser(String email){
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE "+ USER_EMAIL +" = '" + email + "'", null);
        if (cursor.moveToFirst()){
            do {
                user = new User();
                user.setUserId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                user.setUserEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                user.setUserPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
                user.setUserActive(cursor.getInt(cursor.getColumnIndex(USER_ACTIVE)));
            }while (cursor.moveToNext());
        }
        db.close();
        return user;
    }

    public void updateUser(int id){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ACTIVE,id);
        db.update(TABLE_USER,contentValues,USER_ACTIVE + "=?",new String[] {String.valueOf(id)});
    }

    public boolean checkDataAvalible() {
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TABLE_USER , null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Employee> getAllEmployee() {
        List<Employee> empList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                //   user.setId(cursor.getInt(0));
                employee.setEmployeeName(cursor.getString(cursor.getColumnIndex(EMPLOYEE_NAME)));
                employee.setEmployeePhone(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PHONE)));
            } while (cursor.moveToNext());
        }
        return empList;
    }
}
