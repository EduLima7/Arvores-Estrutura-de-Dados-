/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tree;

/**
 *
 * @author Edu
 */

public class RN {

     public static void main(String[] args) {
        arvoreRN RN = new arvoreRN();
        int resp = 0,resp2=0;
        Object resposta;
        while (resp==0) {
            String[] opcoes = {"Inserir", "Exibir"};
            resposta = JOptionPane.showInputDialog(null, "O que deseja fazer?", "Escolha", JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
            if (resposta == "Inserir") {
                RN.inserir(Integer.parseInt(JOptionPane.showInputDialog("Digite um valor inteiro para colocar na arvore")));
                resp2=JOptionPane.showConfirmDialog(null, "Deseja fazer outra inserção?", "", 0, 1);;
                while(resp2==0){
                    RN.inserir(Integer.parseInt(JOptionPane.showInputDialog("Digite um valor inteiro para colocar na arvore")));
                    resp2=JOptionPane.showConfirmDialog(null, "Deseja fazer outra inserção?", "", 0, 1);
                }
            }
            if (resposta == "Exibir") {
                RN.exibirarvore();
            }
            resp=JOptionPane.showConfirmDialog(null, "Deseja fazer algo a mais?", "", 0, 1);
        }
        System.out.println("Arvore final:");
        RN.exibirarvore();
    }
}
class arvoreRN {
    noRN raiz;
    int hesq=0,hdir=0,havl=0; // variaveis que representam a altura.
    int lesq=0,ldir=0; // variaveis que vao guardar o ultimo valor colocado ao lado esquerdo e direito da raiz.
    public arvoreRN() {
        this.raiz = null;
    }
    
    int alturaesq(noRN atual,int valor){ // achar a altura de um no a esquerda dele.
        int altura=0;
        if(raiz.dir==null || havl==0){
            havl++;
            altura++;
        }
        else{
            hdir=0;
        }
        atual=atual.esq;
        while(atual!=null){
            if (valor==atual.valor) {
                return altura;
            }
            else{
                if (valor<atual.valor) {
                    atual=atual.esq;
                    altura++;
                }
                else{
                    atual=atual.dir;
                    altura++;
                }
            }
        }
        return altura;
    }
    
    int alturadir(noRN atual,int valor){// achar a altura de um no a direita dele.
        int altura=0;
        if(raiz.esq==null || havl==0){
            havl++;
            altura++;
        }
        else{
            hesq=0;
        }
        atual=atual.dir;
        while(atual!=null){
            if (valor==atual.valor) {
                return altura;
            }
            else{
                if (valor<atual.valor) {
                    atual=atual.esq;
                    altura++;
                }
                else{
                    atual=atual.dir;
                    altura++;
                }
            }
        }
        return altura;
    }
    
