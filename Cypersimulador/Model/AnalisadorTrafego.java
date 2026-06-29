import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class AnalisadorTrafego implements Runnablegit  {
    protected SOCEngine engine;
    protected String caminhoArquivo;

    public AnalisadorTrafego(SOCEngine engine, String caminhoArquivo) {
        this.engine = engine;
        this.caminhoArquivo = caminhoArquivo;
    }

    public abstract void processarLinhaBruta(String linhaLog);

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                processarLinhaBruta(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o log na thread " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}