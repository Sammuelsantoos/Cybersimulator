package Model.exceptions;

/**
 * Exceção lançada quando um endereço IP não é válido.
 * Valida formato xxx.xxx.xxx.xxx e range 0-255 para cada octeto.
 */
public class IpInvalidoException extends Exception {
    public IpInvalidoException(String mensagem) {
        super(mensagem);
    }
}
