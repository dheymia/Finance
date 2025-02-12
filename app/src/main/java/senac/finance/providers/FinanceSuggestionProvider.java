package senac.finance.providers;


import android.content.SearchRecentSuggestionsProvider;

public class FinanceSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "senac.finance.FinanceSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public FinanceSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}