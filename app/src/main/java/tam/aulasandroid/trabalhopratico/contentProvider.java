package tam.aulasandroid.trabalhopratico;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class contentProvider extends ContentProvider {

    static final String PROVIDER_NAME="tam.aulasandroid.trabalhopratico.refeicao";
    static final String PATH =	"refeicao";
    static final String PATH2 =	"historico";
    static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/" + PATH);
    static final Uri CONTENT_URI2 = Uri.parse("content://"+ PROVIDER_NAME + "/" + PATH2);
    String TAG = "";
    DatabaseHelper dbHelper;

    // this is the database stuff
    String DB_NAME = "MyDB";
    String DB_TABLE = "refeicao";
    String DB_TABLE2 = "historico";
    int DB_VERSION = 2;


    static final int CONTACTS = 1;
    static final int CONTACT_ID = 2;

    // This class helps to obtain the CONTENT_TYPE from the URI of the request
    // see method getType bellow


    String SQL_CREATE = "CREATE TABLE "+DB_TABLE+"(id TEXT,hora TEXT,refeicao TEXT,informacao TEXT,PRIMARY KEY(id))";
    String SQL_CREATE2 ="CREATE TABLE historico (id TEXT,idref TEXT,estado TEXT,hora TEXT,dia TEXT,obs TEXT,PRIMARY KEY(id));";

    String SQL_DROP = "DROP TABLE IF EXISTS refeicao" ;
    String SQL_DROP2 = "DROP TABLE IF EXISTS historico";
    private SQLiteDatabase db;

//    public contentProvider(){
//        Context context = getContext();
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        db = dbHelper.getWritableDatabase();
//    }

//    public contentProvider(Context ctx){
//        dbHelper = new DatabaseHelper(getContext());
//    }

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, PATH, CONTACTS);
        uriMatcher.addURI(PROVIDER_NAME, PATH + "/#",CONTACT_ID);
        uriMatcher.addURI(PROVIDER_NAME, PATH2, CONTACTS);
        uriMatcher.addURI(PROVIDER_NAME, PATH2 + "/#",CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void open() throws SQLException{
        db= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //-----------------------------------------
    class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DB_TABLE, null, DB_VERSION);

        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
            db.execSQL(SQL_CREATE2);
            Log.d(TAG, "Table contacts created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP);
            db.execSQL(SQL_DROP2);
            onCreate(db);
            Log.d(TAG, "Table contacts recreated");
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID;

        if (uri.toString().contains("historico")) {
             rowID = db.insert("historico", // table
                    null,      // null when some value is provided
                    values);   // initial values

        }else{
             rowID = db.insert("refeicao", // table
                    null,      // null when some value is provided
                    values);
        }
        //---if added successfully---
        if (rowID>0)
        {Log.d(TAG, "Table created");
            // obtains a new URI ending with the contact id
            Uri contactUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            // notifies the ContentResolver that the contents of the requested URI have changed
            getContext().getContentResolver().notifyChange(contactUri, null);
            // retuns the URI of the new contact
            return contactUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {


        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
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


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
                 db.delete(
                "refeicao",       // table
                selection,      // where clause
                selectionArgs);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db.update(
                "refeicao",       // table
                values,         // new values
                selection,      // where clause
                selectionArgs);
        return 0;
    }
}
