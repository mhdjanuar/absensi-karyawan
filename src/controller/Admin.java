package controller;

import models.Cuti;
import repository.ImplementDB;

import java.util.List;
import java.util.Scanner;

public class Admin extends ImplementDB {
    Scanner input = new Scanner(System.in);
    public void getListMenuAdmin () {
        System.out.println("1. Konfirmasi Cuti Karyawan");
    }

    public void getMenuAdmin (Integer menu) {
        switch (menu) {
            case 1:
                List<Cuti> listCuti = listOfCutiKaryawan();

                System.out.println("DAFTAR CUTI KARYAWAN");
                System.out.println("+---------+-----------+--------------+----------------+----------------+--------+");
                System.out.println("|  ID_CUTI | NAMA_KARYAWAN | JUMLAH_CUTI |     ALASAN     |   START DATE   | STATUS |");
                System.out.println("+---------+-----------+--------------+----------------+----------------+--------+");

                for (Cuti cuti : listCuti) {
                    System.out.printf("| %-7d | %-9s | %-12d | %-14s | %-14s | %-6s |%n",
                            cuti.getIdCuti(), cuti.getNameKaryawan(), cuti.getJumlahCuti(),
                            cuti.getAlasan(), cuti.getStartDateCuti(), "PENDING");
                }

                System.out.println("+---------+-----------+--------------+----------------+----------------+--------+");

                System.out.println("Konfirmasi Cuti Karyawan");
                System.out.print("Masukan ID Cuti : ");
                String idCuti = input.nextLine();

                System.out.print("Terima Cuti (YA / TIDAK) : ");
                String approve = input.nextLine();

                if(approve.equals("YA")) {
                    updateCutiStatus(Integer.parseInt(idCuti), "APPROVE");
                    updateUsersJumlahCuti(Integer.parseInt(idCuti));
                }

                break;

            default:
                System.out.println("Tidak ada menu");
        }
    }
}
