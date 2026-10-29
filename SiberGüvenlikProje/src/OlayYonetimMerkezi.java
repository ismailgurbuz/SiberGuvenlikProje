import java.util.ArrayList;
import java.util.List;

// SOLID: Dependency Inversion & Liskov Substitution (Somut siniflara degil, soyut GuvenlikOlayi sinifina bagimlidir)
public class OlayYonetimMerkezi {
    private List<GuvenlikOlayi> olayListesi;

    public OlayYonetimMerkezi() {
        this.olayListesi = new ArrayList<>();
    }

    public void setOlayListesi(List<GuvenlikOlayi> olayListesi) {
        this.olayListesi = olayListesi;
    }

    public List<GuvenlikOlayi> getOlayListesi() {
        return olayListesi;
    }

    public void olayEkle(GuvenlikOlayi olay) {
        olayListesi.add(olay);
    }

    // ALGORITMA: Agirlikli Risk Skoruna ve Cozum Durumuna Gore Triyaj Siralama Algoritmasi (Selection Sort Tabanli)
    // Cozulmemis (aktif) olaylari en basa alir ve kendi iclerinde en yuksek risk skorundan en dusuge dogru siralar.
    public void onceliklendirVeSirala() {
        int n = olayListesi.size();
        for (int i = 0; i < n - 1; i++) {
            int enYuksekOncelikIndeksi = i;
            for (int j = i + 1; j < n; j++) {
                if (ikinciOlayDahaOncelikliMi(olayListesi.get(enYuksekOncelikIndeksi), olayListesi.get(j))) {
                    enYuksekOncelikIndeksi = j;
                }
            }
            GuvenlikOlayi gecici = olayListesi.get(enYuksekOncelikIndeksi);
            olayListesi.set(enYuksekOncelikIndeksi, olayListesi.get(i));
            olayListesi.set(i, gecici);
        }
    }

    private boolean ikinciOlayDahaOncelikliMi(GuvenlikOlayi mevcut, GuvenlikOlayi aday) {
        // 1. Kriter: Cozulmemis olay her zaman cozulmus olaydan daha onceliklidir
        if (mevcut.isCozulduMu() && !aday.isCozulduMu()) {
            return true;
        }
        if (!mevcut.isCozulduMu() && aday.isCozulduMu()) {
            return false;
        }
        // 2. Kriter: Ikisinin de durumu ayniysa Risk Skoru buyuk olan daha onceliklidir
        return aday.riskSkoruHesapla() > mevcut.riskSkoruHesapla();
    }

    public boolean olayaMudahaleEt(String olayId) {
        for (GuvenlikOlayi olay : olayListesi) {
            if (olay.getOlayId().equalsIgnoreCase(olayId) && !olay.isCozulduMu()) {
                olay.setCozulduMu(true);
                return true;
            }
        }
        return false;
    }
}