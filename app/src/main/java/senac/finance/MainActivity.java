package senac.finance;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import senac.finance.adapters.FinanceAdapter;
import senac.finance.models.Finance;
import senac.finance.models.FinanceDB;

public class MainActivity extends AppCompatActivity {

    public static FinanceDB financeDB;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (financeDB == null) {
            financeDB = new FinanceDB(this);
        }

        recyclerView = findViewById(R.id.listFinances);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layout);

        recyclerView.setAdapter(new FinanceAdapter(financeDB.select(), this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent novaFinance = new Intent(getBaseContext(), FinanceActivity.class);
                startActivity(novaFinance);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setAdapter(new FinanceAdapter(financeDB.select(), this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent configuracoes = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(configuracoes);
        }

        return super.onOptionsItemSelected(item);
    }
}