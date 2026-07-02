package Model.entities;

import Model.exceptions.IpInvalidoException;

/**
 * Representa um ataque de força bruta detectado.
 * Monitora tentativas de login falhadas.
 */
public class ForcaBruta extends Incidente {
    private int tentativasFalhas;

    /**
     * Construtor que cria um incidente de força bruta.
     * 
     * @param ipOrigem IP do atacante
     * @param ipDestino IP do alvo
     * @throws IpInvalidoException se algum IP for inválido
     */
    public ForcaBruta(String ipOrigem, String ipDestino) throws IpInvalidoException {
        super(ipOrigem, ipDestino);
        this.tentativasFalhas = 5;
    }

    public int getTentativasFalhas() {
        return tentativasFalhas;
    }

    /**
     * Ação de bloqueio do IP atacante no firewall.
     */
    @Override
    public void executarMitigacao() {
        System.out.println("[MITIGAÇÃO] Bloqueando IP " + getIpOrigem() + 
                " no Firewall. (Motivo: Força Bruta detectada)");
    }

    /**
     * Gera linha formatada para auditoria.
     * 
     * @return String com todos os detalhes do ataque
     */
    @Override
    public String gerarLinhaAuditoria() {
        return String.format("[%s] ID: %s | TIPO: Forca Bruta | ORIGEM: %s | DESTINO: %s | TENTATIVAS: %d",
                getTimestampFormatado(), getId(), getIpOrigem(), getIpDestino(), tentativasFalhas);
    }
}
