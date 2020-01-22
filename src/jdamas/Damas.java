/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Damas.java
 *
 * Created on 27-feb-2011, 20:58:41
 */

package jdamas;


import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author neoligero
 */
public class Damas extends javax.swing.JFrame {

    static int matriz[][];
    static JButton tablero[][];
    static JPanel mesa;
    boolean ganado=false;
    boolean pulsado=false;
    int x1, y1, x2, y2, xobj, nobj, nd;
    int objetivos1[], objetivos2[],objetivosorigen[], origenes[], destinos[];
    boolean objetivo1=false;
    boolean objetivo2=false;
    boolean movido=false;
    boolean matado=false;
    ImageIcon peon=new ImageIcon("peon.gif");
    ImageIcon peon2=new ImageIcon("peon2.gif");
    ImageIcon dama=new ImageIcon("dama.gif");
    ImageIcon dama2=new ImageIcon("dama2.gif");


    /** Creates new form Damas */
    public Damas() {
        initComponents();
        creaTablero();
    }


    private void nuevoJuego() {
        dispose();
        new Damas().setVisible(true);
    }


//Devuelve true si es un movimiento valido
    //Movimientos simples
    public boolean mPeonValido (int i, int j){
        return (((i==x1+1 && j==y1-1) || (i==x1+1 && j==y1+1)) && matriz[i][j]==0);
    }

    public boolean mPeon2Valido (int i, int j){
        return (((i==x1-1 && j==y1-1) || (i==x1-1 && j==y1+1))  && matriz[i][j]==0 );
    }

    //Movimiento de 2 posiciones hacia arriba o hacia abajo
    public boolean mPeonMatarValido(int i, int j){
        return (((i==x1+2 && j==y1-2) || (i==x1+2 && j==y1+2)) && matriz[i][j]==0);
    }

    public boolean mPeon2MatarValido(int i, int j){
         return (((i==x1-2 && j==y1-2) || (i==x1-2 && j==y1+2)) && matriz[i][j]==0);
    }

    //Hacia los 4 lugares posibles
    public boolean mDamaValido (int i, int j){
        return ((i==x1-1 && j==y1-1) || (i==x1-1 && j==y1+1) || (i==x1+1 && j==y1-1) || (i==x1+1 && j==y1+1)  && matriz[i][j]==0 );
    }

    //Para matar hacia arriba o hacia abajo
    public boolean mMatarValido (int i, int j){
        return ((matriz[(i+x1)/2][(j+y1)/2]==2 || matriz[(i+x1)/2][(j+y1)/2]==4)  && matriz[i][j]==0);
    }

    public boolean mMatar2Valido (int i, int j){
        return ((matriz[(i+x1)/2][(j+y1)/2]==1 || matriz[(i+x1)/2][(j+y1)/2]==3)  && matriz[i][j]==0);
    }

    public boolean veObjetivoPeon(int i, int j){
        boolean b=false;
        
        if(i<6 && j>1){
                    if(matriz[i][j]==1 && (matriz[i+1][j-1]==2 || matriz[i+1][j-1]==4) && matriz[i+2][j-2]==0){
                        b=true;
                    }
        }
        if(i<6 && j<6){
                    if(matriz[i][j]==1 && (matriz[i+1][j+1]==2 || matriz[i+1][j+1]==4) && matriz[i+2][j+2]==0){
                        b=true;
                    }
        }
        return b;
    }

    public boolean veObjetivoDama(int i, int j){
        boolean b=false;

        if (i<6 && j>1)
                    if(matriz[i][j]==3 && (matriz[i+1][j-1]==2 || matriz[i+1][j-1]==4) && matriz[i+2][j-2]==0)
                        b=true;
        if (i<6 && j<6)
                    if(matriz[i][j]==3 && (matriz[i+1][j+1]==2 || matriz[i+1][j+1]==4) && matriz[i+2][j+2]==0)
                        b=true;
        if (i>1 && j>1)
                    if(matriz[i][j]==3 && (matriz[i-1][j-1]==2 || matriz[i-1][j-1]==3) && matriz[i-2][j-2]==0)
                        b=true;
        if (i>1 && j<6)
                    if(matriz[i][j]==3 && (matriz[i-1][j+1]==2 || matriz[i-1][j+1]==3) && matriz[i-2][j+2]==0)
                        b=true;
        return b;
    }