    void balanceararvore(int valor) { // funcao que pegara o necessario para fazer o balançeamento.
        noRN filho=this.raiz;
        noRN tio=null;
        noRN pai=null;
        noRN avo=null;
        noRN bisavo=null;
        noRN tioavo=null;
        while(filho!=null && filho.valor!=valor){ //loop para chegar ate o valor que acabou de ser inserido
            bisavo=avo;
            avo=pai;
            pai=filho;
            if (filho.valor>valor) {
                filho=filho.esq;
            }
            else{
                filho=filho.dir;
            }
        } // a partir daqui os ifs irao ver qual é a forma de balanceamento a ser aplicada
        if(bisavo==null){ // caso especial
            System.out.println("esq "+alturaesq(avo,lesq));
            System.out.println("dir "+alturadir(avo,ldir));
            if (alturaesq(avo,lesq)>alturadir(avo,ldir)){
                if (pai.esq==null) {
                    rotacaodupladiresp(avo,pai,filho);
                }
                else{
                    rotacaodir(avo,pai,filho);
                }
            }
            else{
                if (pai.dir==null) {
                    rotacaoduplaesqesp(avo,pai,filho);
                }
                else{
                    rotacaoesq(avo,pai,filho); 
                }
            }
        }
        else{
            System.out.println("paiesq"+alturaesq(pai,lesq));
            System.out.println("paidir"+alturadir(pai,ldir));
            System.out.println("esq "+alturaesq(avo,lesq));
            System.out.println("dir "+alturadir(avo,ldir));
            if(alturaesq(bisavo,lesq)>alturadir(bisavo,ldir)){
                tio=avo.dir;
                tioavo=bisavo.dir;
                if (alturaesq(avo,lesq)>alturadir(avo,ldir)){
                    System.out.println("rotacaodir");
                    rotacaodir(avo,pai,filho);
                }
                else{
                    if (alturaesq(pai,lesq)<alturadir(pai,ldir)) {
                        System.out.println("rotacaoesq");
                        rotacaoesq(avo,pai,filho);
                    }else{
                        System.out.println("rotacaodupladir");
                        rotacaodupladir(bisavo,avo,pai,filho,tio,tioavo); 
                    }
                }
            }
            else{
                tio=avo.esq;
                tioavo=bisavo.esq;
                if (alturadir(avo,ldir)>alturaesq(avo,lesq)){
                    System.out.println("rotacaoesq");
                    rotacaoesq(avo,pai,filho);
                }
                else{
                    if (alturaesq(pai,lesq)>alturadir(pai,ldir)) {
                        System.out.println("rotacaodir");
                        rotacaodir(avo,pai,filho);
                    }else{
                        System.out.println("rotacaoduplaesq");
                        rotacaoduplaesq(bisavo,avo,pai,filho,tio,tioavo);
                    }
                }
            }
        }
    }
    // aqui vem as 4 rotaçoes de balanceamento possivel
    void rotacaodir(noRN avo,noRN pai,noRN filho){
        avo.dir=new noRN(avo.valor,null,null,avo.cor);
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaodupladiresp(noRN avo,noRN pai,noRN filho){
        avo.dir=new noRN(avo.valor,null,null,"R");
        avo.valor=filho.valor;
        pai.dir=null;
    }
    
    void rotacaodupladir(noRN bisavo,noRN avo,noRN pai,noRN filho,noRN tio,noRN tioavo){
        tioavo.dir=new noRN(tioavo.valor,null,null,tioavo.cor);
        tioavo.esq=new noRN(tio.valor,null,null,tio.cor);
        tioavo.valor=bisavo.valor;
        bisavo.valor=avo.valor;
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaoesq(noRN avo,noRN pai,noRN filho){
        avo.esq=new noRN(avo.valor,null,null,avo.cor);
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.dir=null;
    }
    
    void rotacaoduplaesqesp(noRN avo,noRN pai,noRN filho){
        avo.esq=new noRN(avo.valor,null,null,"R");
        avo.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaoduplaesq(noRN bisavo,noRN avo,noRN pai,noRN filho,noRN tio,noRN tioavo){
        tioavo.esq=new noRN(tioavo.valor,null,null,tioavo.cor);
        tioavo.dir=new noRN(tio.valor,null,null,tio.cor);
        tioavo.valor=bisavo.valor;
        bisavo.valor=avo.valor;
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.dir=null;
    }
    
    void inserir(int valor) { //funcao que insere o numero na arvore, ela tambem ja chama a funcao de balanceamento caso seja necessario
        noRN prev=null;
        noRN atual=this.raiz;
        if (this.raiz == null) {
            this.raiz = new noRN(valor,null,null,"N");
        } 
        else{
            if (valor>atual.valor) {
                while (atual!= null) {
                    if (valor>atual.valor) {
                        prev=atual;
                        atual=atual.dir;
                    }
                    else{
                        prev=atual;
                        atual=atual.esq;
                    }
                }
                if(atual!=null){
                    if (valor>atual.valor) {
                        atual.dir = new noRN(valor,null,null,"R");
                    }else{
                        atual.esq = new noRN(valor,null,null,"R");
                    }
                }
                else{
                    if (valor<prev.valor) {
                        prev.esq=new noRN(valor,null,null,"R");
                    }
                    else{
                        prev.dir=new noRN(valor,null,null,"R");
                    }
                }
                hdir=alturadir(this.raiz,valor);// me da o valor a direita a partir da raiz
                ldir=valor;
            }
            else{
                while (atual!= null) {
                    if (valor>atual.valor) {
                        prev=atual;
                        atual=atual.dir;
                    }
                    else{
                        prev=atual;
                        atual=atual.esq;
                    }
                }
                if(atual!=null){
                    if (valor>atual.valor) {
                        atual.dir = new noRN(valor,null,null,"R");
                    }else{
                        atual.esq = new noRN(valor,null,null,"R");
                    }
                }
                else{
                    if (valor<prev.valor) {
                        prev.esq=new noRN(valor,null,null,"R");
                    }
                    else{
                        prev.dir=new noRN(valor,null,null,"R");
                    }
                }    
                hesq=alturaesq(this.raiz,valor);// me da o valor a esquerda a partir da raiz
                lesq=valor;
            }
        }
        System.out.println(hdir-hesq);
        if ((hdir-hesq)==2 || (hdir-hesq)==-2) {// se isso for verdade(true) isso significa que a arvore precisa ser balanceada
            System.out.println(hdir-hesq);
            balanceararvore(valor);
            hesq=alturaesq(this.raiz,valor); // 
            hdir=alturadir(this.raiz,valor); // Recalcula o valor dos dois para dar a altura da arvore.
        }
        havl+=Math.max(hesq,hdir); //altura da arvore
    }
    noRN acharpai(int valor){
        noRN filho=this.raiz;
        noRN pai=null;
        while(filho!=null && filho.valor!=valor){ 
            pai=filho;
            if (filho.valor>valor) {
                filho=filho.esq;
            }
            else{
                filho=filho.dir;
            }
            if (filho!=null && filho.valor==valor){
                return pai;
            }
        }
        return null;
    }
   
    void exibirarvore() {
    	if (this.raiz == null) {
            System.out.println("Arvore vazia");
        }
                int hatual=2;
		String separator = String.valueOf("  |__");
		System.out.println(this.raiz.valor+"("+1+")"+raiz.cor);
		subarvore(raiz.esq,  separator,hatual);
		subarvore(raiz.dir, separator,hatual);
	}
	void subarvore(noRN no, String separator,int hatual) {
		if (no != null) {
			noRN pai = this.acharpai(no.valor);
			if (no.equals(pai.esq) == true) {
				System.out.println(separator+no.valor+"("+(hatual++)+")"+" (ESQ)"+no.cor);
			}else{
				System.out.println(separator+no.valor+"("+(hatual++)+")"+" (DIR)"+no.cor);
			}			
			subarvore(no.esq,  "     "+separator,hatual);
			subarvore(no.dir, "     "+separator,hatual);
		}
	}
}
 
class noRN { // classe do no da arvore (parecida com a da lista)

    int valor;
    noRN esq,dir;
    String cor;

    public noRN(int valor, noRN esq,noRN dir,String cor) {
        this.valor = valor;
        this.esq = esq;
        this.dir = dir;
        this.cor=cor;
    }
}
