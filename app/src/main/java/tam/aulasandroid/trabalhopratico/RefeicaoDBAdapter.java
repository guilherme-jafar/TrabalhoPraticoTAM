package tam.aulasandroid.trabalhopratico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RefeicaoDBAdapter {

    String TAG = "Exemplo29";

    String DB_NAME = "MyDB";
    String DB_TABLE = "refeicao";
    int DB_VERSION = 2;

    String SQL_CREATE = "CREATE TABLE "+DB_TABLE+"(id TEXT,hora TEXT,refeicao TEXT,informacao TEXT,PRIMARY KEY(id))";

    String SQL_DROP = "DROP TABLE IF EXISTS " + DB_TABLE;

    DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public RefeicaoDBAdapter(Context ctx){
        dbHelper = new DatabaseHelper(ctx);
    }

    //-----------------------------------------
    class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
            Log.d(TAG, "Table Refeicao created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP);
            onCreate(db);
            Log.d(TAG, "Table Refeicao recreated");
        }

    }
    //---------------------------------------------

    public void open() throws SQLException {
        db= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    // returns number of rows inserted or -1 if an error occurred
    public long insertContact(String name, String email){
        ContentValues vals = new ContentValues();
        vals.put("name", name);
        vals.put("email", email);
        return db.insert(DB_TABLE,      // table
                null,  // null when some value is provided (nullColumnHack)
                vals );       // initial values
    }

//    // returns number of rows updated
//    public int updateContact(String name, String email){
//        ContentValues vals = new ContentValues();
//        //vals.put("name", name);
//        vals.put("email", email);
//        return db.update(DB_TABLE,                   // table
//                vals,                      // new values
//                "name=?",             // where clause
//                new String[]{ name } ); // where arguments
//    }

    // returns a Cursor object positioned before the first entry
//    public Cursor getContact(String name){
//        Cursor cursor = db.query(
//                DB_TABLE,                            // table to perform the query
//                new String[] { "name", "email" }, // resultset columns/fields
//                "name=?",                         // condition or selection
//                new String[] { name },         // selection arguments (fills in '?' above)
//                null,                             // groupBy
//                null,                              // having
//                null );                           // orderBy
//
//        return cursor;
//    }

    public Cursor getAllRefeicoes(){
        Cursor cursor = db.query(
                "refeicao",
                new String[]{"id", "hora", "refeicao", "informacao"},
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    // returns number of rows deleted
    public int deleteContact(String name){
        return db.delete(DB_TABLE,                   // table
                "name=?",              // where clause
                new String[]{ name} );  // where arguments

    }


}
