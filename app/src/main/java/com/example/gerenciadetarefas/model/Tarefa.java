package com.example.gerenciadetarefas.model;

import java.io.Serializable;

public class Tarefa  implements Serializable {

    private String Tarefa;
    private boolean concluida;


    public Tarefa(String tarefa, boolean cocluida) {
        Tarefa = tarefa;
        this.concluida = cocluida;
    }

    public String getTarefa() {
        return Tarefa;
    }

    public void setTarefa(String tarefa) {
        Tarefa = tarefa;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
