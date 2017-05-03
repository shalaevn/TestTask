package client;


public class MainClient {
    public static void main(String[] args) {
        if (args.length > 2)
            new Client(args);
        else
            System.out.println("Проверьте входные данные ");
    }
}
