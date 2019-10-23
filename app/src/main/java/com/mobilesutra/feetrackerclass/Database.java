package com.mobilesutra.feetrackerclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.mobilesutra.feetrackerclass.model.StudentInfo;
import com.mobilesutra.feetrackerclass.table.TABLE_BATCH;
import com.mobilesutra.feetrackerclass.table.TABLE_BRANCH;
import com.mobilesutra.feetrackerclass.table.TABLE_INSERT_FEE;
import com.mobilesutra.feetrackerclass.table.TABLE_PROFILE_COMBINATION;
import com.mobilesutra.feetrackerclass.table.TABLE_STANDARD;
import com.mobilesutra.feetrackerclass.table.TABLE_SUBJECT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Database extends SQLiteOpenHelper {
    String sname, cname, sid, std,feep, bal, cno, deedate,paymentstatus;
    String res = " ";

    private static final int DATABASE_VERSION = 5; // old 4
    private static final String DATABASE_NAME = "StudentDatabase.db";

    private static final String TABLE_NAME1 = "Profile";
    private static final String BranchTable = "branchtable";
    private static final String BatchTable = "batchtable";
    private static final String SubjectTable = "subjecttable";
    private static final String StandardTable = "standardtable";
    private static final String ProfileTable = "profiletable";
    private static final String student_subject = "student_subject";
    private static final String insert_fee = "insert_fee";
    private static final String StudentFeeTable = "studentfeetable";
    private static final String AbsentStudentRegister = "absentstudentregister";
    private static final String Registration = "registration";
    private static final String Student_Data_Table = "insert_local_add_student";






    private static final String Auto_Id = "Auto_Id";
    private static final String branch = "Branch";
    private static final String batch = "Batch";
    private static final String class_id = "ClassID";
    private static final String ID = "id";

    private static final String branchname = "BranchName";
    private static final String batchname = "BatchName";
    private static final String subjectname = "SubjectName";
    private static final String standardname = "StandardName";

    private static final String branch_id = "BranchId";
    private static final String batch_id = "BatchId";
    private static final String subject_id = "SubjectId";
    private static final String standard_id = "StandardId";

    private static final String studentname = "StudentName";
    private static final String studentprofile = "StudentProfile";
    private static final String classname = "ClassName";
    private static final String studentid = "StudentId";
    private static final String studentidwithall = "StudentIdWithAll";
    private static final String standard = "Standard";




    private static final String classid = "ClassId";
    private static final String class_name = "ClassName";
    private static final String contact_no = "ContactNo";
    private static final String emailId = "EmailID";
    private static final String address = "Address";
    private static final String city = "City";
    private static final String description = "Description";
    private static final String uname = "Username";
    private static final String pwd = "Password";
    private static final String valid_from = "Valid_From";
    private static final String valid_till = "Valid_Till";
    private static final String Flag_Active = "flag_active";

    private static final String doa = "DOA";
    private static final String feeid = "FeeID";
    private static final String student_name = "StudentName";
    private static final String standard1 = "Standard";
    private static final String ph1 = "Phone1";
    private static final String ph2 = "Phone2";
    private static final String subjectoption = "SubjectOption";

    private static final String Studid = "StudentID";
    private static final String Subid = "SubjectID";
    private static final String Payment_Status = "PaymentStatus";

    private static final String LOG_TAG = "Database";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db) {




        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + BranchTable
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,BranchName TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + BatchTable
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,BatchName TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + SubjectTable
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,SubjectName TEXT);");


        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + StandardTable
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,StandardName TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ProfileTable
                        + " (id INTEGER PRIMARY KEY AUTOINCREMENT," + class_id
                        + " TEXT, " + class_name
                        + " TEXT, " + branch_id + " TEXT, " + standard_id + " TEXT, " + batch_id + " TEXT, "
                        + subject_id + " TEXT, " + Flag_Active + " TEXT, "
                        + " FOREIGN KEY( " + branch_id + " ) REFERENCES " + BranchTable
                        + " (id), " + " FOREIGN KEY( " + standard_id + " ) REFERENCES "
                        + StandardTable + " (id));" + " FOREIGN KEY( " + batch_id + " ) REFERENCES "
                        + BatchTable + " (id), " + " FOREIGN KEY( " + subject_id
                        + " ) REFERENCES " + SubjectTable + " (id), "
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + student_subject
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,StudentID TEXT,SubjectID TEXT,StudentIdWithAll TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + StudentFeeTable
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT,StudentID TEXT,Standard TEXT,FeesID TEXT,FeesPaid TEXT,Balance TEXT,Date TEXT,Remark TEXT,Batch TEXT,Subjects TEXT,Branch TEXT,PaymentStatus Text);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + Student_Data_Table
                + " (Auto_Id INTEGER PRIMARY KEY AUTOINCREMENT, id INT NOT NULL, ClassID TEXT, Branch TEXT,StudentName TEXT,StudentProfile BLOB,Standard TEXT,Batch TEXT,DOA TEXT,Address TEXT,Phone1 TEXT,Phone2 TEXT);");
                //+ " (Auto_Id INTEGER PRIMARY KEY AUTOINCREMENT, id INT NOT NULL UNIQUE , ClassID TEXT, Branch TEXT,StudentName TEXT,Standard TEXT,Batch TEXT,DOA TEXT,Address TEXT,Phone1 TEXT,Phone2 TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + AbsentStudentRegister
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, ClassID TEXT, StudentID TEXT, StudentName TEXT, Date TEXT, Branch TEXT,Standard TEXT, Batch TEXT, Subjects TEXT,SubjectOption TEXT,  IsPresent);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + insert_fee
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT, FeeID TEXT,Fee TEXT,Subjects TEXT,Branch TEXT," +
                "Standard TEXT,Batch TEXT);");


        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + Registration
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, ClassId TEXT,ClassName TEXT,ContactNo TEXT,EmailID TEXT,Address TEXT,City TEXT,Description TEXT, Username TEXT,Password TEXT,Valid_From Date,Valid_Till Date );");


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        if (oldVersion == 1) {

            Log.i("InsertRow:", "onUpgrade onUpgrade");
         /*   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + BranchTable);
            db.execSQL("DROP TABLE IF EXISTS " + BatchTable);
            db.execSQL("DROP TABLE IF EXISTS " + SubjectTable);
            db.execSQL("DROP TABLE IF EXISTS " + StandardTable);
            db.execSQL("DROP TABLE IF EXISTS " + ProfileTable);
            db.execSQL("DROP TABLE IF EXISTS " + student_subject);
            db.execSQL("DROP TABLE IF EXISTS " + insert_fee);
            db.execSQL("DROP TABLE IF EXISTS " + StudentFeeTable);
            db.execSQL("DROP TABLE IF EXISTS " + AbsentStudentRegister);
            db.execSQL("DROP TABLE IF EXISTS " + Registration);*/
            db.execSQL("ALTER TABLE "+ Student_Data_Table + " ADD "+ studentprofile +" BLOB DEFAULT ''");
        }

        if (oldVersion == 2) {

            Log.i("InsertRow:", "onUpgrade onUpgrade");
         /*   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + BranchTable);
            db.execSQL("DROP TABLE IF EXISTS " + BatchTable);
            db.execSQL("DROP TABLE IF EXISTS " + SubjectTable);
            db.execSQL("DROP TABLE IF EXISTS " + StandardTable);
            db.execSQL("DROP TABLE IF EXISTS " + ProfileTable);
            db.execSQL("DROP TABLE IF EXISTS " + student_subject);
            db.execSQL("DROP TABLE IF EXISTS " + insert_fee);
            db.execSQL("DROP TABLE IF EXISTS " + StudentFeeTable);
            db.execSQL("DROP TABLE IF EXISTS " + AbsentStudentRegister);
            db.execSQL("DROP TABLE IF EXISTS " + Registration);*/
            db.execSQL("ALTER TABLE "+ Student_Data_Table + " ADD "+ studentprofile +" BLOB DEFAULT ''");
        }
        if (oldVersion == 3) {

            Log.i("InsertRow:", "onUpgrade onUpgrade");
         /*   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + BranchTable);
            db.execSQL("DROP TABLE IF EXISTS " + BatchTable);
            db.execSQL("DROP TABLE IF EXISTS " + SubjectTable);
            db.execSQL("DROP TABLE IF EXISTS " + StandardTable);
            db.execSQL("DROP TABLE IF EXISTS " + ProfileTable);
            db.execSQL("DROP TABLE IF EXISTS " + student_subject);
            db.execSQL("DROP TABLE IF EXISTS " + insert_fee);
            db.execSQL("DROP TABLE IF EXISTS " + StudentFeeTable);
            db.execSQL("DROP TABLE IF EXISTS " + AbsentStudentRegister);
            db.execSQL("DROP TABLE IF EXISTS " + Registration);*/
            db.execSQL("ALTER TABLE "+ Student_Data_Table + " ADD "+ studentprofile +" BLOB DEFAULT ''");
        }

        if (oldVersion == 4) {

            Log.i("InsertRow:", "onUpgrade onUpgrade");
         /*   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + BranchTable);
            db.execSQL("DROP TABLE IF EXISTS " + BatchTable);
            db.execSQL("DROP TABLE IF EXISTS " + SubjectTable);
            db.execSQL("DROP TABLE IF EXISTS " + StandardTable);
            db.execSQL("DROP TABLE IF EXISTS " + ProfileTable);
            db.execSQL("DROP TABLE IF EXISTS " + student_subject);
            db.execSQL("DROP TABLE IF EXISTS " + insert_fee);
            db.execSQL("DROP TABLE IF EXISTS " + StudentFeeTable);
            db.execSQL("DROP TABLE IF EXISTS " + AbsentStudentRegister);
            db.execSQL("DROP TABLE IF EXISTS " + Registration);*/
            db.execSQL("ALTER TABLE "+ Student_Data_Table + " ADD "+ studentprofile +" BLOB DEFAULT ''");
        }



      //  db.execSQL("ALTER TABLE student ADD COLUMN student_rollno INTEGER DEFAULT 0");

      //  onCreate(db);




    }



    public String checkLogin(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "select count(*)  from " + TABLE_NAME1
                + " where UserName='" + user.toString() + "' and Password='"
                + pass.toString() + "'";
        SQLiteStatement statement = db.compileStatement(str);
        String count = statement.simpleQueryForString();
        Integer num = Integer.valueOf(count);
        statement.close();
        if (num > 0) {
            return "1";
        } else {
            return "0";
        }

    }



    public String getStudentID(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "select count(*)  from " + TABLE_NAME1
                + " where UserName='" + user.toString() + "' and Password='"
                + pass.toString() + "'";
        SQLiteStatement statement = db.compileStatement(str);
        String count = statement.simpleQueryForString();
        Integer num = Integer.valueOf(count);
        statement.close();
        if (num > 0) {
            String str1 = "select *  from " + TABLE_NAME1 + " where UserName='"
                    + user.toString() + "' and Password='" + pass.toString()
                    + "' LIMIT 1";
            Cursor c = db.rawQuery(str1, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                String s1 = c.getString(c.getColumnIndexOrThrow("StudentID"));
                Log.i("mobilesutra.feetracker",
                        "Vlaue of student id got from query is " + s1);

                c.close();
                return s1;
            } else {
                c.close();
                return "n";
            }

        } else {
            return "n";
        }
    }

        public void deleteStudents()
        {

            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "delete   from " + Student_Data_Table;
            Cursor c = db.rawQuery(sql, null);
            log(c.getCount() + "delete Students sucessfully");
        }
       public  void deleteStudent_subject()
       {

          SQLiteDatabase db = this.getReadableDatabase();
          String sql = "delete   from " +student_subject;
         Cursor c = db.rawQuery(sql,null);
           log(c.getCount()+"delete Student_subject sucessfully");
         }

    public  void deleteAttendance()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete   from " +AbsentStudentRegister;
        Cursor c = db.rawQuery(sql,null);
        log(c.getCount() + "delete AbsentStudentRegister sucessfully");
    }


    public  boolean isRollNoSetForSubject(String classId, String rollNo, String bran,
                                          String std, String batch){

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + Student_Data_Table
                + " WHERE  ClassID=? and Branch=? and Standard=? and Batch=? and id =? ";

        Cursor c = db.rawQuery(sql, new String[]{classId, bran, std, batch, rollNo});

       if( c.getCount() > 0)
           return true;
        else
           return false;
    }

    public long insert_local_student(String clid, String sid, String bran, String name,String studentprofile,
                                       String std, String batch, String doa, String address,
                                       String phone1, String phone2) {

        SQLiteDatabase db = this.getWritableDatabase();
        long rowId;
        try {
            ContentValues initialValues1 = new ContentValues();
            initialValues1.put("ClassID", clid);
            initialValues1.put("id", sid);
            initialValues1.put("Branch", bran);
            initialValues1.put("StudentName", name);
            initialValues1.put("studentprofile", studentprofile);
            initialValues1.put("Standard", std);
            //	initialValues1.put("SubjectName", sub);
            initialValues1.put("Batch", batch);
            initialValues1.put("DOA", doa);
            initialValues1.put("Address", address);
            initialValues1.put("Phone1", phone1);
            initialValues1.put("Phone2", phone2);

        /*rowid = db.insert(Student_Data_Table, null, initialValues1);
        Log.i("Database:", "cv:insert_student=" + initialValues1);

        return String.valueOf(rowid);*/
            //  Log.i("sql->", "22222" + initialValues1);
            String sql = "SELECT * FROM " + Student_Data_Table
                    + " WHERE  ClassID=? and StudentName=? and Branch=? and Standard=? and Batch=? and DOA=? and Address=? and Phone1=? and Phone2=?";

            Cursor c = db.rawQuery(sql, new String[]{clid, name, bran, std, batch, doa, address, phone1, phone2});

            // Cursor c = db.rawQuery(stmt, null);
            int num = c.getCount();
            if (num > 0) {
                rowId = db.update(Student_Data_Table, initialValues1, "id" + "=? and " + "ClassID" + "=? and " + "StudentName" + "=? and " + "Branch" + "=? and " + "Standard" + "=? and " + "Batch" + "=? " +
                        "and " + "DOA" + "=? and " + "Address" + "=? and " + "Phone1" + "=? and " + "Phone2" + "=?", new String[]{sid, clid, name, bran, std, batch, doa, address, phone1, phone2});
                Log.i("UpdatedRows:", "" + rowId);

            } else {
                rowId = db.insert(Student_Data_Table, null, initialValues1);
                Log.i("InsertRow:", "" + rowId);
            }
            c.close();
        }catch (Exception e){
            log("insert_local_student() ERROR :", e.toString());
            return -1;
        }
        return rowId;

    }
    public String insert_local_student2(String autoid,String aid,String clid, String bran, String name,
                                       String std, String batch, String doa, String address,
                                       String phone1, String phone2) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        long rowid;

        ContentValues initialValues1 = new ContentValues();
        initialValues1.put("Auto_Id", autoid);
        initialValues1.put("id", aid);
        initialValues1.put("ClassID", clid);
        initialValues1.put("Branch", bran);
        initialValues1.put("StudentName", name);
        initialValues1.put("Standard", std);
        //	initialValues1.put("SubjectName", sub);
        initialValues1.put("Batch", batch);
        initialValues1.put("DOA", doa);
        initialValues1.put("Address", address);
        initialValues1.put("Phone1", phone1);
        initialValues1.put("Phone2", phone2);

        /*rowid = db.insert(Student_Data_Table, null, initialValues1);
        Log.i("Database:", "cv:insert_student=" + initialValues1);

        return String.valueOf(rowid);*/

        String sql = "SELECT * FROM " + Student_Data_Table
                + " WHERE Auto_Id=? and ClassID=? and StudentName=? and Branch=? and Standard=? and Batch=? and DOA=? and Address=? and Phone1=? and Phone2=?";;

        Log.i("sql->", "22222" + sql);

        Cursor c = db.rawQuery(sql, new String[]{autoid,clid, name, bran, std, batch,doa,address,phone1,phone2});

        // Cursor c = db.rawQuery(stmt, null);
        int num = c.getCount();
        long rowId;
        if (num > 0) {
            rowId = db.update(Student_Data_Table, initialValues1, "Auto_Id=? and ClassID" + "=? and " + "StudentName" + "=? and " + "Branch" + "=? and " + "Standard" + "=? and " + "Batch" + "=? " +
                    "and " + "DOA" + "=? and " + "Address" + "=? and " + "Phone1" + "=? and " + "Phone2" + "=?", new String[]{autoid,clid, name, bran, std, batch,doa,address,phone1,phone2});
            Log.i("UpdatedRows:", "insert_local_student2-*" + rowId);

        } else {
            rowId = db.insert(Student_Data_Table, null, initialValues1);
            Log.i("InsertRow:", "insert_local_student2-*#" + rowId);
        }

        c.close();
        return rowId + "";

    }


    public long update_local_student(String id,String sid,String clid, String bran, String name,String studentprofile,
                                       String std, String batch, String doa, String address,
                                       String phone1, String phone2) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        long rowId;

        try {
            ContentValues initialValues1 = new ContentValues();
            initialValues1.put("id", sid);
            initialValues1.put("ClassID", clid);
            initialValues1.put("Branch", bran);
            initialValues1.put("StudentName", name);
            initialValues1.put("studentprofile", studentprofile);
            initialValues1.put("Standard", std);
            initialValues1.put("Batch", batch);
            initialValues1.put("DOA", doa);
            initialValues1.put("Address", address);
            initialValues1.put("Phone1", phone1);
            initialValues1.put("Phone2", phone2);

            log("studentprofile " + studentprofile);
            rowId = db.update(Student_Data_Table, initialValues1, "Auto_Id" + "=?", new String[]{id});

            log("updated student record " + rowId);
        }catch (Exception e){
            log("updated student record EXCEPTION " + e.toString());
            return -1;
        }
        return rowId;
    }



    public String insertFee(String clid, String feeid, String fee1, String sub, String branch1, String std, String batch1) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues initialValues1 = new ContentValues();
        initialValues1.put("ClassID", clid);
        initialValues1.put("FeeID", feeid);
        initialValues1.put("Fee", fee1);
        initialValues1.put("Subjects", sub);
        initialValues1.put("Branch", branch1);
        initialValues1.put("Standard", std);
        initialValues1.put("Batch", batch1);


        Log.i("insertORupdatefee", "");
        Log.i("cv->", "" + initialValues1);

        String sql = "SELECT * FROM " + insert_fee
                + " WHERE ClassID=? and FeeID=? and Branch=? and Standard=? and Batch=?  and Subjects=? ";

        Log.i("sql->", "" + sql);

        Cursor c = db.rawQuery(sql, new String[]{clid, feeid, branch1, std, batch1,sub});

        // Cursor c = db.rawQuery(stmt, null);
        int num = c.getCount();
        long rowId;
        if (num > 0) {
            rowId = db.update(insert_fee, initialValues1, "ClassID" + "=? and " + "FeeID" + "=? and " + "Branch" + "=? and " + "Standard" + "=? and " + "Batch" + "=?  and " + "Subjects" + "=?", new String[]{clid, feeid, branch1, std, batch1,sub});
            Log.i("UpdatedRows:", "" + rowId);

        } else {
            rowId = db.insert(insert_fee, null, initialValues1);
            Log.i("InsertRow:", "" + rowId);
        }

        c.close();
        return rowId + "";


    }


    public String updateFee(String id,String clid, String feeid, String fee1, String sub, String branch1, String std, String batch1) {
        SQLiteDatabase db = this.getWritableDatabase();

        long rowId=0;
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put("ClassID", clid);
        initialValues1.put("FeeID", feeid);
        initialValues1.put("Fee", fee1);
        initialValues1.put("Subjects", sub);
        initialValues1.put("Branch", branch1);
        initialValues1.put("Standard", std);
        initialValues1.put("Batch", batch1);

        String sql = "SELECT * FROM " + insert_fee
                + " WHERE id=?";

        Log.i("sql->", "" + sql);

        Cursor c = db.rawQuery(sql, new String[]{id});
        int num = c.getCount();
        if(num>0) {
            rowId = db.update(insert_fee, initialValues1, "id" + "=?", new String[]{id});
            Log.i("UpdatedRows:", "" + rowId);
        }

        return rowId+"";
    }

    public String getStudentName(String id)
    {
        String stud_name="";
        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + Student_Data_Table + " where " + Auto_Id
                + " =? " + " ;";

        Cursor c = db.rawQuery(sql, new String[]{id});
        int num = c.getCount();
        Log.i("FeeTracker",id+ "sql-insert query" + sql+" num->"+num);
        if(num > 0)
        {
            c.moveToFirst();
            stud_name= c.getString(c.getColumnIndexOrThrow(student_name));
        }
        return  stud_name;
    }


    public void insert_registration_details(String classid1, String clsname,
                                            String contactno, String email, String address1, String city1,
                                            String description1, String uname1, String pass1,
                                            String validfrom1, String validtill1) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues1 = new ContentValues();

        initialValues1.put(classid, classid1);
        initialValues1.put(class_name, clsname);
        initialValues1.put(contact_no, contactno);
        initialValues1.put(emailId, email);
        initialValues1.put(address, address1);
        initialValues1.put(city, city1);
        initialValues1.put(description, description1);
        initialValues1.put(uname, uname1);
        initialValues1.put(pwd, pass1);
        initialValues1.put(valid_from, validfrom1);
        initialValues1.put(valid_till, validtill1);

        Log.d("tag", "insert" + initialValues1);

        db.insert(Registration, null, initialValues1);

    }



    public long insertBranch(String clsid, String branchname1) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + BranchTable + " where " + class_id
                + " =?  and " + branchname + " =? " + " ;";
        Log.i("FeeTracker", "sql-insert query" + sql);
        Cursor c = db.rawQuery(sql, new String[]{clsid, branchname1});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, clsid);
        initialValues1.put(branchname, branchname1);

        Log.i("FeeTracker", "cv" + initialValues1);

        if (num > 0) {
            c.moveToFirst();
            int numRows = db.update(BranchTable, initialValues1, class_id
                    + " =?  and " + branchname + " =?", new String[]{clsid,
                    branchname1});
            Log.i("FeeTracker", "UPDATE BranchTable:" + numRows);
            return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id")));
        } else {
            rowId = db.insert(BranchTable, null, initialValues1);
            Log.d("FeeTracker", "INSERT BranchTable:" + rowId);
            return rowId;
        }

    }

    public long insertBatch(String clsid, String batchname1) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + BatchTable + " where " + class_id
                + " =?  and " + batchname + " =? " + " ;";
        Log.i("FeeTracker", "sql-insert query" + sql);
        Cursor c = db.rawQuery(sql, new String[]{clsid, batchname1});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, clsid);
        initialValues1.put(batchname, batchname1);

        Log.i("FeeTracker", "cv" + initialValues1);

        if (num > 0) {
            c.moveToFirst();
            int numRows = db.update(BatchTable, initialValues1, class_id
                    + " =?  and " + batchname + " =?", new String[]{clsid,
                    batchname1});
            Log.i("FeeTracker", "UPDATE batchtable:" + numRows);
            return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id")));
        } else {
            rowId = db.insert(BatchTable, null, initialValues1);
            Log.d("FeeTracker", "INSERT BatchTable:" + rowId);
            return rowId;
        }

    }

    public long insertSubject(String clsid, String subjectname1) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + SubjectTable + " where " + class_id
                + " =?  and " + subjectname + " =? " + " ;";

        Log.i("FeeTracker", "sql-insert query" + sql);
        Cursor c = db.rawQuery(sql, new String[]{clsid, subjectname1});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, clsid);
        initialValues1.put(subjectname, subjectname1);
        Log.i("FeeTracker", "cv" + initialValues1);

        if (num > 0) {
            c.moveToFirst();
            int numRows = db.update(SubjectTable, initialValues1, class_id
                    + " =?  and " + subjectname + " =?", new String[]{clsid,
                    subjectname1});
            Log.i("FeeTracker", "UPDATE SubjectTable:" + numRows);
            return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id")));
        } else {
            rowId = db.insert(SubjectTable, null, initialValues1);
            Log.d("FeeTracker", "INSERT SubjectTable:" + rowId);
            return rowId;
        }

    }

    public long insertStandard(String clsid, String stdname1) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + StandardTable + " where " + class_id
                + " =?  and " + standardname + " =? " + " ;";
        Log.i("FeeTracker", "sql-insert query" + sql);

        Cursor c = db.rawQuery(sql, new String[]{clsid, stdname1});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, clsid);
        initialValues1.put(standardname, stdname1);

        Log.i("FeeTracker", "cv" + initialValues1);

        if (num > 0) {
            c.moveToFirst();
            int numRows = db.update(StandardTable, initialValues1, class_id
                    + " =?  and " + standardname + " =?", new String[]{clsid,
                    stdname1});
            Log.i("FeeTracker", "UPDATE StandardTable:" + numRows);
            return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id")));
        } else {
            rowId = db.insert(StandardTable, null, initialValues1);
            Log.d("FeeTracker", "INSERT StandardTable:" + rowId);
            return rowId;
        }

    }

    public long insertStudent_Subject(String classId,String studid1, String subid1,String subjid2) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = 0;
        String sql = "SELECT * FROM " + student_subject + " where " + Studid
                + " =?  and " + Subid + " =? and " + class_id + " =? " + " ;";
        Log.i("FeeTracker", "sql-insert query" + sql);
        Cursor c = db.rawQuery(sql, new String[]{studid1, subid1,classId});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, classId);
        initialValues1.put(Studid, studid1);
        initialValues1.put(Subid, subid1);
        initialValues1.put(studentidwithall, subjid2);
        Log.i("FeeTracker", "cv:insert student_subject" + initialValues1);

        if (num > 0) {
            c.moveToFirst();
            int numRows = db.update(student_subject, initialValues1, Studid
                    + " =?  and " + Subid + " =?", new String[]{studid1,
                    subid1});
            Log.i("FeeTracker", "UPDATE student_subject:" + numRows);
            return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id")));
        } else {
            rowId = db.insert(student_subject, null, initialValues1);
            Log.d("FeeTracker", "INSERT student_subject:" + rowId);
            return rowId;
        }

    }
    public long DeleteStudent_Subject(String studid1)
    {    SQLiteDatabase db = this.getWritableDatabase();
        long    numRows  = 0;
        numRows= db.delete(student_subject, Studid+ " =?", new String[]{studid1});
        return  numRows;
    }
    public long updateStudent_Subject(String class_id,String studid1, String subid1,String subjid2) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        long    numRows  = 0;
        String sql = "SELECT * FROM " + student_subject + " where " + Studid
                + " =?  and " + Subid + " =? " + " ;";

        Cursor c = db.rawQuery(sql, new String[]{studid1, subid1});
        int num = c.getCount();
        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(classid, class_id);
        initialValues1.put(Studid, studid1);
        initialValues1.put(Subid, subid1);
        initialValues1.put(studentidwithall, subjid2);

        if(num > 0) {

           numRows = db.update(student_subject, initialValues1, Studid
                    + " =?", new String[]{studid1});
            log(numRows + "");
        }else
        {
            numRows = db.insert(student_subject, null, initialValues1);
        }
        return  numRows;

    }



    public String insertProfileTable(String clsid, String clsname, String branchid, String stdid, String batchid,
                                     String subid2,String flag) {

        int count=0;

        SQLiteDatabase db = this.getReadableDatabase();


        String sql = "SELECT * FROM " + ProfileTable + " where " + class_id + "='" + clsid + "' and " + class_name + "='" + clsname + "' and " + branch_id + "='" + branchid + "' and " + standard_id + "='" + stdid + "' and " + batch_id + "='" + batchid + "' and " + subject_id + "='" + subid2 + "' and " + Flag_Active + "='" + flag + "'";;

        Log.i("FeeTracker", "sql---" + sql);
        Cursor c = db.rawQuery(sql, null);
        count= c.getCount();
        Log.i("FeeTracker", "sql---" + count);

        ContentValues initialValues1 = new ContentValues();
        initialValues1.put(class_id, clsid);
        initialValues1.put(class_name, clsname);
        initialValues1.put(branch_id, branchid);
        initialValues1.put(standard_id, stdid);
        initialValues1.put(batch_id, batchid);
        initialValues1.put(subject_id, subid2);
        initialValues1.put(Flag_Active, flag);

        Log.i("FeeTracker", "cv" + initialValues1);

        if (count == 0) {

            long ROWiD = db.insert(ProfileTable, null, initialValues1);

            Log.d("FeeTracker", "Data inserted Rowid====" + ROWiD);
            c.close();
            return String.valueOf(ROWiD);
            // c.close();
            //return 0;
        } else {

            long ROWiD = db.update(ProfileTable, initialValues1, "ClassID='" + class_id + "' and BranchId='" + branchid + "' and StandardId='" + standard_id + "' and BatchId=' " + batch_id + "'  and SubjectId=' " + subject_id + "'", null);

            Log.e("FeeTracker", "ProfileTable updated data-" + ROWiD);
            return String.valueOf(ROWiD);
        }

    }




    public void updateFlagActiveBatch(String classid2,String branchid,String batchid,String standid,String subjid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + ProfileTable
                + " where ClassID='" + classid2 + "' and BranchId= '" +branchid+ "' and BatchId= '" +batchid+ "'  and StandardId= '" +standid+ "' and SubjectID IN(" + subjid +") and flag_active= 'N';";

        Log.d("tag", "updateFlagActiveBatch" + sql);
        Cursor c = db.rawQuery(sql, null);
        int num = c.getCount();
        Log.d("tag", "updateFlagActiveBatch" + num);
        if (num > 0) {
            c.moveToFirst();
            do {

                String id = c.getString(c.getColumnIndexOrThrow(ID));
                Log.d("tag", "updateFlagActiveBatch" + id);

                db.execSQL("update " + ProfileTable + " set " + Flag_Active + "='"
                        + "Y" + "' where " + ID + "='" + id + "';");
            }while (c.moveToNext());
        }
        c.close();
    }

    public void updateFlagActive(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String str = "select * from " + ProfileTable + " where " + ID + "='" + id + "'";
        Cursor c = db.rawQuery(str, null);
        int num = c.getCount();
        Log.d("tag", "flag" + num);
        if (num > 0) {
           db.execSQL("update " + ProfileTable + " set " + Flag_Active + "='"
                   + "Y" + "' where " + ID + "='" + id + "';");
        }
        c.close();
    }
    public void updateFlagInActive(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String str = "select * from " + ProfileTable + " where " + ID + "='" + id + "'";
        Cursor c = db.rawQuery(str, null);
        int num = c.getCount();
        Log.d("tag","flag"+num);
        if (num > 0) {
            db.execSQL("update " + ProfileTable + " set " + Flag_Active + "='"
                    + "N" + "' where " + ID + "='" + id + "';");
        }
        c.close();
    }


    public String getstudentinfo(String Studid) {

        SQLiteDatabase db = this.getReadableDatabase();
        String all = "";
        String sql = "SELECT * FROM StudentInfoTable where StudentID='"
                + Studid + "';";
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            sname = c.getString(c.getColumnIndexOrThrow(studentname));
            cname = c.getString(c.getColumnIndexOrThrow(classname));
            sid = c.getString(c.getColumnIndexOrThrow(studentid));
            std = c.getString(c.getColumnIndexOrThrow(standard));
            // subjid=c.getString(c.getColumnIndexOrThrow(subjectid));
            all = sname.concat("?").concat(cname).concat("?").concat(sid)
                    .concat("?").concat(std);
            Log.d("stinfoooooo", all);

        } else {
            all = "???";

        }
        c.close();
        return all;
    }



    public String getfeereportcount(String studid, String std) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT COUNT(*) FROM " + StudentFeeTable
                + " where StudentID='" + studid + "' and Standard='" + std
                + "';";
        SQLiteStatement statement = db.compileStatement(sql);
        String count = statement.simpleQueryForString();
        statement.close();
        return count;
    }

    public String getstudentfeerecord(String studid, String std) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + StudentFeeTable + " where StudentID='"
                + studid + "'  and Standard='" + std + "';";
        Cursor c = db.rawQuery(sql, null);

        res = "";
        String subname = "";
        String FeesID = "";
        String id = "";
        if (c.getCount()> 0) {
            c.moveToFirst();
            do {

               /* subname = c.getString(c
                        .getColumnIndexOrThrow("Subjects"));*/
                id = c.getString(c.getColumnIndexOrThrow("id"));
                feep = c.getString(c.getColumnIndexOrThrow("FeesPaid"));
                bal = c.getString(c.getColumnIndexOrThrow("Balance"));
                cno = c.getString(c.getColumnIndexOrThrow("Remark"));
                deedate = c.getString(c.getColumnIndexOrThrow("Date"));
                paymentstatus = c.getString(c.getColumnIndexOrThrow("PaymentStatus"));
MyApplication.log("","getstudentfeerecord = ");
                MyApplication.log("","getstudentfeerecord = ");
                MyApplication.log("","getstudentfeerecord feep= "+feep);
                MyApplication.log("","getstudentfeerecord bal= "+bal);
                MyApplication.log("","getstudentfeerecord Remark= "+cno);
                MyApplication.log("","getstudentfeerecord deedate= "+deedate);

                res = res.concat(feep).concat("?").concat(bal).concat("?")
                        .concat(cno).concat("?").concat(deedate).concat("?").concat(id).concat("?")
                        .concat(c.getString(c.getColumnIndexOrThrow("FeesID"))).concat("?").concat(paymentstatus).concat(";");;


                Log.d("fees", "" + c.getString(c.getColumnIndexOrThrow("FeesID")));
            } while (c.moveToNext());
        }
        Log.d("student fee", "getstudentfeerecord = " + res);
        c.close();
        return res;

    }




