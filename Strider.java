package strider;
/*
    Deiziane Natani da Silva
    Desafio dos Pixels Strider

    1- Contar a quantidade que pontos vermelhos na imagem;
    2- Encontrar a frase escondida nos pontos

    No desafio 1, contei quantos pixels vermelhos existem na imagem (299)

    No desafio 2, após várias tentativas frustradas como: desenhar um mapa de pixels, aplicar filtros na imagem etc,
    resolvi seguir o seguinte algoritmo:
        - Varrer a imagem verificando coluna a coluna, iterando sobre as linhas
        - Caso haja um pixel vermelho na linha, TODA a coluna é considerada vermelha. 
          Nesse caso, essa coluna é representada pelo bit 1
        - Caso não exista um pixel vermelho nas linhas, a coluna é considerada branca
          Nesse caso, essa coluna é representada pelo bit 0
        - No final é feita a conversão de binário para texto, resultando na frase:
          h4sKKkE7 : "History will be kind to me, for I intend to write it" Winston Churchill
*/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Strider {
    public static void main(String[] args) throws IOException{
	File arq = null;    
        BufferedImage img = null;
        
        FileWriter writer = new FileWriter("./pixelmap.txt"); //arquivo onde vou salvar os bits 0 ou 1
        PrintWriter gravarArq = new PrintWriter(writer);
    
	//Lê a imagem 
	try{
	    arq = new File("./challenge_strider.png"); 
	    img = ImageIO.read(arq);
	}
        catch(IOException e){ //Caso imagem não possa ser lida, dispara exceção
	    System.out.println(e);
	}
        
	//Identifica dimensões da imagem
	int largura = img.getWidth();
	int altura = img.getHeight();
	
        int cnt = 0; //inicializa contador dos pontos vermelhos
	    
	//Itera a imagem pixel a pixel
	for(int y = 0; y < altura; y++){
            for(int x = 0; x < largura; x++){  
	    	Color c = new Color(img.getRGB(x, y)); //pega os valores RGB de cada pixel na imagem
	    	int r = c.getRed();
	    	int g = c.getGreen();
	    	int b = c.getBlue();
	        
	    	//Verifica se o pixel é vermelho
	        if(r == 255 && g == 0 && b == 0){
	        	cnt++;
	        }
	    }
	}
	
        int flag = 0; // flag para sinalizar se encontrei ponto vermelho na coluna - 0 se nao, 1 se sim
        int aux = 1;
        
        //Itera a imagem pixel a pixel
        // Grava 0 ou 1 no arquivo txt, dependendo se há um pixel vermelho na coluna
	for(int y = 0; y < largura; y++){
            flag = 0;
            for(int x = 0; x < altura; x++){  
	    	Color c = new Color(img.getRGB(y, x)); //pega os valores RGB de cada pixel na imagem
	    	int r = c.getRed();
	    	int g = c.getGreen();
	    	int b = c.getBlue();
	        
	    	//Verifica se o pixel é vermelho
	        if(r == 255 && g == 0 && b == 0){
                    gravarArq.printf("1"); 
                    flag = 1;
                    break;
	        }
	    }
            if(flag!=1){
                gravarArq.printf("0");
            }
            if(aux%8 == 0){ // separa string de 8 em 8 
                gravarArq.printf("\n");
            }
            aux++;
	}
        
        writer.close();
	//imprime resultado
	System.out.println("A imagem possui "+cnt+" pontos vermelhos!");
        
        
        File file = new File("./pixelmap.txt");
        Scanner sc = new Scanner(file);
        String data, str;
        int charCode;
        
        while (sc.hasNextLine()) { //lê arquivo txt
            data = sc.nextLine();
            charCode = Integer.parseInt(data, 2); // transforma binario em integer
            str = new Character((char) charCode).toString();  //transforma integer em caractere

            System.out.print(str);
        } 
        System.out.print("\n");
    }
}
