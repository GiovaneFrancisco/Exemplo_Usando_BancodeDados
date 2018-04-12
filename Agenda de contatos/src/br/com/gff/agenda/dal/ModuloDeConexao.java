package br.com.gff.agenda.dal;

import java.sql.*; //Importa recursos para trabalhar 	

public class ModuloDeConexao {
	public static Connection conector(){
		Connection conexao = null;
		String driver = "com.mysql.jdbc.Driver"; //Caminho para chegar no banco
		String url = "jdbc:mysql://10.26.49.46:3306/agenda?useSSL=false"; // //ip:porta padr�o/nome do banco/ignorar criptografia
		String user = "admin"; //Define o nome do usu�rio 
		String senha = "123"; //Define a senha do usu�rio
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url,user,senha);
			return conexao;
		} catch (Exception e) {
			//System.out.println(e);
			return null;
		}
	}
}
