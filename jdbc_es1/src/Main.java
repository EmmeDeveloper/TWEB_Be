import dao.DAO;

public class Main {
    public static void main(String[] args) {
        DAO.registerDriver();
        System.out.println("Hello database!");

        DAO.printUtenti();
        DAO.printDocenti();
        DAO.printCorsi();

    }


}