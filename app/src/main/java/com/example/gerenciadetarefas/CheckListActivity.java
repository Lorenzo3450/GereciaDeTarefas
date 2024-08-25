package com.example.gerenciadetarefas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadetarefas.adpter.ListaAdpter;
import com.example.gerenciadetarefas.model.Tarefa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CheckListActivity extends AppCompatActivity {

    private List<Tarefa> tarefas;
    private ListaAdpter listaAdpter;
    private EditText txtTarefa;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checklist);

        txtTarefa = findViewById(R.id.editTextText);
        Button btnAdicionarTarefa = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.listaDeTarefas);

        tarefas = new ArrayList<>();
        carregarTarefas(); // Carrega as tarefas ao iniciar
        configurarRecyclerView();

        // Adiciona o TextWatcher para mudar a cor do texto se a tarefa tiver menos de 5 caracteres
        txtTarefa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 5) {
                    txtTarefa.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                } else {
                    txtTarefa.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tarefaTexto = txtTarefa.getText().toString();
                if (tarefaTexto.length() <= 5) {
                    Toast.makeText(getApplicationContext(), "A tarefa deve ter mais de 5 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    Tarefa tarefa = new Tarefa(tarefaTexto, false);
                    tarefas.add(tarefa);
                    listaAdpter.notifyItemInserted(tarefas.size() - 1);
                    salvarTarefas();
                    txtTarefa.setText("");
                }
            }
        });

    }

    private void configurarRecyclerView() {
        listaAdpter = new ListaAdpter(this, tarefas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdpter);
    }

    private void salvarTarefas() {
        try {
            FileOutputStream fos = openFileOutput("tarefas.dat", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tarefas);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
