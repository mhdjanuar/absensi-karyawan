package models;

public class Cuti {
    private Integer idCuti;
    private Integer idKaryawan;
    private String nameKaryawan;
    private Integer jumlahCuti;
    private String alasan;
    private String startDateCuti;
    private String endDateCuti;

    public Integer getIdCuti() {
        return idCuti;
    }

    public void setIdCuti(Integer idCuti) {
        this.idCuti = idCuti;
    }

    public Integer getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(Integer idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public Integer getJumlahCuti() {
        return jumlahCuti;
    }

    public void setJumlahCuti(Integer jumlahCuti) {
        this.jumlahCuti = jumlahCuti;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getStartDateCuti() {
        return startDateCuti;
    }

    public void setStartDateCuti(String startDateCuti) {
        this.startDateCuti = startDateCuti;
    }

    public String getEndDateCuti() {
        return endDateCuti;
    }

    public void setEndDateCuti(String endDateCuti) {
        this.endDateCuti = endDateCuti;
    }

    public String getNameKaryawan() {
        return nameKaryawan;
    }

    public void setNameKaryawan(String nameKaryawan) {
        this.nameKaryawan = nameKaryawan;
    }
}
