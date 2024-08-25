package com.example.gerenciadetarefas.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadetarefas.CheckListActivity;
import com.example.gerenciadetarefas.R;
import com.example.gerenciadetarefas.model.Tarefa;

import java.util.List;

public class ListaAdpter extends RecyclerView.Adapter<ListaAdpter.ViewHolder> {

    private final List<Tarefa> itemList;
    private final Context context;

    public ListaAdpter(Context context, List<Tarefa> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = itemList.get(position);
        holder.checkBox.setText(tarefa.getTarefa());
        holder.checkBox.setChecked(tarefa.isConcluida());

        // Configura o listener do botão de exclusão
        holder.imageButton.setOnClickListener(v -> {
            // Remove a tarefa da lista
            itemList.remove(position);
            // Notifica o adaptador sobre a mudança
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
            // Salva a lista atualizada
            ((CheckListActivity) context).salvarTarefas();
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarefa.setConcluida(isChecked);
            // Salva a tarefa após a mudança
            ((CheckListActivity) context).salvarTarefas();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }
}
