import controller.Admin;
import controller.Karyawan;
import controller.Login;
import models.User;

import java.util.Scanner;

public class Main{
    public static void main(String[]args) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        String exit = "";

        /*
        *  Flow Login
        * */
        Login login = new Login();

        System.out.println("Login");
        System.out.print("Masukan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukan Password: ");
        String password = scanner.nextLine();

        User authenticatedUser = login.login(username, password);

        if(authenticatedUser != null) {
            System.out.println("Login Berhasil");

            System.out.println("================ DASHBOARD =====================");
            System.out.println("Nama: " + authenticatedUser.getUserName());
            System.out.println("===========================================");

            Karyawan karyawan = new Karyawan();
            Admin admin = new Admin();

            do{
                if(authenticatedUser.getRole().equals("karyawan")) {
                    karyawan.getListMenuKaryawan();
                    System.out.print("Pilih Menu : ");
                    String pilihan = scanner.nextLine();
                    karyawan.getMenuKaryawan(Integer.parseInt(pilihan), authenticatedUser);
                } else {
                    admin.getListMenuAdmin();
                    System.out.print("Pilih Menu : ");
                    String pilihan = scanner.nextLine();
                    admin.getMenuAdmin(Integer.parseInt(pilihan));
                }

                System.out.print("Lanjut (YA/TIDAK) ? ");
                exit = scanner.nextLine();
            } while (exit.equals("YA"));

        } else {
            System.out.println("Login Gagal");
        }
    }
}