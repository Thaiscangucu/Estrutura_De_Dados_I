// |--------------- Projeto Estrutura De Dados I ------------------|
// |-------------- Interpretador REPL de Assembly -----------------|

// |-- Nome: Thais Ferreira Canguçu  RA: 10403283 -----------------|
// |-- Nome: Pedro Moniz Canto       RA: 10426546 -----------------|

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {        
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
                        File arquivo = new File(nomeArquivo);
                        if (!arquivo.isAbsolute()) {
                            arquivo = arquivo.getAbsoluteFile();
                        }
                        // Verifica se o arquivo existe
                        if (!arquivo.exists()) {
                            throw new FileNotFoundException();
                        }
                        Scanner input = new Scanner(arquivo);

                        // Insere as linhas do arquivo na lista encadeada listArquivo.
                        while (input.hasNextLine()) {
                            String linha = input.nextLine();
                            listArquivo.addLast(linha);
                        }

                        // Se tiver sucesso mostra mensagem para o usuário.
                        System.out.println("\n" + arquivo.getAbsolutePath() + " carregado com sucesso. \n");
                        ArquivoCarregado = arquivo.getAbsolutePath();
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
                    if (listArquivo.isEmpty()) {
                        System.out.println("Arquivo vazio.\n");
                    }
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

                        // Verifica se o primeiro argumento é um registrador ou um número.
                        else if(palavras[2].matches("\\d+")){
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
                        File arquivo = new File(ArquivoCarregado);
                        if (!arquivo.isAbsolute()) {
                            arquivo = arquivo.getAbsoluteFile();
                        }
                        writer = new BufferedWriter(new FileWriter(arquivo));
                        Node pAnda = listArquivo.getHead(); 

                        // Percorre a lista encadeada e escreve cada elemento
                        while (pAnda != null) {
                            writer.write(pAnda.getDado());
                            writer.newLine(); 
                            pAnda = pAnda.getProx(); 
                        }
                        // Se tiver sucesso mostra mensagem para o usuário.
                        System.out.println(arquivo.getAbsolutePath() + " salvo com sucesso.\n");
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
            else if (comando.matches("(?i)SAVE\\s+\\S+")) {
                // Verifica se um arquivo já foi carregado.
                if (ArquivoCarregado == null) {
                    System.out.println("Erro: Nenhum arquivo carregado.\n");
                } 
                else {
                    BufferedWriter writer = null;
                    String novoNome = comando.split(" ")[1];

                    // Trata o nome do arquivo como um caminho absoluto
                    File arquivo = new File(novoNome);
                    if (!arquivo.isAbsolute()) {
                        arquivo = arquivo.getAbsoluteFile();
                    }

                    // Verifica se o arquivo já existe e pergunta ao usuário se deseja sobrescrever
                    if (arquivo.getAbsolutePath().equals(ArquivoCarregado)) {
                        System.out.println("Esse arquivo já existe. Deseja sobrescrever? (s/n)");
                        String resposta = scanner.nextLine();
                        if (resposta.equalsIgnoreCase("s")) {
                            try {
                                writer = new BufferedWriter(new FileWriter(arquivo));
                                Node pAnda = listArquivo.getHead();

                                // Percorre a lista encadeada e escreve cada elemento
                                while (pAnda != null) {
                                    writer.write(pAnda.getDado());
                                    writer.newLine();
                                    pAnda = pAnda.getProx();
                                }
                                System.out.println(arquivo.getAbsolutePath() + " salvo com sucesso.\n");
                            } 
                            catch (IOException e) {
                                System.out.println("Erro ao salvar o arquivo: " + e.getMessage() + "\n");
                            } 
                            finally {
                                if (writer != null) {
                                    try {
                                        writer.close();
                                    } 
                                    catch (IOException e) {
                                        System.out.println("Erro ao fechar o arquivo.\n");
                                    }
                                }
                            }
                        }
                    } 
                    else {
                        // Se o arquivo não existir, cria um novo arquivo com o nome inserido
                        try {
                            writer = new BufferedWriter(new FileWriter(arquivo));
                            Node pAnda = listArquivo.getHead();

                            // Percorre a lista encadeada e escreve cada elemento
                            while (pAnda != null) {
                                writer.write(pAnda.getDado());
                                writer.newLine();
                                pAnda = pAnda.getProx();
                            }
                            System.out.println(arquivo.getAbsolutePath() + " salvo com sucesso.\n");
                        } 
                        catch (IOException e) {
                            System.out.println("Erro ao salvar o arquivo: " + e.getMessage() + "\n");
                        } 
                        finally {
                            if (writer != null) {
                                try {
                                    writer.close();
                                } 
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

// Referências:
// ORACLE. Interface Queue (Java SE 8). Disponível em: <https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html>. Acesso em: 3 nov. 2024.
// ORACLE. Class LinkedList (Java SE 8). Disponível em: <https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html>. Acesso em: 4 nov. 2024.
// ORACLE. Interface Node (W3C DOM Level 3 Core Specification). Disponível em: <https://docs.oracle.com/javase/8/docs/api/org/w3c/dom/Node.html>. Acesso em: 5 nov. 2024.
// LENOVO. What is REPL?. Disponível em: <https://www.lenovo.com/us/en/glossary/repl/>. Acesso em: 3 nov. 2024.
// DIGITALOCEAN. What is REPL?. Disponível em: <https://www.digitalocean.com/community/tutorials/what-is-repl>. Acesso em: 4 nov. 2024.
// W3SCHOOLS. Java Files - Reading Files in Java. Disponível em: <https://www.w3schools.com/java/java_files_read.asp>. Acesso em: 5 nov. 2024.
// ACM DIGITAL LIBRARY. Assembly Language For Students. Disponível em: <https://dl.acm.org/doi/book/10.5555/3125846>. Acesso em: 6 nov. 2024.
// MISSOURI STATE UNIVERSITY. MARS MIPS Simulator. Disponível em: <https://computerscience.missouristate.edu/mars-mips-simulator.htm>. Acesso em: 4 nov. 2024.
// UFSM. MARS: IDE para programação em Assembly. Disponível em: <https://www.ufsm.br/pet/sistemas-de-informacao/2018/09/01/mars-ide-para-programacao-em-assembl>. Acesso em: 5 nov. 2024.
// SANDERSON, D. MARS (MIPS Assembler and Runtime Simulator). Disponível em: <https://dpetersanderson.github.io/>. Acesso em: 6 nov. 2024.
// PUGA, S.; RISSETTI, G. Lógica de programação e estrutura de dados: com aplicações em Java. 2ª ed. São Paulo: Pearson, 2010.
// ASCENCIO, A. F. G.; ARAÚJO, G. S. Estrutura de dados: algoritmos, análise da complexidade e implementações em Java e C/C++. São Paulo: Pearson Education do Brasil, 2011.
// ZIVIANI, N. Projeto de Algoritmos: Com Implementações em Java e C++. São Paulo: Cengage Learning, 2011.
