import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static String path = "/Users/tatacangucu/";
  private static String ArquivoCarregado = null;
  public static void main(String[] args) throws IOException {

    // Cria uma lista encadeada que receberá cada linha do arquivo carregado pelo usuário com um nó.
    LinkedList listArquivo = new LinkedList();
    Scanner scanner = new Scanner(System.in);
    String comando = "";

    // enquanto comando != de "EXIT" continua recebendo comandos
    while (!comando.equalsIgnoreCase("EXIT")) {
      System.out.print("Insira um comando: ");
      comando = scanner.nextLine();

      // Comando LOAD <arquivo.ed1>
      if (comando.matches("(?i)LOAD\\s+\\S+")) { 
        String nomeArquivo = comando.split(" ")[1];

        // Verifica se já tem um arquivo aberto.
        if (ArquivoCarregado != null) {
          System.out.println("Aviso: Já existe um arquivo aberto: " + ArquivoCarregado);
        }
        else {
          try {
            // Procura o arquivo com o nome inserido
            File arquivo = new File(path+nomeArquivo);
            Scanner input = new Scanner(arquivo);
            
            // Insere as linhas do arquivo na lista encadeada listArquivo.
            while (input.hasNextLine()) {
              String linha = input.nextLine();
              listArquivo.addLast(linha);
            }

            // Se tiver sucesso mostra mensagem para o usuário.
            System.out.println("\n" + nomeArquivo + " carregado com sucesso. \n");
            ArquivoCarregado = nomeArquivo;
          }

          // Se não, mostra mensagem de erro.
          catch (FileNotFoundException fe) {
            System.out.println("Arquivo não encontrado. ");
          }
        }
      }

      // Comando LIST
      else if (comando.equalsIgnoreCase("LIST")) {
        // Verifica se um arquivo foi carregado. 
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 

        // Se sim imprime as linhas do arquivo.
        else {
          try {
            int count = 0;
            for (int i = 1; i <= listArquivo.getSize(); i++) {
              System.out.println(listArquivo.get(i).getDado());
              count++;

              // Se o arquivo tiver mais que 20 linhas imprime de 20 em 20 linhas.
              if (count % 20 == 0) {
                System.out.print("Pressione Enter para continuar...");
                scanner.nextLine();
              }
            }
            System.out.println();
          } 
          catch (NullPointerException e) {
            System.out.println();
          }
        }
      }

      // Comando RUN
      else if(comando.equalsIgnoreCase("RUN")){
        Run executar = new Run(listArquivo);
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 
        // Se já houver um arquivo carregado, programa executa o arquivo com a classe Run.
        else {
          executar.run();
        }
      }
     
      // Comando INS 
      else if (comando.split(" ")[0].equalsIgnoreCase("INS")) {
        //Verifica se um arquivo já foi carregado
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 

        //Se sim separa as palavras do comando inserido
        else {
            String[] partes = comando.split(" ", 2);
            String conteudo = partes[1];
            String[] palavras = conteudo.split(" ");

          // Verifica se uma número foi inserido como linha.
          if (palavras.length >= 3 && palavras.length < 5 && palavras[0].matches("\\d+")){

            // Verifica se o segundo argumento é um dos comandos disponíveis: mov/add/jnz etc.
            if(palavras[1].length()!= 3){
              System.out.println("Erro: comando INS inválido.\n");
            }

            // Se passar por todas as verificações insere a linha na lista encadeada.
            else{
              Node no = new Node();
              no.setDado(conteudo);
              listArquivo.insertOrder(no);
              System.out.println("linha inserida: \n" + conteudo + "\n");
            }
          }

          // Se não, mostra o erro para o usuário.
          else{
            System.out.println("Erro: comando INS inválido.\n");
          }
        }
      }

      // Comando DEL <linha> 
      else if (comando.matches("(?i)DEL\\s+\\d+")) {
        // Verifica se um arquivo já foi carregado.
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        }

        // Se sim, deleta a linha.
        else {
          // Extrai a linha do comando inserido
          String[] partes = comando.split("\\s+");
          String linhaASerDeletada = partes[1];
          System.out.println("Linha deletada: ");

          // Se a linha existir no arquivo, o programa a deleta.
          if (listArquivo.searchLinha(linhaASerDeletada)) {
            listArquivo.removeLinha(linhaASerDeletada);
            System.out.println();
          }

          // Se não mostra mensagem de erro.
          else{
            System.out.println("Erro: linha" + linhaASerDeletada + " não existe.");
          }
        }
      }

      // Comando DEL <linhaI> <linhaF>
      else if (comando.matches("(?i)DEL\\s+\\d+\\s+\\d+")) {
        // Verifica se um arquivo já foi carregado.
        if (ArquivoCarregado == null) {
            System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 
        // Se sim, deleta intervalo de linhas.
        else {
          // Extrai o inicio e o fim do intervalo.
          String[] partes = comando.split("\\s+");
          int posInicio = Integer.parseInt(partes[1]);
          int posFim = Integer.parseInt(partes[2]);
          System.out.println("\nLinhas deletadas: ");
          // Se o intervalo for encontrado na lista encadeada, é deletado.
          if (listArquivo.removeIntervalo(posInicio, posFim)) {
            System.out.println();
          } 
          // Se não, mostra mensagem de erro.
          else {
              System.out.println("Erro: Nenhuma linha encontrada no intervalo especificado.l\n");
          }
        }
      }

      // Comando SAVE
      else if (comando.equalsIgnoreCase("SAVE")) {
        // Verifica se um arquivo já foi carregado.
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 
        else {
          BufferedWriter writer = null;
          try {
              // Salva o arquivo com o nome do arquivo carregado
              File arquivo = new File("/Users/tatacangucu/" + ArquivoCarregado);
              writer = new BufferedWriter(new FileWriter(arquivo));
              Node pAnda = listArquivo.getHead(); 

              // Percorre a lista encadeada e escreve cada elemento
              while (pAnda != null) {
                  writer.write(pAnda.getDado());
                  writer.newLine(); 
                  pAnda = pAnda.getProx(); 
              }
              // Se tiver sucesso mostra mensagem para o usuário.
              System.out.println(ArquivoCarregado + " salvo com sucesso.\n");
          } 
          // Se não mostra mensagem de erro.
          catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage() + "\n");
          } 
          finally {
            if (writer != null) {
              try {writer.close();} 
              catch (IOException e) {
                System.out.println("Erro ao fechar o arquivo: " + e.getMessage() + "\n");
              }
            }
          }     
        }
      }

      // Comando SAVE <arquivo.ed1>
      else if (comando.matches("(?i)SAVE\\s+\\S+")){
        // Verifica se um arquivo já foi carregado.
        if (ArquivoCarregado == null) {
          System.out.println("Erro: Nenhum arquivo carregado.\n");
        } 
        else {
          BufferedWriter writer = null;
          String novoNome = comando.split(" ")[1];

          //Se o arquivo já existe, o programa pergunta se a pessoa deseja sobrescrever o arquivo existente.
          if (novoNome.matches(ArquivoCarregado)){
            System.out.println("Esse arquivo já exite. Deseja sobrescrever? (s/n)");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")){
              BufferedWriter writer2 = null;
              try {
                // Cria o arquivo com o nome do arquivo inserido
                File arquivo = new File(path + novoNome);
                writer2 = new BufferedWriter(new FileWriter(arquivo));
                Node pAnda = listArquivo.getHead(); 
                // Percorre a lista encadeada e escreve cada elemento
                while (pAnda != null) {
                    writer2.write(pAnda.getDado()); 
                    writer2.newLine();
                    pAnda = pAnda.getProx();
                }
                
                System.out.println(novoNome + " salvo com sucesso.\n");
              } 
              // Se não, mostra mensagem de erro.
              catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo, arquivo não salvo.\n");
              } 
              finally {
                if (writer2 != null) {
                  try {writer2.close();} 
                  catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo.\n");
                  }
                }
              } 
            }
          }
          // Se o arquivo não existir, o programa cria um novo arquivo com o nome inserido pelo usuário.
          else{
            try {
              File novoArquivo = new File(path + novoNome);
              writer = new BufferedWriter(new FileWriter(novoArquivo));
              Node pAnda = listArquivo.getHead();

              // Percorre a lista encadeada e escreve cada elemento
              while (pAnda != null) {
                writer.write(pAnda.getDado()); 
                writer.newLine(); 
                pAnda = pAnda.getProx(); 
              }
              // Se tiver sucesso mostra mensagem para o usuário.
              System.out.println(novoNome + " salvo com sucesso.\n");
            }
            // Se não, mostra mensagem de erro.
            catch (IOException e) {
              System.out.println("Erro ao salvar o arquivo, arquivo não salvo.\n");
            } 
            finally {
              if (writer != null) {
                try {writer.close();} 
                catch (IOException e) {
                  System.out.println("Erro ao fechar o arquivo.\n");
                }
              }
            }    
          }
        }
      }

      // Comando EXIT
      else if (comando.equalsIgnoreCase("EXIT")) {
        System.out.println("Programa encerrado.");
        break;
      }

      else {
        System.out.println("Erro. Comando inválido. \n");
      }
    }
    scanner.close();
  }
}
