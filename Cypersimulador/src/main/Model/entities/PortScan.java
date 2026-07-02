package Model.entities;

import Model.exceptions.IpInvalidoException;

/**
 * Representa uma varredura de portas (Port Scan) detectada.
 * Monitora tentativas de scan em múltiplas portas.
 */
public class PortScan extends Incidente {
    private int portasVarridas;

    /**
     * Construtor do incidente de port scan.
     * 
     * @param ipOrigem IP de quem está fazendo o scan
     * @param ipDestino IP sendo scaneado
     * @param portasVarridas Quantidade de portas testadas
     * @throws IpInvalidoException se algum IP for inválido
     */
    public PortScan(String ipOrigem, String ipDestino, int portasVarridas) throws IpInvalidoException {
        super(ipOrigem, ipDestino);
        this.portasVarridas = portasVarridas;
    }

    public int getPortasVarridas() {
        return portasVarridas;
    }

    /**
     * Aplica drop temporário no tráfego do IP suspeito.
     */
    @Override
    public void executarMitigacao() {
        System.out.println("[MITIGAÇÃO] Aplicando Drop Temporário ao IP " + getIpOrigem() + 
                ". (Motivo: Varredura de Portas)");
    }

    /**
     * Gera linha para relatório de auditoria.
     * 
     * @return String formatada com dados do scan
     */
    @Override
    public String gerarLinhaAuditoria() {
        return String.format("[%s] ID: %s | TIPO: Port Scan | ORIGEM: %s | DESTINO: %s | PORTAS VARRIDAS: %d",
                getTimestampFormatado(), getId(), getIpOrigem(), getIpDestino(), portasVarridas);
    }
}
