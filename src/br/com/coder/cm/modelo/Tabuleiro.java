package br.com.coder.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.coder.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	//ATRIBUTOS
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	//CONSTRUTOR
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	//MÉTODO ABRIR CAMPO
	public void abrir(int linha, int coluna) {
		try {
			campos.stream()
			.filter(c -> c.getLinha() == linha && c.getColuna()==coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
			
		} catch (ExplosaoException e) {
			campos.forEach(campo -> campo.setAberto(true));
			throw e;
		}
	}
	//MÉTODO MARCAR CAMPO
	public void marcar(int linha, int coluna) {
		campos.stream()
		.filter(c -> c.getLinha() == linha && c.getColuna()==coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	//MÉTODO GERAR CAMPOS
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
			
		}
		
	}

	//MÉTODOS ASSICIAR OS VIZINHOS
	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
			
		}
	}

	//MÉTODO SORTEAR AS MINAS
	private void sortearMinas() {
		long minasArmadas = 0;
		
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
		
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		
		} while (minasArmadas < minas);
		
	}
	
	//MÉTODO OBJETIVO ALCANÇADO
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	//MÉTODO REINICIAR JOGO
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	//MÉTODO EXIBIR
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//ÍNDICES DAS COLUNAS
		sb.append("  ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
	} sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			// ÍNDICES DAS LINHAS
			sb.append(l);
			sb.append(" ");
			
			//Imprimir o valor das colunas
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
				
			}
			sb.append("\n");//Quebrar as linhas
			
		}
		return sb.toString();
	}


}
