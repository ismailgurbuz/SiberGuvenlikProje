public class YetkisizErisimSaldirisi extends GuvenlikOlayi {
    private boolean yoneticiHesabiMi;

    public YetkisizErisimSaldirisi(String olayId, String hedefSistem, int kritiklikSeviyesi, boolean yoneticiHesabiMi) {
        super(olayId, hedefSistem, kritiklikSeviyesi);
        this.yoneticiHesabiMi = yoneticiHesabiMi;
    }

    public boolean isYoneticiHesabiMi() {
        return yoneticiHesabiMi;
    }

    @Override
    public String getOlayTuru() {
        return "Yetkisiz Erisim";
    }

    @Override
    public double riskSkoruHesapla() {
        // Cok Bicimlilik (Polymorphism): Yetkisiz erisime ozel risk formulu
        double katSayi = yoneticiHesabiMi ? 40.0 : 10.0;
        return (getKritiklikSeviyesi() * 9.0) + katSayi;
    }

    @Override
    public String mudahalePlaniUret() {
        String ekOnlem = yoneticiHesabiMi ? "TUM ADMIN TOKENLARINI IPTAL ET!" : "Kullanici parolasini sifirla.";
        return getHedefSistem() + " uzerindeki oturumlari sonlandir. " + ekOnlem;
    }

    @Override
    public String dosyaFormatinaCevir() {
        return "ERISIM;" + getOlayId() + ";" + getHedefSistem() + ";" + getKritiklikSeviyesi() + ";" + isCozulduMu() + ";" + yoneticiHesabiMi;
    }
}