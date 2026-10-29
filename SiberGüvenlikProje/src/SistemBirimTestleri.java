import java.util.List;

public class SistemBirimTestleri {

    public static void tumTestleriCalistir() {
        System.out.println("\n=== 8 BIRIM TESTI (UNIT TEST) BASLATILIYOR ===");
        int basarili = 0;

        if (test1_KapsullemeSinirKontrolu()) basarili++;
        if (test2_DdosRiskSkoruHesaplama()) basarili++;
        if (test3_FidyeRiskSkoruHesaplama()) basarili++;
        if (test4_YetkisizErisimAdminFarki()) basarili++;
        if (test5_FactoryNesneUretimi()) basarili++;
        if (test6_SingletonTekNesneKontrolu()) basarili++;
        if (test7_TriyajSiralamaAlgoritmasi()) basarili++;
        if (test8_MudahaleVeDurumGuncelleme()) basarili++;

        System.out.println("=== TEST SONUCU: " + basarili + "/8 TEST BASARIYLA GECTI ===\n");
    }

    // Test 1: Kapsulleme ( Kritiklik seviyesi 1-10 disina cikamamali )
    public static boolean test1_KapsullemeSinirKontrolu() {
        GuvenlikOlayi olay = new DdosSaldirisi("T1", "Sunucu1", 99, 10.0);
        boolean sonuc = (olay.getKritiklikSeviyesi() == 10);
        raporla("Test 1 - Kapsulleme Sinir Kontrolu", sonuc);
        return sonuc;
    }

    // Test 2: DDoS Risk Skoru Hesaplama (Cok Bicimlilik)
    public static boolean test2_DdosRiskSkoruHesaplama() {
        GuvenlikOlayi ddos = new DdosSaldirisi("T2", "WebAPI", 5, 20.0);
        // (5 * 8.5) + (20 * 1.5) = 42.5 + 30.0 = 72.5
        boolean sonuc = Math.abs(ddos.riskSkoruHesapla() - 72.5) < 0.01;
        raporla("Test 2 - DDoS Risk Skoru Hesaplama", sonuc);
        return sonuc;
    }

    // Test 3: Fidye Yazilimi Risk Skoru Hesaplama
    public static boolean test3_FidyeRiskSkoruHesaplama() {
        GuvenlikOlayi fidye = new FidyeYazilimiSaldirisi("T3", "Veritabani", 8, 200);
        // (8 * 12.0) + (200 * 0.05) = 96.0 + 10.0 = 106.0
        boolean sonuc = Math.abs(fidye.riskSkoruHesapla() - 106.0) < 0.01;
        raporla("Test 3 - Fidye Yazilimi Risk Skoru Hesaplama", sonuc);
        return sonuc;
    }

    // Test 4: Yetkisiz Erisim Admin Katsayisi Farki
    public static boolean test4_YetkisizErisimAdminFarki() {
        GuvenlikOlayi normalErisim = new YetkisizErisimSaldirisi("T4A", "Panel", 5, false);
        GuvenlikOlayi adminErisim = new YetkisizErisimSaldirisi("T4B", "Panel", 5, true);
        boolean sonuc = adminErisim.riskSkoruHesapla() > normalErisim.riskSkoruHesapla();
        raporla("Test 4 - Yetkisiz Erisim Admin Farki", sonuc);
        return sonuc;
    }

    // Test 5: Factory Design Pattern dogru alt sinif uretiyor mu?
    public static boolean test5_FactoryNesneUretimi() {
        GuvenlikOlayi olay = OlayFabrikasi.olayOlustur("FIDYE", "T5", "MuhasebePC", 7, 500);
        boolean sonuc = (olay instanceof FidyeYazilimiSaldirisi);
        raporla("Test 5 - Factory Pattern Nesne Uretimi", sonuc);
        return sonuc;
    }

    // Test 6: Singleton Design Pattern ayni referansi donuyor mu?
    public static boolean test6_SingletonTekNesneKontrolu() {
        VeriKayitYoneticisi v1 = VeriKayitYoneticisi.getInstance();
        VeriKayitYoneticisi v2 = VeriKayitYoneticisi.getInstance();
        boolean sonuc = (v1 == v2);
        raporla("Test 6 - Singleton Pattern Tekil Ornek Kontrolu", sonuc);
        return sonuc;
    }

    // Test 7: Siralama Algoritmasi en yuksek riskli olayi en basa aliyor mu?
    public static boolean test7_TriyajSiralamaAlgoritmasi() {
        OlayYonetimMerkezi merkez = new OlayYonetimMerkezi();
        GuvenlikOlayi dusuk = new DdosSaldirisi("DUSUK", "S1", 2, 5.0);
        GuvenlikOlayi yuksek = new FidyeYazilimiSaldirisi("YUKSEK", "S2", 10, 1000);
        merkez.olayEkle(dusuk);
        merkez.olayEkle(yuksek);
        merkez.onceliklendirVeSirala();
        List<GuvenlikOlayi> liste = merkez.getOlayListesi();
        boolean sonuc = liste.get(0).getOlayId().equals("YUKSEK");
        raporla("Test 7 - Triyaj Oncelik Siralama Algoritmasi", sonuc);
        return sonuc;
    }

    // Test 8: Olaya mudahale edildiginde durumu cozuldu olarak guncelleniyor mu?
    public static boolean test8_MudahaleVeDurumGuncelleme() {
        OlayYonetimMerkezi merkez = new OlayYonetimMerkezi();
        GuvenlikOlayi olay = new DdosSaldirisi("OLAY8", "DNS", 6, 15.0);
        merkez.olayEkle(olay);
        boolean islem = merkez.olayaMudahaleEt("OLAY8");
        boolean sonuc = islem && olay.isCozulduMu();
        raporla("Test 8 - Mudahale ve Durum Guncelleme", sonuc);
        return sonuc;
    }

    private static void raporla(String testAdi, boolean gectiMi) {
        System.out.println((gectiMi ? "[BASARILI] " : "[HATALI]   ") + testAdi);
    }
}