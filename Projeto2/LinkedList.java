// |--------------- Projeto Estrutura De Dados I ------------------|
// |-------------- Interpretador REPL de Assembly -----------------|

// |-- Nome: Thais Ferreira Canguçu  RA: 10403283 -----------------|
// |-- Nome: Pedro Moniz Canto       RA: 10426546 -----------------|

public class LinkedList {
    private Node head;
    private int size; 

    public LinkedList() {
        head = null;
        size = 0;
    }
    public boolean isEmpty() {
        return getHead() == null;
    }

    public boolean isFull() {
        Node aux = new Node();
        return aux == null;
    }

    public int getSize() {
        return size;
    }

    public Node getHead() {
        return head;
    }

    public Node get(int pos) {
        if (isEmpty() || pos <= 0 || pos > size)
            return null;
        int cont = 1;
        Node pAnda = head;
        while (cont != pos) {
            pAnda = pAnda.getProx();
            cont++;
        }
        return pAnda;
    }

    public boolean insert(String id, int pos) {
        if (pos <= 0)
            return false;

        if (!isFull()) {
            Node aux = new Node(id, null);
            if (isEmpty()) {
                head = aux;
            } else if (pos >= size + 1) {
                insertTail(id);
            } else {
                int cont = 1;
                Node pAnda = head;
                Node pAnt = null;
                while (pAnda.getProx() != null && cont != pos) {
                    pAnt = pAnda;
                    pAnda = pAnda.getProx();
                    cont++;
                }
                aux.setProx(pAnda);
                if (cont == 1) {
                    head = aux;
                } else {
                    pAnt.setProx(aux);
                }
            }
            size++;
            return true;
        }
        return false;
    }

    // Método que insere de acordo com a posição da linha do arquivo.
    public boolean insertOrder(Node id) {
        String linha = id.getDado();
        String posLinha = linha.split(" ")[0];

        Node pAnda = head;
        Node pAnt = null;

        while (pAnda != null) {
            String pAndaLinha = pAnda.getDado().split("\\s+")[0];
            int comparison = posLinha.compareTo(pAndaLinha);

            if (comparison < 0) {
                if (pAnt == null) {
                    id.setProx(head);
                    head = id;
                } else {
                    pAnt.setProx(id);
                    id.setProx(pAnda);
                }
                size++;
                return true;
            } 
            else if (comparison == 0) {
                pAnda.setDado(linha);
                return true;
            } 
            else {
                pAnt = pAnda;
                pAnda = pAnda.getProx();
            }
        }
        if (pAnt != null) {
            pAnt.setProx(id);
            id.setProx(null);
        } 
        else {
            head = id;
            id.setProx(null); 
        }
        size++;
        return true;
    }

    public boolean addFirst(String id) {
        if (!isFull()) {
            Node aux = new Node(id, null);
            if (isEmpty()) {
                head = aux;
            } else {
                aux.setProx(head);
                head = aux;
            }
            size++;
            return true;
        }
        return false;
    }

    public boolean insertTail(String id) {
        if (!isFull()) {
            Node aux = new Node(id, null);
            if (isEmpty()) {
                head = aux;
            } else {
                Node pAnda = head;
                while (pAnda.getProx() != null)
                    pAnda = pAnda.getProx();
                pAnda.setProx(aux);
            }
            size++;
            return true;
        }
        return false;
    }

    public boolean addLast(String id) {
        return insertTail(id);
    }

    public boolean search(int id) {
        if (isEmpty())
            return false;
        Node pAnda = head;
        while (pAnda != null && !pAnda.getDado().equals(id))
            pAnda = pAnda.getProx();
        return true;
    }

    // Método que procura uma linha específica do arquivo.
    public boolean searchLinha(String linhaASerProcurada) {
        if (isEmpty()) {
            return false;
        }
        Node pAnda = head;
        while (pAnda != null) {
            String linha = pAnda.getDado().split("\\s+")[0];
            if (linha.equals(linhaASerProcurada)) {
                return true;
            }
            pAnda = pAnda.getProx(); 
        }
        return false;
  }

