package com.example.gerenciadetarefas.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadetarefas.R;

import java.util.List;

public class SelecionarAdpter extends RecyclerView.Adapter<SelecionarAdpter.ViewHolder> {

    private List<String> tarefas;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String tarefa);
    }

    public SelecionarAdpter(List<String> tarefas, OnItemClickListener listener) {
        this.tarefas = tarefas;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_selecionar_adapter, parent, false); // Substitua "seu_layout" pelo nome do layout XML
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tarefa = tarefas.get(position);
        holder.txtTarefa.setText(tarefa);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(tarefa));
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTarefa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTarefa = itemView.findViewById(R.id.TxtTarefa); // Substitua "textView" pelo id do TextView no seu layout
        }
    }
}
