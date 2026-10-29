public class FidyeYazilimiSaldirisi extends GuvenlikOlayi {
    private int sifrelenenDosyaSayisi;

    public FidyeYazilimiSaldirisi(String olayId, String hedefSistem, int kritiklikSeviyesi, int sifrelenenDosyaSayisi) {
        super(olayId, hedefSistem, kritiklikSeviyesi);
        this.sifrelenenDosyaSayisi = Math.max(1, sifrelenenDosyaSayisi);
    }

    public int getSifrelenenDosyaSayisi() {
        return sifrelenenDosyaSayisi;
    }

    @Override
    public String getOlayTuru() {
        return "Fidye Yazilimi";
    }

    @Override
    public double riskSkoruHesapla() {
        // Cok Bicimlilik (Polymorphism): Fidye yazilimina ozel risk formulu
        return (getKritiklikSeviyesi() * 12.0) + (sifrelenenDosyaSayisi * 0.05);
    }

    @Override
    public String mudahalePlaniUret() {
        return getHedefSistem() + " makinesini agdan derhal izole et ve " + sifrelenenDosyaSayisi + " dosya icin yedekten geri yukleme baslat.";
    }

    @Override
    public String dosyaFormatinaCevir() {
        return "FIDYE;" + getOlayId() + ";" + getHedefSistem() + ";" + getKritiklikSeviyesi() + ";" + isCozulduMu() + ";" + sifrelenenDosyaSayisi;
    }
}