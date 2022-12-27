package br.com.coder.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.coder.cm.excecao.ExplosaoException;
import br.com.coder.cm.excecao.SairException;
import br.com.coder.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	// ATRIBUTO
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	// CONSTRUTOR
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	// MÉTODO EXECUTAR JOGO
	private void executarJogo() {
		try {

			boolean continuar = true;

			while (continuar) {

				cicloDoJogo();

				System.out.println("Outra partida? (S/n)");
				String resposta = entrada.nextLine();

				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();

				}
			}

		} catch (SairException e) {
			System.out.println("Tchau!!");
		}
	}

	private void cicloDoJogo() {
		try {

			while (!tabuleiro.objetivoAlcancado()) {
				// INICIA O JOGO
				System.out.println(tabuleiro);

				// PEGA O VALOR
				String digitado = capturarValorDigitado("Digite (x e y): ");

				// TRANSFORMA EM INTEIRO
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(elemento -> Integer.parseInt(elemento.trim())).iterator();

				// PERGUNTA SE QUER ABRIR OU MARCAR
				digitado = capturarValorDigitado("1 - Abrir ou 2 - Marcar");

				// EXECUTA CONFORME ESCOLHA 1 OU 2
				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());

				} else if ("2".equals(digitado)) {
					tabuleiro.marcar(xy.next(), xy.next());
				}

			}

			// GANHA O JOGO SE MARCAR TUDO SEM MINA
			System.out.println("Você ganhou!");

			// SE PEGAR MINA, LANÇA EXCEÇÃO E PERDE O JOGO
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}

	}

	// SE DIGITAR EM ALGUM MOMENTO A PALAVRA SAIR = LANÇA EXCEÇÃO E SAI DO JOGO
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();

		}
		return digitado;
	}

}
