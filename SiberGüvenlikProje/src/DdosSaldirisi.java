public class DdosSaldirisi extends GuvenlikOlayi {
    private double trafikBoyutuGbps;

    public DdosSaldirisi(String olayId, String hedefSistem, int kritiklikSeviyesi, double trafikBoyutuGbps) {
        super(olayId, hedefSistem, kritiklikSeviyesi);
        this.trafikBoyutuGbps = Math.max(0.1, trafikBoyutuGbps);
    }

    public double getTrafikBoyutuGbps() {
        return trafikBoyutuGbps;
    }

    @Override
    public String getOlayTuru() {
        return "DDoS Saldirisi";
    }

    @Override
    public double riskSkoruHesapla() {
        // Cok Bicimlilik (Polymorphism): DDoS'a ozel agirlikli risk formulu
        return (getKritiklikSeviyesi() * 8.5) + (trafikBoyutuGbps * 1.5);
    }

    @Override
    public String mudahalePlaniUret() {
        return "CDN korumasini aktif et, " + trafikBoyutuGbps + " Gbps trafigi filtrele ve IP kara listesini guncelle.";
    }

    @Override
    public String dosyaFormatinaCevir() {
        return "DDOS;" + getOlayId() + ";" + getHedefSistem() + ";" + getKritiklikSeviyesi() + ";" + isCozulduMu() + ";" + trafikBoyutuGbps;
    }
}