// ======================== Declaração do package =========================
package model.services;

// ==================================== Declaração da classe e seus atributos =========================

public abstract class AnalisadorTrafego implements Runnable{

    protected SOCEngine engine;

// ============================ Construtor e validação do engine =======================

    public AnalisadorTrafego(SOCEngine engine){
        if (engine == null)
            throw new IllegalArgumentException("SOCEngine nao pode ser nulo.");
        this.engine = engine;
    }

// ====================================== Contrato de processamento e execução da thread =================================

    public abstract void processarLinhaBruta(String linhaLog);

    @Override
    public void run(){
        // Ponto de entrada da thread
    }
}