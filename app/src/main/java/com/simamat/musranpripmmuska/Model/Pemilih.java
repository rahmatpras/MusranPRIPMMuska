package com.simamat.musranpripmmuska.Model;

public class Pemilih {
    private String id,nama,kelas,pilihan;


    public Pemilih() {
    }

    public Pemilih(String id, String nama, String kelas, String pilihan) {
        this.id = id;
        this.nama = nama;
        this.kelas = kelas;
        this.pilihan = pilihan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getPilihan() {
        return pilihan;
    }

    public void setPilihan(String pilihan) {
        this.pilihan = pilihan;
    }
}
