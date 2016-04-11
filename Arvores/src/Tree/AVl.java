/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tree;

import javax.swing.JOptionPane;

/**
 *
 * @author Derian
 * dir=direita
 * esq=esquerda
 * h=altura
 */
public class AVl {
      public static void main(String[] args) {
        arvoreavl avl = new arvoreavl();
        int resp = 0,resp2=0;
        Object resposta;
        while (resp==0) {
            String[] opcoes = {"Inserir", "Exibir"};
            resposta = JOptionPane.showInputDialog(null, "O que deseja fazer?", "Escolha", JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
            if (resposta == "Inserir") {
                avl.inserir(Integer.parseInt(JOptionPane.showInputDialog("Digite um valor inteiro para colocar na arvore")));
                resp2=JOptionPane.showConfirmDialog(null, "Deseja fazer outra inserção?", "", 0, 1);;
                while(resp2==0){
                    avl.inserir(Integer.parseInt(JOptionPane.showInputDialog("Digite um valor inteiro para colocar na arvore")));
                    resp2=JOptionPane.showConfirmDialog(null, "Deseja fazer outra inserção?", "", 0, 1);
                }
            }
            if (resposta == "Exibir") {
                avl.exibirarvore();
            }
            resp=JOptionPane.showConfirmDialog(null, "Deseja fazer algo a mais?", "", 0, 1);
        }
        System.out.println("Arvore final:");
        avl.exibirarvore();
    }
}
class arvoreavl {
    noavl raiz;
    int hesq=0,hdir=0,havl=0; // variaveis que representam a altura.
    int lesq=0,ldir=0; // variaveis que vao guardar o ultimo valor colocado ao lado esquerdo e direito da raiz.
    public arvoreavl() {
        this.raiz = null;
    }
    
    int alturaesq(noavl atual,int valor){ // achar a altura de um no a esquerda dele.
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
    
    int alturadir(noavl atual,int valor){// achar a altura de um no a direita dele.
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
        noavl filho=this.raiz;
        noavl tio=null;
        noavl pai=null;
        noavl avo=null;
        noavl bisavo=null;
        noavl tioavo=null;
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
    void rotacaodir(noavl avo,noavl pai,noavl filho){
        avo.dir=new noavl(avo.valor,null,null);
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaodupladiresp(noavl avo,noavl pai,noavl filho){
        avo.dir=new noavl(avo.valor,null,null);
        avo.valor=filho.valor;
        pai.dir=null;
    }
    
    void rotacaodupladir(noavl bisavo,noavl avo,noavl pai,noavl filho,noavl tio,noavl tioavo){
        tioavo.dir=new noavl(tioavo.valor,null,null);
        tioavo.esq=new noavl(tio.valor,null,null);
        tioavo.valor=bisavo.valor;
        bisavo.valor=avo.valor;
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaoesq(noavl avo,noavl pai,noavl filho){
        avo.esq=new noavl(avo.valor,null,null);
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.dir=null;
    }
    
    void rotacaoduplaesqesp(noavl avo,noavl pai,noavl filho){
        avo.esq=new noavl(avo.valor,null,null);
        avo.valor=filho.valor;
        pai.esq=null;
    }
    
    void rotacaoduplaesq(noavl bisavo,noavl avo,noavl pai,noavl filho,noavl tio,noavl tioavo){
        tioavo.esq=new noavl(tioavo.valor,null,null);
        tioavo.dir=new noavl(tio.valor,null,null);
        tioavo.valor=bisavo.valor;
        bisavo.valor=avo.valor;
        avo.valor=pai.valor;
        pai.valor=filho.valor;
        pai.dir=null;
    }
    
    void inserir(int valor) { //funcao que insere o numero na arvore, ela tambem ja chama a funcao de balanceamento caso seja necessario
        noavl prev=null;
        noavl atual=this.raiz;
        if (this.raiz == null) {
            this.raiz = new noavl(valor,null,null);
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
                        atual.dir = new noavl(valor,null,null);
                    }else{
                        atual.esq = new noavl(valor,null,null);
                    }
                }
                else{
                    if (valor<prev.valor) {
                        prev.esq=new noavl(valor,null,null);
                    }
                    else{
                        prev.dir=new noavl(valor,null,null);
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
                        atual.dir = new noavl(valor,null,null);
                    }else{
                        atual.esq = new noavl(valor,null,null);
                    }
                }
                else{
                    if (valor<prev.valor) {
                        prev.esq=new noavl(valor,null,null);
                    }
                    else{
                        prev.dir=new noavl(valor,null,null);
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
    noavl pai(int valor){
        noavl filho=this.raiz;
        noavl pai=null;
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
		System.out.println(this.raiz.valor+"("+1+")");
		subarvore(raiz.esq,  separator,hatual);
		subarvore(raiz.dir, separator,hatual);
	}
	void subarvore(noavl no, String separator,int hatual) {
		if (no != null) {
			noavl pai = this.pai(no.valor);
			if (no.equals(pai.esq) == true) {
				System.out.println(separator+no.valor+"("+(hatual++)+")"+" (ESQ)");
			}else{
				System.out.println(separator+no.valor+"("+(hatual++)+")"+" (DIR)");
			}			
			subarvore(no.esq,  "     "+separator,hatual);
			subarvore(no.dir, "     "+separator,hatual);
		}
	}
}
 
class noavl { // classe do no da arvore (parecida com a da lista)

    int valor;
    noavl esq,dir;

    public noavl(int valor, noavl esq,noavl dir) {
        this.valor = valor;
        this.esq = esq;
        this.dir = dir;
    }
}
