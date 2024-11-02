public class Node {
  private String dado;
  private Node prox;

  public Node() {
    this(null, null);
  }

  public Node(String dado, Node prox) {
    this.dado = dado;
    this.prox = prox;
  }

  public Node getProx() { 
    return prox; 
  }

  public String getDado() { 
    return dado; 
  }

  public void setProx(Node prox) { 
    this.prox = prox; 
  }

  public void setDado(String dado) { 
    this.dado = dado; 
  }

  @Override
  public String toString() {
      return "Node{" +
             "dado=" + dado +
             ", prox=" + (prox != null ? prox.getDado() : "null") +
             '}';
  }
}