//    public String gettotalfee(String classid, String std, String studid) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String res1 = "0";
//        String sql1 = "SELECT * FROM " + TABLE_NAME11 + " where StudentID='"
//                + studid + "' and Standard='" + std + "' and ClassID='"
//                + classid + "';";
//        Cursor c1 = db.rawQuery(sql1, null);
//        if (c1.getCount() > 0) {
//            String sq2 = "SELECT sum(TotalFee) as TotalFee FROM "
//                    + TABLE_NAME11 + " where StudentID='" + studid
//                    + "' and Standard='" + std + "' and ClassID='" + classid
//                    + "';";
//            Cursor c2 = db.rawQuery(sq2, null);
//
//            if (c2.getCount() > 0) {
//                c2.moveToFirst();
//                String re = c2.getString(c2.getColumnIndexOrThrow("TotalFee"));
//
//                res1 = re;
//                Log.d("total", "t :- " + re);
//
//            }
//            c2.close();
//            c1.close();
//        } else {
//            c1.close();
//            return "0";
//        }
//
//        return res1;
//
//    }

    public String gettotalbal(String std, String studid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String res1 = "0";
        String sql1 = "SELECT * FROM " + StudentFeeTable + " where StudentID='"
                + studid + "' and Standard='" + std + "';";
        Cursor c1 = db.rawQuery(sql1, null);
        if (c1.getCount() > 0) {
            String sq2 = "SELECT sum(FeesPaid) as TotalFee FROM " + StudentFeeTable
                    + " where StudentID='" + studid + "' and Standard='" + std
                    + "';";
            Cursor c2 = db.rawQuery(sq2, null);

            if (c2.getCount() > 0) {
                c2.moveToFirst();
                String re = c2.getString(c2.getColumnIndexOrThrow("TotalFee"));

                res1 = re;
                Log.d("totalbal", "tbal :- " + re);

            }
            c2.close();

        } else {
            c1.close();
            return "0";
        }
        c1.close();
        return res1;

    }
    public String gettotalbal_new(String std, String studid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String res1 = "0";
        String sql1 = "SELECT * FROM " + StudentFeeTable + " where StudentID='"
                + studid + "' and Standard='" + std + "';";
        //MyApplication.log("get paid fees sql is " + sql1);
        Cursor c1 = db.rawQuery(sql1, null);
        if (c1.getCount() > 0) {
            String sq2 = "SELECT sum(FeesPaid) as TotalFee FROM " + StudentFeeTable
                    + " where StudentID='" + studid + "' and Standard='" + std
                    + "';";
            MyApplication.log("get paid fees sql2 is " + sq2);
            Cursor c2 = db.rawQuery(sq2, null);

            if (c2.getCount() > 0) {
                c2.moveToFirst();
                String re = c2.getString(c2.getColumnIndexOrThrow("TotalFee"));

                res1 = re;
                Log.d("totalbal", "tbal :- " + re);

            }
            c2.close();

        } else {
            c1.close();
            return "0";
        }
        c1.close();
        return res1;

    }








    public ArrayList<HashMap<String, String>> getStudentListForAttendance(String classid, String branch, String std, String batch, String subs,String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String studname = "", studid = "";
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
       String flags="0";

//        String sql3 = "SELECT * FROM " + AbsentStudentRegister
//                + " WHERE  Date=?  and Branch=? and Standard=? and Batch=? and Subjects=?";

        String sql3 = "SELECT  *  FROM "+AbsentStudentRegister+" where Date='" + date
                + "' and Branch='" + branch + "' and Standard='" + std + "' and Batch='" + batch + "' and Subjects IN(" + subs + ")";
        Log.i("TAG", "@@" + sql3);
        Cursor c1 = db.rawQuery(sql3, null);
        int cnt = c1.getCount();
        Log.i("TAG", "@@c" + cnt);
        String [] studlist=new String[cnt];

        if(cnt >0)
        {  int i=0;
            c1.moveToFirst();
            do {
            studlist[i]=c1.getString(c1.getColumnIndexOrThrow(Studid));
                i++;

            }while (c1.moveToNext());

        }

        Log.i("TAG", "cnt-attendance list1" + Arrays.toString(studlist));
        String sql2 = "SELECT distinct insert_local_add_student.Auto_Id, insert_local_add_student.id, insert_local_add_student.StudentName, insert_local_add_student.StudentProfile,insert_local_add_student.Phone1, insert_local_add_student.Phone2 FROM " + Student_Data_Table + " INNER JOIN " + student_subject
                + " where " + Student_Data_Table + ".ClassID ='" + classid + "' and " + Student_Data_Table + ".Branch ='" + branch + "' and " + Student_Data_Table + ".Standard='" + std
                + "' and " + Student_Data_Table + ".Batch ='" + batch + "' and " + Student_Data_Table + ".Auto_Id = " + student_subject + ".StudentID   and " + student_subject + ".SubjectID IN(" + subs + ")"+ " ORDER BY insert_local_add_student.id ASC ";

        Log.i("TAG", "sql-attendance list1" + sql2);

        Cursor c = db.rawQuery(sql2, null);
        int cnt2 = c.getCount();
        Log.i("TAG", "cnt-attendance list1" + cnt2);
        if (cnt2 > 0) {
            if (c.moveToFirst()) {
                do {
                   flags="N";
                    HashMap<String, String> menu = new HashMap<String, String>();


                    String sid = c.getString(c.getColumnIndexOrThrow("Auto_Id"));
                    if(studlist.length >0)
                    {
                        for(int j=0;j<studlist.length;j++)
                        {
                            Log.i("TAG", "if! "+studlist[j]+"->"+sid);
                            if(studlist[j].equals(sid))
                            {
                               flags="Y";
                                Log.i("TAG", "if! flag");
                            }
                        }
                    }

                    menu.put("id",sid);
                    menu.put("auto_id",c.getString(c.getColumnIndexOrThrow(ID)));
                    menu.put("sname", c.getString(c.getColumnIndexOrThrow(student_name)));
                    menu.put("studentprofile", c.getString(c.getColumnIndexOrThrow(studentprofile)));
                    menu.put("stud_phno", c.getString(c.getColumnIndexOrThrow("Phone1")));
                    menu.put("parent_phno", c.getString(c.getColumnIndexOrThrow("Phone2")));
                    menu.put("flags",flags);

                    menuItems.add(menu);
                } while (c.moveToNext());
            }
            c.close();
        }
        Log.i("TAG", "cnt-attendance list1" + menuItems);
        return menuItems;
    }




    public ArrayList<HashMap<String, String>> getstudentlist2(String classid, String branch,
                                                              String std, String batch,String subj) {
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        String sql3 = "SELECT distinct insert_local_add_student.Auto_Id,insert_local_add_student.id, insert_local_add_student.StudentName, insert_local_add_student.StudentProfile,insert_local_add_student.Phone1, insert_local_add_student.Phone2 FROM " + Student_Data_Table + " INNER JOIN " + student_subject
                + " where " + Student_Data_Table + ".ClassID ='" + classid + "' and " + Student_Data_Table + ".Branch ='" + branch + "' and " + Student_Data_Table + ".Standard='" + std
                + "' and " + Student_Data_Table + ".Batch ='" + batch + "' and " + Student_Data_Table + ".Auto_Id = " + student_subject + ".StudentID   and " + student_subject + ".SubjectID IN(" + subj + ")"+" ORDER BY insert_local_add_student.id ASC ";
        //Log.i("Database", "id*#" + sql3);

        Cursor c = db.rawQuery(sql3, null);
        c.moveToFirst();

        res = "";
        String name = "";
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("sname", c.getString(c.getColumnIndexOrThrow("StudentName")));
                map.put("StudentProfile", c.getString(c.getColumnIndexOrThrow("StudentProfile")));
                map.put("rollno", c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                map.put("auto_id", c.getString(c.getColumnIndexOrThrow("id")));
                map.put("stud_phno", c.getString(c.getColumnIndexOrThrow("Phone1")));
                map.put("parent_phno", c.getString(c.getColumnIndexOrThrow("Phone2")));

                map.put("flag", "0");
                menuItems.add(map);
                Log.d("student","   " + c.getString(c.getColumnIndexOrThrow("StudentName")));
                Log.i("Database", "id*##" + menuItems);

            } while (c.moveToNext());
        }
        c.close();
        return menuItems;
    }


