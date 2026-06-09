package model;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public abstract class Incidente implements IAcaoDefensiva, IRelatorioAuditavel, ICalculavelRisco {
    private String ipOrigem;
    private String ipDestino;
    private int severidadeBase;
    private LocalDateTime dataHora;
 
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
 
    private static boolean ipValido(String ip) {
        if (ip == null || ip.isBlank()) return false;
        String[] partes = ip.split("\\.");
        if (partes.length != 4) return false;
        for (String parte : partes) {
            try {
                int valor = Integer.parseInt(parte);
                if (valor < 0 || valor > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }