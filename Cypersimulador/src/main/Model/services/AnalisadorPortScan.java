package Model.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.entities.PortScan;
import Model.exceptions.IpInvalidoException;

/**
 * Analisador especializado em detectar varredura de portas.
 * Rastreia quantas portas diferentes um IP tenta acessar.
 */
public class AnalisadorPortScan extends AnalisadorTrafego {
    private Map<String, Set<String>> portasVarridasPorIp;
    private static final int LIMITE_PORTAS = 10;

    /**
     * Construtor que inicializa o rastreamento de portas.
     * 
     * @param engine Motor SOC
     * @param caminhoArquivo Caminho do log
     */
    public AnalisadorPortScan(SOCEngine engine, String caminhoArquivo) {
        super(engine, caminhoArquivo);
        this.portasVarridasPorIp = new HashMap<>();
    }

    /**
     * Processa linhas do log contando portas distintas por IP.
     * Formato esperado: timestamp,ipOrigem,ipDestino,porta,acao
     * 
     * @param linhaLog Linha do arquivo
     */
    @Override
    public void processarLinhaBruta(String linhaLog) {
        try {
            if (linhaLog == null || linhaLog.isBlank()) return;
            String[] campos = linhaLog.split(",");
            if (campos.length != 5) return;

            String ipOrigem = campos[1].trim();
            String ipDestino = campos[2].trim();
            String porta = campos[3].trim();

            portasVarridasPorIp.putIfAbsent(ipOrigem, new HashSet<>());
            Set<String> portas = portasVarridasPorIp.get(ipOrigem);
            portas.add(porta);

            if (portas.size() == LIMITE_PORTAS) {
                PortScan incidente = new PortScan(ipOrigem, ipDestino, portas.size());
                this.engine.receberIncidente(incidente);
                portas.clear();
            }
        } catch (IpInvalidoException e) {
            System.err.println("[AnalisadorPortScan] " + e.getMessage());
        } catch (Exception e) {
            // Ignora silenciosamente
        }
    }
}
