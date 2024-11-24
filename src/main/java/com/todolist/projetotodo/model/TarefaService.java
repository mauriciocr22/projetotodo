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

  public void inserir(Tarefa tarefa) {
    tarefaDAO.inserir(tarefa);
  }

  public void atualizar(Tarefa tarefa) {
    tarefaDAO.atualizar(tarefa);
  }

  public void deletar(int id) {
    tarefaDAO.deletar(id);
  }

  public Tarefa buscarPorId(int id) {
    return tarefaDAO.buscarPorId(id);
  }

  public List<Tarefa> listarTodas() {
    return tarefaDAO.listarTodas()
        .stream()
        .sorted(Comparator.comparing(Tarefa::isFeita))
        .collect(Collectors.toList());
  }

  public void deletarFeitas() {
    List<Tarefa> feitas = listarTodas().stream()
        .filter(Tarefa::isFeita)
        .collect(Collectors.toList());

    for (Tarefa tarefa : feitas) {
      tarefaDAO.deletar(tarefa.getId());
    }
  }
}
