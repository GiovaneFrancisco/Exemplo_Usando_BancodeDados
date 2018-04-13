package br.com.gff.agenda.formulario;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.gff.agenda.dal.ModuloDeConexao;

public class Contatos extends JFrame {

	Connection conexao = null; // Puxa o status da conexão do banco de dados
	PreparedStatement pst = null; // Executa os comandos do banco
	ResultSet rs = null; // Recupera os valores do banco

	private void create() {
		String adicionar = "insert into tb_contatos(id,nome,fone,email)"
				+ "values(?,?,?,?)"; //Pega os valores escritos no aplicativo e manda para o banco de dados
		try {
			pst = conexao.prepareStatement(adicionar);
			pst.setString(1, txtId.getText()); //Pega o valor Id e armazena no campo 1 do banco de dados
			pst.setString(2, txtNome.getText());
			pst.setString(3, txtTelefone.getText());
			pst.setString(4, txtEmail.getText());
			int adc = pst.executeUpdate(); //Inserir ou alterar uma linha na tabela
			if(adc>0) {
				JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso", "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				limpar();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void read() {
		String pesquisar = "select * from tb_contatos where id = ?";
		try {
			pst = conexao.prepareStatement(pesquisar); // Pesquisa no banco
			pst.setString(1, txtId.getText()); // Substitui o ? pelo texto do campo 1 (Id)
			rs = pst.executeQuery(); // Recupera os dados
			if(rs.next()) { //Se houver dados a serem recuperados no campo do ID
				txtNome.setText(rs.getString(2)); //Completa os campos com as informações
				txtTelefone.setText(rs.getString(3));
				txtEmail.setText(rs.getString(4));
			}else {
				JOptionPane.showMessageDialog(null, "Contato inexistente", "Erro", JOptionPane.ERROR_MESSAGE);
				limpar();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void update() {
		String alterar = "update tb_contatos set nome=?,fone=?, email=? where id=?";
		try {
			pst = conexao.prepareStatement(alterar);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtTelefone.getText());
			pst.setString(3, txtEmail.getText());
			pst.setString(4, txtId.getText());
			int adc = pst.executeUpdate(); //Inserir ou alterar uma linha na tabela
			if(adc>0) {
				JOptionPane.showMessageDialog(null, "Contato alterado com sucesso", "Alterado", JOptionPane.INFORMATION_MESSAGE);
				limpar();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void delete() {
		UIManager.put("OptionPane.noButtonText", "Nem fodendo");
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja deletar o contato?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(confirma==0) {
			String apagar = "delete from tb_contatos where id=?";
			try {
				pst = conexao.prepareStatement(apagar);
				pst.setString(1, txtId.getText());
				int removido = pst.executeUpdate();
				if(removido==1) {
					JOptionPane.showMessageDialog(null, "Contato Removido com sucesso");
				}
				limpar();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			
		}
	}
	
	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtEmail.setText(null);
	}
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtId;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnRead;
	private JButton btnDelete;
	private JLabel lblStatus;
	private JButton btnLimpar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contatos frame = new Contatos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor
	 */

	public Contatos() {

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Contatos.class.getResource("/br/com/gff/icons/Agenda.png")));
		setTitle("Agenda de Contatos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 51, 46, 14);
		contentPane.add(lblNome);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 92, 95, 14);
		contentPane.add(lblTelefone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 140, 46, 14);
		contentPane.add(lblEmail);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 14, 46, 14);
		contentPane.add(lblId);

		lblStatus = new JLabel("");
		lblStatus.setToolTipText("Status do banco");
		lblStatus.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/BancoDesconectado.png")));
		lblStatus.setBounds(331, 11, 32, 32);
		contentPane.add(lblStatus);

		txtNome = new JTextField();
		txtNome.setToolTipText("Coloque o nome do contato aqui");
		txtNome.setBounds(66, 48, 206, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtTelefone = new JTextField();
		txtTelefone.setToolTipText("Coloque o telefone do contato aqui");
		txtTelefone.setBounds(66, 89, 206, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setToolTipText("Coloque o email do contato aqui");
		txtEmail.setBounds(66, 140, 206, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtId = new JTextField();
		txtId.setToolTipText("Coloque o ID do contato aqui");
		txtId.setBounds(66, 11, 64, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				create();
			}
		});
		btnCreate.setToolTipText("Criar um contato");
		btnCreate.setBackground(SystemColor.menu);
		btnCreate.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/Adicionar.png")));
		btnCreate.setBounds(10, 195, 64, 64);
		contentPane.add(btnCreate);
		btnCreate.setBorderPainted(false);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				read();
			}
		});
		btnRead.setToolTipText("Pesquisar um contato");
		btnRead.setBackground(SystemColor.menu);
		btnRead.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/Procurar.png")));
		btnRead.setBounds(299, 195, 64, 64);
		contentPane.add(btnRead);
		btnRead.setBorderPainted(false);
		
		btnUpdate.setToolTipText("Editar um contato");
		btnUpdate.setBackground(SystemColor.menu);
		btnUpdate.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/Editar.png")));
		btnUpdate.setBounds(109, 195, 64, 64);
		contentPane.add(btnUpdate);
		btnUpdate.setBorderPainted(false);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				
			}
		});
		btnDelete.setToolTipText("Apagar um contato");
		btnDelete.setBackground(SystemColor.menu);
		btnDelete.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/Remover.png")));
		btnDelete.setBounds(208, 195, 64, 64);
		contentPane.add(btnDelete);
		btnDelete.setBorderPainted(false);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(284, 161, 89, 23);
		contentPane.add(btnLimpar);

		conexao = ModuloDeConexao.conector(); // Estabelece a conexão como banco dentro da interface gráfica
		if (conexao != null) { // Se o banco estiver ligado e conectado, devolve true
			lblStatus.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/BancoConectado.png")));
		} else { // Se o banco não estiver ligado e conectado, devolve false
			lblStatus.setIcon(new ImageIcon(Contatos.class.getResource("/br/com/gff/icons/BancoDesconectado.png")));
		}
		setLocationRelativeTo(null);
	}
}
