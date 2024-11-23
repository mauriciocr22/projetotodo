package com.todolist.projetotodo.controller;

import com.todolist.projetotodo.model.Tarefa;
import com.todolist.projetotodo.model.TarefaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TarefaController {

  @Autowired
  TarefaService tarefaService;

  @GetMapping
  public String listarTarefas(Model model) {
    model.addAttribute("tarefas", tarefaService.listarTodas());
    return "tarefas/listar";
  }

  @GetMapping("/tarefas/estatisticas")
  public String estatisticas(Model model) {
    int totalTarefas = tarefaService.listarTodas().size();
    long concluidas = tarefaService.listarTodas().stream().filter(Tarefa::isFeita).count();
    long pendentes = totalTarefas - concluidas;

    model.addAttribute("totalTarefas", totalTarefas);
    model.addAttribute("concluidas", concluidas);
    model.addAttribute("pendentes", pendentes);

    return "tarefas/estatisticas";
  }

  @GetMapping("/nova")
  public String novaTarefaForm(Model model) {
    model.addAttribute("tarefa", new Tarefa());
    return "tarefas/formulario";
  }

  @GetMapping("/editar/{id}")
  public String editarTarefaForm(@PathVariable int id, Model model) {
    Tarefa tarefa = tarefaService.buscarPorId(id);
    model.addAttribute("tarefa", tarefa);
    return "tarefas/formulario";
  }

  @PostMapping("/nova")
  public String salvarTarefa(@ModelAttribute Tarefa tarefa) {
    if (tarefa.getId() == null || tarefa.getId() == 0) {
      tarefaService.inserir(tarefa); // Chama o método de inserção
    } else {
      tarefaService.atualizar(tarefa); // Chama o método de atualização
    }
    return "redirect:/tarefas"; // Redireciona para a lista
  }

  @GetMapping("/deletar/{id}")
  public String deletarTarefa(@PathVariable int id) {
    tarefaService.deletar(id);
    return "redirect:/tarefas";
  }
}