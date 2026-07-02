import java.util.Scanner;

import Model.services.AnalisadorForcaBruta;
import Model.services.AnalisadorPortScan;
import Model.services.SOCEngine;

/**
 * Classe principal do Cybersimulator.
 * Oferece menu interativo para carregar logs, executar análise e gerar relatórios.
 * Usa multithreading para processar o arquivo com múltiplos analisadores simultaneamente.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String caminhoArquivo = "";

        if (args.length > 0) {
            caminhoArquivo = args[0];
        } else {
            System.out.print("Nenhum arquivo de log informado via parâmetro.\n" +
                    "Digite o caminho do arquivo de log (.txt ou .csv): ");
            caminhoArquivo = scanner.nextLine();
        }

        SOCEngine engine = new SOCEngine();
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n========================================");
            System.out.println("    CYBERSIMULATOR - SOC ORIENTADO");
            System.out.println("========================================");
            System.out.println("1. Iniciar Simulação (Disparar Threads)");
            System.out.println("2. Exibir Resumo de Ações Tomadas");
            System.out.println("3. Gerar Relatório Final de Auditoria");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n[SISTEMA] Iniciando Threads de Análise para o arquivo: " + caminhoArquivo);
                    Thread t1 = new Thread(new AnalisadorForcaBruta(engine, caminhoArquivo), "Thread-ForcaBruta");
                    Thread t2 = new Thread(new AnalisadorPortScan(engine, caminhoArquivo), "Thread-PortScan");
                    
                    t1.start();
                    t2.start();
                    
                    try {
                        t1.join();
                        t2.join();
                    } catch (InterruptedException e) {
                        System.err.println("Erro na execução multithread: " + e.getMessage());
                    }
                    System.out.println("[SISTEMA] Varredura finalizada. Ameaças detectadas foram enviadas ao motor central.");
                    break;
                    
                case "2":
                    if (!engine.temIncidentes()) {
                        System.out.println("\n[SISTEMA] Nenhum incidente registrado ainda.");
                    } else {
                        engine.gerarAcoesTomadas();
                    }
                    break;
                    
                case "3":
                    if (!engine.temIncidentes()) {
                        System.out.println("\n[SISTEMA] Nenhum incidente registrado para gerar relatório.");
                    } else {
                        engine.gerarRelatorioFinal();
                    }
                    break;
                    
                case "4":
                    rodando = false;
                    System.out.println("\nEncerrando Cybersimulator. Até mais!");
                    break;
                    
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }
}
