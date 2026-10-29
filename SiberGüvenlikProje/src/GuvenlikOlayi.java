public abstract class GuvenlikOlayi implements IMudahaleEdilebilir {
    private String olayId;
    private String hedefSistem;
    private int kritiklikSeviyesi; // 1 ile 10 arasi
    private boolean cozulduMu;

    public GuvenlikOlayi(String olayId, String hedefSistem, int kritiklikSeviyesi) {
        this.olayId = olayId;
        this.hedefSistem = hedefSistem;
        setKritiklikSeviyesi(kritiklikSeviyesi);
        this.cozulduMu = false;
    }

    // Kapsulleme (Encapsulation) - Getter ve Setter metotlari
    public String getOlayId() {
        return olayId;
    }

    public String getHedefSistem() {
        return hedefSistem;
    }

    public void setHedefSistem(String hedefSistem) {
        this.hedefSistem = hedefSistem;
    }

    public int getKritiklikSeviyesi() {
        return kritiklikSeviyesi;
    }

    public void setKritiklikSeviyesi(int kritiklikSeviyesi) {
        if (kritiklikSeviyesi < 1) {
            this.kritiklikSeviyesi = 1;
        } else if (kritiklikSeviyesi > 10) {
            this.kritiklikSeviyesi = 10;
        } else {
            this.kritiklikSeviyesi = kritiklikSeviyesi;
        }
    }

    public boolean isCozulduMu() {
        return cozulduMu;
    }

    public void setCozulduMu(boolean cozulduMu) {
        this.cozulduMu = cozulduMu;
    }

    public abstract String getOlayTuru();

    @Override
    public String toString() {
        String durum = cozulduMu ? "[COZULDU]" : "[AKTIF]";
        return String.format("%s ID: %s | Tur: %-15s | Hedef: %-15s | Kritiklik: %d/10 | Risk Skoru: %.2f",
                durum, olayId, getOlayTuru(), hedefSistem, kritiklikSeviyesi, riskSkoruHesapla());
    }
}