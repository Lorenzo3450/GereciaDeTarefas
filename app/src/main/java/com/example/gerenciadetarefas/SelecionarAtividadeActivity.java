package com.example.gerenciadetarefas;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadetarefas.adpter.SelecionarAdpter;
import com.example.gerenciadetarefas.model.Tarefa;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SelecionarAtividadeActivity extends AppCompatActivity {

    private List<Tarefa> tarefas;
    private SelecionarAdpter listaAdpter;
    private RecyclerView recyclerView;
    private List<String> listaDeTarefasConcluidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selecionaratividade_timer);

        recyclerView = findViewById(R.id.recyclerView);
        tarefas = new ArrayList<>();
        carregarTarefas(); // Carrega as tarefas ao iniciar
        listaDeTarefasConcluidas = filtrarTarefasNaoConcluidas(tarefas);

        configurarRecyclerView();
    }

    private void configurarRecyclerView() {
        listaAdpter = new SelecionarAdpter(listaDeTarefasConcluidas, tarefa -> {
            Intent intent = new Intent(SelecionarAtividadeActivity.this, CronometroActivity.class);
            intent.putExtra("tarefa", tarefa);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdpter);
    }

    private void carregarTarefas() {
        try {
            FileInputStream fis = openFileInput("tarefas.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tarefas = (ArrayList<Tarefa>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> filtrarTarefasNaoConcluidas(List<Tarefa> todasTarefas) {
        List<String> tarefasNaoConcluidas = new ArrayList<>();
        for (Tarefa tarefa : todasTarefas) {
            if (!tarefa.isConcluida()) {
                tarefasNaoConcluidas.add(tarefa.getTarefa());
            }
        }
        return tarefasNaoConcluidas;
    }
}
