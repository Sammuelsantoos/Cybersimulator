// ======================== Importação de bibliotecas e declaração do package =========================
package model.services;

import model.entities.ForcaBruta;
import java.util.HashMap;
import java.util.Map;

// ==================================== Declaração da classe e seus atributos =========================

public class AnalisadorForcaBruta extends AnalisadorTrafego{

    private Map<String, ForcaBruta > incidentesPorIp;

// ============================ Construtor e inicialização dos atributos =======================

    public AnalisadorForcaBruta(SOCEngine engine){
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

            if (status.equalsIgnoreCase("FAILED")){
                if (incidentesPorIp.containsKey(ipOrigem)){
                    ForcaBruta incidenteExistente = incidentesPorIp.get(ipOrigem);
                    incidenteExistente.incrementarTentativas();
                }else{
                    ForcaBruta incidente = new ForcaBruta(ipOrigem, ipDestino);
                    incidente.incrementarTentativas();
                    incidentesPorIp.put(ipOrigem, incidente);
                    engine.receberIncidente(incidente);
                }
            }
        }catch (Exception e){
            System.out.println("  [AnalisadorForcaBruta] Erro ao processar linha: " + e.getMessage());
        }
    }
}