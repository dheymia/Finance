package senac.finance;

import android.app.SearchManager;
import android.content.Context;
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

import android.provider.SearchRecentSuggestions;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import senac.finance.adapters.FinanceAdapter;
import senac.finance.models.Finance;
import senac.finance.models.FinanceDB;
import senac.finance.providers.FinanceSuggestionProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Finance>> {

    public static FinanceDB financeDB;
    RecyclerView recyclerView;
    ProgressBar loading;
    LoaderManager loaderManager;
    static List<Finance> financeList = new ArrayList<>();
    static Finance financeSelecionado;
    static boolean alterar = false;

    public static final int OPERATION_SEARCH_LOADER = 15;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            financeSelecionado = financeList.get(position);
            Toast.makeText(MainActivity.this, "You Clicked: " + financeSelecionado.getId(), Toast.LENGTH_SHORT).show();

            alterar = true;

            Intent intent = new Intent(getBaseContext(), FinanceActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (financeDB == null) {
            financeDB = new FinanceDB(this);
        }

        loaderManager = getSupportLoaderManager();

        loading = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.listFinances);

        /*
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layout);
        */

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar = false;
                Intent novaFinance = new Intent(getBaseContext(), FinanceActivity.class);
                startActivity(novaFinance);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Loader<List<Finance>> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);

        if (loader == null) {
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, null, MainActivity.this);
        } else {
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, null, MainActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onSearchRequested() {


        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<List<Finance>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Finance>>(this) {
            @Nullable
            @Override
            public List<Finance> loadInBackground() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Get the intent, verify the action and get the query
                // Pesquisar por datas
                Intent intent = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    String query = intent.getStringExtra(SearchManager.QUERY);

                    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getBaseContext(),
                            FinanceSuggestionProvider.AUTHORITY, FinanceSuggestionProvider.MODE);
                    suggestions.saveRecentQuery(query, null);

                    List<Finance> q = new ArrayList<>();

                    try {
                        Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(query);

                        q = financeDB.select(new SimpleDateFormat("yyyy-MM-dd").format(dt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return q;
                } else {

                    return financeDB.select();
                }
            }

            @Override
            protected void onStartLoading() {
                loading.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Finance>> loader, List<Finance> data) {
        loading.setVisibility(View.GONE);

        financeList = new ArrayList<>(data);

        FinanceAdapter adapter = new FinanceAdapter(financeList, this);
        adapter.setOnItemClickListener(onItemClickListener);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Finance>> loader) {

    }
}