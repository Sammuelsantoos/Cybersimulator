package model;

//================================= Declaracao da classe herdeira ==========================
public class ForcaBruta extends Incidente{

    private static int idFB = 0;
    private int tentativasFalhas;

    public ForcaBruta(String ipOrigem, String ipDestino){
        super("FB-" + (++idFB), ipOrigem, ipDestino);
        this.tentativasFalhas = 0;
    }
    
//============================ Consulta e incrementação do atributo ===========================
    public int getTentativasFalhas(){ 
        return tentativasFalhas;
    }

    public void incrementarTentativas(){
        this.tentativasFalhas++;
    }

//================================= Implementação das intefaces ==============================
    @Override
    public void executarMitigacao(){
        System.out.println("[ForcaBruta] Bloqueando IP suspeito: " + getIpOrigem()
            + ", tentativas falhas de acesso: " + tentativasFalhas);
    }

    @Override
    public String gerarLinhaAuditoria(){
        return String.format("[%s] ID: %s | TIPO: Forca Bruta | ORIGEM: %s | DESTINO: %s | NUM.TENTATIVAS: %d",
            getTimestampFormatado(), getId(), getIpOrigem(), getIpDestino(), tentativasFalhas);
    }
}
