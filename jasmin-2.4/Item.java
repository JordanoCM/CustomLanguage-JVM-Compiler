import java.io.*;
import java.util.LinkedList;
public class Item {
   private char tipo; // pode ser 'i' (inteiro), 'd' (double), 'b' (boolean), 's' (string), 'c' (char), 'v' (variável) ou 'o' (operador) ou 'l' (Lista de comandos) 
   private String valor; // contém o valor do item
   private LinkedList<Comando> listaComandos = null;
   public Item(char _tipo, String _valor) {
      this.tipo = _tipo;
      this.valor = _valor;
   }
   public Item(char _tipo, LinkedList<Comando> _listaComandos) {
      this.tipo = _tipo;
      this.listaComandos = _listaComandos;
      this.valor = "Comando";
   }
   public char getTipo() {
      return this.tipo;
   }
   public String getvalor() {
      return this.valor;
   }
   public LinkedList<Comando> getLista(){
      return this.listaComandos;
   }
   public String toString() {
      return this.tipo + "-" + this.valor;
   }
}