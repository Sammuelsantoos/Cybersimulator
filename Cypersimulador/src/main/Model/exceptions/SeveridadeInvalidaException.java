package Model.exceptions;

/**
 * Exceção para severidades inválidas.
 * Pode ser usada futuramente se houver classificação de criticidade.
 */
public class SeveridadeInvalidaException extends Exception {
    /**
     * Construtor com mensagem personalizada.
     * 
     * @param mensagem Motivo da invalidação
     */
    public SeveridadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
