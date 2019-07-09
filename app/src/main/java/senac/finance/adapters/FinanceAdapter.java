package senac.finance.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import senac.finance.R;
import senac.finance.models.Finance;

public class FinanceAdapter extends RecyclerView.Adapter {

    private List<Finance> financeList;
    private Context context;

    public FinanceAdapter(List<Finance> financeList, Context context) {
        this.financeList = financeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_finance, parent, false);

        FinanceViewHolder holder = new FinanceViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FinanceViewHolder viewHolder = (FinanceViewHolder) holder;

        Finance finance = financeList.get(position);

        String formato = "R$ #,##0.00";
        DecimalFormat d = new DecimalFormat(formato);

        viewHolder.dia.setText(finance.getDia());
        viewHolder.valor.setText(d.format(finance.getValor()));

        if (finance.getTipo().equals("Receita")){
            viewHolder.valor.setTextColor(Color.GREEN);
        } else {
            viewHolder.valor.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }
}
