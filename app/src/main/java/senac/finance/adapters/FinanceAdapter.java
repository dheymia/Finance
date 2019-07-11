package senac.finance.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import senac.finance.MainActivity;
import senac.finance.R;
import senac.finance.models.Finance;
import senac.finance.models.FinanceDB;

import static java.nio.file.Files.delete;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FinanceViewHolder viewHolder = (FinanceViewHolder) holder;

        Finance finance = financeList.get(position);

        Date diaSelecionado = null;
        try {
            diaSelecionado = new SimpleDateFormat("yyyy-MM-dd").parse(finance.getDia());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formato = "R$ #,##0.00";
        DecimalFormat d = new DecimalFormat(formato);

        viewHolder.dia.setText(new SimpleDateFormat("dd/MM/yyyy").format(diaSelecionado));
        viewHolder.valor.setText(d.format(finance.getValor()));
        viewHolder.tipo.setImageResource(finance.getFoto());
        viewHolder.transacao.setText(finance.getTipo());
        viewHolder.excluir.setOnClickListener(view -> removerItem(position));

        if (finance.getTipo().equals("Receita")){
            viewHolder.valor.setTextColor(Color.rgb(104,133,207));
            viewHolder.item.setBackgroundColor(Color.rgb(239,242,250));
            viewHolder.transacao.setTextColor(Color.rgb(104,133,207));
            viewHolder.dia.setTextColor(Color.rgb(104,133,207));
        } else {
            viewHolder.valor.setTextColor(Color.rgb(182,5,102));
            viewHolder.item.setBackgroundColor(Color.rgb(247,230,239));
            viewHolder.transacao.setTextColor(Color.rgb(182,5,102));
            viewHolder.dia.setTextColor(Color.rgb(182,5,102));

        }

    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    private void removerItem(int position) {
        financeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, financeList.size());


    }
}
