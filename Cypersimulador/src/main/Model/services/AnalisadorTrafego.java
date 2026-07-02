package Model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe abstrata que define o comportamento de um analisador de tráfego.
 * Implementa Runnable para rodar em thread separada.
 * Cada subclasse define sua própria lógica de processamento.
 */
public abstract class AnalisadorTrafego implements Runnable {
    protected SOCEngine engine;
    protected String caminhoArquivo;

    /**
     * Construtor que recebe o motor SOC e o caminho do arquivo de log.
     * 
     * @param engine Instância do SOCEngine para enviar incidentes
     * @param caminhoArquivo Caminho do arquivo de log a ser analisado
     */
    public AnalisadorTrafego(SOCEngine engine, String caminhoArquivo) {
        this.engine = engine;
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Método abstrato que cada analisador implementa conforme sua regra.
     * 
     * @param linhaLog Linha bruta do arquivo de log
     */
    public abstract void processarLinhaBruta(String linhaLog);

    /**
     * Execução da thread: lê o arquivo linha por linha e processa.
     */
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                processarLinhaBruta(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o log na thread " + 
                    Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}