    //Comprueba todos los posibles objetivos a la vista
    private boolean veObjetivos1(){
        xobj=0;
        nobj=0;
        objetivo1=false;
        objetivos1=new int[10];
        boolean b=false;

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(i<6 && j>1){
                    //comprueba que sea ficha blanca, que haya un enemigo cerca y una posicion vacia de salto
                    if(matriz[i][j]==1 && (matriz[i+1][j-1]==2 || matriz[i+1][j-1]==4) && matriz[i+2][j-2]==0){
                        objetivos1[xobj]=i+2;
                        objetivos1[xobj+1]=j-2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }

                if(i<6 && j<6){
                    if(matriz[i][j]==1 && (matriz[i+1][j+1]==2 || matriz[i+1][j+1]==4) && matriz[i+2][j+2]==0){
                        objetivos1[xobj]=i+2;
                        objetivos1[xobj+1]=j+2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }
            }
        }
        return b;
    }

    private boolean veObjetivos2(){
        boolean b=false;

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(i>1 && j>1){
                    //comprueba que sea ficha blanca, que haya un enemigo cerca y una posicion vacia de salto
                    if(matriz[i][j]==2 && (matriz[i-1][j-1]==1 || matriz[i-1][j-1]==3) && matriz[i-2][j-2]==0){
                        objetivos2[xobj]=i-2;
                        objetivos2[xobj+1]=j-2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }

                if(i>1 && j<6){
                    if(matriz[i][j]==2 && (matriz[i-1][j+1]==1 || matriz[i-1][j+1]==3) && matriz[i-2][j+2]==0){
                        objetivos2[xobj]=i-2;
                        objetivos2[xobj+1]=j+2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }
            }
        }
        return b;
    }

