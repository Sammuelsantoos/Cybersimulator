package Model.services;

import java.util.HashMap;
import java.util.Map;

import Model.entities.ForcaBruta;
import Model.exceptions.IpInvalidoException;

/**
 * Analisador especializado em detectar ataques de força bruta.
 * Acumula falhas de login por IP e dispara incidente ao atingir o limite.
 */
public class AnalisadorForcaBruta extends AnalisadorTrafego {
    private Map<String, Integer> falhasPorIp;
    private static final int LIMITE = 5;

    /**
     * Construtor que inicializa o mapa de rastreamento de falhas.
     * 
     * @param engine Motor SOC para enviar incidentes
     * @param caminhoArquivo Caminho do arquivo de log
     */
    public AnalisadorForcaBruta(SOCEngine engine, String caminhoArquivo) {
        super(engine, caminhoArquivo);
        this.falhasPorIp = new HashMap<>();
    }

    /**
     * Processa linha do log procurando por FALHA_LOGIN.
     * Formato esperado: timestamp,ipOrigem,ipDestino,porta,FALHA_LOGIN
     * 
     * @param linhaLog Linha bruta do arquivo
     */
    @Override
    public void processarLinhaBruta(String linhaLog) {
        try {
            if (linhaLog == null || linhaLog.isBlank()) return;
            String[] campos = linhaLog.split(",");
            if (campos.length != 5) return;

            String ipOrigem = campos[1].trim();
            String ipDestino = campos[2].trim();
            String acao = campos[4].trim();

            if (acao.equalsIgnoreCase("FALHA_LOGIN")) {
                int tentativas = falhasPorIp.getOrDefault(ipOrigem, 0) + 1;
                falhasPorIp.put(ipOrigem, tentativas);

                if (tentativas == LIMITE) {
                    ForcaBruta incidente = new ForcaBruta(ipOrigem, ipDestino);
                    this.engine.receberIncidente(incidente);
                    falhasPorIp.put(ipOrigem, 0);
                }
            }
        } catch (IpInvalidoException e) {
            System.err.println("[AnalisadorForcaBruta] " + e.getMessage());
        } catch (Exception e) {
            // Ignora linha quebrada
        }
    }
}
