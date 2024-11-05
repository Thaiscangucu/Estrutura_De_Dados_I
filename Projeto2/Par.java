// |--------------- Projeto Estrutura De Dados I ------------------|
// |-------------- Interpretador REPL de Assembly -----------------|

// |-- Nome: Thais Ferreira Canguçu  RA: 10403283 -----------------|
// |-- Nome: Pedro Moniz Canto       RA: 10426546 -----------------|

public class Par {
    private char registrador;
    private int valor;

    public Par(char registrador, int valor) {
        this.registrador = registrador;
        this.valor = valor;
    }

    public Par() {
        this.registrador = ' ';
        this.valor = 0;
    }

    public char getRegistrador() {
        return registrador;
    }

    public void setRegistrador(char registrador) {
        this.registrador = registrador;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Par(" + registrador + " " + valor + ')';
    }
}
