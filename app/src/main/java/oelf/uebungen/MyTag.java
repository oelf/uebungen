package oelf.uebungen;

public class MyTag {
    public int idkategorie;
    public int idknoten;
    public String source;

    public MyTag() {
        idkategorie = 0;
        idknoten = 0;
    }

    public MyTag(String source, int idkategorie) {
        this.source = source;
        this.idkategorie = idkategorie;
    }

    public MyTag(String source, int idkategorie, int idknoten) {
        this.source = source;
        this.idkategorie = idkategorie;
        this.idknoten = idknoten;
    }
}