//tejas

    public ArrayList<HashMap<String, String>> getBranchList(String ClassID) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> sub;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + BranchTable
                + " where ClassID='" + ClassID + "';";

        Log.i("tag", "sql" + sql);
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                sub = new HashMap<String, String>();
                sub.put("Bname", c.getString(c.getColumnIndexOrThrow("BranchName")));
                sub.put("Id", c.getString(c.getColumnIndexOrThrow("id")));
                list.add(sub);
            } while (c.moveToNext());

        }
        c.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getStand(String ClassID) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> sub;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + StandardTable
                + " where ClassID='" + ClassID + "';";

        Log.i("tag", "sql" + sql);
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                sub = new HashMap<String, String>();
                sub.put("Sname", c.getString(c.getColumnIndexOrThrow("StandardName")));
                sub.put("Id", c.getString(c.getColumnIndexOrThrow("id")));
                list.add(sub);
            } while (c.moveToNext());

        }
        c.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getbatch(String ClassID) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> sub;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + BatchTable
                + " where ClassID='" + ClassID + "';";

        Log.i("tag", "sql" + sql);
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                sub = new HashMap<String, String>();
                sub.put("Batname", c.getString(c.getColumnIndexOrThrow("BatchName")));
                sub.put("Id", c.getString(c.getColumnIndexOrThrow("id")));
                list.add(sub);
            } while (c.moveToNext());

        }
        c.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getSub(String ClassID) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> sub;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + SubjectTable
                + " where ClassID='" + ClassID + "';";

        Log.i("tag", "sql" + sql);
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                sub = new HashMap<String, String>();
                sub.put("Subname", c.getString(c.getColumnIndexOrThrow("SubjectName")));
                sub.put("Id", c.getString(c.getColumnIndexOrThrow("id")));
                list.add(sub);
            } while (c.moveToNext());

        }
        c.close();
        return list;
    }

    public int deleteBranch(String branchid) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
