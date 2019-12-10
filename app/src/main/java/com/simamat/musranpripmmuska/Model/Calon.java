package com.simamat.musranpripmmuska.Model;

public class Calon {

    private String image;
    private String nama;

    public Calon() {
    }

    public Calon(String image, String nama) {
        this.image = image;
        this.nama = nama;
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
}
