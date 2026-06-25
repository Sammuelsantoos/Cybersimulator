public abstract class AnalisadorTrafego {
    protected SOCEngine engine;

    public AnalisadorTrafego(SOCEngine engine){
        this.engine = engine;
    }

    public abstract void processarLinhaBruta(String linhaLog);
}