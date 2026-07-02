// ======================== Importação de bibliotecas e declaração do package =========================
package model.services;

import model.entities.PortScan;
import java.util.HashMap;
import java.util.Map;

// ==================================== Declaração da classe e seus atributos =========================

public class AnalisadorPortScan extends AnalisadorTrafego{

    private Map<String, PortScan > incidentesPorIp;

// ============================ Construtor e inicialização dos atributos =======================

    public AnalisadorPortScan(SOCEngine engine){
        super(engine);
        this.incidentesPorIp = new HashMap<>();
    }

// ====================================== Processamento e detecção do padrão de ataque =================================

    @Override
    public void processarLinhaBruta(String linhaLog){
        try{
            if (linhaLog == null || linhaLog.trim().isEmpty()) return;
            String[] partes = linhaLog.split("\\|");

            if (partes.length < 5) return;

            String ipOrigem  = partes[1].trim();
            String ipDestino = partes[2].trim();
            String status    = partes[4].trim();

            if (status.equalsIgnoreCase("SCAN")){
                if (incidentesPorIp.containsKey(ipOrigem)){
                    PortScan incidenteExistente = incidentesPorIp.get(ipOrigem);
                    incidenteExistente.incrementarPortas();
                }else{
                    PortScan incidente = new PortScan(ipOrigem, ipDestino);
                    incidente.incrementarPortas();
                    incidentesPorIp.put(ipOrigem, incidente);
                    engine.receberIncidente(incidente);
                }
            }

        }catch (Exception e){
            System.out.println("  [AnalisadorPortScan] Erro ao processar linha: " + e.getMessage());
        }
    }
}