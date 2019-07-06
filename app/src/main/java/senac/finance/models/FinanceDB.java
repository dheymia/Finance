package senac.finance.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FinanceDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "finance.db";
    private static final int VERSAO = 1;

    public FinanceDB(Context context) {
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TB_FINANCE ("
                + "id integer primary key autoincrement,"
                + "dia text,"
                + "tipo text,"
                + "valor real"
                +")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TB_FINANCE");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(Finance finance){
        ContentValues values;
        long resultado;

        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("dia", finance.getDia().toString());
        values.put("tipo", finance.getTipo());
        values.put("valor", finance.getValor());

        resultado = db.insert("TB_FINANCE", null, values);
        db.close();

        if (resultado ==-1) {
            return false;
        } else {
            return true;
        }
    }
}