    public boolean remove(int id) {
        if (isEmpty())
            return false;

        Node pAnda = head, pAnt = null;
        while (pAnda != null && !pAnda.getDado().equals(id)) {
            pAnt = pAnda;
            pAnda = pAnda.getProx();
        }

        if (pAnda == null)
            return false;

        if (head == pAnda) {
            head = pAnda.getProx();
        } else {
            pAnt.setProx(pAnda.getProx());
        }
        size--;
        return true;
    }

    // Método que remove uma linha específica do arquivo.
    public boolean removeLinha(String linhaASerProcurada) {
        if (isEmpty()) {
            return false; 
        }
        Node pAnda = head;
        Node pAnt = null;
        while (pAnda != null) {  
            String linha = pAnda.getDado();
            String posLinha = pAnda.getDado().split("\\s+")[0];
            if (posLinha.equals(linhaASerProcurada)) {
                System.out.println(linha);
                if (pAnt == null) {
                    head = pAnda.getProx(); 
                } 
                else {
                    pAnt.setProx(pAnda.getProx());
                }
                return true;
            }
            pAnt = pAnda;
            pAnda = pAnda.getProx();
        }
        return false;
  }

    // Método que remove um intervalo de linhas do arquivo.
    public boolean removeIntervalo(int inicio, int fim) {
        if (isEmpty() || inicio > fim || inicio < 0) {
            return false; 
        }
        Node pAnda = head;
        boolean linhaEncontrada = false;

        while (pAnda != null){
            String linha = pAnda.getDado();
            String posLinhaStr = linha.split("\\s+")[0];
            int posLinha = Integer.parseInt(posLinhaStr);

            if (posLinha >= inicio && posLinha <= fim) {
                linhaEncontrada = true;
                break; 
            }
            pAnda = pAnda.getProx();
        }
        if (!linhaEncontrada) {
            return false;
        }
        pAnda = head;
        Node pAnt = null;

        while (pAnda != null) {
            String linha = pAnda.getDado();
            String posLinhaStr = linha.split("\\s+")[0]; 
            int posLinha = Integer.parseInt(posLinhaStr);

            if (posLinha >= inicio && posLinha <= fim) {
                System.out.println(linha);
                if (pAnt == null) {
                    head = pAnda.getProx();
                } 
                else {
                    pAnt.setProx(pAnda.getProx());
                }
                pAnda = pAnt != null ? pAnt.getProx() : head;
            } else {
                pAnt = pAnda;
                pAnda = pAnda.getProx();
            }
        }
        return true;
    }

    public String pollFirst() {
        if (isEmpty())
            return null;
        Node pAux = head;
        head = head.getProx();
        size--;
        return pAux.getDado();
    }

    public String pollLast() {
        if (isEmpty())
            return null;
        Node pAnda = head, pAnt = null;
        while (pAnda.getProx() != null) {
            pAnt = pAnda;
            pAnda = pAnda.getProx();
        }
        if (pAnt != null) {
            pAnt.setProx(null);
        } else {
            head = null;
        }
        size--;
        return pAnda.getDado();
    }

    public void print() {
        Node pAnda = head;
        while (pAnda != null) {
            System.out.println(pAnda.getDado());
            pAnda = pAnda.getProx();
        }
    }

    public void clear() {
        Node pAnda = head;
        while (pAnda != null) {
            Node pAux = pAnda;
            pAnda = pAnda.getProx();
            pAux.setProx(null);
        }
        head = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int qtde = 0;
        sb.append("\n[Lista]\n");
        sb.append("L: [ ");
        Node pAnda = head;
        while (pAnda != null) {
            sb.append(pAnda.getDado()).append(" ");
            qtde++;
            pAnda = pAnda.getProx();
        }
        sb.append("]\n");
        sb.append("Qtde.: ").append(qtde);
        sb.append("\n");
        return sb.toString();
    }
}
