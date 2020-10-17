// 474 project 1 part 2
// noted: send and receive event starting with 1. ex: s1,s2,s3,s4.... 
// receive array template: receive[0] is size, and receive[1],receive[2]... is value number

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class yifan_2{
  public static void main(String[] args) {
    try {
      //open file and find the number of rows and columns
      File input= new File("input.txt");
      Scanner scanner= new Scanner(input);
      int N = 0;
      int M = 0;
      
      // get input 
      while (scanner.hasNextLine()) {
        //get file lines (N processors)
        String string = scanner.nextLine();
        N++;
        //get max file columns (M)
        String[] substring = string.split(" ");
        M = Math.max(M,substring.length);
      }

      // origin and result array
      String[][] origin = new String[N][M];
      String[][] result = new String[N][M];

      scanner= new Scanner(input);
      int n=0;
      // assign input to origin 2d array
      while (scanner.hasNextLine()){
        String data = scanner.nextLine();
        String[] substring = data.split(" ");
        for(int k=0,m=0;k<substring.length;k++){
          origin[n][m] = substring[k];
          m++;
        }
        n++;
      }
      
      // create receive array, make receive[0] become size of the array
      int[] receive = new int[9]; // at most 9 events

      // first step: find gap number and store to receive[], receive.size++, and store to result[][].
      // when j=0, first column must be '1', if not, it's a gap number
      for(int i=0;i<N;i++){
        if( !origin[i][0].equals("1") ){
          receive[1] = Integer.parseInt(origin[i][0]);
          receive[0]++;
          result[i][0] = "r1";
        }
      }
      // when j>=1
      for(int j=1;j<M;j++){
        for(int i=0;i<N;i++){
          if(origin[i][j]!=null
             && Integer.parseInt(origin[i][j]) != (Integer.parseInt(origin[i][j-1])+1) 
             && !origin[i][j].equals("0")  ){
            // store to receive[], receive.size++
            int k=0;
            for(k=1;k<receive.length;k++){
              if(receive[k] == 0){
                receive[k] = Integer.parseInt(origin[i][j]);
                receive[0]++;
                // store to result[][]
                result[i][j] = "r"+k;
                break;
              }
            }
          }
        }
      }

      // second step: while loop, until receive[0] is empty
      // varify: a receive must have a send, if not, end program and output "INCORRECT"
      // compute: convert numbers to letters, use receive event to find corresponding send event
      while(receive[0]>0){ // receive array size is not empty 
        for(int k=1;k<receive.length;k++){
          //search origin with value of r[k]-1, which will be send value
          if(receive[k]!=0){
            boolean find = false; // initiate find=false
            for(int i=0;i<N;i++){ //search origin with value of r[k]-1, which is send value
              for(int j=0;j<M;j++){
                if(origin[i][j]!=null 
                   && Integer.parseInt(origin[i][j]) == (receive[k]-1) 
                   && result[i][j]==null){
                  find = true;
                  result[i][j] = "s"+k;
                  receive[0]--;
                }
              }
            }
            // if a receive doesn't find a send, find doesn't change and remain False
            if(find==false){
              System.out.println("INCORRECT");
              return;
            }
          }
        }
      }

      // third step: fill the rest of internal letters
      char c = 'a';
      for(int i=0;i<N;i++) 
        for(int j=0;j<M;j++)
          if( origin[i][j]!=null 
              && !origin[i][j].equals("0") 
              && result[i][j]==null)
            result[i][j] = Character.toString(c++);
            
      // output
      System.out.println("origin is:");
      for(int i=0;i<N;i++){
        for(int j=0;j<M;j++)
          if(origin[i][j]!=null)
            System.out.print(origin[i][j]+" ");
        System.out.println();
      }
      System.out.println("result is:");
      for(int i=0;i<N;i++){
        for(int j=0;j<M;j++)
          if(origin[i][j]!=null)
            System.out.print(result[i][j]+" ");
        System.out.println();
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }


}