//        String sql2 = "SELECT * FROM " + ProfileTable
//                + " where id='" + branchid + "';";
        String sql = "SELECT * FROM " + BranchTable
                + " where id='" + branchid + "';";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            a = db.delete(BranchTable, ID + " =?", new String[]{branchid});
        }
        return a;
    }

    public int deleteStand(String standid) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + StandardTable
                + " where id='" + standid + "';";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            a = db.delete(StandardTable, ID + " =?", new String[]{standid});
        }
        return a;
    }

    public int deletebatch(String batchid) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + BatchTable
                + " where id='" + batchid + "';";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            a = db.delete(BatchTable, ID + " =?", new String[]{batchid});
        }
        return a;
    }

    public int deletesubj(String subjid) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + SubjectTable
                + " where id='" + subjid + "';";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            a = db.delete(SubjectTable, ID + " =?", new String[]{subjid});
        }
        return a;
    }

    public String update_branch(String branch_id, String bname) {


        SQLiteDatabase db = this.getWritableDatabase();
        int num;
        long ROWiD = 0;
        String sql = "SELECT * FROM " + BranchTable
                + " where id='" + branch_id + "';";

        Cursor c = db.rawQuery(sql, null);

        num = c.getCount();
        ContentValues cv = new ContentValues();
        Log.d("tag", "Communication_TBL_QuestionAll_Update" + num);
        if (num > 0) {

            cv.put(branchname, bname);


            Log.e("Socket", "ReturnFile sound" + cv);
            ROWiD = db.update(BranchTable, cv, ID + " =?", new String[]{branch_id});
            //ROWiD = db.insert(TBL_Notification_Question, null, cv);
            Log.d("tag", "Communication_TBL_QuestionAll_Update" + ROWiD);

        }

        c.close();
        ;
        return String.valueOf(ROWiD);

    }


    public String update_stand(String stand_id, String satnd_name) {


        SQLiteDatabase db = this.getWritableDatabase();
        int num;
        long ROWiD = 0;
        String sql = "SELECT * FROM " + StandardTable
                + " where id='" + stand_id + "';";

        Cursor c = db.rawQuery(sql, null);

        num = c.getCount();
        ContentValues cv = new ContentValues();
        Log.d("tag", "Communication_TBL_QuestionAll_Update" + num);
        if (num > 0) {

            cv.put(standardname, satnd_name);


            Log.e("Socket", "ReturnFile sound" + cv);
            ROWiD = db.update(StandardTable, cv, ID + " =?", new String[]{stand_id});
            //ROWiD = db.insert(TBL_Notification_Question, null, cv);
            Log.d("tag", "Communication_TBL_QuestionAll_Update" + ROWiD);

        }

        c.close();
        ;
        return String.valueOf(ROWiD);

    }

    public String update_batch(String batch_id, String batchname2) {


        SQLiteDatabase db = this.getWritableDatabase();
        int num;
        long ROWiD = 0;
        String sql = "SELECT * FROM " + BatchTable
                + " where id='" + batch_id + "';";

        Cursor c = db.rawQuery(sql, null);

        num = c.getCount();
        ContentValues cv = new ContentValues();
        Log.d("tag", "Communication_TBL_QuestionAll_Update" + num);
        if (num > 0) {

            cv.put(batchname, batchname2);


            Log.e("Socket", "ReturnFile sound" + cv);
            ROWiD = db.update(BatchTable, cv, ID + " =?", new String[]{batch_id});
            //ROWiD = db.insert(TBL_Notification_Question, null, cv);
            Log.d("tag", "Communication_TBL_QuestionAll_Update" + ROWiD);

        }

        c.close();
        ;
        return String.valueOf(ROWiD);

    }


    public String update_subj(String subj_id, String subj_name) {


        SQLiteDatabase db = this.getWritableDatabase();
        int num;
        long ROWiD = 0;
        String sql = "SELECT * FROM " + SubjectTable
                + " where id='" + subj_id + "';";

        Cursor c = db.rawQuery(sql, null);

        num = c.getCount();
        ContentValues cv = new ContentValues();
        Log.d("tag", "Communication_TBL_QuestionAll_Update" + num);
        if (num > 0) {

            cv.put(subjectname, subj_name);


            Log.e("Socket", "ReturnFile sound" + cv);
            ROWiD = db.update(SubjectTable, cv, ID + " =?", new String[]{subj_id});
            //ROWiD = db.insert(TBL_Notification_Question, null, cv);
            Log.d("tag", "Communication_TBL_QuestionAll_Update" + ROWiD);

        }

        c.close();
        ;
        return String.valueOf(ROWiD);

    }




    public boolean get_registration_details(String uname1, String pass1) {
        String cid = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + Registration + " where Username ='"
                + uname1 + "'  and Password ='" + pass1 + "';";
        Cursor c = db.rawQuery(sql, null);
        int count = c.getCount();

        if (count > 0) {
            c.moveToFirst();

            cid = c.getString(c.getColumnIndexOrThrow("ClassId"));

            MyApplication.set_session("classid", cid);
            return true;
        }

        return false;
    }

    public LinkedHashMap<String, String> getProfileDetails(String classid) {
        SQLiteDatabase db = this.getReadableDatabase();
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
        String sql = "SELECT * FROM " + Registration + " where ClassId ='"
                + classid + "';";
        Cursor c = db.rawQuery(sql, null);
        int num = c.getCount();

        if (num > 0) {
            c.moveToFirst();
            lhm.put("name", c.getString(c.getColumnIndexOrThrow(classname)));
            lhm.put("valid_from", c.getString(c.getColumnIndexOrThrow(valid_from)));
            lhm.put("valid_till", c.getString(c.getColumnIndexOrThrow(valid_till)));

        }
        c.close();
        return lhm;
    }

    public long update_profile(String classid, String pass, String email, String mob) {
        long ROWiD = 0;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            int num;
            String sql = "SELECT * FROM " + Registration + " where ClassId ='"
                    + classid + "';";
            Cursor c = db.rawQuery(sql, null);
            num = c.getCount();
            ContentValues cv = new ContentValues();
            if (num > 0) {

                cv.put(uname, pass);
                cv.put(emailId, email);
                cv.put(contact_no, mob);


                ROWiD = db.update(Registration, cv, class_id + " =?", new String[]{classid});
                Log.d("tag", "Communication_TBL_QuestionAll_Insert====" + ROWiD);


            }
        } finally {

        }
        return ROWiD;
    }






    public ArrayList<String> getFeeid( String classid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> menuItems = new ArrayList<String>();
        String str1 = "select distinct FeeID from " + insert_fee + " where "
                + class_id + "=?" + ";"; // + " and " + branch + "=?" + " and " + level+ "=?" + " and " + batch_new + "=?"
        Cursor c = db.rawQuery(str1, new String[]{classid});
        // Log.d("database","cursor value="+c);
        res = " ";
        if (c.moveToFirst()) {
            do {

                String map = (c.getString(c.getColumnIndexOrThrow("FeeID")));
                menuItems.add(map);
            } while (c.moveToNext());
        }
        c.close();
        return menuItems;
    }
    public Boolean checkStudAddStatus(String class_id1, String branch1, String standard1, String batch1, String feeid1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int num = -1;
        String str1 = "select * from " + insert_fee + " where "
                + class_id + "=?" +" and "+branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +"and "+feeid+"=?" +";";
        Cursor c = db.rawQuery(str1, new String[]{class_id1, branch1, standard1, batch1, feeid1});

        num = c.getCount();
        Log.d("tag", "addstudent_checkstudent" + str1 + "--" + num);

        if (num > 0) {

            return true;

        } else {

            return false;

        }

    }


    public String getDataFromTable(String tableName) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null,
                null);
        String data = "";
        Log.i("ColumnCount", dbCursor.getCount() + "");

        String[] columnNames = dbCursor.getColumnNames();
        int columnCount = columnNames.length;
        for (int i = 0; i < columnCount; i++) {
            Log.i("Column-" + i, columnNames[i]);
        }

        if (columnCount > 0) {
            Cursor cT = db.rawQuery("SELECT * FROM " + tableName, null);
            int recordCount = cT.getCount();
            Log.i("RecordCount", recordCount + "");

            if (recordCount > 0) {
                cT.moveToFirst();
                do {
                    for (int i = 0; i < columnCount; i++) {
                        try {
                            data += cT.getString(i).toString()
                                    .replace(",", "~+").toString()
                                    + ",";
                            Log.i("Column-" + columnNames[i],
                                    "Value-" + cT.getString(i));
                        } catch (Exception e) {
                            data += e.getLocalizedMessage() + "";
                        }
                    }
                    data = data.substring(0, data.length() - 1);
                    data += "\n";
                } while (cT.moveToNext());
            }
        }
        return data;
    }

    public String[] getTableColumnNames(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null,
                null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }





    public LinkedHashMap<String, String> getBranchWithActiveFlag(String clsid) {


        SQLiteDatabase db = this.getWritableDatabase();
        String bid, bname;

        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

        String str = "SELECT distinct BranchId FROM " + ProfileTable
                + " where ClassID='" + clsid + "' and flag_active= 'Y';";

        Log.i("FeeTracker", "getstd" + str);

        Cursor c = db.rawQuery(str, null);
        int count = c.getCount();
        Log.i("FeeTracker", "getcount:c=" + count);
        if (count > 0) {
            if (c.moveToFirst()) {
                do {

                    bid = c.getString(c.getColumnIndexOrThrow(branch_id));

                    bname = " SELECT * FROM " + BranchTable
                            + " WHERE id = '" + bid + "'";

                    Log.i("FeeTracker", "getstdname" + bname);

                    Cursor c1 = db.rawQuery(bname, null);
                    int count1 = c1.getCount();
                    Log.i("FeeTracker", "getcount:c1=" + count1);
                    if (c1.moveToFirst()) {
                        do {

                            lhm.put(c1.getString(c1.getColumnIndexOrThrow(branchname)), c1.getString(c1.getColumnIndexOrThrow("id")));

                        } while (c1.moveToNext());
                    }
                    c1.close();

                } while (c.moveToNext());

            }
            c.close();

            Log.i("Database", "Lhm got in get Std is " + lhm);

        }


        return lhm;
    }

    public ArrayList<HashMap<String, String>> GetStudentData() {

        String ltype = "", fd = "", td = "", reason = "", status1 = "", app_id = "";
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
        String str1 = "SELECT * FROM " + Student_Data_Table;
//        + " where " + standard + "='" + "1" + "'";
//        String str2 = "select * from " + Student_Data_Table + " where "
//                + class_id + "=?" +" and "+branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +";";
        MyApplication.log("", "sql 1= " + str1);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(str1, null);

        //GetStudentData1("kothrud","2");
        if (c.moveToFirst()) {
            do {


                HashMap<String, String> menu = new HashMap<String, String>();
                menu.put("id", c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                menu.put("auto_id", c.getString(c.getColumnIndexOrThrow("id")));
                menu.put("sname", c.getString(c.getColumnIndexOrThrow(student_name)));
                menu.put("studentprofile", c.getString(c.getColumnIndexOrThrow(studentprofile)));
                menu.put("Branch", c.getString(c.getColumnIndexOrThrow(branch)));
                menu.put("Batch", c.getString(c.getColumnIndexOrThrow(batch)));
                menu.put("phone1", c.getString(c.getColumnIndexOrThrow(ph1)));
                menu.put("studphone", c.getString(c.getColumnIndexOrThrow(ph2)));
                menu.put("std", c.getString(c.getColumnIndexOrThrow(standard1)));
                menu.put("date", c.getString(c.getColumnIndexOrThrow(doa)));
                menuItems.add(menu);

            } while (c.moveToNext());
        }
        c.close();
        return menuItems;
    }


    public ArrayList<HashMap<String, String>> GetStudentData1(String branch_ID,String batch_s,String standard_S,String subject_s) {
    MyApplication.log("","in GetStudentData1 = "+branch_ID+" and "+standard_S);
        String ltype = "", fd = "", td = "", reason = "", status1 = "", app_id = "";
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
//        String str1 = "SELECT * FROM " + Student_Data_Table + " where "
//                + branch + "='" + branch_ID + "'" + " and "
//                + standard1 + "='" + standard_S + "'";
        String str1 = "SELECT * from " + Student_Data_Table + " WHERE  Standard='" + standard_S
                                                            + "' and Branch='" + branch_ID + "' and batch = '" + batch_s + "'";

//        + " where " + standard + "='" + "1" + "'";
//        String str2 = "select * from " + Student_Data_Table + " where "
//                + class_id + "=?" +" and "+branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +";";
        MyApplication.log("", "sql 2= " + str1);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(str1, null);


        if (c.moveToFirst()) {
            do {


                HashMap<String, String> menu = new HashMap<String, String>();
                menu.put("id",
                        c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                menu.put("auto_id",
                        c.getString(c.getColumnIndexOrThrow("id")));
                menu.put("sname",
                        c.getString(c.getColumnIndexOrThrow(student_name)));
                menu.put("Branch",
                        c.getString(c.getColumnIndexOrThrow(branch)));
                menu.put("Batch",
                        c.getString(c.getColumnIndexOrThrow(batch)));
                menu.put("phone1",
                        c.getString(c.getColumnIndexOrThrow(ph1)));
                menu.put("studphone",
                        c.getString(c.getColumnIndexOrThrow(ph2)));
                menu.put("std", c.getString(c
                        .getColumnIndexOrThrow(standard1)));
                menu.put("date", c.getString(c
                        .getColumnIndexOrThrow(doa)));
                menuItems.add(menu);

            } while (c.moveToNext());
        }
        c.close();
        return menuItems;
    }


//    public ArrayList<HashMap<String, String>> GetStudentData(String branch1,String standrd1,String batch1,String subject1) {
//
//        String ltype = "", fd = "", td = "", reason = "", status1 = "", app_id = "";
//        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
//        SQLiteDatabase db = this.getReadableDatabase();
////        String str1 = "SELECT * FROM " + Student_Data_Table;
//
////        String str2 = "select * from " + Student_Data_Table + " where "
////                + class_id + "=?" +" and "+branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +";";
//
//        MyApplication.log(LOG_TAG,"spnr_branch in db->"+branch1+"-"+standrd1+"-"+batch1+"-"+subject1);
//
//
////        String str1 = "select * from " + Student_Data_Table + " where "
////                +branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +";";
////        Cursor c = db.rawQuery(str1, new String[]{ branch1, standrd1, batch1});
//
//        String str1 = "select * from " + Student_Data_Table + " where " + branch + "='"
//                + branch1 + "'";
//        Cursor c = db.rawQuery(str1, null);
//
//        MyApplication.log("","sql = "+str1);
//
//
//
//
//        if (c.moveToFirst()) {
//            do {
//
//
//                HashMap<String, String> menu = new HashMap<String, String>();
//                menu.put("id",
//                        c.getString(c.getColumnIndexOrThrow("Auto_Id")));
//                menu.put("auto_id",
//                        c.getString(c.getColumnIndexOrThrow("id")));
//                menu.put("sname",
//                        c.getString(c.getColumnIndexOrThrow(student_name)));
//                menu.put("Branch",
//                        c.getString(c.getColumnIndexOrThrow(branch)));
//                menu.put("Batch",
//                        c.getString(c.getColumnIndexOrThrow(batch)));
//                menu.put("phone1",
//                        c.getString(c.getColumnIndexOrThrow(ph1)));
//                menu.put("studphone",
//                        c.getString(c.getColumnIndexOrThrow(ph2)));
//                menu.put("std", c.getString(c
//                        .getColumnIndexOrThrow(standard1)));
//                menu.put("date", c.getString(c
//                        .getColumnIndexOrThrow(doa)));
//                menuItems.add(menu);
//MyApplication.log("","standard1 in db = "+ c.getString(c
//        .getColumnIndexOrThrow(standard1)));
//            } while (c.moveToNext());
//        }
//        c.close();
//        return menuItems;
//    }

    public ArrayList<HashMap<String, String>> GetStudentDataNew(String classid,String branchid,String standid,String batchid,String subjid) {


        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        String str1 = "SELECT distinct insert_local_add_student.Auto_Id," +
                " insert_local_add_student.id," +
                " insert_local_add_student.StudentName," +
                " insert_local_add_student.StudentProfile," +
                " insert_local_add_student.Branch," +
                " insert_local_add_student.Standard," +
                " insert_local_add_student.Batch," +
                " insert_local_add_student.Phone1," +
                " insert_local_add_student.Phone2," +
                " insert_local_add_student.DOA" +
                " FROM " + Student_Data_Table + " INNER JOIN " + student_subject  + " where insert_local_add_student.ClassID = '" + classid + "'" +
                " and insert_local_add_student.Branch = '" + branchid + "' and insert_local_add_student.Standard = '" + standid + "'" +
                " and insert_local_add_student.Batch = '" + batchid + "'" +
                " and student_subject.StudentIdWithAll = '" + subjid + "'" +
                " and insert_local_add_student.Auto_Id = student_subject.StudentID "+ " ORDER BY insert_local_add_student.id ASC ";

       // Log.i("tag","getstud"+str1);


        Cursor c = db.rawQuery(str1,null);
        int j=c.getCount();
        Log.i("tag", "getstud" + j);


        if (c.moveToFirst()) {
            do {

                HashMap<String, String> menu = new HashMap<String, String>();
                menu.put("id", c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                menu.put("auto_id", c.getString(c.getColumnIndexOrThrow("id")));
                menu.put("sname", c.getString(c.getColumnIndexOrThrow(student_name)));
                menu.put("studentprofile", c.getString(c.getColumnIndexOrThrow(studentprofile)));
                menu.put("Branch", c.getString(c.getColumnIndexOrThrow(branch)));
                menu.put("Batch", c.getString(c.getColumnIndexOrThrow(batch)));
                menu.put("phone1", c.getString(c.getColumnIndexOrThrow(ph1)));
                menu.put("studphone", c.getString(c.getColumnIndexOrThrow(ph2)));
                menu.put("std", c.getString(c.getColumnIndexOrThrow(standard1)));
                menu.put("date", c.getString(c.getColumnIndexOrThrow(doa)));

                menuItems.add(menu);

                Log.i("tag", "menu " + menu);

            } while (c.moveToNext());
        }
        c.close();

//        String stro = "SELECT insert_local_add_student.id," +
//                " insert_local_add_student.StudentName," +
//                " insert_local_add_student.Branch," +
//                " insert_local_add_student.Standard," +
//                " insert_local_add_student.Batch," +
//                " insert_local_add_student.Phone1," +
//                " insert_local_add_student.Phone2" +
//                " FROM " + Student_Data_Table + " INNER JOIN " + student_subject  + " where insert_local_add_student.ClassID = '" + classid + "'" +
//                " and insert_local_add_student.Branch = '" + branchid + "' and insert_local_add_student.Standard = '" + standid + "'" +
//                " and insert_local_add_student.Batch = '" + batchid + "'" +
//                " and student_subject.SubjectID = '" + subjid + "'" +
//                " and insert_local_add_student.id = student_subject.StudentID";

        String sqlr="select count(student_subject.SubjectID) as subjectcount from student_subject where SubjectID IN (" + subjid + ") group by StudentID";
        Log.i("tag", "new*" + sqlr);
        Cursor c2 = db.rawQuery(sqlr,null);
        int jj=c2.getCount();
        Log.i("tag", "new*count" + jj);
        if(jj> 0) {
            c2.moveToFirst();
            do {
                    String count4=c2.getString(c2.getColumnIndexOrThrow("subjectcount"));
                Log.i("tag", "new*value" + count4);
            }while (c2.moveToNext());
        }
        return menuItems;
    }


    /*public ArrayList<HashMap<String, String>> GetStudentDataById(String id) {


        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        String str1 = "select * from " + Student_Data_Table + " where "
                + Auto_Id + "=?" + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(str1, new String[]{id});


        if (c.moveToFirst()) {
            do {


                HashMap<String, String> menu = new HashMap<String, String>();
                menu.put("id", c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                menu.put("Rollno", c.getString(c.getColumnIndexOrThrow(Auto_Id)));
                menu.put("sname", c.getString(c.getColumnIndexOrThrow(student_name)));
                menu.put("sprofile", c.getBlob(c.getColumnIndex(studentprofile)));
                menu.put("Branch", c.getString(c.getColumnIndexOrThrow(branch)));
                menu.put("Batch", c.getString(c.getColumnIndexOrThrow(batch)));
                menu.put("phone1", c.getString(c.getColumnIndexOrThrow(ph1)));
                menu.put("studphone", c.getString(c.getColumnIndexOrThrow(ph2)));
                menu.put("std", c.getString(c.getColumnIndexOrThrow(standard1)));
                menu.put("date", c.getString(c.getColumnIndexOrThrow(doa)));

                menuItems.add(menu);

            } while (c.moveToNext());
        }
        c.close();
        return menuItems;
    }*/

    public ArrayList<HashMap<String, String>> GetFeeData(String classidd) {

        String ltype = "", fd = "", td = "", reason = "", status1 = "", app_id = "";
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
//        String str1 = "SELECT * FROM " + insert_fee +"where classid="+classidd;

        String  str1 = " SELECT * FROM " + insert_fee
                + " WHERE ClassID = '" + classidd + "'";
        //	+ " ORDER BY " + leaveid+ " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(str1, null);
        // Log.d("database","cursor value="+c);

        int cnt=c.getCount();
         Log.d("database", "cursor $$alue=" + cnt);
        if(cnt >0) {
    if (c.moveToFirst()) {
        do {
            // LinkedHashMap<String, String> map = new LinkedHashMap<String,
            // String>();
            // List<String> map1 = new ArrayList();

            HashMap<String, String> menu = new HashMap<String, String>();
            menu.put("id", c.getString(c.getColumnIndexOrThrow("id")));

            menu.put("fee_amount", c.getString(c.getColumnIndexOrThrow("Fee")));
            menu.put("classid", c.getString(c.getColumnIndexOrThrow("ClassID")));
            menu.put("feeid", c.getString(c.getColumnIndexOrThrow("FeeID")));
            menu.put("branch", c.getString(c.getColumnIndexOrThrow("Branch")));
            menu.put("standard", c.getString(c.getColumnIndexOrThrow("Standard")));
            menu.put("batch", c.getString(c.getColumnIndexOrThrow("Batch")));
            menu.put("subids", c.getString(c.getColumnIndexOrThrow("Subjects")));

            String subjects_id = c.getString(c.getColumnIndexOrThrow("Subjects"));
            String array[] = subjects_id.split(",");
            String subList = "";
            for (int j = 0; j < array.length; j++) {
                String subSQL = "SELECT SubjectName from " + SubjectTable + " WHERE id = '" + array[j] + "'";
                Cursor c123 = db.rawQuery(subSQL, null);
                if (c123.getCount() > 0) {
                    c123.moveToFirst();
                    if (subList.equals("")) {
                        subList = c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                    } else {
                        subList += "," + c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                    }
                }
            }

            menu.put("subjects", subList);

				/*menu.put("fd", c.getString(c.getColumnIndexOrThrow(from_date)));
				menu.put("td", c.getString(c.getColumnIndexOrThrow(to_date)));
				menu.put("status2",
						c.getString(c.getColumnIndexOrThrow(status)));
				menu.put("reason2",
						c.getString(c.getColumnIndexOrThrow(leave_reason)));*/
            menuItems.add(menu);

        } while (c.moveToNext());
    }
}
        c.close();
        return menuItems;
    }

    public String getSubName(String subids) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subname, id = null;

        String subjects_id = subids;
        String array[] = subjects_id.split(",");
        String subList = "";
        for (int j = 0; j < array.length; j++) {
            String subSQL = "SELECT SubjectName from " + SubjectTable + " WHERE id = '" + array[j] + "'";
            Cursor c123 = db.rawQuery(subSQL, null);
            if (c123.getCount() > 0) {
                c123.moveToFirst();
                if (subList.equals("")) {
                    subList = c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                } else {
                    subList += "," + c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                }
            }
        }


        return subList;

    }

    public String getSubName1(String subids) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subname, id = null;

        String subjects_id = subids;
        String array[] = subjects_id.split(",");
        String subList = "";
        for (int j = 0; j < array.length; j++) {
            String subSQL = "SELECT SubjectName from " + SubjectTable + " WHERE id = '" + array[j] + "'";
            Cursor c123 = db.rawQuery(subSQL, null);
            if (c123.getCount() > 0) {
                c123.moveToFirst();
                if (subList.equals("")) {
                    subList = c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                } else {
                    subList += "," + "\n" + c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                }
            }
        }


        return subList;

    }

    public void deleteStudData(String position) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + Student_Data_Table + " where Auto_Id='"
                + position + "'");
        Log.i("del_loc", "temp_del_data");
    }


    public void deleteFeeData(String position) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + insert_fee + " where id='"
                + position + "'");
        Log.i("del_loc", "temp_del_data");
    }


    public void DeleteStudentFeeTable(String position) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + StudentFeeTable + " where StudentID='"
                + position + "'");
        Log.i("del_loc", "temp_del_data");
    }



    public LinkedHashMap<String, String> getStandard(String clsid,
                                                     String branchid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sid, sname, stdname1;
        // ArrayList<String> menuItems = new ArrayList<String>();
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

//        String str = "SELECT distinct StandardId FROM " + ProfileTable
//                + " where ClassID='" + clsid + "' and BranchId= '" + branchid + "' and flag_active= 'Y';";

        String str = "SELECT distinct StandardId FROM " + ProfileTable
                + " where ClassID='" + clsid + "'  and flag_active= 'Y';";
        Log.i("FeeTracker", "getstd" + str);

        Cursor c = db.rawQuery(str, null);
        int count = c.getCount();
        Log.i("FeeTracker", "getcount:c=" + count);
        if (count > 0) {
            if (c.moveToFirst()) {
                do {

                    sid = c.getString(c.getColumnIndexOrThrow(standard_id));

                    sname = " SELECT * FROM " + StandardTable
                            + " WHERE id = '" + sid + "'";

                    Log.i("FeeTracker", "getstdname" + sname);

                    Cursor c1 = db.rawQuery(sname, null);
                    int count1 = c1.getCount();
                    Log.i("FeeTracker", "getcount:c1=" + count1);
                    if (c1.moveToFirst()) {
                        do {
                            // lhm.put("stdid",
                            // c1.getString(c1.getColumnIndexOrThrow(ID)));
                            lhm.put(c1.getString(c1
                                    .getColumnIndexOrThrow(standardname)), c1.getString(c1.getColumnIndexOrThrow("id")));

                            // stdname1 = c1.getString(c1
                            // .getColumnIndexOrThrow(standardname));
                            //
                            // menuItems.add(stdname1);
                        } while (c1.moveToNext());
                    }
                    c1.close();

                } while (c.moveToNext());

            }
            c.close();

            Log.i("Database", "Lhm got in get Std is " + lhm);

        }
// else {
//
//            sname = " SELECT * FROM " + StandardTable
//                    + " WHERE  ClassID= '" + clsid + "'";
//            Log.i("FeeTracker", "lhm-new" + sname);
//            Cursor c2 = db.rawQuery(sname, null);
//            int count1 = c2.getCount();
//            Log.i("FeeTracker", "lhm-new" + count1);
//
//            if (count1 > 0) {
//                c2.moveToFirst();
//                do {
//                    lhm.put(c2.getString(c2.getColumnIndexOrThrow(standardname)), c2.getString(c2.getColumnIndexOrThrow("id")));
//
//                } while (c2.moveToNext());
//            }
//            Log.i("Database", "21-11-lhm-new" + lhm);
//            c2.close();
//            return lhm;
//
//        }

        return lhm;
    }

    public LinkedHashMap<String, String> getBatch(String clsid,
                                                  String branchid, String stdid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String batid, batname, batname1;
        // ArrayList<String> menuItems = new ArrayList<String>();
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

        String str = "SELECT distinct BatchId FROM " + ProfileTable
                + " where ClassID='" + clsid + "' and flag_active= 'Y';";

        Log.i("FeeTracker", "getbatch" + str);

        Cursor c = db.rawQuery(str, null);
        int count = c.getCount();
        Log.i("FeeTracker", "getcount:c=" + count);
        if (count > 0) {
            if (c.moveToFirst()) {
                do {

                    batid = c.getString(c.getColumnIndexOrThrow(batch_id));

                    batname = " SELECT * FROM " + BatchTable
                            + " WHERE id = '" + batid + "'";

                    Log.i("FeeTracker", "getbatchname" + batname);

                    Cursor c1 = db.rawQuery(batname, null);
                    int count1 = c1.getCount();
                    Log.i("FeeTracker", "getcount:c1=" + count1);
                    if (c1.moveToFirst()) {
                        do {
                            // lhm.put("batchid",
                            // c1.getString(c1.getColumnIndexOrThrow(ID)));
                            lhm.put(c1.getString(c1.getColumnIndexOrThrow(batchname)), c1.getString(c1.getColumnIndexOrThrow("id")));

                            // batname1 =
                            // c1.getString(c1.getColumnIndexOrThrow(batchname));
                            //
                            // menuItems.add(batname1);
                        } while (c1.moveToNext());
                    }
                    c1.close();

                } while (c.moveToNext());

            }
            c.close();

        }
//        else {
//            batname = " SELECT * FROM " + BatchTable
//                    + " WHERE  ClassID= '" + clsid + "'";
//            Log.i("FeeTracker", "lhm-new" + batname);
//            Cursor c2 = db.rawQuery(batname, null);
//            int count1 = c2.getCount();
//            Log.i("FeeTracker", "lhm-new" + count1);
//
//            if (count1 > 0) {
//                c2.moveToFirst();
//                do {
//                    lhm.put(c2.getString(c2.getColumnIndexOrThrow(batchname)), c2.getString(c2.getColumnIndexOrThrow("id")));
//
//                } while (c2.moveToNext());
//            }
//            Log.i("Database", "21-11-lhm-new" + lhm);
//            c2.close();
//            return lhm;
//        }
        return lhm;
    }

    public LinkedHashMap<String, String> getSubject(String clsid,
                                                    String branchid, String stdid, String batchid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subid, subname, subname1;
        // ArrayList<String> menuItems = new ArrayList<String>();
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

                String str = "SELECT distinct SubjectId FROM " + ProfileTable
                + " where ClassID='" + clsid
                + "'and flag_active= 'Y';";

//        String str = "SELECT distinct SubjectId FROM " + ProfileTable
//                + " where ClassID='" + clsid + "' and BranchId= '" + branchid
//                + "' and StandardId= '" + stdid + "' and BatchId= '" + batchid
//                + "'and flag_active= 'Y';";
        Log.i("FeeTracker", "getsubject" + str);

        Cursor c = db.rawQuery(str, null);
        int count = c.getCount();
        Log.i("FeeTracker", "getcount:c=" + count);
        if (count > 0) {
            if (c.moveToFirst()) {
                do {

                    subid = c.getString(c.getColumnIndexOrThrow(subject_id));

                    subname = " SELECT * FROM " + SubjectTable
                            + " WHERE id = '" + subid + "'";

                    Log.i("FeeTracker", "getSubjectname" + subname);

                    Cursor c1 = db.rawQuery(subname, null);
                    int count1 = c1.getCount();
                    Log.i("FeeTracker", "getcount:c1=" + count1);
                    if (c1.moveToFirst()) {
                        do {
                            // lhm.put("subid",
                            // c1.getString(c1.getColumnIndexOrThrow(ID)));
                            lhm.put(c1.getString(c1.getColumnIndexOrThrow(subjectname)), c1.getString(c1.getColumnIndexOrThrow("id")));

                            // subname1 = c1.getString(c1
                            // .getColumnIndexOrThrow(subjectname));
                            //
                            // menuItems.add(subname1);
                        } while (c1.moveToNext());
                    }
                    c1.close();

                } while (c.moveToNext());

            }
            c.close();
//            return lhm;
        }
//        else {
//            subname = " SELECT * FROM " + SubjectTable
//                    + " WHERE  ClassID= '" + clsid + "'";
//            Log.i("FeeTracker", "lhm-new" + subname);
//            Cursor c2 = db.rawQuery(subname, null);
//            int count1 = c2.getCount();
//            Log.i("FeeTracker", "lhm-new" + count1);
//
//            if (count1 > 0) {
//                c2.moveToFirst();
//                do {
//                    lhm.put(c2.getString(c2.getColumnIndexOrThrow(subjectname)), c2.getString(c2.getColumnIndexOrThrow("id")));
//
//                } while (c2.moveToNext());
//            }
//            Log.i("Database", "21-11-lhm-new" + lhm);
//            c2.close();
//            return lhm;
//        }
        return lhm;
    }


    public LinkedHashMap<String, String> getSubjectWithInsertFee(String clsid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subid, subname, subname1;
        // ArrayList<String> menuItems = new ArrayList<String>();
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

        String str = "SELECT distinct Subjects FROM " + insert_fee
                + " where ClassID='" + clsid + "'";
        Log.i("FeeTracker", "lhm_fee" + str);
        String id = "";

        Cursor c = db.rawQuery(str, null);
        int count = c.getCount();
        Log.i("FeeTracker", "lhm_fee_c" + count);

        if (count > 0) {
            if (c.moveToFirst()) {
                do {

                    subid = c.getString(c.getColumnIndexOrThrow("Subjects"));

                    if (subid.length() == 1) {
                        Log.i("FeeTracker", "lhm_fee_c" + subid);
                        subname = " SELECT * FROM " + SubjectTable
                                + " WHERE id = '" + subid + "'";

                        Log.i("FeeTracker", "lhm_fee_s" + subname);


                        Cursor c1 = db.rawQuery(subname, null);
                        int count1 = c1.getCount();
                        Log.i("FeeTracker", "lhm_fee_s" + count1);
                        if (c1.moveToFirst()) {
                            do {


                                if (id.equals("")) {
                                    id = c1.getString(c1.getColumnIndexOrThrow(subjectname));
                                    Log.i("Database", "id" + id);
                                } else {
                                    id = id + "," + c1.getString(c1.getColumnIndexOrThrow(subjectname));
                                    Log.i("Database", "id" + id);
                                }
                                Log.i("Database", "lhm_fee_s_id" + id + "" + subid);
                                lhm.put(id, subid);


                            } while (c1.moveToNext());
                        }
                        c1.close();
                        id = "";

                    }else
                    {
                        Log.i("FeeTracker", "lhm_fee_c" + subid);
                        final String[] tokens = subid.split(",");
                        for(int i=0;i<tokens.length;i++) {
                            subname = " SELECT * FROM " + SubjectTable
                                    + " WHERE id = '" + tokens[i] + "'";

                            Log.i("FeeTracker", "lhm_fee_s" + subname);


                            Cursor c1 = db.rawQuery(subname, null);
                            int count1 = c1.getCount();
                            Log.i("FeeTracker", "lhm_fee_s" + count1);
                            if (c1.moveToFirst()) {
                                do {


                                    if (id.equals("")) {
                                        id = c1.getString(c1.getColumnIndexOrThrow(subjectname));
                                        Log.i("Database", "id" + id);
                                    } else {
                                        id = id + "," + c1.getString(c1.getColumnIndexOrThrow(subjectname));
                                        Log.i("Database", "id" + id);
                                    }
                                    Log.i("Database", "lhm_fee_s_id" + id + "" + subid);



                                } while (c1.moveToNext());
                            }
                            c1.close();
                        }
                        lhm.put(id, subid);
                        id="";
                    }

                } while (c.moveToNext());

                c.close();


            }

        }
        Log.i("Database", "lhm_fee_s_id" + id + "" + lhm);
        return lhm;
    }


    public Boolean checkFeeCombination(String classid,String branch1,String stand,String batch,String subjectid)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String str1 = "SELECT * FROM " + insert_fee+ " where ClassID='" + classid + "' and Branch='" + branch1 + "'and Standard='" + stand + "'and Batch='" + batch + "'and Subjects='" + subjectid + "'";
