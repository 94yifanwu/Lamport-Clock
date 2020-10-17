// 474 project 1 part 1
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class yifan_1{
  public static void main(String[] args) {
    try {
      //open file and find the number of rows and columns
      File input= new File("input.txt");
      Scanner scanner= new Scanner(input);
      int N = 0;
      int M = 0;
      
      //traverse input file to get N and M
      while (scanner.hasNextLine()) {
        //get file lines (N processors)
        String string = scanner.nextLine();
        N++;
        //get max file columns (M)
        String[] substring = string.split(" ");
        M = Math.max(M,substring.length);
      }

      //origin and result array
      String[][] origin = new String[N][M];
      int[][] result = new int[N][M];
      for(int i=0;i<N;i++)
        for(int j=0;j<M;j++)
          result[i][j]=0;

      scanner= new Scanner(input);
      int n=0;
      while (scanner.hasNextLine()){
        String data = scanner.nextLine();
        String[] substring = data.split(" ");
        for(int k=0,m=0;k<substring.length;k++){
          origin[n][m] = substring[k];
          m++;
        }
        n++;
      }
      // create send array, make send[0] become size of the array
      int[] send = new int[9]; // at most 9 events

      // first step: assign numbers to send and internal letter
      for(int i=0;i<N;i++){
        for(int j=0;j<M;j++){
          if(origin[i][j]!=null && origin[i][j].contains("r"))
            break;
          else if(origin[i][j]!=null && !origin[i][j].contains("r")){
            result[i][j] = j+1;
            // if it's send, add send letter to send array
            if(origin[i][j].contains("s")){
              send[origin[i][j].charAt(1)-'0'] = result[i][j];
              send[0]++; // size++
            }
          }
        }
      }
      
      System.out.println("origin is: ");
      // print origin
      for(int i=0;i<N;i++){
        for(int j=0;j<M;j++)
          System.out.print(origin[i][j]+" ");
      	System.out.println();
      }
      // second step: while loop, until send[0] is empty
      while(send[0]>0){
        //traverse origin[][] 
        for(int i=0;i<N;i++){
          for(int j=0;j<M;j++){
            // if it's receive, find corresponding send event and write to result
            if(result[i][j]==0 && origin[i][j]!=null && origin[i][j].contains("r")){
                // find column of this send
                int columnOfSend = 0;
                for(int a=0;a<N;a++)
                  for(int b=0;b<M;b++)
                    if(origin[a][b]!=null && origin[a][b].equals("s"+origin[i][j].charAt(1)))
                      columnOfSend = b;
                // compare the max of LC(send)+1 or distance
                result[i][j] = Math.max(send[origin[i][j].charAt(1)-'0']+1,j-columnOfSend+1);
                send[0]--;
            }
            // if it's send, add 1 and store to send array
            else if(result[i][j]==0 && origin[i][j]!=null && origin[i][j].contains("s")){
              result[i][j] = result[i][j-1]+1;
              send[origin[i][j].charAt(1)-'0'] = result[i][j];
              send[0]++;
            }
            // if it's internal, add 1
            else if(result[i][j]==0 && origin[i][j]!=null ) // internal
              result[i][j] = result[i][j-1]+1;
          
          }
        }
      }

      // output result
      System.out.println("result is: ");
      for(int i=0;i<N;i++){
        for(int j=0;j<M;j++)
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

