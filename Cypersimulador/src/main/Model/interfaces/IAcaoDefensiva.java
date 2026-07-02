package Model.interfaces;

/**
 * Interface que define comportamento defensivo automático.
 * Toda ameaça detectada deve saber como se defender sozinha.
 */
public interface IAcaoDefensiva {
    /**
     * Executa a ação de mitigação específica para o tipo de incidente.
     */
    void executarMitigacao();
}
