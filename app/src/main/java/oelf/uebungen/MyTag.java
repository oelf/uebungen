package oelf.uebungen;

public class MyTag {
    int idkategorie;
    int idknoten;
    String bezeichnung;

    public MyTag() {
        idkategorie = 0;
        idknoten = 0;
    }

    public MyTag(int idkategorie) {
        this.idkategorie = idkategorie;
    }

    public MyTag(int idkategorie, int idknoten) {
        this.idkategorie = idkategorie;
        this.idknoten = idknoten;
    }
}
