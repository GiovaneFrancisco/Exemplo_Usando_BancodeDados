package br.com.gff.agenda.formulario;

import java.sql.*;
import br.com.gff.agenda.dal.ModuloDeConexao;

public class Formulario {
	public static void main(String[] args) {
		Connection conexao = null; // Conectar com o banco de dados
		conexao = ModuloDeConexao.conector();
		if (conexao != null) {
			System.out.println("Conectado");
		} else {
			System.out.println("Erro de conexão");
		}

	}
}
