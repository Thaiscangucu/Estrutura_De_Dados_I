public class Fila<T> {

  private static final int TAMANHO_PADRAO = 26;
  private T dados[];
  private int contagem;
  private int primeiro;
  private int ultimo;
  private Par parAtual;

  public Fila() {
    this(TAMANHO_PADRAO);
  }

  @SuppressWarnings("unchecked")
  public Fila(int tamanho) {
    dados = (T[]) new Object[tamanho];
    contagem = 0;
    primeiro = 0;
    ultimo = 0;
  }

  public void enqueue(T valor) {
    if (isFull()) {
      throw new RuntimeException("enqueue(): fila cheia.");
    }
    dados[ultimo] = valor;
    contagem++;
    ultimo = (ultimo + 1) % dados.length;
  }

  public boolean isFull() {
    return contagem == dados.length;
  }

  public T dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("dequeue(): a fila está vazia.");
    }
    T valor = dados[primeiro];
    primeiro = (primeiro + 1) % dados.length;
    contagem--;
    return valor;
  }

  public Par search(Fila<Par> registradores, char registrador) {
      Par resultado = null;
      int tamanhoFila = registradores.size(); 
      for (int i = 0; i < tamanhoFila; i++) {
          Par parAtual = registradores.dequeue();
          if (parAtual.getRegistrador() == registrador && resultado == null) {
              resultado = parAtual; 
          }
          registradores.enqueue(parAtual);
      }
      return resultado; 
  }
  
  public Par searchDequeue(Fila<Par> registradores, char registrador) {
    for (int i = 0; i < 26; i++) {
      parAtual = registradores.dequeue();
      if (parAtual.getRegistrador() == registrador) {
        return parAtual;
      } else {
        registradores.enqueue(parAtual);
      }
    }
    return null;
  }

  public boolean isEmpty() {
    return contagem == 0;
  }

  public T front() {
    if (isEmpty()) {
      throw new RuntimeException("front(): fila vazia");
    }
    return dados[primeiro];
  }

  public T back() {
    if (isEmpty()) {
      throw new RuntimeException("back(): fila vazia");
    }
    int indice = (dados.length + ultimo - 1) % dados.length;
    return dados[indice];
  }

  public int size() {
    return dados.length;
  }

  public int getCount() {
    return contagem;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Fila: [");

    for (int i = 0; i < contagem; i++) {
      int index = (primeiro + i) % dados.length;
      sb.append(dados[index]);

      if (i < contagem - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