//        String str1 = "select * from " + insert_fee + " where "
//                + class_id + "=?" +" and "+branch+"=?" +" and "+standard+"=?" +"and "+batch+"=?" +"and Subjects=?" +";";
       Log.i("Database", "checkFeeCombination" + str1);
        Cursor c = db.rawQuery(str1, null);
        int count = c.getCount();
        Log.i("Database", "checkFeeCombination" + count);
        if(count > 0)
        {   Log.i("Database", "checkFeeCombination  true" );
            return  true;

        }else
        {
            Log.i("Database", "checkFeeCombination  false" );
            return  false;

        }

    }



    public String getSubjectId(String lhm) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subname, id = null;

        subname = " SELECT * FROM " + SubjectTable
                + " WHERE SubjectName = '" + lhm + "'";

        Log.i("FeeTracker", "getSubjectId" + subname);

        Cursor c1 = db.rawQuery(subname, null);
        int count1 = c1.getCount();
        Log.i("FeeTracker", "getcount:c1=" + count1);
        if (c1.moveToFirst()) {
            do {
                id = c1.getString(c1.getColumnIndexOrThrow("id"));

            } while (c1.moveToNext());
        }
        c1.close();

        return id;

    }


    public String getSubjectId1(LinkedHashMap lhm) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subname, id = "";

        for (Object o : lhm.keySet()) {
            if (lhm.get(o).toString().equals("1")) {

                subname = " SELECT * FROM " + SubjectTable
                        + " WHERE SubjectName = '" + o.toString() + "'";

                Log.i("FeeTracker", "getSubjectId" + subname);

                Cursor c1 = db.rawQuery(subname, null);
                int count1 = c1.getCount();
                Log.i("FeeTracker", "getcount:c1=" + count1);
                if (c1.moveToFirst()) {
                    do {
                        if (id.equals(""))
                            id = c1.getString(c1.getColumnIndexOrThrow("id"));
                        else
                            id = id + "," + c1.getString(c1.getColumnIndexOrThrow("id"));

                    } while (c1.moveToNext());
                }
                c1.close();
            }
        }
        Log.i("FeeTracker", "getcount:c1=" + id);
        return id;

    }

    public String getSId(String str) {

        SQLiteDatabase db = this.getWritableDatabase();
        String subname, id = "", sname, id1 = "";


        subname = " SELECT * FROM " + student_subject
                + " WHERE StudentID = '" + str + "'" + " order by SubjectID ";

        Log.i("FeeTracker", "getSubjectId" + subname);

        Cursor c = db.rawQuery(subname, null);
        int count = c.getCount();
        Log.i("FeeTracker", "getcount:c1=" + count);
        if (c.moveToFirst()) {
            do {


                if (id.equals("")) {
                    id = c.getString(c.getColumnIndexOrThrow("SubjectID"));
                    Log.i("Database", "id" + id);
                } else {
                    id = id + "," + c.getString(c.getColumnIndexOrThrow("SubjectID"));
                    Log.i("Database", "id" + id);
                }

            } while (c.moveToNext());

                /*sname = " SELECT * FROM " + SubjectTable
                        + " WHERE id = '" + id + "'";

                Log.i("FeeTracker", "sname" + sname);

                Cursor c1 = db.rawQuery(sname, null);
                int count1 = c1.getCount();
                Log.i("FeeTracker", "getcount:c1=" + count1);
                if (c1.moveToFirst()) {

                    if (id1.length() > 0) {
                        id1 = id1 + "," + c1.getString(c1.getColumnIndexOrThrow("SubjectName"));
                        Log.i("Database", "id1" + id1);
                    } else {
                        id1 = c1.getString(c1.getColumnIndexOrThrow("SubjectName"));
                        Log.i("Database", "id1" + id1);
                    }

                }
                c1.close();*/


        }
        c.close();

        return id;

    }







    public String[] getTableNames() {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'", null);
        int tableCount = c.getCount();
        Log.i("CursorCount", tableCount + "");

        String tableNames[] = new String[tableCount];
        int i = 0;
        if (tableCount > 0) {
            c.moveToFirst();
            do {
                Log.i("TableName", c.getString(0));
                tableNames[i++] = c.getString(0);
            } while (c.moveToNext());

        }
        return tableNames;
    }



    public String GetFee(String sid, String std,String batc,String branc,String class_id2) {
        String feeAmt = "0";
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT Fee from " + insert_fee + " WHERE Subjects='" + sid + "' and Standard='" + std + "' and Batch='" + batc + "' and Branch='" + branc + "' and ClassID='" + class_id2 + "'";
        Log.i("Database", "get fee--SQL is  " + SQL);
        Cursor c = db.rawQuery(SQL, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            feeAmt = c.getString(c.getColumnIndexOrThrow("Fee"));
            Log.i("Database", "get fee--feeAmt is  " + feeAmt);
        }
        c.close();
        return feeAmt;
    }


    public String GetFeesPaid(String sid) {
        String feeAmt = "0";
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT sum(FeesPaid) as paid FROM "
                + StudentFeeTable + " where StudentID='" + sid + "'";
        Log.i("Database", "GetFeespaid:SQL" + SQL);
        Cursor c = db.rawQuery(SQL, null);
        if (c.moveToFirst()) {

            feeAmt = c.getString(c.getColumnIndexOrThrow("paid"));
            Log.i("Database", "GetFeespaid" + feeAmt);

            if (TextUtils.isEmpty(feeAmt)) {
                feeAmt = "0";
            }
        }
        c.close();
        return feeAmt;
    }


    public void InsertStudentfeetable1(String classid2, String studid, String std, String FeesID, String feepaid, String balance,
                                       String date, String remark, String batch,String branch,String subj,String payment_status) {

        SQLiteDatabase db = this.getWritableDatabase();
        String Subbid = "", Subjectn = "", totfee = "", oldfee = "", sname = "", abc, feeId = "";
        int addbal, fees, bal, balance1;
        fees = Integer.parseInt(feepaid);
        Log.d("in", "fees--" + fees);
        Log.d("Database", "studid--" + studid);
        Log.d("Database", "feepaid--" + feepaid);
        Log.d("Database", "balance--" + balance);
        String gettotalsub = "select * from " + student_subject
                + " where StudentID='" + studid + "' order by SubjectID asc";
        Cursor csub = db.rawQuery(gettotalsub, null);
        if (csub.moveToFirst()) {
            do {
                if (Subbid.equals(""))
                    Subbid = csub.getString(csub.getColumnIndexOrThrow("SubjectID"));
                else
                    Subbid += "," + csub.getString(csub.getColumnIndexOrThrow("SubjectID"));


            } while (csub.moveToNext());
        }
        csub.close();

        abc = " SELECT * FROM " + insert_fee
                + " WHERE Subjects = '" + Subbid + "' and Standard = '" + std + "'";

        Log.i("Database", "subjects" + abc);

        Cursor c2 = db.rawQuery(abc, null);
        int count2 = c2.getCount();

        Log.i("Database", "getcount:c1=" + count2);
        if (c2.moveToFirst()) {

            totfee = c2.getString(c2.getColumnIndexOrThrow("Fee"));
            feeId = c2.getString(c2.getColumnIndexOrThrow("FeeID"));
            Log.i("Database", "totfee is -> " + totfee+" and feeId is -> "+feeId);
        }
        c2.close();

        String feespaid = "SELECT sum(FeesPaid)  as Feespaid FROM "
                + StudentFeeTable + " where  Standard='" + std
                + "'  and StudentID='" + studid + "' and FeesID ='"
                + feeId + "'";
        Log.i("Database", "feespaid" + feespaid);

        Cursor cfeespaid = db.rawQuery(feespaid, null);
        Log.i("Database", "Count" + cfeespaid.getCount());
        if (cfeespaid.moveToFirst()) {

            oldfee = cfeespaid.getString(cfeespaid.getColumnIndexOrThrow("Feespaid"));
            Log.i("Database", "Old fee value is " + oldfee);
                /*if (oldfee.equals("") || oldfee.equals("null")) {
                    oldfee = "0";
                }*/
            if (TextUtils.isEmpty(oldfee)|| oldfee.equals("null")) {
                Log.i("Database", "oldfee if");
                oldfee = "0";
            }

        }
        Log.d("database", "" + Integer.parseInt(totfee) + " total and  old "
                + Integer.parseInt(oldfee));
        addbal = Integer.parseInt(totfee)
                - Integer.parseInt(oldfee);
  /*      Log.d("ddddddddddddddddddddd", "" + totfee + " total and  old "
                + oldfee);*/
        if (addbal < fees) {

            fees = fees - addbal;
            bal = Integer.parseInt(oldfee) + addbal;
            balance1 = Integer.parseInt(totfee) - bal;

            if (addbal > 0) {
                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("ClassID", classid2);
                initialValues1.put("StudentID", studid);
                initialValues1.put("Standard", std);
                initialValues1.put("FeesID", feeId);
                initialValues1.put("Remark", remark);
                initialValues1.put("FeesPaid", addbal);
                initialValues1.put("Balance", balance1);
                initialValues1.put("Date", date);
                initialValues1.put("Batch", batch);
                initialValues1.put("Subjects", subj);
                initialValues1.put("Branch", branch);
                initialValues1.put("PaymentStatus", payment_status);
                db.insert(StudentFeeTable, null, initialValues1);
                Log.d("StudentFeeTable in if", "" + initialValues1);
            }

        } else {
            bal = Integer.parseInt(oldfee) + fees;
            balance1 = Integer.parseInt(totfee) - bal;
            Log.i("Database", "oldfee" + oldfee);
            Log.i("Database", "balance" + bal);
            Log.i("Database", "balance1" + balance1);
            if (fees >= 0) {

                if (balance1 == 0) {
                    ContentValues initialValues1 = new ContentValues();
                    initialValues1.put("ClassID", classid2);
                    initialValues1.put("StudentID", studid);
                    initialValues1.put("Standard", std);
                    //                initialValues1.put("SubjectID", Subbid);
                    initialValues1.put("Remark", remark);
                    initialValues1.put("FeesID", feeId);
                    initialValues1.put("FeesPaid", fees);
                    initialValues1.put("Balance", balance1);
                    initialValues1.put("Date", date);
                    initialValues1.put("Batch", batch);
                    initialValues1.put("Subjects", subj);
                    initialValues1.put("Branch", branch);
                    initialValues1.put("PaymentStatus", payment_status);
                    db.insert(StudentFeeTable, null, initialValues1);
                    Log.d("Table in else", "" + initialValues1);
                    //break;
                } else {

                    ContentValues initialValues1 = new ContentValues();
                    initialValues1.put("ClassID", classid2);
                    initialValues1.put("StudentID", studid);
                    initialValues1.put("Standard", std);
                    initialValues1.put("Remark", remark);
                    initialValues1.put("FeesID", feeId);
                    initialValues1.put("FeesPaid", fees);
                    initialValues1.put("Balance", balance1);
                    initialValues1.put("Date", date);
                    initialValues1.put("Batch", batch);
                    initialValues1.put("Subjects", subj);
                    initialValues1.put("Branch", branch);
                    initialValues1.put("PaymentStatus", payment_status);
                    db.insert(StudentFeeTable, null, initialValues1);
                    Log.d("Table in else", "" + initialValues1);
                }
            }
        }
        cfeespaid.close();
    }


    @SuppressLint("NewApi")
    public int insertEditedFee(String Date, String ID, String fees,
                               String remark, String StudentID, String selectedstd,
                               String ClassID2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String subjectids = "", Subjectn = "", abc = "", totfee = "", feeId = "", abc1 = "";
        String sub_array[];
        int i = 0, response = 0, temp_bal = 0, balance = 0, paidFees = 0, feespaying = 0, check = 0, sub_count = 0, feesPaid = 0, extra = 0, bal = 0, cur_bal = 0, extraFee = 0;
        String sql1 = "select * from " + student_subject
                + " where StudentID='" + StudentID + "'";
        Cursor c1 = db.rawQuery(sql1, null);
        if (c1.moveToFirst()) {
            do {


                if (subjectids.equals(""))
                    subjectids = c1.getString(c1.getColumnIndexOrThrow("SubjectID"));
                else
                    subjectids += "," + c1.getString(c1.getColumnIndexOrThrow("SubjectID"));

                /*sname = " SELECT * FROM " + SubjectTable
                        + " WHERE id = '" + subjectids + "'";

                Log.i("FeeTracker", "sname" + sname);

                Cursor c2 = db.rawQuery(sname, null);
                int count1 = c2.getCount();
                Log.i("FeeTracker", "getcount:c2=" + count1);
                if (c2.moveToFirst()) {

                    if (Subjectn.length() > 0) {
                        Subjectn = Subjectn + "," + c2.getString(c2.getColumnIndexOrThrow("SubjectName"));
                        Log.i("Database", "Subjectn" + Subjectn);
                    } else {
                        Subjectn = c2.getString(c2.getColumnIndexOrThrow("SubjectName"));
                        Log.i("Database", "Subjectn" + Subjectn);
                    }
                }
                c2.close();
*/
            } while (c1.moveToNext());
        }


        // (id INTEGER PRIMARY KEY AUTOINCREMENT,ClassID TEXT, FeeID TEXT,Fee TEXT,Subjects TEXT,Branch TEXT," +
        //"Standard TEXT,Batch TEXT);");

        abc = " SELECT * FROM " + insert_fee
                + " WHERE Subjects = '" + subjectids + "' AND Standard = '" + selectedstd + "' AND ClassID = '" + ClassID2 + "'";

        Log.i("FeeTracker", "subjects" + abc);

        Cursor c3 = db.rawQuery(abc, null);
        int count3 = c3.getCount();
        Log.i("FeeTracker", "getcount:c3=" + count3);
        if (c3.moveToFirst()) {

            totfee = c3.getString(c3.getColumnIndexOrThrow("Fee"));
            feeId = c3.getString(c3.getColumnIndexOrThrow("FeeID"));
            Log.i("FeeTracker", "totfee" + totfee);
        }
        c3.close();

        String Told_Fee = "";

        String sql6 = "Select * From StudentFeeTable WHERE Standard='"
                + selectedstd + "' AND StudentID ='" + StudentID
                + "' and FeesID ='" + feeId
                + "'";
        Cursor c6 = db.rawQuery(sql6, null);

        if (c6.getCount() > 0) {

            String sql4 = "Select SUM( FeesPaid ) as oldfee From StudentFeeTable WHERE Standard='"
                    + selectedstd
                    + "' AND StudentID ='"
                    + StudentID
                    + "' and  FeesID ='"
                    + feeId + "'";
            Cursor cd = db.rawQuery(sql4, null);
            Log.i("sql4", sql4);
            Log.i("count", cd.getCount() + "");

            if (cd.getCount() > 0) {
                cd.moveToFirst();
                Told_Fee = cd.getString(cd.getColumnIndexOrThrow("oldfee"));
            } else {
                Told_Fee = "0";
            }
            cd.close();
        } else {
            Told_Fee = "0";
        }
        Log.i("Tolde_Fee", Told_Fee);

        abc1 = " SELECT Fee FROM " + insert_fee
                + " WHERE Subjects = '" + subjectids + "' and Standard = '" + selectedstd + "'";

        SQLiteStatement statement5 = db.compileStatement(abc1);
        String TotalFee = statement5.simpleQueryForString();

        balance = Integer.parseInt(TotalFee) - Integer.parseInt(Told_Fee);
        //      int bal1 = Integer.parseInt(Told_Fee) - Integer.parseInt(fees);
        String oldRowFee = "";
        int oldRowFeeInt = 0;
        String SQLoldRowFee = "Select FeesPaid From StudentFeeTable WHERE ID ='"
                + ID + "'";
        Cursor cOldRowFee = db.rawQuery(SQLoldRowFee, null);
        if (cOldRowFee.getCount() > 0) {
            cOldRowFee.moveToFirst();
            oldRowFee = cOldRowFee.getString(cOldRowFee.getColumnIndexOrThrow("FeesPaid"));
        }
        if (!oldRowFee.equals("")) {
            oldRowFeeInt = Integer.parseInt(oldRowFee);
        }

        if (Integer.parseInt(fees) <= (balance + oldRowFeeInt)) {

        } else {
            response = 2;
            return response;
        }

        if (oldRowFeeInt >= Integer.parseInt(fees)) {
            balance += (oldRowFeeInt - Integer.parseInt(fees));
        } else {
            balance -= (Integer.parseInt(fees) - oldRowFeeInt);
        }

        String str123 = "select * from StudentFeeTable where id ='" + ID + "'";
        //     StudentID='" + StudentID+ "' and Standard='" + selectedstd +"' and FeesID='" + feeId + "'";
        Cursor c = db.rawQuery(str123, null);
        int num = c.getCount();
        if (num > 0) {

            if (balance == 0) {
                db.execSQL("update " + StudentFeeTable + " set " + "FeesPaid" + "='" + fees + "',"
                        + "Balance" + "='" + balance + "'," + "Date" + "='"
                        + Date + "'," + "Remark" + "='" + remark + "' where id ='" + ID + "';");
                   /* StudentID='"
                    + StudentID + "' and Standard='" + selectedstd
                    + "'  and FeesID='" + feeId
                    + "';");// and "+feepaid+"='"+string1+"' and*/

            } else {

                db.execSQL("update " + StudentFeeTable + " set " + "FeesPaid" + "='" + fees + "',"
                        + "Balance" + "='" + balance + "'," + "Date" + "='"
                        + Date + "'," + "Remark" + "='" + remark + "' where id ='" + ID + "';");
            }
        } else {

            if (balance == 0) {
                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("StudentID", StudentID);
                initialValues1.put("Standard", selectedstd);
                initialValues1.put("Remark", remark);
                initialValues1.put("FeesID", feeId);
                initialValues1.put("FeesPaid", fees);
                initialValues1.put("Balance", balance);
                initialValues1.put("Date", Date);
                initialValues1.put("ClassID", ClassID2);
                db.insert(StudentFeeTable, null, initialValues1);
                Log.i("I_FeesID", feeId);
            } else {
                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("StudentID", StudentID);
                initialValues1.put("Standard", selectedstd);
                initialValues1.put("Remark", remark);
                initialValues1.put("FeesID", feeId);
                initialValues1.put("FeesPaid", fees);
                initialValues1.put("Balance", balance);
                initialValues1.put("Date", Date);
                initialValues1.put("ClassID", ClassID2);
                //    initialValues1.put("Batch", );
                db.insert(StudentFeeTable, null, initialValues1);
                Log.i("I_FeesID", feeId);
            }
        }

        String sql2 = "Select SUM( FeesPaid ) as oldfee From StudentFeeTable WHERE StudentID ='"
                + StudentID
                + "' and Standard='"
                + selectedstd
                + "' and FeesID ='"
                + feeId + "'";
        SQLiteStatement statement = db.compileStatement(sql2);
        String oldFee = statement.simpleQueryForString();


        if (Integer.parseInt(fees) <= 0) {
            response = 1;
            return response;
        }


        Log.i("SubIDs", subjectids);
        Log.i("oldFee", oldFee);
        Log.i("TotalFee", TotalFee);

        String sql3 = "Select Fee From insert_fee where ClassID='"
                + ClassID2 + "' and Standard='" + selectedstd
                + "' and Subjects='" + subjectids + "'";
        SQLiteStatement statement1 = db.compileStatement(sql3);
        String Tsub_Fee = statement1.simpleQueryForString();
        Log.i("TSubFee", Tsub_Fee);


        c1.close();
        c6.close();

        statement.close();
        statement1.close();
        statement5.close();

        String str = "select * from StudentFeeTable where id ='" + ID + "'";
        //     StudentID='" + StudentID+ "' and Standard='" + selectedstd +"' and FeesID='" + feeId + "'";
        Cursor c123 = db.rawQuery(str, null);
        int num123 = c123.getCount();
        if (num123 > 0) {

            if (balance == 0) {
                db.execSQL("update " + StudentFeeTable + " set " + "Balance" + "='" + balance + "' where id ='" + ID + "';");
                   /* StudentID='"
                    + StudentID + "' and Standard='" + selectedstd
                    + "'  and FeesID='" + feeId
                    + "';");// and "+feepaid+"='"+string1+"' and*/
            } else {
                db.execSQL("update " + StudentFeeTable + " set " + "Balance" + "='" + balance + "' where id ='" + ID + "';");

            }
        } else {

            if (balance == 0) {
                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("StudentID", StudentID);
                initialValues1.put("Standard", selectedstd);
                initialValues1.put("Remark", remark);
                initialValues1.put("FeesID", feeId);
                initialValues1.put("FeesPaid", fees);
                initialValues1.put("Balance", balance);
                initialValues1.put("Date", Date);
                initialValues1.put("ClassID", ClassID2);
                //    initialValues1.put("Batch", );
                db.insert(StudentFeeTable, null, initialValues1);
                Log.i("I_FeesID", feeId);
            } else {
                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("StudentID", StudentID);
                initialValues1.put("Standard", selectedstd);
                initialValues1.put("Remark", remark);
                initialValues1.put("FeesID", feeId);
                initialValues1.put("FeesPaid", fees);
                initialValues1.put("Balance", balance);
                initialValues1.put("Date", Date);
                initialValues1.put("ClassID", ClassID2);
                //    initialValues1.put("Batch", );
                db.insert(StudentFeeTable, null, initialValues1);
                Log.i("I_FeesID", feeId);
            }
        }
        c.close();

        return response;
    }



    public long updateRestore(String ClassID1,String StudentID1,String Standard1,String FeesID1,String FeesPaid1,String Remark1,
                       String Balance1,String Batch1,String Date1,String Subjects1,String Branch1,String PaymentStatus1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
      String  sql = "SELECT * FROM "
                + StudentFeeTable + " where  ClassID='" + ClassID1 + "' and StudentID ='" + StudentID1 + "' and FeesID ='" + FeesID1 +
                "'and FeesPaid ='" + FeesPaid1 + "'and Balance ='" + Balance1 + "'"
              + "and Date ='" + Date1 + "' and Remark ='" + Remark1 + "'and Batch ='" + Batch1 + "'and Subjects ='" + Subjects1 + "'" +
                "and Branch ='" + Branch1 + "' and PaymentStatus ='" + PaymentStatus1 + "'and Standard ='" + Standard1 + "'";
      Log.d("tag","updaterestore"+sql);
        Cursor c = db.rawQuery(sql, null);
        int num=c.getCount();
        Log.d("tag","updaterestore_num"+num);
        long rowId=0;

        ContentValues initialValues1 = new ContentValues();
        initialValues1.put("ClassID", ClassID1);
        initialValues1.put("StudentID", StudentID1);
        initialValues1.put("FeesID", FeesID1);
        initialValues1.put("FeesPaid", FeesPaid1);
        initialValues1.put("Balance", Balance1);
        initialValues1.put("Date", Date1);

        initialValues1.put("Remark", Remark1);
        initialValues1.put("Batch", Batch1);
        initialValues1.put("Subjects", Subjects1);
        initialValues1.put("Branch", Branch1);
        initialValues1.put("Standard", Standard1);
        initialValues1.put("PaymentStatus", PaymentStatus1);

        if ( num == 0) {

            //    initialValues1.put("Batch", );
            rowId = db.insert(StudentFeeTable, null, initialValues1);
            Log.d("tag","inserted ..."+num);

        }else
        {
            rowId = db.update(StudentFeeTable, initialValues1, "ClassID=? and StudentID" + "=? and " + "FeesID" + "=? and "
                    + "FeesPaid" + "=? and " + "Balance" + "=? and " + "Date" + "=? and " + "Remark" + "=? and  " + "Batch" + "=? and " + "Subjects" + "=? and " + "Branch" + "=? and " + "Standard" + "=? and " + "PaymentStatus" + "=? "
                    , new String[]{ ClassID1, StudentID1, FeesID1, FeesPaid1,
                     Balance1,  Date1,Remark1,Batch1, Subjects1, Branch1,Standard1, PaymentStatus1});
            Log.d("tag","updated ..."+num);

        }
        return rowId;
    }





    public ArrayList<LinkedHashMap<String, String>> DisplayBalanceFeeNew(String classid) {

        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> lhm = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String Feeid = "", Fee = "", std = "", sql = "", feepaid = "", totfees = "", sub = "",branc="",batc="";
        int addbal, fees, bal, balance1, totalcount, total;
        String str = "select Standard,FeeID,Fee,Subjects,Branch,Batch from " + insert_fee
                + " where ClassID='" + classid + "'";
        Cursor c = db.rawQuery(str, null);
        Log.i("Database", "Fee Summary Count :  " + c.getCount());
        if (c.getCount() > 0) {

            c.moveToFirst();
            int do_iteration = 0;

            do {

                do_iteration++;
                Log.i("Database", "Do iteration is " + do_iteration);


                Feeid = c.getString(c.getColumnIndexOrThrow("FeeID"));
                Fee = c.getString(c.getColumnIndexOrThrow("Fee"));
                std = c.getString(c.getColumnIndexOrThrow("Standard"));
                sub = c.getString(c.getColumnIndexOrThrow("Subjects"));
                branc = c.getString(c.getColumnIndexOrThrow("Branch"));
                batc = c.getString(c.getColumnIndexOrThrow("Batch"));

                Log.i("Database", "Fee Summary Feeid :  " + Feeid);
                Log.i("Database", "Fee Summary Fee :  " + Fee);
                Log.i("Database", "Fee Summary std :  " + std);
                Log.i("Database", "Fee Summary sub :  " + sub);
                Log.i("Database", "Fee Summary branc :  " + branc);
                Log.i("Database", "Fee Summary batc :  " + batc);

                String sql1 = "SELECT Count(insert_local_add_student.Auto_Id) as StudentId FROM "
                        + Student_Data_Table + " INNER JOIN " + student_subject  + " where insert_local_add_student.ClassID = '" + classid + "' and insert_local_add_student.Standard='" + std + "' and insert_local_add_student.Branch ='" + branc + "' and insert_local_add_student.Batch ='" + batc + "'"
                        + " and student_subject.StudentIdWithAll = '" + sub + "'" +
                        " and insert_local_add_student.Auto_Id = student_subject.StudentID "+ " ORDER BY insert_local_add_student.id ASC ";
                String studCount = "";


               /* String sql1 = "SELECT Count(Auto_Id) as StudentId FROM "
                        + Student_Data_Table + " where ClassID = '" + classid + "' and Standard='" + std + "' and Branch ='" + branc + "' and Batch ='" + batc + "'";
                String studCount = "";*/

                Cursor c22 = db.rawQuery(sql1,null);
                Log.i("Database", "Fee Summary cursor2 count  :  " + c22.getCount());
                if(c22.getCount() > 0)
                {
                    c22.moveToFirst();
                    studCount = c22.getString(c22.getColumnIndexOrThrow("StudentId"));
                    Log.i("Database","Fee Summary studCount is:"+studCount);
                }

                sql = "SELECT sum(FeesPaid)  as Feespaid FROM "
                        + StudentFeeTable + " where  Standard='" + std + "' and Subjects ='" + sub + "' and Branch ='" + branc + "'and Batch ='" + batc + "'and FeesID ='" + Feeid + "'";


                Cursor c1 = db.rawQuery(sql, null);
                Log.i("Database", "c1 is " + c1);

                if (c1.moveToFirst()) {

                    feepaid = c1.getString(c1.getColumnIndexOrThrow("Feespaid"));
                    Log.i("Database", "feespaid is " + feepaid);

                    if (TextUtils.isEmpty(feepaid)) {
                        feepaid = "0";
                    }

                }

                c1.close();

                if(feepaid!="0") {

                    lhm = new LinkedHashMap<String, String>();
                    lhm.put("std", std);
                    lhm.put("sub", sub);
                    lhm.put("branch", branc);
                    lhm.put("batch", batc);
                    lhm.put("feepaid", feepaid);
                    lhm.put("studCount", studCount);

                    String str2 = "SELECT count( Distinct StudentID) as count FROM "
                            + StudentFeeTable + " where  Standard='" + std
                            + "' and FeesID ='" + Feeid + "' and Subjects ='" + sub + "' and Branch ='" + branc + "'and Batch ='" + batc + "' group by Subjects";
                    Log.i("Database", "Str2 is  " + str2);

                    Cursor c2 = db.rawQuery(str2, null);
                    Log.i("Database", "count of feesid for " + Feeid + " is " + c2.getCount());
                    Log.i("Database", "c2 is " + DatabaseUtils.dumpCursorToString(c2));

                    if (c2.getCount() > 0) {
                        c2.moveToFirst();
                        total = 0;
                        do {
                            totalcount = Integer.parseInt(c2.getString(c2.getColumnIndexOrThrow("count")));
                            Log.i("Database", "Total count is  " + totalcount);
                            fees = Integer.parseInt(Fee);
                            total += (totalcount * fees);
                        } while (c2.moveToNext());


                        Log.i("Database", "Fees is DB " + fees + "");
                        Log.i("Database", "Total is  " + total + "");

                        bal = total - Integer.parseInt(feepaid);

                        lhm.put("total", total + "");
                        lhm.put("bal", bal + "");
                    }

                    c2.close();
                    Log.i("Database", "lhm for display balance " + lhm);
                    arrayList.add(lhm);

                 /*   } while (c_total.moveToNext());

                }*/
                }
            } while (c.moveToNext());
        }
        c.close();

        Log.i("Database", "arrayList for display balance " + arrayList);
        return arrayList;
    }




    public ArrayList<LinkedHashMap<String, String>> DisplayBalance(String classid) {

        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> lhm = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String Feeid = "", Fee = "", std = "", sql = "", feepaid = "", totfees = "", sub = "",branc="",batc="";
        int addbal, fees, bal, balance1, totalcount, total;
        String str = "select Distinct(Standard),FeeID,Fee,Subjects,Branch,Batch from " + insert_fee
                + " where ClassID='" + classid + "'";
        Cursor c = db.rawQuery(str, null);
        if (c.getCount() > 0) {

            c.moveToFirst();
            int do_iteration = 0;

            do {
                do_iteration++;
                Log.i("Database", "Do iteration is " + do_iteration);

                lhm = new LinkedHashMap<String, String>();

                Feeid = c.getString(c.getColumnIndexOrThrow("FeeID"));
                Fee = c.getString(c.getColumnIndexOrThrow("Fee"));
                std = c.getString(c.getColumnIndexOrThrow("Standard"));
                sub = c.getString(c.getColumnIndexOrThrow("Subjects"));
                branc = c.getString(c.getColumnIndexOrThrow("Branch"));
                batc = c.getString(c.getColumnIndexOrThrow("Batch"));

                lhm.put("std", std);
                lhm.put("sub", sub);
                lhm.put("branch", branc);
                lhm.put("batch", batc);


                sql = "SELECT sum(FeesPaid)  as Feespaid FROM "
                        + StudentFeeTable + " where  Standard='" + std
                        + "' and FeesID ='"
                        + Feeid + "'";


                Cursor c1 = db.rawQuery(sql, null);
                Log.i("Database", "c1 is " + c1);

                if (c1.moveToFirst()) {

                    feepaid = c1.getString(c1.getColumnIndexOrThrow("Feespaid"));
                    Log.i("Database", "feespaid is " + feepaid);

                    if (TextUtils.isEmpty(feepaid)) {
                        feepaid = "0";
                    }

                    lhm.put("feepaid", feepaid);
                }

                c1.close();

                /*String str23 = "SELECT Distinct StudentID FROM "
                        + StudentFeeTable;
                Cursor c_total = db.rawQuery(str23, null);
                if(c_total.getCount()>0) {
                    c_total.moveToFirst();
                    do {

                        String studid = c_total.getString(c_total.getColumnIndexOrThrow("StudentID"));*/

                String str2 = "SELECT count( Distinct StudentID) as count FROM "
                        + StudentFeeTable + " where  Standard='" + std
                        + "' and FeesID ='"
                        + Feeid + "' group by Standard";
                Log.i("Database", "Str2 is  " + str2);

                Cursor c2 = db.rawQuery(str2, null);
                Log.i("Database", "count of feesid for " + Feeid + " is " + c2.getCount());
                Log.i("Database", "c2 is " + DatabaseUtils.dumpCursorToString(c2));

                if (c2.getCount() > 0) {
                    c2.moveToFirst();
                    total = 0;
                    do {
                        totalcount = Integer.parseInt(c2.getString(c2.getColumnIndexOrThrow("count")));
                        Log.i("Database", "Total count is  " + totalcount);
                        fees = Integer.parseInt(Fee);
                        total += (totalcount * fees);
                    } while (c2.moveToNext());


                    Log.i("Database", "Fees is  " + fees + "");
                    Log.i("Database", "Total is  " + total + "");

                    bal = total - Integer.parseInt(feepaid);

                    lhm.put("total", total + "");
                    lhm.put("bal", bal + "");
                }

                c2.close();
                Log.i("Database", "lhm for display balance " + lhm);
                arrayList.add(lhm);

                 /*   } while (c_total.moveToNext());
                }*/
            } while (c.moveToNext());
        }
        c.close();

        Log.i("Database", "arrayList for display balance " + arrayList);
        return arrayList;
    }


