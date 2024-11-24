package com.todolist.projetotodo.controller;

import com.todolist.projetotodo.model.Tarefa;
import com.todolist.projetotodo.model.TarefaService;

import java.util.List;

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
    model.addAttribute("novaTarefa", new Tarefa());
    return "tarefas/listar";
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
      tarefaService.inserir(tarefa);
    } else {
      tarefaService.atualizar(tarefa);
    }
    return "redirect:/";
  }

  @PostMapping("/atualizar-feito/{id}")
  public String atualizarFeito(@PathVariable int id) {
    Tarefa tarefa = tarefaService.buscarPorId(id);
    tarefa.setFeita(!tarefa.isFeita());
    tarefaService.atualizar(tarefa);
    return "redirect:/";
  }

  @PostMapping("/deletar-feitas")
  public String deletarTarefasFeitas() {
    tarefaService.deletarFeitas();
    return "redirect:/";
  }

  @PostMapping("/deletar/{id}")
  public String deletarTarefa(@PathVariable int id) {
    tarefaService.deletar(id);
    return "redirect:/";
  }

  @GetMapping("/debug/tarefas")
  @ResponseBody
  public List<Tarefa> listarTarefasDebug() {
    return tarefaService.listarTodas();
  }
}
