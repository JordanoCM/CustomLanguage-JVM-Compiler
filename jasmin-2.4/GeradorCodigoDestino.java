import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
public class GeradorCodigoDestino {
      public static int referenciaDaComparacao = 1;
      public static boolean verificar = false;
      public static boolean ternario = false;
      public static void geraCodigoAssembler(LinkedList<Comando> listaComandos) {             
            BufferedWriter arqSaida;
            
            try {                 
                  arqSaida = new BufferedWriter(new FileWriter("prog_destino.j"));	
                  arqSaida.write(".source prog_destino.java\r\n");	
                  arqSaida.write(".class public prog_destino\r\n");
                  arqSaida.write(".super java/lang/Object\r\n");
                  arqSaida.write(".method public <init>()V\r\n");
                  arqSaida.write(".limit stack 1\r\n");
                  arqSaida.write(".limit locals 1\r\n");
                  arqSaida.write("aload_0\r\n");
                  arqSaida.write("invokespecial java/lang/Object/<init>()V\r\n");
                  arqSaida.write("return\r\n");
                  arqSaida.write(".end method\r\n");
                  arqSaida.write(".method public static main([Ljava/lang/String;)V\r\n");
                  arqSaida.write(".limit stack 1000\r\n");  
                  arqSaida.write(".limit locals 1000\r\n"); /* máximo de variáveis locais (deve ser calculado) */	
                  
	arqSaida.write(processaListaComandos(listaComandos)); /* Aqui que são obtidos os resultados!  */
		//System.out.println(processaListaComandos(listaComandos));
	
                  arqSaida.write("return\r\n");
                  arqSaida.write(".end method\r\n");
	
                  arqSaida.close();
	
                  
            }
            catch(IOException e) {
                  System.out.println("Problema no arquivo 'prog_destino.j'");
            }
            catch(Exception e) {
                  System.out.println(e.getMessage());
            }
      }
      static String processaListaComandos(LinkedList<Comando> listaComandos) {
                  String saida = "";
                  Comando com;
                  String nomeDaVariavel, codigoExpressao;
                  int referenciaDaVariavel;
                  for(int i = 0; i < listaComandos.size(); i++) {
                        com = (Comando)listaComandos.get(i);
                        nomeDaVariavel = (String)com.getRef1();
                        referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                        String tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                        if(verificar){
                              LinkedList<Item> condicaoini = (LinkedList<Item>)(com.getRef2());
                              if(condicaoini.get(0).getTipo() == 'i' || condicaoini.get(0).getTipo() == 'd'){
                                    tipoVariavel = "DOUBLE";
                              }
                              else if(condicaoini.get(0).getTipo() == 's' || condicaoini.get(0).getTipo() == 'c'){
                                    tipoVariavel = "STRING";
                              }
                              else if(condicaoini.get(0).getTipo() == 'b'){
                                    tipoVariavel = "BOOL";
                              }
                              else if(condicaoini.get(0).getTipo() == 'v'){
                                    nomeDaVariavel = condicaoini.get(0).getvalor();
                                    tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                              }
                              verificar = false;
                        }
                        // se o comando é uma atribuição
                        if(com.getTipo() == 'A') {
                              codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()), tipoVariavel);
                              saida += codigoExpressao;
                              if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")) saida += "astore " + referenciaDaVariavel + "\r\n";
                              else if(tipoVariavel.equals("BOOL")) saida += "istore " + referenciaDaVariavel + "\r\n";
                              else saida += "dstore " + referenciaDaVariavel + "\r\n";
                              if(ternario){
                                    if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")) saida += "aload " + referenciaDaVariavel + "\r\n";
                                    else if(tipoVariavel.equals("BOOL")) saida += "iload " + referenciaDaVariavel + "\r\n";
                                    else saida += "dload " + referenciaDaVariavel + "\r\n";
                                    ternario = false;
                              }
                        }
                        // se o comando é uma exibição
                        else if(com.getTipo() == 'E') {  
                                    int referenciaDaComparacaoE = referenciaDaComparacao;
                                    codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()), tipoVariavel);
                                    saida += codigoExpressao;
                                    if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")) saida += "astore " + referenciaDaVariavel + "\r\n";
                                    else if(tipoVariavel.equals("BOOL")) saida += "istore " + referenciaDaVariavel + "\r\n";
                                    else saida += "dstore " + referenciaDaVariavel + "\r\n";
                                    if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")){
                                          saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                                          saida += "aload " + referenciaDaVariavel + "\r\n";
                                          saida += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\r\n";
                                    }
                                    else if(tipoVariavel.equals("BOOL")){
                                          saida += "iload " + referenciaDaVariavel + "\r\n";
                                          saida += "ifne LPrintTrue"+referenciaDaComparacaoE+"\r\n";
                                          saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";  
                                          saida += "ldc \"false\"\r\n";
                                          saida += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\r\n";
                                          saida += "goto LEnd"+referenciaDaComparacaoE+"\r\n";
                                          saida += "LPrintTrue"+referenciaDaComparacaoE+":\r\n";
                                          saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                                          saida += "ldc \"true\"\r\n";
                                          saida += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\r\n";
                                          saida += "LEnd"+referenciaDaComparacaoE+":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else{
                                          saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                                          saida += "dload " + referenciaDaVariavel + "\r\n";
                                          saida += "invokevirtual java/io/PrintStream/println(D)V\r\n";
                                    } 
                              }
                        
                        //Se o comando é um if
                        else if(com.getTipo() == 'I'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              saida += processaListaComandos(condicao.get(0).getLista());
                              int referenciaDaComparacaoIF = referenciaDaComparacao;
                              saida += "iload " + referenciaDaVariavel + "\r\n";
                              saida += "ifne IFTrue"+referenciaDaComparacaoIF+"\r\n";
                              if(condicao.get(2).getLista().size() != 0) saida += processaListaComandos(condicao.get(2).getLista());
                              saida += "goto IFEnd"+referenciaDaComparacaoIF+"\r\n";
                              saida += "IFTrue"+referenciaDaComparacaoIF+":\r\n";
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "IFEnd"+referenciaDaComparacaoIF+":\r\n";
                              referenciaDaComparacao += 1;
                        }
                        //Se o comando é de switch case
                        else if(com.getTipo() == 'S'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              Comando tipoini = condicao.get(0).getLista().get(0);
                              LinkedList<Item> condicaoini = (LinkedList<Item>)(tipoini.getRef2());
                              int referenciaDaComparacaoSW = referenciaDaComparacao;
                              if(condicaoini.get(0).getTipo() == 'i' || condicaoini.get(0).getTipo() == 'd'){
                                    tipoVariavel = "DOUBLE";
                              }
                              else if(condicaoini.get(0).getTipo() == 's' || condicaoini.get(0).getTipo() == 'c'){
                                    tipoVariavel = "STRING";
                              }
                              else if(condicaoini.get(0).getTipo() == 'b'){
                                    tipoVariavel = "BOOL";
                              }
                              else if(condicaoini.get(0).getTipo() == 'v'){
                                    nomeDaVariavel = condicaoini.get(0).getvalor();
                                    tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                              }
                              verificar = true;
                              saida += processaListaComandos(condicao.get(0).getLista());
                              
                              int j=1;
                              int referenciaDaVariavel2 = 0;
                              String nomeDaVariavel2;
                              Item item = null;
                              if(condicao != null){
                                    for(int k=1; k<condicao.size(); k+=2){
                                          item = condicao.get(k);
                                          //System.out.println(k + " " + condicao.size());
                                          if(item.getTipo() == 'o' && item.getvalor().equals("Default")){
                                                saida += processaListaComandos(condicao.get(k+1).getLista());
                                          }
                                          else if(tipoVariavel.equals("DOUBLE") || tipoVariavel.equals("INT")|| tipoVariavel.equals("FLOAT")){
                                                saida += "dload " + referenciaDaVariavel + "\r\n";
                                                if (item.getTipo() == 'i') {
                                                      saida += "ldc2_w " + item.getvalor() + ".0\r\n"; // Carrega número para a pilha
                                                }
                                                else if(item.getTipo() == 'd'){
                                                      saida += "ldc2_w " + item.getvalor() + "\r\n"; // Carrega número para a pilha
                                                }
                                                else if (item.getTipo() == 'v') {
                                                      nomeDaVariavel2 = item.getvalor();
                                                      String tipoVariavel2 = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel2);
                                                      if(tipoVariavel2.equals("DOUBLE") == false && tipoVariavel2.equals("INT") ==  false && tipoVariavel2.equals("FLOAT") ==  false){
                                                            System.out.println("ERROR: incompatible types: " + tipoVariavel + " cannot be converted to " + tipoVariavel2);
                                                            System.exit(1);
                                                      }
                                                      referenciaDaVariavel2 = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel2);
                                                      saida += "dload " + referenciaDaVariavel2 + "\r\n"; // Carrega valor da variável para a pilha
                                                }
                                                saida += "dcmpg\r\n";
                                                saida += "ifne Case"+ j +"False" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += processaListaComandos(condicao.get(k+1).getLista());
                                                saida += "goto EndSwitch" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += "Case"+ j +"False" +  referenciaDaComparacaoSW + ":\r\n";

                                          }
                                          else if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")){
                                                saida += "aload " + referenciaDaVariavel + "\r\n";
                                                if (item.getTipo() == 's'|| item.getTipo() == 'c' ) {
                                                      saida += "ldc \"" + item.getvalor() + "\"\r\n"; // Carrega String para a pilha com aspas duplas
                                                }
                                                else if (item.getTipo() == 'v') {
                                                      nomeDaVariavel2 = item.getvalor();
                                                      String tipoVariavel2 = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel2);
                                                      if(tipoVariavel2.equals("CHAR") == false && tipoVariavel2.equals("STRING") ==  false){
                                                            System.out.println("ERROR: incompatible types: " + tipoVariavel + " cannot be converted to " + tipoVariavel2);
                                                            System.exit(1);
                                                      }
                                                      referenciaDaVariavel2 = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel2);
                                                      saida += "aload " + referenciaDaVariavel2 + "\r\n"; // Carrega valor da variável para a pilha 
                                                }
                                                saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                                saida += "ifne Case"+ j +"False" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += processaListaComandos(condicao.get(k+1).getLista());
                                                saida += "goto EndSwitch" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += "Case"+ j +"False" +  referenciaDaComparacaoSW + ":\r\n";

                                          }
                                          else if(tipoVariavel.equals("BOOL")){
                                                saida += "iload " + referenciaDaVariavel + "\r\n";
                                                if (item.getTipo() == 'b'){
                                                      if(item.getvalor().equals("true")) saida += "iconst_1\r\n";
                                                      else saida += "iconst_0\r\n";
                                                }
                                                else if (item.getTipo() == 'v') {
                                                      nomeDaVariavel2 = item.getvalor();
                                                      String tipoVariavel2 = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel2);
                                                      if(tipoVariavel2.equals("BOOL") == false){
                                                            System.out.println("ERROR: incompatible types: " + tipoVariavel + " cannot be converted to " + tipoVariavel2);
                                                            System.exit(1);
                                                      }
                                                      referenciaDaVariavel2 = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel2);
                                                      saida += "iload " + referenciaDaVariavel2 + "\r\n"; // Carrega valor da variável para a pilha 
                                                }
                                                saida += "if_icmpeq Case" + j + "False" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += processaListaComandos(condicao.get(k+1).getLista());
                                                saida += "goto EndSwitch" +  referenciaDaComparacaoSW + "\r\n";
                                                saida += "Case" + j + "False" +  referenciaDaComparacaoSW + ":\r\n";
                                          }
                                          j++;
                                    }
                                    saida += "EndSwitch" +  referenciaDaComparacaoSW + ":\r\n";
                                    referenciaDaComparacao += 1;
                              }


                        }
                        //Se o comando é de Operação ternária
                        else if(com.getTipo() == 'T'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              ternario = true;
                              saida += processaListaComandos(condicao.get(0).getLista());
                              int referenciaDaComparacaoTE = referenciaDaComparacao;
                              saida += "ifne TIFTrue"+referenciaDaComparacaoTE+"\r\n";
                              ternario = true;
                              saida += processaListaComandos(condicao.get(2).getLista());
                              saida += "goto TIFEnd"+referenciaDaComparacaoTE+"\r\n";
                              saida += "TIFTrue"+referenciaDaComparacaoTE+":\r\n";
                              ternario = true;
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "TIFEnd"+referenciaDaComparacaoTE+":\r\n";
                              if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")) saida += "astore " + referenciaDaVariavel + "\r\n";
                              else if(tipoVariavel.equals("BOOL")) saida += "istore " + referenciaDaVariavel + "\r\n";
                              else saida += "dstore " + referenciaDaVariavel + "\r\n";
                              referenciaDaComparacao += 1;
                        }
                        else if(com.getTipo() == 'W'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              int referenciaDaComparacaoW = referenciaDaComparacao;
                              saida += "Wloop"+referenciaDaComparacaoW+":\r\n";
                              saida += processaListaComandos(condicao.get(0).getLista());
                              saida += "iload " + referenciaDaVariavel + "\r\n";
                              saida += "ifne WTrue"+referenciaDaComparacaoW+"\r\n";
                              saida += "goto WEnd"+referenciaDaComparacaoW+"\r\n";
                              saida += "WTrue"+referenciaDaComparacaoW+":\r\n";
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "goto Wloop"+referenciaDaComparacaoW+"\r\n";
                              saida += "WEnd"+referenciaDaComparacaoW+":\r\n";
                              referenciaDaComparacao += 1;
                        }
                        else if(com.getTipo() == 'F'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              if(condicao.get(0).getLista().get(0) != null) saida += processaListaComandos(condicao.get(0).getLista());
                              int referenciaDaComparacaoF = referenciaDaComparacao;
                              saida += "Floop"+referenciaDaComparacaoF+":\r\n";
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "iload " + referenciaDaVariavel + "\r\n";
                              saida += "ifne FTrue"+referenciaDaComparacaoF+"\r\n";
                              saida += "goto FEnd"+referenciaDaComparacaoF+"\r\n";
                              saida += "FTrue"+referenciaDaComparacaoF+":\r\n";
                              saida += processaListaComandos(condicao.get(2).getLista());
                              if(condicao.get(2).getLista().size()>0) saida += processaListaComandos(condicao.get(3).getLista());
                              saida += "goto Floop"+referenciaDaComparacaoF+"\r\n";
                              saida += "FEnd"+referenciaDaComparacaoF+":\r\n";
                              referenciaDaComparacao += 1;
                        }
                        else if(com.getTipo() == 'D'){
                              LinkedList<Item> condicao = (LinkedList<Item>)(com.getRef2());
                              int referenciaDaComparacaoW = referenciaDaComparacao;
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "Wloop"+referenciaDaComparacaoW+":\r\n";
                              saida += processaListaComandos(condicao.get(0).getLista());
                              saida += "iload " + referenciaDaVariavel + "\r\n";
                              saida += "ifne WTrue"+referenciaDaComparacaoW+"\r\n";
                              saida += "goto WEnd"+referenciaDaComparacaoW+"\r\n";
                              saida += "WTrue"+referenciaDaComparacaoW+":\r\n";
                              saida += processaListaComandos(condicao.get(1).getLista());
                              saida += "goto Wloop"+referenciaDaComparacaoW+"\r\n";
                              saida += "WEnd"+referenciaDaComparacaoW+":\r\n";
                              referenciaDaComparacao += 1;
                        }
                  }
                  return saida;
            }
      static String geraCodigoExpressao(LinkedList<Item> listaExp, String type) {
            String saida = "";
            Item item;
            Item proximo;
            Item anterior;
            String nomeDaVariavel = "";
            int referenciaDaVariavel = 0;
            // Itera pela lista de operações (expressões em notação pós-fixa)
            if(type.equals("INT") || type.equals("FLOAT") || type.equals("DOUBLE")){
                  for (int i = 0; i < listaExp.size(); i++) {
                        item = listaExp.get(i);

                        // Se é um número (inteiro ou double)
                        if (item.getTipo() == 'i') {
                              saida += "ldc2_w " + item.getvalor() + ".0\r\n"; // Carrega número para a pilha
                        }
                        else if(item.getTipo() == 'd'){
                              saida += "ldc2_w " + item.getvalor() + "\r\n"; // Carrega número para a pilha
                        }
                        else if(item.getTipo() == 's' || item.getTipo() == 'c' || item.getTipo() == 'b'){
                              System.out.println("ERROR: incompatible types: " + type + " cannot be converted to " + item.getTipo());
                              System.exit(1);
                        }
                        // Se é um operador de adição, subtração, multiplicação ou divisão
                        else if (item.getTipo() == 'o') {
                              if (item.getvalor().equals("*")) {
                              saida += "dmul\r\n"; // Multiplicação
                              }
                              else if (item.getvalor().equals("/")) {
                              saida += "ddiv\r\n"; // Divisão
                              }
                              else if (item.getvalor().equals("+")) {
                              saida += "dadd\r\n"; // Adição
                              }
                              else if (item.getvalor().equals("-")) {
                              saida += "dsub\r\n"; // Subtração
                              }
                              else if (item.getvalor().equals("^")) {
                              saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Exponenciação
                              }
                              else if (item.getvalor().equals("~")){
                              saida += "ldc2_w -1.0\r\n";
                              saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Radiciacao
                              saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Radiciacao 
                              }
                              else{
                                    System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to "+type);
                                    System.exit(1);
                              }
                        }
                        // Se é uma variável
                        else if (item.getTipo() == 'v') {
                              nomeDaVariavel = item.getvalor();
                              String tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                              if(tipoVariavel.equals("DOUBLE") == false && tipoVariavel.equals("INT") ==  false && tipoVariavel.equals("FLOAT") ==  false){
                                    System.out.println("ERROR: incompatible types: " + type + " cannot be converted to " + CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel));
                                    System.exit(1);
                              }
                              referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                              saida += "dload " + referenciaDaVariavel + "\r\n"; // Carrega valor da variável para a pilha
                        }
                        else{
                              System.err.println("ERROR: command "+ item.getvalor() + "not defined");
                              System.exit(1);
                        }
                  }
            }
            else if(type.equals("STRING") || type.equals("CHAR")){
                  for (int i = 0; i < listaExp.size(); i++) {
                        item = listaExp.get(i);
                        if (item.getTipo() == 's'|| item.getTipo() == 'c' ) {
                              saida += "ldc \"" + item.getvalor() + "\"\r\n"; // Carrega String para a pilha com aspas duplas

                        }
                        else if(item.getTipo() == 'i' || item.getTipo() == 'd' || item.getTipo() == 'b'){
                              saida += "ldc \"" + item.getvalor() + "\"\r\n";
                        }
                        // Se é um operador de adição, subtração, multiplicação ou divisão
                        else if (item.getTipo() == 'o') {
                              if (item.getvalor().equals("+")) {
                                    saida += "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\r\n"; // Concatenação
                              }
                              else{
                                    System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to String, String or String, Char");
                                    System.exit(1);
                              }
                        }
                        // Se é uma variável
                        else if (item.getTipo() == 'v') {
                              nomeDaVariavel = item.getvalor();
                              String tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                              if(tipoVariavel.equals("CHAR") == false && tipoVariavel.equals("STRING") ==  false){
                                   if(tipoVariavel.equals("BOOL")){
                                          saida += "iload " + CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel) + "\r\n";
                                          saida += "ifne LPrintTrue"+referenciaDaComparacao+"\r\n";  
                                          saida += "ldc \"false\"\r\n";
                                          saida += "goto LEnd"+referenciaDaComparacao+"\r\n";
                                          saida += "LPrintTrue"+referenciaDaComparacao+":\r\n";
                                          saida += "ldc \"true\"\r\n";
                                          saida += "LEnd"+referenciaDaComparacao+":\r\n";
                                          referenciaDaComparacao += 1;
                                   }
                                   else{
                                          saida += "dload " + CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel) + "\r\n";
                                          if(tipoVariavel.equals("INT")){
                                                saida += "d2i\r\n";
                                                saida += "invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\r\n";
                                          }
                                          else if(tipoVariavel.equals("FLOAT")){
                                                saida += "d2f\r\n";
                                                saida += "invokestatic java/lang/String/valueOf(F)Ljava/lang/String;\r\n";
                                          }
                                          else{
                                                saida += "invokestatic java/lang/String/valueOf(D)Ljava/lang/String;\r\n";
                                          }
                                   }   
                              }
                              else{
                                    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                                    saida += "aload " + referenciaDaVariavel + "\r\n"; // Carrega valor da variável para a pilha
                              }
                        }
                        else{
                              System.err.println("ERROR: command "+ item.getvalor() + "not defined");
                              System.exit(1);
                        }
                  }
            }
            else if(type.equals("BOOL")){
                  String tipovar = null;
                  for (int i = 0; i < listaExp.size(); i++) {
                        item = listaExp.get(i);
                        if (item.getTipo() == 'i') {
                              tipovar = "INT";
                              saida += "ldc2_w " + item.getvalor() + ".0\r\n"; // Carrega número para a pilha
                        }
                        else if(item.getTipo() == 'd'){
                              tipovar = "DOUBLE";
                              saida += "ldc2_w " + item.getvalor() + "\r\n"; // Carrega número para a pilha
                              if((i+1) < listaExp.size()) proximo = listaExp.get(i+1);
                              else proximo = null;
                              if(i>0) anterior = listaExp.get(i-1);
                              else anterior = null;
                              if(proximo.getTipo() != 'i' && proximo.getTipo() != 'd' && proximo.getTipo() != 'o'){
                                    if(anterior != null){
                                          if(anterior.getTipo() != 'i' && anterior.getTipo() != 'd' && anterior.getTipo() != 'o'){
                                                System.out.println("ERROR: incompatible types: " + item.getTipo() + " cannot be converted to " + proximo.getTipo());
                                                System.exit(1);
                                          }
                                    }
                              }
                        }
                        else if (item.getTipo() == 's'|| item.getTipo() == 'c' ) {
                              if(item.getTipo() == 's') tipovar = "STRING";
                              else tipovar = "CHAR";
                              saida += "ldc \"" + item.getvalor() + "\"\r\n"; // Carrega String para a pilha com aspas duplas
                              if((i+1) < listaExp.size()) proximo = listaExp.get(i+1);
                              else proximo = null;
                              if(i>0) anterior = listaExp.get(i-1);
                              else anterior = null;
                              if(proximo.getTipo() != 's' && proximo.getTipo() != 'c' && proximo.getTipo() != 'o'){
                                    if(anterior != null){
                                          if(anterior.getTipo() != 's' && anterior.getTipo() != 'c' && anterior.getTipo() != 'o'){
                                                System.out.println("ERROR: incompatible types: " + item.getTipo() + " cannot be converted to " + proximo.getTipo());
                                                System.exit(1);
                                          }
                                    }
                              }
                        }
                        else if (item.getTipo() == 'b'){
                              tipovar = "BOOL";
                              if(item.getvalor().equals("true")) saida += "iconst_1\r\n";
                              else saida += "iconst_0\r\n";
                        }
                        else if (item.getTipo() == 'v'){
                              nomeDaVariavel = item.getvalor();
                              String tipoVariavel = CompiladorExemploIntermediarioTotal.tabela.getTipo(nomeDaVariavel);
                              String tipoProximo = null;
                              String tipoAnterior = null;
                              tipovar = tipoVariavel;
                              if((i+1) < listaExp.size()){
                                    proximo = listaExp.get(i+1);
                                    if(proximo.getTipo() == 'v')  tipoProximo = CompiladorExemploIntermediarioTotal.tabela.getTipo(proximo.getvalor());
                              }
                              if(i>0){
                                    anterior = listaExp.get(i-1);
                                    if(anterior.getTipo() == 'v') tipoAnterior = CompiladorExemploIntermediarioTotal.tabela.getTipo(anterior.getvalor());
                              }
                              if(tipoVariavel.equals("INT") || tipoVariavel.equals("FLOAT") || tipoVariavel.equals("DOUBLE")){
                                    if(tipoProximo != null){
                                          if(tipoProximo.equals("INT") == false && tipoProximo.equals("FLOAT") == false && tipoProximo.equals("DOUBLE") == false){
                                                if(tipoAnterior != null){
                                                      if(tipoAnterior.equals("INT") == false && tipoAnterior.equals("FLOAT") == false && tipoAnterior.equals("DOUBLE") == false){
                                                            System.out.println("ERROR: incompatible types: " + tipoVariavel + " cannot be converted to " + tipoAnterior);
                                                            System.exit(1);
                                                      }
                                                }
                                          }
                                    }
                                    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                                    saida += "dload " + referenciaDaVariavel + "\r\n"; // Carrega valor da variável para a pilha
                              }
                              else if(tipoVariavel.equals("STRING") || tipoVariavel.equals("CHAR")){
                                    if(tipoProximo != null){
                                          if(tipoProximo.equals("STRING") == false && tipoProximo.equals("CHAR") == false){
                                                if(tipoAnterior != null){
                                                      if(tipoAnterior.equals("STRING") == false && tipoAnterior.equals("CHAR") == false){
                                                            System.out.println("ERROR: incompatible types: " + tipoVariavel + " cannot be converted to " + tipoAnterior);
                                                            System.exit(1);
                                                      }
                                                }
                                          }
                                    }
                                    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                                    saida += "aload " + referenciaDaVariavel + "\r\n"; // Carrega valor da variável para a pilha
                              }
                              else{
                                    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                                    saida += "iload " + referenciaDaVariavel + "\r\n"; // Carrega valor da variável para a pilha
                              }
                        }
                        else if (item.getTipo() == 'o'){
                              if(item.getvalor().equals("and")){
                                    saida += "iand\r\n";
                              }
                              else if(item.getvalor().equals("andb")){
                                    saida += "iand\r\n";
                              }
                              else if(item.getvalor().equals("or")){
                                    saida += "ior\r\n";
                              }
                              else if(item.getvalor().equals("bor")){
                                    saida += "ior\r\n";
                              }
                              else if(item.getvalor().equals("xorb")){
                                    saida += "ixor\r\n";
                              }
                              else if(tipovar.equals("INT") || tipovar.equals("FLOAT") || tipovar.equals("DOUBLE")){
                                    if (item.getvalor().equals("*")) {
                                          saida += "dmul\r\n"; // Multiplicação
                                    }
                                    else if (item.getvalor().equals("/")) {
                                          saida += "ddiv\r\n"; // Divisão
                                    }
                                    else if (item.getvalor().equals("+")) {
                                          saida += "dadd\r\n"; // Adição
                                    }
                                    else if (item.getvalor().equals("-")) {
                                          saida += "dsub\r\n"; // Subtração
                                    }
                                    else if (item.getvalor().equals("^")) {
                                          saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Exponenciação
                                    }
                                    else if (item.getvalor().equals("~")){
                                          saida += "ldc2_w -1.0\r\n";
                                          saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Radiciacao
                                          saida += "invokestatic java/lang/Math/pow(DD)D\r\n"; // Radiciacao 
                                    }
                                    else if(item.getvalor().equals("<")){
                                          saida += "dcmpg\r\n";
                                          saida += "iflt LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals(">")){
                                          saida += "dcmpg\r\n";
                                          saida += "ifgt LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("<=")){
                                          saida += "dcmpg\r\n";
                                          saida += "ifle LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals(">=")){
                                          saida += "dcmpg\r\n";
                                          saida += "ifge LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("==")){
                                          saida += "dcmpg\r\n";
                                          saida += "ifeq LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("!=")){
                                          saida += "dcmpg\r\n";
                                          saida += "ifne LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else{
                                          System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to "+ tipovar);
                                          System.exit(1);
                                    }
                              }
                              else if(tipovar.equals("STRING") || tipovar.equals("CHAR")){
                                    if (item.getvalor().equals("+")) {
                                          saida += "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\r\n"; // Concatenação
                                    }
                                    else if(item.getvalor().equals("<")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "iflt LcmpTrue" + referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("<=")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "ifle LcmpTrue" + referenciaDaComparacao + "\r\n";  
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals(">")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "ifgt LcmpTrue" + referenciaDaComparacao + "\r\n";  
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals(">=")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "ifge LcmpTrue" + referenciaDaComparacao + "\r\n";  
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("==")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "ifeq LcmpTrue" + referenciaDaComparacao + "\r\n";  
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("!=")) {
                                          saida += "invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\r\n";
                                          saida += "ifne LcmpTrue" + referenciaDaComparacao + "\r\n";  
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" + referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" + referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" + referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else{
                                          System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to String, String or String, Char");
                                          System.exit(1);
                                    }
                              }
                              else if(tipovar.equals("BOOL") || listaExp.get(i-1).getTipo() == 'o'){
                                    if(item.getvalor().equals("==")){
                                          saida += "if_icmpeq LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else if(item.getvalor().equals("!=")){
                                          saida += "if_icmpne LcmpTrue" +  referenciaDaComparacao + "\r\n";
                                          saida += "iconst_0\r\n";
                                          saida += "goto LcmpStore" +  referenciaDaComparacao + "\r\n";
                                          saida += "LcmpTrue" +  referenciaDaComparacao + ":\r\n";
                                          saida += "iconst_1\r\n";
                                          saida += "LcmpStore" +  referenciaDaComparacao + ":\r\n";
                                          referenciaDaComparacao += 1;
                                    }
                                    else{
                                          System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to "+ tipovar);
                                          System.exit(1);
                                    }
                              }
                              else{
                                    System.err.println("ERROR: operator"+ item.getvalor() +" cannot be applied to "+type);
                                    System.exit(1);
                              }
                        }

                  }
            }
            return saida;
      }
}


/* 
Locais para buscar comandos opcode java / comandos bytecode interpretados pela JVM - São comandos de baixo nível/assembler:
https://javaalmanac.io/bytecode/
https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions
   */