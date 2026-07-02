package model.entities;

//================================= Declaracao da classe herdeira ==========================
public class PortScan extends Incidente{
    private int portasVarridas;

    public PortScan(String ipOrigem, String ipDestino){
        super("PS", ipOrigem, ipDestino);
        this.portasVarridas = 0;
    }

//============================ Consulta e incrementação do atributo ===========================
    public int getPortasVarridas(){ 
        return portasVarridas; 
    }

    public void incrementarPortas(){
        this.portasVarridas++;
    }

//================================= Implementação das intefaces ==============================
    
    @Override
    public void executarMitigacao(){
        System.out.println("  >> [PortScan] Ativando firewall para o IP: " + getIpOrigem()
            + " | Portas varridas: " + portasVarridas);
    }

    @Override
    public String gerarLinhaAuditoria(){
        return String.format("[%s] ID: %s | TIPO: Port Scan | ORIGEM: %s | DESTINO: %s | NUM.PORTAS: %d",
            getTimestampFormatado(), getId(), getIpOrigem(), getIpDestino(), portasVarridas);
    }
}