//
//    public void insertStudentAttendance(String classid, String branch, String std, String batch, String sub, ArrayList<String> ids, ArrayList<String> names, String date) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        int id = 0;
//        String name = "";
//
//        for (int i = 0; i < ids.size(); i++) {
//            id = Integer.parseInt(ids.get(i));
//            name = names.get(i);
//            Log.i("Database", "sid" + id);
//
//            String sql = "SELECT " + SubjectTable + ".SubjectName FROM " + SubjectTable + " INNER JOIN " + student_subject + " where " + SubjectTable + ".id =" + student_subject + ".SubjectID and " + student_subject + ".StudentID ='" + id + "'";
//            Log.i("Database", "sql" + sql);
//
//            Cursor c = db.rawQuery(sql, null);
//            int cnt = c.getCount();
//            Log.i("Database", "cnt-----" + cnt);
//            if (cnt > 0) {
//                c.moveToFirst();
//                do {
//
//                    String subname = c.getString(c.getColumnIndexOrThrow("SubjectName"));
//                    Log.i("Database", "subname-----" + subname);
//
//                    if (sub.contains(subname)) {
//                        Log.i("Database", "In If-----");
//                        Log.i("Database", "sub-----" + sub);
//                        Log.i("Database", "subname in if-----" + subname);
//
//                        String rowId = insertAbsentStudentRegister(classid, String.valueOf(id), name, date, branch, std, batch, subname, "N");
//                        Log.i("Database", "rowId" + rowId);
//
//                    }
//
//                } while (c.moveToNext());
//
//
//            }
//
//        }
//    }


    public String insertAbsentStudentRegister(String clid, String sid, String sname, String date, String branch, String std, String batch, String sub, String ispresent,String subjoption) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues initialValues1 = new ContentValues();

        initialValues1.put("ClassID", clid);
        initialValues1.put("StudentID", sid);
        initialValues1.put("StudentName", sname);
        initialValues1.put("Date", date);
        initialValues1.put("Branch", branch);
        initialValues1.put("Standard", std);
        initialValues1.put("Batch", batch);
        initialValues1.put("Subjects", sub);
        initialValues1.put("IsPresent", ispresent);
        initialValues1.put("SubjectOption", subjoption);

        Log.i("insertAbsentReg", "");
        Log.i("cv->", "" + initialValues1);

        String sql = "SELECT * FROM " + AbsentStudentRegister
                + " WHERE ClassID=? and StudentID=? and Date=? and Branch=? and Standard=? and Batch=? and Subjects=? and SubjectOption=?";

        Log.i("sql->", "" + sql);

        Cursor c = db.rawQuery(sql, new String[]{clid, sid, date, branch, std, batch, sub,subjoption});

        // Cursor c = db.rawQuery(stmt, null);
        int num = c.getCount();
        long rowId;
        if (num > 0) {
            rowId = db.update(AbsentStudentRegister, initialValues1, "Date=? and ClassID" + "=? and " + "StudentID" + "=? and " + "Branch" + "=? and " + "Standard" + "=? and " + "Batch" + "=? and " + "Subjects" + "=? and " + "SubjectOption" + "=?", new String[]{date,clid, sid, branch, std, batch, sub,subjoption});
            Log.i("UpdatedRows:", "" + rowId);

        } else {
        rowId = db.insert(AbsentStudentRegister, null, initialValues1);
            Log.i("InsertRow:", "" + rowId);
             }

        c.close();
        return rowId + "";


    }

    public  boolean checkStudent(String classid2,String branchid,String standid,String batchid,String subjid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        try
         {


        String sql = "SELECT distinct insert_local_add_student.Auto_Id, insert_local_add_student.StudentName, insert_local_add_student.Phone2 FROM " + Student_Data_Table + " INNER JOIN " + student_subject
                + " where " + Student_Data_Table + ".ClassID ='" + classid2 + "' and " + Student_Data_Table + ".Branch ='" + branchid + "' and " + Student_Data_Table + ".Standard='" + standid
                + "' and " + Student_Data_Table + ".Batch ='" + batchid + "' and " + Student_Data_Table + ".Auto_Id = " + student_subject + ".StudentID   and " + student_subject + ".SubjectID IN(" + subjid + ")";
             Log.i("InsertRow:", "checkStudent->" + sql);
             cursor= db.rawQuery(sql, null);
             int cnt = cursor.getCount();
             Log.i("InsertRow:", "checkStudent->" + cnt);
             if(cnt > 0)
                 return  true;
             else
                 return  false;

         } finally
        {
            cursor.close();
         }

    }
    public  boolean checkActiveBatch(String classid2,String branchid,String standid,String batchid,String subjid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        try
        {


            String sql = "SELECT * FROM " + ProfileTable
                    + " where ClassID='" + classid2 + "'and flag_active= 'Y' and BranchId= '" +branchid+ "' and BatchId= '" +batchid+ "'  and StandardId= '" +standid+ "' and SubjectID IN(" + subjid + ")";
            Log.i("InsertRow:", "checkActiveBatch->" + sql);
            cursor= db.rawQuery(sql, null);
            int cnt = cursor.getCount();
            Log.i("InsertRow:", "checkActiveBatch->" + cnt);
            if(cnt > 0)
                return  true;
            else
                return  false;

        } finally
        {
            cursor.close();
        }

    }

    public String getStandardName(String stdid) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT distinct StandardName FROM " + StandardTable
                + " where id='" + stdid
                + "';";
        // String sql="SELECT * FROM " + TABLE_NAME5 + "where resultsubjectid="+
        // selectedsubject + ";";
        Cursor c = db.rawQuery(sql, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                id = c.getString(c.getColumnIndexOrThrow("StandardName"));

            } while (c.moveToNext());
        }
        c.close();
        return id;
    }

    public ArrayList<HashMap<String, String>> getBalance(String studid) {
        String id = "", feeflag = "";
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<HashMap<String, String>> menulist = new ArrayList<HashMap<String, String>>();

        String sql = "SELECT * FROM " + StudentFeeTable
                + " where Balance='0' and StudentID = '" + studid + "'";

        Log.i("Database", "sql---->" + sql);

        Cursor c = db.rawQuery(sql, null);
        MyApplication.log(LOG_TAG, "getBalance(), ID:-------->"+studid+" , c.getCount()-------->"+c.getCount() );

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {


                HashMap<String, String> menu = new HashMap<String, String>();

                menu.put("sid",
                        c.getString(c.getColumnIndexOrThrow("StudentID")));

                menulist.add(menu);

            } while (c.moveToNext());
        }

        c.close();

        return menulist;
    }

    public ArrayList<HashMap<String, String>> getSubDate1(String clsid) {
        int count = 0;
        ArrayList<HashMap<String, String>> subs = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm;
        SQLiteDatabase db = this.getReadableDatabase();

       String sql = "SELECT * FROM " + ProfileTable + " where " + class_id+ "='"+clsid+"'";
     //   String sql = "SELECT BranchTable.BranchName,BatchTable.BatchName,StandardTable.StandardName,SubjectTable.SubjectName FROM BranchTable,BatchTable,StandardTable,SubjectTable WHERE BranchTable.id=ProfileTable.BranchId and BatchTable.id=ProfileTable.BatchId and StandardTable.id=ProfileTable.StandardId and SubjectTable.id=ProfileTable.SubjectId and ProfileTable.ClassID='" + clsid + "'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        Log.e("count-TBL_DATA", "count-TBL_DATA--" + count);

        if (count > 0) {
            c.moveToFirst();
            do {
                hm = new HashMap<String, String>();
                hm.put("BId", c.getString(c.getColumnIndexOrThrow(branch_id)));
                hm.put("SId", c.getString(c.getColumnIndexOrThrow(standard_id)));
                hm.put("BaId", c.getString(c.getColumnIndexOrThrow(batch_id)));
                hm.put("SuId", c.getString(c.getColumnIndexOrThrow(subject_id)));
                hm.put("Rid",c.getString(c.getColumnIndexOrThrow(ID)));
                hm.put("flag",c.getString(c.getColumnIndexOrThrow(Flag_Active)));

                Log.e("count-TBL_DATA", "hm" + hm);

                subs.add(hm);
            } while (c.moveToNext());
        }


        return subs;
    }
        public String getBranchName(String bid)
        { String branchname2="";
            int count = 0;
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT BranchName FROM " + BranchTable + " where " + ID+ "='"+bid+"'";
            Log.e("count-TBL_DATA", "hm" + sql);
            Cursor c = db.rawQuery(sql, null);
            count = c.getCount();
            Log.e("count-TBL_DATA", "hm" + count);
            if(count>0)
            {
                c.moveToFirst();
             branchname2= c.getString(c.getColumnIndexOrThrow(branchname));
            }
         return branchname2;
        }


    public String getBranchID(String bid)
    { String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT id FROM " + BranchTable + " where " + branchname+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        Log.e("count-TBL_DATA", "hm" + count);
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(ID));
        }
        return branchname2;
    }
    public String getBatchID(String bid)
    { String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT id FROM " + BatchTable + " where " + batchname+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        Log.e("count-TBL_DATA", "hm" + count);
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(ID));
        }
        return branchname2;
    }
    public String getStandID(String bid)
    {
        String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT id FROM " + StandardTable + " where " + standardname+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        Log.e("count-TBL_DATA", "hm" + count);
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(ID));
        }
        return branchname2;
    }
     public String getStandName(String bid)
    { String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT StandardName FROM " + StandardTable + " where " + ID+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(standardname));
        }
        return branchname2;
    }
    public String getbatchhhName(String bid)
    { String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT BatchName FROM " + BatchTable + " where " + ID+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(batchname));
        }
        c.close();
        return branchname2;
    }




    public String getSubjjjname(String bid)
    { String branchname2="";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT SubjectName FROM " + SubjectTable + " where " + ID+ "='"+bid+"'";
        Log.e("count-TBL_DATA", "hm" + sql);
        Cursor c = db.rawQuery(sql, null);
        count = c.getCount();
        if(count>0)
        {
            c.moveToFirst();
            branchname2= c.getString(c.getColumnIndexOrThrow(subjectname));
        }
        c.close();
        return branchname2;
    }


    public String getSubjjjnameWithMultilpe(String bid)
    {   String subList = "";

        String dummy[] = bid.split(",");
        Log.e("count-TBL_DATA", "getSubjjjnameWithMultilpecount" + dummy.length);
        SQLiteDatabase db = this.getReadableDatabase();
        for (int j = 0; j < dummy.length; j++) {
            String subSQL = "SELECT SubjectName from " + SubjectTable + " WHERE id = '" + dummy[j] + "'";
            Log.e("count-TBL_DATA", "getSubjjjnameWithMultilpe" + subSQL);
            Cursor c123 = db.rawQuery(subSQL, null);
            if (c123.getCount() > 0) {
                c123.moveToFirst();
                if (subList.equals("")) {

                    subList = c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                } else {
                    subList += "," + c123.getString(c123.getColumnIndexOrThrow("SubjectName"));
                }
            }
        }
        Log.e("count-TBL_DATA", "getSubjjjnameWithMultilpe" + subList);
        return subList;
    }

    public static void log(String str) {
       /* if (str.length() > 4000) {
            Log.i("Database", str.substring(0, 4000));
            log(str.substring(4000));
        } else*/
            Log.i("Database", str);
    }

    public static void log(String LOG_TAG,String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }

    public JSONObject getDataFromBackup(String tableName) throws JSONException {
        // TODO Auto-generated method stub
        JSONObject jsonObject=new JSONObject();
        JSONArray jarray=new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null,
                null);
        String data = "";


        String[] columnNames = dbCursor.getColumnNames();
        int columnCount = columnNames.length;
        for (int i = 0; i < columnCount; i++) {
            Log.i("Column-" + i, columnNames[i]);
            Log.i("Database_table", "columnNames " + columnNames[i]);
        }

        if (columnCount > 0) {
            Cursor cT = db.rawQuery("SELECT * FROM " + tableName, null);
            int recordCount = cT.getCount();
            Log.i("RecordCount", recordCount + "");
            if (recordCount > 0) {
                jsonObject.put("tablename", tableName);
                cT.moveToFirst();
                do {
                    JSONObject  json_obj1 = new JSONObject();

                    Log.i("RecordCount", "json_obj1 before is " + json_obj1);
                    for (int i = 0; i < columnCount; i++) {


                        try {
                           /* data += cT.getString(i).toString()
                                    .replace(",", "~+").toString()
                                    + ",";*/

                            json_obj1.put(columnNames[i],cT.getString(i));
                           /*if (columnNames[i].equalsIgnoreCase("StudentProfile"))
                           {
                               json_obj1.put(columnNames[i],cT.getBlob(i));
                               Log.i("RecordCount", "json_obj1 is if" + json_obj1);
                               Log.i("RecordCount", "columnNames is if" + columnNames[i]);
                           }
                           else
                           {
                               json_obj1.put(columnNames[i],cT.getString(i));
                               Log.i("RecordCount", "json_obj1 is else" + json_obj1);
                               Log.i("RecordCount", "columnNames is else" + columnNames[i]);
                           }*/


                        } catch (Exception e) {
                            data += e.getLocalizedMessage() + "";
                        }
                    }
                    Log.i("RecordCount", "json_obj1 is " + json_obj1);
                    jarray.put(json_obj1);

                   // data = data.substring(0, data.length() - 1);
                   // data += "\n";
                    Log.i("RecordCount", "data is " + data);
                } while (cT.moveToNext());
                jsonObject.put("records", jarray);
                Log.i("RecordCount", "jsonObject is " + jsonObject);
            }
        }
        return jsonObject;
    }


    public JSONObject getDataFromBackupNew(String tableName) throws JSONException {
        // TODO Auto-generated method stub
        JSONObject jsonObject=new JSONObject();
        JSONArray jarray=new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null,
                null);
        String data = "";


        String[] columnNames = dbCursor.getColumnNames();
        int columnCount = columnNames.length;
        for (int i = 0; i < columnCount; i++) {
            Log.i("Column-" + i, columnNames[i]);
        }

        if (columnCount > 0) {
            Cursor cT = db.rawQuery("SELECT * FROM " + tableName, null);
            int recordCount = cT.getCount();
            Log.i("RecordCount", recordCount + "");

            jsonObject.put("tablename", tableName);
            jsonObject.put("count", recordCount);


            if (recordCount > 0) {
                cT.moveToFirst();
                do {
                    JSONObject  json_obj1 = new JSONObject();

                    for (int i = 0; i < columnCount; i++) {

                        try {
                            data += cT.getString(i).toString()
                                    .replace(",", "~+").toString()
                                    + ",";


                            json_obj1.put(columnNames[i],cT.getString(i));

                        } catch (Exception e) {
                            data += e.getLocalizedMessage() + "";
                        }

                    }

                    jarray.put(json_obj1);

                    data = data.substring(0, data.length() - 1);
                    data += "\n";
                } while (cT.moveToNext());
            }
        }
        jsonObject.put("records", jarray);

        return jsonObject;
    }

    public String getTotalFee(String subid,String classid,String std,String branch,String batch) {

        SQLiteDatabase db = this.getReadableDatabase();
        String totfee="";
        String abc = " SELECT Fee FROM " + insert_fee
                + " WHERE Subjects = '" + subid + "' and Standard = '" + std + "' and Branch = '" + branch + "' and Batch = '" + batch + "' and ClassID = '" + classid +"'";

        Log.i("FeeTracker", "subjects" + abc);

        Cursor c3 = db.rawQuery(abc, null);
        int count3 = c3.getCount();
        Log.i("FeeTracker", "getcount:c3=" + count3);
        if (c3.moveToFirst()) {

             totfee = c3.getString(c3.getColumnIndexOrThrow("Fee"));
            Log.i("FeeTracker", "totfee" + totfee);
        }
        c3.close();
        return totfee;
    }


    public void insert_student_fee_default(String classid2,String studid, String std,
                                           String FeesID, String feepaid, String balance, String date,
                                           String remark, String batch,String branch,String subj,String payment_status) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues1 = new ContentValues();

        initialValues1.put("ClassID", classid2);
        initialValues1.put("StudentID", studid);
        initialValues1.put("Standard", std);
        initialValues1.put("Remark", remark);
        initialValues1.put("FeesID", FeesID);
        initialValues1.put("FeesPaid", feepaid);
        initialValues1.put("Balance", balance);
        initialValues1.put("Date", date);
        initialValues1.put("Batch", batch);
        initialValues1.put("Subjects", subj);
        initialValues1.put("Branch", branch);
        initialValues1.put("PaymentStatus", payment_status);

        String sql = "Select StudentID from " + StudentFeeTable + " where StudentID='"+ studid +"' and Standard='"+ std +"' and Batch='"+ batch +"' and Branch='"+ branch +"' and ClassID='"+ classid2 +"'";
        Cursor c = db.rawQuery(sql, null);
        if(c.getCount() > 0){

            db.update(StudentFeeTable, initialValues1, "StudentID = ? and Standard = ? and  Batch = ? and Branch = ? and ClassID = ?",new String[]{studid,std,batch,branch,classid2});
            Log.d("StudentFeeTable", "" + initialValues1);
        }
        else {
            db.insert(StudentFeeTable, null, initialValues1);
            Log.d("StudentFeeTable", "" + initialValues1);
        }

        c.close();

    }




    public void deleteFromStudentSubjectTable(String stud_ids) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String sql = "DELETE FROM " + student_subject
                    + " where StudentID IN ("+ stud_ids +") ";

            Log.i("database", "sql : " + sql);
            Cursor c = db.rawQuery(sql, null);
            Log.i("database", "student_subject delete count : " + c.getCount());

        }catch (Exception e){

            System.out.println(e.getMessage());
        }

    }

    public String deleteFromStudentDataTable(String branch,String batch,String std) {
        int a = 0;
        String sid="";
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT Auto_Id FROM " + Student_Data_Table
                + " where Branch='" + branch + "' and Batch='" + batch + "' and Standard='" + std + "'";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                if(sid.equals("")) {

                    sid = c.getString(c.getColumnIndexOrThrow("Auto_Id"));
                }
                else
                {
                    sid = sid + "," + c.getString(c.getColumnIndexOrThrow("Auto_Id"));
                }

            }while (c.moveToNext());

            a = db.delete(Student_Data_Table, "Branch" + " =? and Batch" + " =? and Standard" + " =?", new String[]{branch, batch, std});
        }

        Log.d("database","deleted records from student_data table : "+a);
        Log.d("database","Student_ids : "+sid);
        return sid;
    }


    public void deleteFromInsertFeeTable(String branch,String batch,String std) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + insert_fee
                + " where Branch='" + branch + "' and Batch='" + batch + "' and Standard='" + std + "'";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();

            a = db.delete(insert_fee, "Branch" + " =? and Batch" + " =? and Standard" + " =?", new String[]{branch, batch, std});
        }

        Log.d("database","deleted records from insert_fee table : "+a);
    }


    public void deleteFromStudentFeeTable(String branch,String batch,String std) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + StudentFeeTable
                + " where Branch='" + branch + "' and Batch='" + batch + "' and Standard='" + std + "'";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();

            a = db.delete(StudentFeeTable, "Branch" + " =? and Batch" + " =? and Standard" + " =?", new String[]{branch, batch, std});
        }

        Log.d("database","deleted records from StudentFeeTable table : "+a);
    }


    public void deleteFromAbsentStudentTable(String branch,String batch,String std) {
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + AbsentStudentRegister
                + " where Branch='" + branch + "' and Batch='" + batch + "' and Standard='" + std + "'";
        Cursor c = db.rawQuery(sql, null);
        Log.i("tag", "sql" + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();

            a = db.delete(AbsentStudentRegister, "Branch" + " =? and Batch" + " =? and Standard" + " =?", new String[]{branch, batch, std});
        }

        Log.d("database","deleted records from AbsentStudentRegister table : "+a);
    }




    public void bulk_Branch(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_Branch";
            db.beginTransaction();

            String sql =  "INSERT INTO "+ TABLE_BRANCH.NAME +" ("+TABLE_BRANCH.COL_ID+","+TABLE_BRANCH.COL_CLASS_ID+","+TABLE_BRANCH.COL_BRANCH_NAME+") VALUES (?,?,?);";
            MyApplication.log(TAG + sql);
            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(LOG_TAG,TAG +"ArrayLen->"+count_array);
            MyApplication.log(LOG_TAG,TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                MyApplication.log(TAG + jsonObject2RecordsChild);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String BranchName = jsonObject2RecordsChild.getString("BranchName");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, BranchName);


                statement.execute();
            }
            MyApplication.log(LOG_TAG,TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void bulk_Batch(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_Batch";
            db.beginTransaction();

            String sql =  "INSERT INTO "+ TABLE_BATCH.NAME +" ("+TABLE_BATCH.COL_ID+","+TABLE_BATCH.COL_CLASS_ID+","+TABLE_BATCH.COL_BATCH_NAME+") VALUES (?,?,?);";
            MyApplication.log(TAG + sql);
            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                MyApplication.log(TAG + jsonObject2RecordsChild);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String BatchName = jsonObject2RecordsChild.getString("BatchName");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, BatchName);


                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void bulk_Standard(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_Standard";
            db.beginTransaction();

            String sql =  "INSERT INTO "+ TABLE_STANDARD.NAME +" ("+TABLE_STANDARD.COL_ID+","+TABLE_STANDARD.COL_CLASS_ID+","+TABLE_STANDARD.COL_STANDARD_NAME+") VALUES (?,?,?);";
            MyApplication.log(LOG_TAG,TAG + sql);
            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(LOG_TAG,TAG +"ArrayLen->"+count_array);
            MyApplication.log(LOG_TAG,TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String StandardName = jsonObject2RecordsChild.getString("StandardName");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, StandardName);


                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void bulk_Subject(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_Subject";
            db.beginTransaction();

            String sql =  "INSERT INTO "+ TABLE_SUBJECT.NAME +" ("+TABLE_SUBJECT.COL_ID+","+TABLE_SUBJECT.COL_CLASS_ID+","+TABLE_SUBJECT.COL_SUBJECT_NAME+") VALUES (?,?,?);";


            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String SubjectName = jsonObject2RecordsChild.getString("SubjectName");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, SubjectName);


                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void bulk_Profile(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_Profile";
            db.beginTransaction();

            //String sql =  "INSERT INTO "+ ProfileTable +" ("+class_id+","+class_name+","+branch_id+","+standard_id+","+batch_id+","+subject_id+","+Flag_Active+") VALUES (?,?,?,?,?,?,?);";
            String sql =  "INSERT INTO "+ TABLE_PROFILE_COMBINATION.NAME
                    +" ("
                    +TABLE_PROFILE_COMBINATION.COL_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_CLASS_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_CLASS_NAME+","
                    +TABLE_PROFILE_COMBINATION.COL_BRANCH_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_STANDARD_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_BATCH_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_SUBJECT_ID+","
                    +TABLE_PROFILE_COMBINATION.COL_FLAG_ACTIVE+") VALUES (?,?,?,?,?,?,?,?);";
            MyApplication.log(LOG_TAG,TAG + sql);
            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(LOG_TAG,TAG +"ArrayLen->"+count_array);
            MyApplication.log(LOG_TAG,TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                MyApplication.log(TAG + jsonObject2RecordsChild);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String ClassName = jsonObject2RecordsChild.getString("ClassName");
                String BranchId = jsonObject2RecordsChild.getString("BranchId");
                String StandardId = jsonObject2RecordsChild.getString("StandardId");
                String BatchId = jsonObject2RecordsChild.getString("BatchId");
                String SubjectId = jsonObject2RecordsChild.getString("SubjectId");
                String flag_active = jsonObject2RecordsChild.getString("flag_active");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, ClassName);
                statement.bindString(4, BranchId);
                statement.bindString(5, StandardId);
                statement.bindString(6, BatchId);
                statement.bindString(7, SubjectId);
                statement.bindString(8, flag_active);


                statement.execute();
            }
            MyApplication.log(LOG_TAG,TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bulk_InsertFee(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_InsertFee";
            db.beginTransaction();


//            String sql =  "INSERT INTO "+ insert_fee +" ("+ "ClassID" +","+"FeeID"+","+ "Fee" +","+ "Subjects" +","+ "Branch" +","+ "Standard" +","+ "Batch" +") VALUES (?,?,?,?,?,?,?);";
            String sql =  "INSERT INTO "+ TABLE_INSERT_FEE.NAME +" ("
                    + TABLE_INSERT_FEE.COL_ID +","
                    + TABLE_INSERT_FEE.COL_CLASS_ID +","
                    + TABLE_INSERT_FEE.COL_FEE_ID+","
                    + TABLE_INSERT_FEE.COL_FEE +","
                    + TABLE_INSERT_FEE.COL_SUBJECTS +","
                    + TABLE_INSERT_FEE.COL_BRANCH +","
                    + TABLE_INSERT_FEE.COL_STANDARD +","
                    + TABLE_INSERT_FEE.COL_BATCH +") VALUES (?,?,?,?,?,?,?,?);";
            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String FeeID = jsonObject2RecordsChild.getString("FeeID");
                String Fee = jsonObject2RecordsChild.getString("Fee");
                String Subjects = jsonObject2RecordsChild.getString("Subjects");
                String Branch = jsonObject2RecordsChild.getString("Branch");
                String Standard = jsonObject2RecordsChild.getString("Standard");
                String Batch = jsonObject2RecordsChild.getString("Batch");

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, ClassID);
                statement.bindString(3, FeeID);
                statement.bindString(4, Fee);
                statement.bindString(5, Subjects);
                statement.bindString(6, Branch);
                statement.bindString(7, Standard);
                statement.bindString(8, Batch);


                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void bulk_AddStudent(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_AddStudent";
            db.beginTransaction();



            String sql =  "INSERT INTO "+ Student_Data_Table +" ("+ "Auto_Id" +","+"id"+","+ "ClassID" +","+ "Branch" +","+ "StudentName" +","+ "StudentProfile" + "," + "Standard" +","+ "Batch" +"," + "DOA" +","+ "Address"+"," + "Phone1"+"," + "Phone2"+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String Branch = jsonObject2RecordsChild.getString("Branch");
                String StudentName = jsonObject2RecordsChild.getString("StudentName");
                String StudentProfile = jsonObject2RecordsChild.getString("StudentProfile");
                String Standard = jsonObject2RecordsChild.getString("Standard");
                String Batch = jsonObject2RecordsChild.getString("Batch");
                String DOB = jsonObject2RecordsChild.getString("DOA");
                String Address = jsonObject2RecordsChild.getString("Address");
                String Phone1 = jsonObject2RecordsChild.getString("Phone1");
                String Phone2 = jsonObject2RecordsChild.getString("Phone2");

                statement.clearBindings();
                statement.bindString(1, Auto_Id);
                statement.bindString(2, id);
                statement.bindString(3, ClassID);
                statement.bindString(4, Branch);
                statement.bindString(5, StudentName);
                statement.bindString(6, StudentProfile);
                statement.bindString(7, Standard);
                statement.bindString(8, Batch);
                statement.bindString(9, DOB);
                statement.bindString(10, Address);
                statement.bindString(11, Phone1);
                statement.bindString(12, Phone2);


                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void bulk_StudentSubject(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_StudentSubject";
            db.beginTransaction();


            String sql =  "INSERT INTO "+ student_subject +" ("+ class_id +","+Studid+","+Subid+","+studentidwithall+") VALUES (?,?,?,?);";

            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String StudentID = jsonObject2RecordsChild.getString("StudentID");
                String SubjectID = jsonObject2RecordsChild.getString("SubjectID");
                String StudentIdWithAll = jsonObject2RecordsChild.getString("StudentIdWithAll");

                statement.clearBindings();
                statement.bindString(1, ClassID);
                statement.bindString(2, StudentID);
                statement.bindString(3, SubjectID);
                statement.bindString(4, StudentIdWithAll);
                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bulk_StudentFeeTable(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_StudentFeeTable";
            db.beginTransaction();


            String sql =  "INSERT INTO "+ StudentFeeTable +" ("+ "ClassID" +","+"StudentID"+"," + "Standard"+"," + "FeesID" +","+ "FeesPaid" +","+ "Balance" +","+ "Date" +","+ "Remark" +"," + "Batch" +","+ "Subjects"+"," + "Branch"+"," + "PaymentStatus" +") VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String StudentID = jsonObject2RecordsChild.getString("StudentID");
                String Standard = jsonObject2RecordsChild.getString("Standard");
                String FeesID = jsonObject2RecordsChild.getString("FeesID");
                String FeesPaid = jsonObject2RecordsChild.getString("FeesPaid");
                String Remark = jsonObject2RecordsChild.getString("Remark");
                String Balance = jsonObject2RecordsChild.getString("Balance");
                String Batch = jsonObject2RecordsChild.getString("Batch");
                String Date = jsonObject2RecordsChild.getString("Date");
                String Subjects = jsonObject2RecordsChild.getString("Subjects");
                String Branch = jsonObject2RecordsChild.getString("Branch");
                String PaymentStatus = jsonObject2RecordsChild.getString("PaymentStatus");

                statement.clearBindings();
                statement.bindString(1, ClassID);
                statement.bindString(2, StudentID);
                statement.bindString(3, Standard);
                statement.bindString(4, FeesID);
                statement.bindString(5, FeesPaid);
                statement.bindString(6, Balance);
                statement.bindString(7, Date);
                statement.bindString(8, Remark);
                statement.bindString(9, Batch);
                statement.bindString(10, Subjects);
                statement.bindString(11, Branch);
                statement.bindString(12, PaymentStatus);



                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bulk_AbsentStudentRegister(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();
            SQLiteDatabase db = this.getWritableDatabase();
            String TAG = "bulk_AbsentStudentRegister";
            db.beginTransaction();

            String sql =  "INSERT INTO "+ AbsentStudentRegister +" ("+ "ClassID" +","+"StudentID"+"," + "StudentName"+"," + "Date" +","+ "Branch" +","+ "Standard" +","+ "Batch" +","+ "Subjects" +"," + "SubjectOption" +"," + "IsPresent"+") VALUES (?,?,?,?,?,?,?,?,?,?);";

            SQLiteStatement statement = db.compileStatement(sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.log(TAG +"ArrayLen->"+count_array);
            MyApplication.log(TAG +"StartTime->");
            for (int i = 0; i < count_array; i++) {

                JSONObject jsonObject2RecordsChild = jsonArray.getJSONObject(i);
                String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                String id = jsonObject2RecordsChild.getString("id");
                String ClassID = jsonObject2RecordsChild.getString("ClassID");
                String StudentID = jsonObject2RecordsChild.getString("StudentID");
                String StudentName = jsonObject2RecordsChild.getString("StudentName");
                String Date = jsonObject2RecordsChild.getString("Date");
                String Branch = jsonObject2RecordsChild.getString("Branch");
                String Standard = jsonObject2RecordsChild.getString("Standard");
                String Batch = jsonObject2RecordsChild.getString("Batch");
                String Subjects = jsonObject2RecordsChild.getString("Subjects");
                String IsPresent = jsonObject2RecordsChild.getString("IsPresent");
                String SubjectOption = jsonObject2RecordsChild.getString("SubjectOption");

                statement.clearBindings();
                statement.bindString(1, ClassID);
                statement.bindString(2, StudentID);
                statement.bindString(3, StudentName);
                statement.bindString(4, Date);
                statement.bindString(5, Branch);
                statement.bindString(6, Standard);
                statement.bindString(7, Batch);
                statement.bindString(8, Subjects);
                statement.bindString(9, SubjectOption);
                statement.bindString(10, IsPresent);

                statement.execute();
            }
            MyApplication.log(TAG +"EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void deleteBranchTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + BranchTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted branches are : "+ c.getCount());
    }

    public void deleteBatchTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + BatchTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted batches are : "+ c.getCount());
    }

    public void deleteStandardTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + StandardTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted standards are : "+ c.getCount());
    }

    public void deleteSubjectTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + SubjectTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted subjects are : "+ c.getCount());
    }

    public void deleteProfileTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + ProfileTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted profile table are : "+ c.getCount());
    }


    public void deleteInsertFeeTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + insert_fee;
        Cursor c = db.rawQuery(sql, null);
        log("deleted insert_fee_table are : "+ c.getCount());
    }

    public void deleteStudentTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + Student_Data_Table;
        Cursor c = db.rawQuery(sql, null);
        log("deleted students are : "+ c.getCount());
    }

    public void deleteStudentSubjectTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + student_subject;
        Cursor c = db.rawQuery(sql, null);
        log("deleted student_subject table are : "+ c.getCount());
    }

    public void deleteStudentFeeTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + StudentFeeTable;
        Cursor c = db.rawQuery(sql, null);
        log("deleted student_fees_table are : "+ c.getCount());
    }

    public void deleteAbsentStudentTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from " + AbsentStudentRegister;
        Cursor c = db.rawQuery(sql, null);
        log("deleted absent student register table are : "+ c.getCount());
    }

    public Cursor GetStudentDataById(String autoid) {

        SQLiteDatabase db = this.getWritableDatabase();
        /*String str1 = ("select * from " + Student_Data_Table + " where "+ Auto_Id + "=?", new String[] {autoid});
                + Auto_Id + "=?" + ";";*/
        Cursor res = db.rawQuery("select * from " + Student_Data_Table + " where "+ Auto_Id + "=?", new String[] {autoid});


        log(res + " "+res);
        return res;
    }

    public ArrayList<HashMap<String, String>> GetStudentDataWithProfile(String classid,String branchid,String standid,String batchid,String subjid) {


        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        String str1 = "SELECT distinct insert_local_add_student.Auto_Id," +
                " insert_local_add_student.id," +
                " insert_local_add_student.StudentName," +
                " insert_local_add_student.StudentProfile," +
                " insert_local_add_student.Branch," +
                " insert_local_add_student.Standard," +
                " insert_local_add_student.Batch," +
                " insert_local_add_student.Phone1," +
                " insert_local_add_student.Phone2," +
                " insert_local_add_student.DOA" +
                " FROM " + Student_Data_Table + " INNER JOIN " + student_subject  + " where insert_local_add_student.ClassID = '" + classid + "'" +
                " and insert_local_add_student.Branch = '" + branchid + "' and insert_local_add_student.Standard = '" + standid + "'" +
                " and insert_local_add_student.Batch = '" + batchid + "'" +
                " and student_subject.StudentIdWithAll = '" + subjid + "'" +
                " and insert_local_add_student.Auto_Id = student_subject.StudentID "+ " ORDER BY insert_local_add_student.id ASC ";

        // Log.i("tag","getstud"+str1);


        Cursor c = db.rawQuery(str1,null);
        int j=c.getCount();
        Log.i("tag", "getstud" + j);

        if (c.moveToFirst()) {
            do {

                HashMap<String, String> menu = new HashMap<String, String>();
                menu.put("id", c.getString(c.getColumnIndexOrThrow("Auto_Id")));
                menu.put("auto_id", c.getString(c.getColumnIndexOrThrow("id")));
                menu.put("sname", c.getString(c.getColumnIndexOrThrow(student_name)));
                menu.put("studentprofile", c.getString(c.getColumnIndexOrThrow(studentprofile)));
                menu.put("Branch", c.getString(c.getColumnIndexOrThrow(branch)));
                menu.put("Batch", c.getString(c.getColumnIndexOrThrow(batch)));
                menu.put("phone1", c.getString(c.getColumnIndexOrThrow(ph1)));
                menu.put("studphone", c.getString(c.getColumnIndexOrThrow(ph2)));
                menu.put("std", c.getString(c.getColumnIndexOrThrow(standard1)));
                menu.put("date", c.getString(c.getColumnIndexOrThrow(doa)));

                menuItems.add(menu);

            } while (c.moveToNext());
        }
        c.close();

//        String stro = "SELECT insert_local_add_student.id," +
//                " insert_local_add_student.StudentName," +
//                " insert_local_add_student.Branch," +
//                " insert_local_add_student.Standard," +
//                " insert_local_add_student.Batch," +
//                " insert_local_add_student.Phone1," +
//                " insert_local_add_student.Phone2" +
//                " FROM " + Student_Data_Table + " INNER JOIN " + student_subject  + " where insert_local_add_student.ClassID = '" + classid + "'" +
//                " and insert_local_add_student.Branch = '" + branchid + "' and insert_local_add_student.Standard = '" + standid + "'" +
//                " and insert_local_add_student.Batch = '" + batchid + "'" +
//                " and student_subject.SubjectID = '" + subjid + "'" +
//                " and insert_local_add_student.id = student_subject.StudentID";

        String sqlr="select count(student_subject.SubjectID) as subjectcount from student_subject where SubjectID IN (" + subjid + ") group by StudentID";
        Log.i("tag", "new*" + sqlr);
        Cursor c2 = db.rawQuery(sqlr,null);
        int jj=c2.getCount();
        Log.i("tag", "new*count" + jj);
        if(jj> 0) {
            c2.moveToFirst();
            do {
                String count4=c2.getString(c2.getColumnIndexOrThrow("subjectcount"));
                Log.i("tag", "new*value" + count4);
            }while (c2.moveToNext());
        }
        return menuItems;
    }
}