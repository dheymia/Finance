package senac.finance.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import senac.finance.R;

public class FinanceViewHolder extends RecyclerView.ViewHolder {

    final ImageView tipo;
    final TextView dia;
    final TextView valor;
    final ImageView editar;
    final ImageView excluir;
    final LinearLayout item;
    final TextView transacao;



    public FinanceViewHolder(@NonNull View itemView) {
        super(itemView);
        tipo = itemView.findViewById(R.id.imageView);
        dia = itemView.findViewById(R.id.txtDia);
        valor = itemView.findViewById(R.id.txtValor);
        editar = itemView.findViewById(R.id.imgEditar);
        excluir = itemView.findViewById(R.id.imgExcluir);
        item = itemView.findViewById(R.id.bcItem);
        transacao = itemView.findViewById(R.id.txtTipo);
    }
}
