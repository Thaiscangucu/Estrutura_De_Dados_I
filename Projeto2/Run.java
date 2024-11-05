// |--------------- Projeto Estrutura De Dados I ------------------|
// |-------------- Interpretador REPL de Assembly -----------------|

// |-- Nome: Thais Ferreira Canguçu  RA: 10403283 -----------------|
// |-- Nome: Pedro Moniz Canto       RA: 10426546 -----------------|

public class Run {
  private LinkedList listArquivo;
  private Fila <Par> registradores;
  private Par par;

  public Run(LinkedList listArquivo) {
    this.listArquivo = listArquivo;
     // Fila de registradores. 
    this.registradores = new Fila<>(26); 
    // Cria registradores de A-Z.
    for (int i = 0; i < 26; i++) {
      char letra = (char) ('A' + i);
      par = new Par(letra, 0);
      registradores.enqueue(par);
    }

  }


  public void run() {
    // Verifica se o arquivo está vazio
    if (listArquivo.isEmpty()) {
        System.out.println("Erro: arquivo vazio.\n");
        return;
    }
    // Se não estiver, percorre o arquivo linha por linha.
    Node pAnda = listArquivo.getHead();
    while (pAnda != null) {
        String linha = pAnda.getDado();
        // Verifica se a linha é em branco ou contém apenas espaços
        if (linha.trim().isEmpty()) {
            System.out.println("Erro: linha em branco encontrada.\n");
            pAnda = pAnda.getProx(); 
            continue; 
        }
        Node x = runLinha(linha);
        if (x != null){
          pAnda = x;
        }        
        pAnda = pAnda.getProx();
    }

}
  // Método que executa linha por linha.
  private Node runLinha(String linha) {
    // Separa a linha em vetor de nome partes e extrai a instrução e o parâmetro 1.
    String[] partes = linha.toUpperCase().split(" ");
    String instrucao = partes[1];
    char param1 = partes[2].charAt(0);

    // Instrução MOV
    if (instrucao.equalsIgnoreCase("mov")){
      
      // Verifica se a linha é válida.
      if(partes.length == 4){
        String param2 = partes[3];
        if(Character.isDigit(param1)){ 
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }

        // Verifica se o segundo parâmetro é um número ou um outro registrador.
        else{
          // Se for um número atribui o número ao registrador param1.
          if(param2.matches("-?\\d+(\\.\\d+)?")){

            par = registradores.searchDequeue(registradores, param1);
            par.setValor(Integer.parseInt(param2));
            registradores.enqueue(par);
          }
          // Se for um outro registrador, busca o seu valor e faz a atribuição corretamente.
          else{
            par = registradores.searchDequeue(registradores, param2.charAt(0));
            int x = par.getValor();
            registradores.enqueue(par);  
            par = registradores.searchDequeue(registradores, param1);
            par.setValor(x);
            registradores.enqueue(par);
          }

        }
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }   
      return null;
    }

    // Instrução INC
    else if (instrucao.equalsIgnoreCase("inc")) {
      // Verifica se a linha é válida.
      if(partes.length == 3){
        if(Character.isDigit(param1)){
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
        // Se a linha for válida, busca o registrador na fila e incrementa seu valor.
        else{
          par = registradores.searchDequeue(registradores, param1);
          int x = par.getValor();
          x += 1;
          par.setValor(x);
          registradores.enqueue(par);
        }
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      } 
      return null;
    }

    // Instrução DEC
    else if (instrucao.equalsIgnoreCase("dec")) {
      // Verifica se a linha é válida.
      if(partes.length == 3){
        if(Character.isDigit(param1)){
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
        // Se a linha for válida, busca o registrador na fila e decrementa seu valor.
        else{
          par = registradores.searchDequeue(registradores, param1);
          int x = par.getValor();
          x -= 1;
          par.setValor(x);
          registradores.enqueue(par);
        }
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      } 
      return null;
    }

    // Instrução ADD
    else if (instrucao.equalsIgnoreCase("add")) {
      
      // Verifica se a linha é válida.
      if(partes.length == 4){
        String param2 = partes[3];
        if(Character.isDigit(param1)){ 
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
        // Verifica se o segundo parâmetro é um número ou um outro registrador.
        else{
          // Se o segundo parametro for um número realiza a soma e armazena no primeiro registrador.
          if(param2.matches("-?\\d+")){
            int valorParam2 = Integer.parseInt(param2);
            par = registradores.searchDequeue(registradores, param1);
            int soma = par.getValor() + valorParam2; 
            par.setValor(soma);
            registradores.enqueue(par);

          }
          // Se o segundo parametro for um segundo registrdor, busca o seu valor na fila, realiza a soma e armazena no primeiro registrador.
          else{
            par = registradores.searchDequeue(registradores, param2.charAt(0));
            int valor1 = par.getValor();
            registradores.enqueue(par);  
            par = registradores.searchDequeue(registradores, param1);
            int valor2 = par.getValor();
            int soma = valor1 + valor2;
            par.setValor(soma);
            registradores.enqueue(par);
          }
        }    
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }
      return null;
    }

    // Instrução SUB
    else if (instrucao.equalsIgnoreCase("sub")) {

      // Verifica se a linha é válida.
      if(partes.length == 4){
        String param2 = partes[3];
        if(Character.isDigit(param1)){ 
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
          
        // Verifica se o segundo parâmetro é um número ou um outro registrador.
        else{
          // Se o segundo parâmetro for um número realiza a subtração e armazena no primeiro registrador.
          if(param2.matches("-?\\d+")){
            int valorParam2 = Integer.parseInt(param2);
            par = registradores.searchDequeue(registradores, param1);
            int sub = par.getValor() - valorParam2; 
            par.setValor(sub);
            registradores.enqueue(par);
          }
            
          // Se o segundo parametro for um segundo registrdor, busca o valor dele na fila, realiza a subtração e armazena no primeiro registrador.
          else{
            par = registradores.searchDequeue(registradores, param2.charAt(0));
            int valor1 = par.getValor();
            registradores.enqueue(par);  
            par = registradores.searchDequeue(registradores, param1);
            int valor2 = par.getValor();
            int sub = valor2 - valor1;
            par.setValor(sub);
            registradores.enqueue(par);
          }
        }    
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }
      return null;
    }

    // Instrução MUL
    else if (instrucao.equalsIgnoreCase("mul")) {
      
      // Verifica se a linha é válida.
      if(partes.length == 4){
        String param2 = partes[3];
        if(Character.isDigit(param1)){ 
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
          
        // Verifica se o segundo parâmetro é um número ou um outro registrador.
        else{
          
          // Se o segundo parâmetro for um inteiro realiza a multiplicação e armazena o resultado no primeiro registrador.
          if(param2.matches("-?\\d+")){
            int valorParam2 = Integer.parseInt(param2);
            par = registradores.searchDequeue(registradores, param1);
            int mul = par.getValor() * valorParam2; 
            par.setValor(mul);
            registradores.enqueue(par);
          }
            
          // Se o segundo parâmetro for um segundo registrdor, busca o valor dele na fila, realiza a multiplicação e armazena o resultado no primeiro registrador.
          else{
            par = registradores.searchDequeue(registradores, param2.charAt(0));
            int valor1 = par.getValor();
            registradores.enqueue(par);  
            par = registradores.searchDequeue(registradores, param1);
            int valor2 = par.getValor();
            int mul = valor1 * valor2;
            par.setValor(mul);
            registradores.enqueue(par);
          }
        }    
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }
      return null;
    }
      
    // Instrução DIV
    else if (instrucao.equalsIgnoreCase("div")) {
      // Verifica se a linha é válida.
      if(partes.length == 4){
        String param2 = partes[3];
        if(Character.isDigit(param1)){ 
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }

          // Verifica se o segundo parâmetro é um número ou um outro registrador.
        else{
          // Se o segundo parâmetro for um inteiro realiza a divisão e armazena o resultado no primeiro registrador.
          if(param2.matches("-?\\d+")){
            int valorParam2 = Integer.parseInt(param2);
            par = registradores.searchDequeue(registradores, param1);
            int div = par.getValor() / valorParam2; 
            par.setValor(div);
            registradores.enqueue(par);
          }
            
          // Se o segundo parâmetro for um segundo registrdor, busca o valor dele na fila, realiza a divisão e armazena o resultado no primeiro registrador.
          else{
            par = registradores.searchDequeue(registradores, param2.charAt(0));
            int valor1 = par.getValor();
            registradores.enqueue(par);  
            par = registradores.searchDequeue(registradores, param1);
            int valor2 = par.getValor();
            int div = valor1 / valor2;
            par.setValor(div);
            registradores.enqueue(par);
          }
        }  

      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }
      return null;
    } 

    // Instrução OUT
    else if (instrucao.equalsIgnoreCase("out")){
      
      // Verifica se a linha é válida.
      if(partes.length == 3){
        if(Character.isDigit(param1)){
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
          
        // Se a linha for válida, busca o registrador na fila e imprime seu valor
        else{
          par = registradores.searchDequeue(registradores, param1);
          int x = par.getValor();
          System.out.println(x);
          registradores.enqueue(par);
          System.out.println();
        }
      }
      else{
        System.out.println("Erro. Linha inválida: " + linha + "\n");
      }
      return null;
    }
      
    // Instrução JNZ
    else if (instrucao.equalsIgnoreCase("jnz")) {
      
      if(partes.length == 4){
        String param2 = partes[3];
        if(param2.matches("-?\\d+")){
          if (registradores.search(registradores, param1).getValor() != 0){   
            Node resultado = jnz(param2);
            if(resultado == null){
              System.out.println("Erro: Linha '" + param2 + "' inexistente.\n");
            }
            else{
              linha = String.valueOf(resultado.getProx().getDado().split(" ")[0]);
              return resultado;
            }
          }
        }
        else{
          System.out.println("Erro. Linha inválida: " + linha + "\n");
        }
      }
      else{
        System.out.println("Erro: Instrução desconhecida " + instrucao + "\n");   
      }
    }
    return null;
  }

  // Método que busca um registrador na fila. Se encontrar, retorna o par anterior senão retorna null.
  private Node jnz(String param2){
    Node pAnda = listArquivo.getHead();
    Node pAnt = null;
    while (pAnda != null) {
        String linha = pAnda.getDado().split("\\s+")[0];
        if (linha.equals(param2)) {
          return pAnt;
        } 
        pAnt = pAnda;
        pAnda = pAnda.getProx(); 
    }
    return null;
  }

}
