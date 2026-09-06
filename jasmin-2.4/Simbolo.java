public class Simbolo {
      private String nome;      // nome da variável
      private String tipo;
      private int referencia;   // referência usada na geração do código destino
      private static int marcador = 1; // armazena a última referência incluída na tabela
     
    public Simbolo(String _nome, String tipo) {
            this.nome = _nome;
            this.tipo = tipo;
            this.referencia = Simbolo.marcador;
            Simbolo.marcador += 2;
      }
      public String getNome() {
            return this.nome;
      }
      public int getReferencia() {
            return this.referencia;
      }
      public String getTipoV(){
            return this.tipo;
      }
      public String toString() {
            return "Nome:"+this.getNome() + " / " + "Referência:"+this.getReferencia() + "\n";
      }
}