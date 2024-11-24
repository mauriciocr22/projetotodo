package com.todolist.projetotodo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

  @Autowired
  TarefaDAO tarefaDAO;

  // Insere uma nova tarefa no banco de dados
  public void inserir(Tarefa tarefa) {
    tarefaDAO.inserir(tarefa);
  }

  // Atualiza uma tarefa existente no banco de dados
  public void atualizar(Tarefa tarefa) {
    tarefaDAO.atualizar(tarefa);
  }

  // Deleta uma tarefa pelo ID
  public void deletar(int id) {
    tarefaDAO.deletar(id);
  }

  // Busca uma tarefa pelo ID
  public Tarefa buscarPorId(int id) {
    return tarefaDAO.buscarPorId(id);
  }

  // Retorna todas as tarefas
  public List<Tarefa> listarTodas() {
    return tarefaDAO.listarTodas()
        .stream()
        .sorted(Comparator.comparing(Tarefa::isFeita)) // Ordena por ID
        .collect(Collectors.toList());
  }
}