    private boolean veObjetivosDama1(){
        boolean b=false;

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(i<6 && j>1){
                    //Comprueba que se trata de una dama, y mira los 4 movimientos posibles de salto
                    if(matriz[i][j]==3 && (matriz[i+1][j-1]==2 || matriz[i+1][j-1]==4) && matriz[i+2][j-2]==0){
                        objetivos1[xobj]=i+2;
                        objetivos1[xobj+1]=j-2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }

                if(i<6 && j<6){
                    if(matriz[i][j]==3 && (matriz[i+1][j+1]==2 || matriz[i+1][j+1]==4) && matriz[i+2][j+2]==0){
                        objetivos1[xobj]=i+2;
                        objetivos1[xobj+1]=j+2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }

                if(i>1 && j>1){
                    if(matriz[i][j]==3 && (matriz[i-1][j-1]==2 || matriz[i-1][j-1]==4) && matriz[i-2][j-2]==0){
                        objetivos1[xobj]=i-2;
                        objetivos1[xobj+1]=j-2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }

                if(i>1 && j<6){
                    if(matriz[i][j]==3 && (matriz[i-1][j+1]==2 || matriz[i-1][j+1]==4) && matriz[i-2][j+2]==0){
                        objetivos1[xobj]=i-2;
                        objetivos1[xobj+1]=j+2;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo1=true;
                    }
                }
            }
        }
        return b;
    }

    private boolean veObjetivosDama2(){
        xobj=0;
        nobj=0;
        objetivo2=false;
        objetivos2=new int[10];
        objetivosorigen=new int[10];
        boolean b=false;

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(i<6 && j>1){
                    //Comprueba que se trata de una dama, y mira los 4 movimientos posibles de salto
                    if(matriz[i][j]==4 && (matriz[i+1][j-1]==1 || matriz[i+1][j-1]==3) && matriz[i+2][j-2]==0){
                        objetivos2[xobj]=i+2;
                        objetivos2[xobj+1]=j-2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }

                if(i<6 && j<6){
                    if(matriz[i][j]==4 && (matriz[i+1][j+1]==1 || matriz[i+1][j+1]==3) && matriz[i+2][j+2]==0){
                        objetivos2[xobj]=i+2;
                        objetivos2[xobj+1]=j+2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }

                if(i>1 && j>1){
                    if(matriz[i][j]==4 && (matriz[i-1][j-1]==1 || matriz[i-1][j-1]==3) && matriz[i-2][j-2]==0){
                        objetivos2[xobj]=i-2;
                        objetivos2[xobj+1]=j-2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }

                if(i>1 && j<6){
                    if(matriz[i][j]==4 && (matriz[i-1][j+1]==1 || matriz[i-1][j+1]==3) && matriz[i-2][j+2]==0){
                        objetivos2[xobj]=i-2;
                        objetivos2[xobj+1]=j+2;
                        objetivosorigen[xobj]=i;
                        objetivosorigen[xobj+1]=j;
                        xobj+=2;
                        nobj++;
                        b=true;
                        objetivo2=true;
                    }
                }
            }
        }
        return b;
    }

    
    //Comprueba si la casilla dada es uno de los objetivos
    public boolean esObjetivo1(int x, int y){
        boolean b=false;
        for (int i=0; i<10; i++){
            if(objetivos1[i]==x && objetivos1[i+1]==y)
                b=true;
        i++;
        }
        return b;
    }

    public boolean esObjetivo2(int x, int y){
        boolean b=false;
        for (int i=0; i<10; i++){
            if(objetivos2[i]==x && objetivos2[i+1]==y)
                b=true;
        i++;
        }
        return b;
    }

    public void pulsacion(java.awt.event.ActionEvent e) {

            boolean b=false;
            movido=false;
            //Primera pulsacion, solo recoge la posicion de inicio y la mete en las varialbes x1 y y1
            if(!pulsado){
            for (int i=0;i<8;i++)
                for (int j=0;j<8;j++)
                    if(e.getSource()==tablero[i][j] && (matriz[i][j]==1 || matriz[i][j]==3)){
                       x1=i;
                       y1=j;
                       b=true;
                    }
            }

            //Segunda pulsacion, pulsacion destino de la ficha
            if(pulsado){
            for (int i=0;i<8;i++)
                for (int j=0;j<8;j++){

                    //Si no hay objetivo a la vista hace el movimiento normal
                    if(e.getSource()==tablero[i][j] && !objetivo1 ){
                        if(matriz[x1][y1]==1 && mPeonValido(i,j)){
                            if(i<7){
                                tablero[i][j].setIcon(peon);
                                matriz[i][j]=1;
                            }
                            else{
                                tablero[i][j].setIcon(dama);
                                matriz[i][j]=3;
                            }
                            tablero[x1][y1].setIcon(null);
                            matriz[x1][y1]=0;
                            veObjetivos1(); //Tras el movimiento comprueba las posible fichas que se pueden matar (solo hacia abajo)
                            movido=true;
                            x2=i;
                            y2=j;
                        }
                        //Si es dama
                        else if (matriz[x1][y1]==3 && mDamaValido(i,j)){
                            tablero[i][j].setIcon(dama);
                            matriz[i][j]=3;
                            tablero[x1][y1].setIcon(null);
                            matriz[x1][y1]=0;
                            veObjetivos1();
                            veObjetivosDama1(); //mira los objetivos hacia las 4 movimientos de la dama
                            movido=true;
                            x2=i;
                            y2=j;
                        }
                        
                    }


                    //Si tiene objetivo hace esto otro
                    else if (e.getSource()==tablero[i][j] && objetivo1 && esObjetivo1(i,j)){
                        //Mata el peon
                        if(matriz[x1][y1]==1 && mPeonMatarValido(i,j)){
                            if(i<7){
                                tablero[i][j].setIcon(peon);
                                matriz[i][j]=1;
                            }
                            else{
                                tablero[i][j].setIcon(dama);
                                matriz[i][j]=3;
                            }
                            tablero[x1][y1].setIcon(null);
                            matriz[x1][y1]=0;
                            tablero[(i+x1)/2][(j+y1)/2].setIcon(null);
                            matriz[(i+x1)/2][(j+y1)/2]=0;
                            veObjetivos1();
                            veObjetivosDama1();
                            movido=true;
                            matado=true;
                            x2=i;
                            y2=j;
                        }
                        //Mata la dama
                        else if (matriz[x1][y1]==3 && mMatarValido(i,j) ){
                            tablero[i][j].setIcon(dama);
                            matriz[i][j]=3;
                            tablero[x1][y1].setIcon(null);
                            matriz[x1][y1]=0;
                            tablero[(i+x1)/2][(j+y1)/2].setIcon(null);
                            matriz[(i+x1)/2][(j+y1)/2]=0;
                            veObjetivos1();
                            veObjetivosDama1();
                            movido=true;
                            matado=true;
                            x2=i;
                            y2=j;
                        }
                        
                    }
                    else if(e.getSource()==tablero[i][j] && objetivo1 && !esObjetivo1(i,j)){
                         JOptionPane.showMessageDialog(rootPane, "Mata!!!");
                    }
                    //System.out.println(x2+"    "+y2);
                    //System.out.println(veObjetivoPeon(x2,y2));
                }
                b=false;
            }
            pulsado=b;

            if(matado && ((matriz[x2][y2]==1 && veObjetivoPeon(x2,y2)) || (matriz[x2][y2]==3 && veObjetivoDama(x2,y2)))){
                System.out.println("Mueve otra vez");
            }
            else if(!pulsado && movido) {
                 mueveMaquina();
            }
            matado=false;
            ganador();

    }

    public void ganador(){
        int nj1, nj2;
        nj1=nj2=0;
        for (int i=0;i<8;i++)
            for (int j=0; j<8; j++){
                if(matriz[i][j]==1 || matriz[i][j]==3)
                    nj1++;
                else if(matriz[i][j]==2 || matriz[i][j]==4)
                    nj2++;
            }
        if(nj1==0){
            JOptionPane.showMessageDialog(rootPane, "Gana la máquina.");
            nuevoJuego();
            /*for (int a=0; a<8; a++)
                for (int b=0; b<8; b++){
                    tablero[a][b].setEnabled(false);
                }*/
        }
        else if(nj2==0){
            JOptionPane.showMessageDialog(rootPane, "Jugador gana!!.");
            nuevoJuego();
            /*for (int a=0; a<8; a++)
                for (int b=0; b<8; b++){
                    tablero[a][b].setEnabled(false);
                }*/
        }

    }
    /*public boolean casillaPeligrosa(int i, int j) {
        boolean peligrosa=false;

        if(i>0 && j>0 && i<7 && j<7){
            if((matriz[i-1][j-1]==1 || matriz[i-1][j-1]==3) && matriz[i+1][j+1]==0)
                peligrosa=true;
        }
        else if(i>0 && j<7){
            if((matriz[i-1][j+1]==1 || matriz[i-1][j+1]==3) && matriz[i+1][j-1]==0)
                peligrosa=true;
        }
        else if(i<7 && j>0){
            if(matriz[i+1][j-1]==3 && matriz[i-1][j+1]==0)
                peligrosa=true;
        }
        else if(i<7 && j<7){
            if(matriz[i+1][j+1]==3 && matriz[i-1][j-1]==0)
                peligrosa=true;
        }
        return peligrosa;
    }*/


    public boolean casillaPeligrosa(int i, int j) {
        boolean peligrosa=false;

        if(i>0 && j>0 && i<7 && j<7){
            if((matriz[i-1][j-1]==1 || matriz[i-1][j-1]==3) && matriz[i+1][j+1]==0)
                peligrosa=true;

            else if((matriz[i - 1][j + 1] == 1 || matriz[i - 1][j + 1] == 3) && matriz[i + 1][j - 1] == 0)
                peligrosa=true;

            else if(matriz[i + 1][j - 1] == 3 && matriz[i - 1][j + 1] == 0)
                peligrosa=true;

            else if(matriz[i + 1][j + 1] == 3 && matriz[i - 1][j - 1] == 0)
                peligrosa=true;
        }
        return peligrosa;
    }


    public int intRandom(int z){
        int x=new Double(Math.random()*z).intValue();
        while(x%2!=0){
             x=new Double(Math.random()*z).intValue();
        }
        return x;
    }

    public void mueveMaquina(){
        //Introduce los objetivos en un array
        veObjetivosDama2();
        veObjetivos2();
        int r=intRandom(xobj);
            int inten=5;
            do{
            r=intRandom(xobj);
            inten--;
            }while(casillaPeligrosa(objetivos2[r],objetivos2[r+1]) && inten>0);
        x1=objetivosorigen[r];
        y1=objetivosorigen[r+1];

        //Primero comprueba que tiene alguna ficha para matar
        if(objetivo2){
            for (int i=0;i<8;i++)
                for (int j=0;j<8;j++){
                    //utiliza una variable random para seleccionar al objetivo

                    if(tablero[i][j]==tablero[objetivosorigen[r]][objetivosorigen[r+1]]){
                        if(matriz[objetivosorigen[r]][objetivosorigen[r+1]]==2){
                            if(objetivos2[r]>0){
                                tablero[objetivos2[r]] [objetivos2[r+1]].setIcon(peon2);
                                matriz[objetivos2[r]] [objetivos2[r+1]]=2;
                            }
                            else{
                                tablero[objetivos2[r]] [objetivos2[r+1]].setIcon(dama2);
                                matriz[objetivos2[r]] [objetivos2[r+1]]=4;
                            }
                            tablero[objetivosorigen[r]] [objetivosorigen[r+1]].setIcon(null);
                            matriz[objetivosorigen[r]] [objetivosorigen[r+1]]=0;
                            tablero[(objetivosorigen[r]+objetivos2[r]) / 2] [(objetivosorigen[r+1]+objetivos2[r+1]) / 2].setIcon(null);
                            matriz[(objetivosorigen[r]+objetivos2[r]) / 2] [(objetivosorigen[r+1]+objetivos2[r+1]) / 2]=0;
                        }
                        else if(matriz[objetivosorigen[r]][objetivosorigen[r + 1]] == 4) {

                            tablero[objetivos2[r]] [objetivos2[r+1]].setIcon(dama2);
                            matriz[objetivos2[r]] [objetivos2[r+1]]=4;
                            tablero[objetivosorigen[r]] [objetivosorigen[r+1]].setIcon(null);
                            matriz[objetivosorigen[r]] [objetivosorigen[r+1]]=0;
                            tablero[(objetivosorigen[r]+objetivos2[r]) / 2] [(objetivosorigen[r+1]+objetivos2[r+1]) / 2].setIcon(null);
                            matriz[(objetivosorigen[r]+objetivos2[r]) / 2] [(objetivosorigen[r+1]+objetivos2[r+1]) / 2]=0;
                        }
                    }
                }
        }
        else{
            nd=0;
            origenes=new int[30];
            destinos=new int[30];
            for (int i=0; i<8; i++)
                for (int j=0; j<8; j++){
                    if (matriz[i][j]==2){
                        x1=i;
                        y1=j;
                        for (int a=0;a<8;a++)
                            for (int b=0;b<8;b++){
                                if (mPeon2Valido(a,b)){
                                    origenes[nd]=i;
                                    origenes[nd+1]=j;
                                    destinos[nd]=a;
                                    destinos[nd+1]=b;
                                    nd+=2;
                                }
                            }
                    }
                    if (matriz[i][j]==4){
                        x1=i;
                        y1=j;
                        for (int a=0;a<8;a++)
                            for (int b=0;b<8;b++){
                                if (mDamaValido(a,b)){
                                    origenes[nd]=i;
                                    origenes[nd+1]=j;
                                    destinos[nd]=a;
                                    destinos[nd+1]=b;
                                    nd+=2;
                                }
                            }
                    }
                }
            int r2=intRandom(nd);
            int intentos=5;
            do{
            r2=intRandom(nd);
            intentos--;
            }while(casillaPeligrosa(destinos[r2],destinos[r2+1]) && intentos>0);

            if(matriz[origenes[r2]][origenes[r2+1]]==2) {
                if(destinos[r2]>0){
                tablero[destinos[r2]][destinos[r2+1]].setIcon(peon2);
                matriz[destinos[r2]][destinos[r2+1]]=2;
                }
                else{
                    tablero[destinos[r2]][destinos[r2+1]].setIcon(dama2);
                    matriz[destinos[r2]][destinos[r2+1]]=4;
                }
                tablero[origenes[r2]][origenes[r2+1]].setIcon(null);
                matriz[origenes[r2]][origenes[r2+1]]=0;
            }
            else if(matriz[origenes[r2]][origenes[r2+1]]==4) {
                tablero[destinos[r2]][destinos[r2+1]].setIcon(dama2);
                matriz[destinos[r2]][destinos[r2+1]]=4;
                tablero[origenes[r2]][origenes[r2+1]].setIcon(null);
                matriz[x1][y1]=0;
            }
        }

        veObjetivos1();
        veObjetivosDama1();
    }


    private void creaTablero(){
        mesa=new JPanel(new GridLayout(8,8));
        mesa.setSize(8*70, 8*70);
        tablero=new JButton[8][8];
        matriz=new int[8][8];

        //Rellena con los botones coloreados
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){

                JButton b=new JButton();
                b.addActionListener(new ActionListener() {public void actionPerformed(java.awt.event.ActionEvent evt){
                    pulsacion(evt);
                }
                });
                //b.setEnabled(false);{

                //b.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                if(j%2 == 0){
                    if(i%2 == 0)
                        b.setBackground(Color.red);
                    else
                        b.setBackground(Color.black);
                }
                else{
                    if(i%2 != 0)
                        b.setBackground(Color.red);
                    else
                        b.setBackground(Color.black);
                }
                tablero[i][j]=b;
                mesa.add(tablero[i][j]);
            }
        }
        //rellena la matriz numerica con las fichas
        for (int i=0; i<3 ;i++){
            for (int j=0; j<8; j++){
                if (j%2 == 0){
                    if (i%2 != 0){
                        matriz[i][j]=1;
                        tablero[i][j].setIcon(peon);
                    }
                    else
                        matriz[i][j]=0;
                }
                else{
                    if (i%2 == 0){
                        matriz[i][j]=1;
                        tablero[i][j].setIcon(peon);
                    }
                    else
                        matriz[i][j]=0;
                }
            }
        }
        for (int i=3;i<5;i++)
            for (int j=0;j<8;j++)
                matriz[i][j]=0;

        for (int i=5; i<8 ;i++){
            for (int j=0; j<8; j++){
                if (j%2 != 0){
                    if (i%2 == 0){
                        matriz[i][j]=2;
                        tablero[i][j].setIcon(peon2);
                    }
                    else
                        matriz[i][j]=0;
                }
                else{
                    if (i%2 != 0){
                        matriz[i][j]=2;
                        tablero[i][j].setIcon(peon2);
                    }
                    else
                        matriz[i][j]=0;
                }
            }
        }
        
        add(mesa);
        pack();
        setSize(8*70+12,8*70+53);
        
        //Muestra la matriz numerica
        /*for (int i=0;i<8;i++){
            for (int j=0;j<8;j++)
                System.out.print(matriz[i][j]);
            System.out.println("");
        }*/
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Nuevo Juego");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem3.setText("Acerca de...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        nuevoJuego();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "JDamasLigeras v1.0");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Damas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    // End of variables declaration//GEN-END:variables

}
