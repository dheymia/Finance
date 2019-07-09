package senac.finance;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import senac.finance.models.Finance;

public class FinanceActivity extends AppCompatActivity {

    CalendarView dia;
    RadioButton tipo;
    EditText valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dia = findViewById(R.id.calendarDia);
        tipo = findViewById(R.id.rbReceita);
        valor = findViewById(R.id.txtValor);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date calend = new Date(dia.getDate());
                    String diaSelecionado = new SimpleDateFormat("yyyy-MM-dd").format(calend);

                    String tipoSelec = tipo.isChecked() ? "Receita" : "Despesa";

                    Double valorSelec = Double.parseDouble(valor.getText().toString());

                    Finance finance = new Finance(0, diaSelecionado, tipoSelec, valorSelec);

                    if (MainActivity.financeDB.insert(finance)){
                        finish();
                    }
                } catch (Exception ex){
                    Log.e("FinanceActivity", ex.getMessage());
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
