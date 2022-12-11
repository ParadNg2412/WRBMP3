package com.example.wrbmp3;

public class Song {
    private String Name;
    private int File;

    public Song(String name, int file) {
        Name = name;
        File = file;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
