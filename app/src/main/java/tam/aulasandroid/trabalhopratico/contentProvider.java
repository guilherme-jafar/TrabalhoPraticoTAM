package tam.aulasandroid.trabalhopratico;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class contentProvider extends ContentProvider {

    static final String PROVIDER_NAME="tam.aulasandroid.trabalhopratico.refeicao";
    static final String PATH =	"refeicao";

    String TAG = "Exemplo36";

    // this is the database stuff
    String DB_NAME = "MyDB";
    String DB_TABLE = "refeicao";
    int DB_VERSION = 1;

    String SQL_CREATE = "CREATE TABLE refeicao (id TEXT,hora TEXT,refeicao TEXT,informacao TEXT,PRIMARY KEY(id))";
    String SQL_CREATE2 ="CREATE TABLE historico (id TEXT,idref TEXT,estado TEXT,hora TEXT,obs TEXT,PRIMARY KEY(id));";

    String SQL_DROP = "DROP TABLE IF EXISTS refeicao" ;
    String SQL_DROP2 = "DROP TABLE IF EXISTS historico";
    private SQLiteDatabase db;

    //-----------------------------------------
    class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
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
    @Override
    public boolean onCreate() {
        return false;
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
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
