package Model.interfaces;

/**
 * Interface para garantir que todo incidente gere uma linha de auditoria.
 * Muito útil para compliance e rastreabilidade.
 */
public interface IRelatorioAuditavel {
    /**
     * Gera uma linha formatada para o relatório de auditoria.
     * 
     * @return String contendo os dados do incidente
     */
    String gerarLinhaAuditoria();
}
