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

  // Exibe a lista de tarefas na página principal
  @GetMapping
  public String listarTarefas(Model model) {
    model.addAttribute("tarefas", tarefaService.listarTodas());
    model.addAttribute("novaTarefa", new Tarefa()); // Objeto para criar nova tarefa
    return "tarefas/listar";
  }

  // Página de estatísticas (mantida, caso necessário)
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

  // Exibe o formulário para criar uma nova tarefa
  @GetMapping("/nova")
  public String novaTarefaForm(Model model) {
    model.addAttribute("tarefa", new Tarefa());
    return "tarefas/formulario";
  }

  // Exibe o formulário para editar uma tarefa existente
  @GetMapping("/editar/{id}")
  public String editarTarefaForm(@PathVariable int id, Model model) {
    Tarefa tarefa = tarefaService.buscarPorId(id);
    model.addAttribute("tarefa", tarefa);
    return "tarefas/formulario";
  }

  // Cria uma nova tarefa ou atualiza uma existente
  @PostMapping("/nova")
  public String salvarTarefa(@ModelAttribute Tarefa tarefa) {
    if (tarefa.getId() == null || tarefa.getId() == 0) {
      tarefaService.inserir(tarefa); // Caso de criação
    } else {
      tarefaService.atualizar(tarefa); // Caso de atualização
    }
    return "redirect:/"; // Redireciona para a página principal
  }

  // Alterna o status de "feito" de uma tarefa (feito/não feito)
  @PostMapping("/atualizar-feito/{id}")
  public String atualizarFeito(@PathVariable int id) {
    Tarefa tarefa = tarefaService.buscarPorId(id);
    tarefa.setFeita(!tarefa.isFeita()); // Inverte o estado do checkbox
    tarefaService.atualizar(tarefa);
    return "redirect:/";
  }

  // Deleta uma tarefa pelo ID
  @PostMapping("/deletar/{id}")
  public String deletarTarefa(@PathVariable int id) {
    tarefaService.deletar(id);
    return "redirect:/";
  }

  @GetMapping("/debug/tarefas")
  @ResponseBody
  public List<Tarefa> listarTarefasDebug() {
    return tarefaService.listarTodas(); // Retorna as tarefas diretamente
  }

}
