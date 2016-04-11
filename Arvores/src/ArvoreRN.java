import java.util.Scanner;

public class ArvoreRN {

    //Variaveis que auxiliam na identificação das cores e no balanceamento das cores
    private final int VERMELHO = 0; 
    private final int PRETO = 1;
    
    //Classe No, representa os no da arvore, com seus atributos e metodos
    private class No {

        //Inicialização das variaveis da classe No
        int valor = -1, cor = PRETO;
        No esquerda = nil, direita = nil, pai = nil;
        
        //Construtor da classe No
        No(int valor) {
            this.valor = valor;
        } 
    }

    //Contrução de um no nulo, também chamado de sentinela ("nil")...
    private final No nil = new No(-1);
    

    //Inicialização da raiz da arvore
    private No raiz = nil;

    //Metodo para exibir a arvore, ainda esta bem ruim. Usa chamadas recursivas para ir colocando em ordem.
    public void printArvore(No node) {
        if (node == nil) {
            return;
        }
        printArvore(node.esquerda);
        System.out.print(((node.cor==VERMELHO)?"Cor: Vermelho ":"Cor: PRETO ")+"Valor: "+node.valor+" Pai: "+node.pai.valor+"\n");
        printArvore(node.direita);
    }

    //Metodo iserir, realiza a inserção de novos elementos na arvore, leva em consideração as condições da arvore rubro negra
    private void inserir(No node) {
        
        //Criação de um No temporario para auxiliar na inserção dos Nos
        No temp = raiz;
        
        //Se a raiz for nula então o novo elemento é a propria raiz, sendo PRETO e como ponteiro do pai nulo
        if (raiz == nil) {
            raiz = node;
            node.cor = PRETO;
            node.pai = nil;
        } else { //Caso já exista uma raiz
            node.cor = VERMELHO; //O No inserido sempre será vermelho, para manter a altura negra da arvore
            while (true) { //Loop, para encontrar o caminho onde o novo elemento será inserido
                if (node.valor < temp.valor) { //Valor inserdo a esquerda da raiz
                    if (temp.esquerda == nil) {
                        temp.esquerda = node;
                        node.pai = temp;
                        break; //Condição de quebra do loop. Encontrada a possição mais a esquerda e saida do loop while
                    } else {
                        temp = temp.esquerda;
                    }
                } else if (node.valor >= temp.valor) {//Valor inserido a direita da raiz
                    if (temp.direita == nil) {
                        temp.direita = node;
                        node.pai = temp;
                        break; //Condição de quebra de loop. Encontrada possição, mais a direita e saida do loop while
                    } else {
                        temp = temp.direita;
                    }
                }
            }
            condicionar(node); //Verifica as condições e caracteristicas, para que se mantenha a arvore Rubro negra
        }
    }

    //O argumento é o nó que acabou de ser inserido, pois a cada inserção é feita um verificação das condições
    private void condicionar(No node) {   
        //O balanceamento da arvore Rubro Negra segue a algumas condições que são implementadas abaixo... 
        
        //Como sugere o loop, enquanto o pai for vermemlho execute...
        while (node.pai.cor == VERMELHO) {
            No tio = nil; //Tio é declarado nulo
            if (node.pai == node.pai.pai.esquerda) {//Caso o tio seja vermelho
                tio = node.pai.pai.direita; 

                if (tio != nil && tio.cor == VERMELHO) {
                    node.pai.cor = PRETO;
                    tio.cor = PRETO;
                    node.pai.pai.cor = VERMELHO;
                    node = node.pai.pai;
                    continue; //Pulo de iteração, o código vai para a proxima iteração
                } 
                if (node == node.pai.direita) {
                    //Neste caso uma rotação dupla é necessaria, seguindo os passos do slide temos:
                    node = node.pai;
                    rotacaoEsq(node);
                } 
                node.pai.cor = PRETO;
                node.pai.pai.cor = VERMELHO;
                //se o else-if for executado, siguinifica que precisamos apenas de um rotação
                //A direita
                rotacaoDir(node.pai.pai);
            } else { //Segundo caso, tio Vermelho e Pai Preto
                tio = node.pai.pai.esquerda;
                 if (tio != nil && tio.cor == VERMELHO) {
                    node.pai.cor = PRETO;
                    tio.cor = PRETO;
                    node.pai.pai.cor = VERMELHO;
                    node = node.pai.pai;
                    continue; //Pulo de iteração, o código vai para a proxima iteração
                }
                if (node == node.pai.esquerda) {
                    //Caso de dupla rotação
                    node = node.pai; //Mudança de no onde vai ocorrer a rotação
                    rotacaoDir(node);
                }
                node.pai.cor = PRETO;
                    node.pai.pai.cor = VERMELHO;
                //se o else-if for executado, siguinifica que precisamos apenas de um rotação
                //A esquerda
                rotacaoEsq(node.pai.pai);
            }
        }
        raiz.cor = PRETO; //Para garantir as caracteristicas da arvore a raiz é preta.
    }

    //Metodos de rotação a esquerda da arvore rubro negra
    void rotacaoEsq(No node) {
        if (node.pai != nil) {
            if (node == node.pai.esquerda) {
                node.pai.esquerda = node.direita;
            } else {
                node.pai.direita = node.direita;
            }
            node.direita.pai = node.pai;
            node.pai = node.direita;
            if (node.direita.esquerda != nil) {
                node.direita.esquerda.pai = node;
            }
            node.direita = node.direita.esquerda;
            node.pai.esquerda = node;
        } else {//Need to rotate root
            No direita = raiz.direita;
            raiz.direita = direita.esquerda;
            direita.esquerda.pai = raiz;
            raiz.pai = direita;
            direita.esquerda = raiz;
            direita.pai = nil;
            raiz = direita;
        }
    }

    //Metodos de rotação a direita da arvore rubro negra
    void rotacaoDir(No node) {
        if (node.pai != nil) {
            if (node == node.pai.esquerda) {
                node.pai.esquerda = node.esquerda;
            } else {
                node.pai.direita = node.esquerda;
            }

            node.esquerda.pai = node.pai;
            node.pai = node.esquerda;
            if (node.esquerda.direita != nil) {
                node.esquerda.direita.pai = node;
            }
            node.esquerda = node.esquerda.direita;
            node.pai.direita = node;
        } else {//Need to rotate root
            No esquerda = raiz.esquerda;
            raiz.esquerda = raiz.esquerda.direita;
            esquerda.direita.pai = raiz;
            raiz.pai = esquerda;
            esquerda.direita = raiz;
            esquerda.pai = nil;
            raiz = esquerda;
        }
    }
    
    //Metodo chamado pela função main. Interface entre o usuario.
    public void painel() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.- Adicionar itens\n"
                    + "2.- Mostra Arvore\n");
            int choice = scan.nextInt();

            int item;
            No node;
            switch (choice) {
                case 1:
                    item = scan.nextInt();
                    while (item != -999) {
                        node = new No(item);
                        inserir(node);
                        item = scan.nextInt();
                    }
                    break;
                case 2:
                    printArvore(raiz);
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        ArvoreRN ARN = new ArvoreRN();
        ARN.painel();
    }
}