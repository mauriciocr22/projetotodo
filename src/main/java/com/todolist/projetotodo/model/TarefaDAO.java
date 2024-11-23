package com.todolist.projetotodo.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Repository
public class TarefaDAO {

  @Autowired
  DataSource dataSource;

  JdbcTemplate jdbc;

  @PostConstruct
  private void initialize() {
    jdbc = new JdbcTemplate(dataSource);
  }

  // Insere uma nova tarefa no banco de dados
  public void inserir(Tarefa tarefa) {
    String sql = "INSERT INTO tarefa (descricao, feita) VALUES (?, ?);";
    Object[] parametros = { tarefa.getDescricao(), tarefa.isFeita() };
    jdbc.update(sql, parametros);
  }

  // Atualiza uma tarefa existente no banco de dados
  public void atualizar(Tarefa tarefa) {
    String sql = "UPDATE tarefa SET descricao = ?, feita = ? WHERE id = ?;";
    Object[] parametros = { tarefa.getDescricao(), tarefa.isFeita(), tarefa.getId() };
    jdbc.update(sql, parametros);
  }

  // Deleta uma tarefa pelo ID
  public void deletar(int id) {
    String sql = "DELETE FROM tarefa WHERE id = ?;";
    jdbc.update(sql, id);
  }

  // Busca uma tarefa pelo ID
  public Tarefa buscarPorId(int id) {
    String sql = "SELECT * FROM tarefa WHERE id = ?";
    return jdbc.query(sql, (rs, rowNum) -> new Tarefa(
        rs.getInt("id"),
        rs.getString("descricao"),
        rs.getBoolean("feita")), id).stream().findFirst().orElse(null);
  }

  // Retorna todas as tarefas
  public List<Tarefa> listarTodas() {
    String sql = "SELECT * FROM tarefa;";
    return jdbc.query(sql, (rs, rowNum) -> {
      return new Tarefa(
          rs.getInt("id"),
          rs.getString("descricao"),
          rs.getBoolean("feita"));
    });
  }
}