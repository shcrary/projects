import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class House{
    public static void main(String[] args) throws IOException{
	InputStreamReader stream = new InputStreamReader(System.in);
	BufferedReader bf = new BufferedReader(stream);
	System.out.println("Enter House name: ");
	String houseName = bf.readLine();
	System.out.println("Enter your House age: ");
	int houseAge = Integer.parseInt(bf.readLine());
	System.out.println("Summary: ");
	System.out.println("House Name: " + houseName);
	System.out.println("House Age: " + houseAge);
    }
}
