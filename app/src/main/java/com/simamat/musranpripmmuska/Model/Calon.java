package com.simamat.musranpripmmuska.Model;

public class Calon {

    private String image;
    private String nama;
    private String nomer;

    public Calon() {
    }

    public Calon(String image, String nama, String nomer) {
        this.image = image;
        this.nama = nama;
        this.nomer = nomer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }
}
