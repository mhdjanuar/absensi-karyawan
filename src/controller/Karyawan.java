package controller;

import models.Absen;
import models.User;
import repository.ImplementDB;

import java.util.List;
import java.util.Scanner;

public class Karyawan extends ImplementDB {
    Scanner input = new Scanner(System.in);
    public void getListMenuKaryawan () {
        System.out.println("1. Absen");
        System.out.println("2. Riwayat Absen");
        System.out.println("3. Form Pengajuan Cuti");
        System.out.println("4. Slip Gaji");
    }

    public void getMenuKaryawan (Integer menu, User user) {
        switch (menu) {
            case 1:
                System.out.println("=======================================");
                System.out.println("Absen Karyawan - Masuk / Keluar");

                System.out.print("Masukan Tanggal Dan Jam (MM/DD/YYYY - HH:mm) : ");
                String dateHours = input.nextLine();
                inputAbsenKaryawan(user.getId(), dateHours);
                break;

            case 2:
                System.out.println("=======================================");

                List<Absen> historyAbsen = getRiwayatAbsen(user.getId());

                for (Absen absen : historyAbsen) {
                    System.out.println("Masuk : " + absen.getDateCheckIn() + " | " + "Keluar : " + absen.getDateCheckOut());
                }
                break;

            case 3:
                System.out.println("==============================");
                System.out.println("Formulir Pengajuan Cuti");

                System.out.print("Mulai dari (MM/DD/YYYY) : ");
                String startDateCuti = input.nextLine();
                System.out.print("Berakhir sampai (MM/DD/YYYY) : ");
                String endDateCuti = input.nextLine();
                System.out.print("Alasan : ");
                String reason = input.nextLine();
                System.out.print("Berapa hari cuti : ");
                Integer jumlahCuti = input.nextInt();

                inputFormCutiKaryawan(user.getId(), jumlahCuti, reason, startDateCuti, endDateCuti);
                break;
            case 4:
                System.out.print("Cek Slip Gaji di bulan ? (01-12) : ");
                String month = input.nextLine();

                System.out.println("==================================");

                Integer totalHariKerja = getTotalHariKerjaByMonth(user.getId(), month);
                Integer gajiKaryawan = user.getGaji_harian();

                System.out.println("Slip Gaji");
                System.out.println("Total Hari Kerja Bulan " + month + " : " + totalHariKerja);
                /* Total Hari Kerja * Gaji Harian */
                System.out.println("Total Gaji : " + totalHariKerja * gajiKaryawan);
                break;
            default:
                System.out.println("Menu Tidak Ada");
        }
    }
}
