package com.todolist.projetotodo.model;

public class Tarefa {

  private Integer id;
  private String descricao;
  private boolean feita;

  public Tarefa() {
    this.id = null;
  }

  public Tarefa(Integer id, String descricao, boolean feita) {
    this.id = id;
    this.descricao = descricao;
    this.feita = feita;
  }

  public Tarefa(String descricao, boolean feita) {
    this.descricao = descricao;
    this.feita = feita;
  }

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public boolean isFeita() {
    return feita;
  }

  public void setFeita(boolean feita) {
    this.feita = feita;
  }
}