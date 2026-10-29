import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OlayYonetimMerkezi merkez = new OlayYonetimMerkezi();
        VeriKayitYoneticisi kayitYoneticisi = VeriKayitYoneticisi.getInstance();

        // Baslangicta dosyadan kalici verileri yukle
        merkez.setOlayListesi(kayitYoneticisi.olaylariYukle());

        // Eger dosya bossa ornek baslangic verileri ekle
        if (merkez.getOlayListesi().isEmpty()) {
            merkez.olayEkle(OlayFabrikasi.olayOlustur("DDOS", "OLAY-101", "AnaWebSunucu", 6, 45.0));
            merkez.olayEkle(OlayFabrikasi.olayOlustur("FIDYE", "OLAY-102", "FinansDB", 9, 1200));
            merkez.olayEkle(OlayFabrikasi.olayOlustur("ERISIM", "OLAY-103", "AktifDizin", 8, 1.0));
            kayitYoneticisi.olaylariKaydet(merkez.getOlayListesi());
        }

        boolean devam = true;
        while (devam) {
            System.out.println("\n=======================================================");
            System.out.println("   SIBER GUVENLIK OLAY YONETIM VE TRIYAJ SISTEMI");
            System.out.println("=======================================================");
            System.out.println("1 - Guvenlik Olaylarini Oncelik Sirasina Gore Listele");
            System.out.println("2 - Yeni Siber Guvenlik Olayi Bildir (Ekle)");
            System.out.println("3 - Olaya Mudahale Et ve Cozumlendi Isaretle");
            System.out.println("4 - 8 Birim Testini (Unit Tests) Calistir");
            System.out.println("0 - Verileri Kaydet ve Cikis Yap");
            System.out.print("Seciminiz: ");

            String secim = scanner.nextLine().trim();

            switch (secim) {
                case "1":
                    merkez.onceliklendirVeSirala();
                    System.out.println("\n--- ONCELIKLENDIRILMIS GUVENLIK OLAYLARI LISTESI ---");
                    for (GuvenlikOlayi olay : merkez.getOlayListesi()) {
                        System.out.println(olay);
                        System.out.println("   -> Onerilen Mudahale: " + olay.mudahalePlaniUret());
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Olay ID (Orn: OLAY-104): ");
                        String id = scanner.nextLine().trim();

                        System.out.print("Olay Tipi (DDOS / FIDYE / ERISIM): ");
                        String tip = scanner.nextLine().trim().toUpperCase();

                        System.out.print("Hedef Sistem (Orn: MailSunucusu): ");
                        String hedef = scanner.nextLine().trim();

                        System.out.print("Kritiklik Seviyesi (1-10): ");
                        int kritiklik = Integer.parseInt(scanner.nextLine().trim());

                        double ekstra = 0;
                        if (tip.equals("DDOS")) {
                            System.out.print("Saldiri Trafigi Boyutu (Gbps): ");
                            ekstra = Double.parseDouble(scanner.nextLine().trim());
                        } else if (tip.equals("FIDYE")) {
                            System.out.print("Sifrelenen Dosya Sayisi: ");
                            ekstra = Double.parseDouble(scanner.nextLine().trim());
                        } else if (tip.equals("ERISIM")) {
                            System.out.print("Yonetici (Admin) Hesabi mi? (1: Evet, 0: Hayir): ");
                            ekstra = Double.parseDouble(scanner.nextLine().trim());
                        }

                        GuvenlikOlayi yeniOlay = OlayFabrikasi.olayOlustur(tip, id, hedef, kritiklik, ekstra);
                        merkez.olayEkle(yeniOlay);
                        kayitYoneticisi.olaylariKaydet(merkez.getOlayListesi());
                        System.out.println("Olay basariyla eklendi ve dosyaya kaydedildi!");
                    } catch (Exception e) {
                        System.out.println("Hata: Gecersiz veri girisi yaptiniz! (" + e.getMessage() + ")");
                    }
                    break;

                case "3":
                    System.out.print("Mudahale edilecek Olay ID: ");
                    String cozumId = scanner.nextLine().trim();
                    if (merkez.olayaMudahaleEt(cozumId)) {
                        kayitYoneticisi.olaylariKaydet(merkez.getOlayListesi());
                        System.out.println(cozumId + " ID'li olaya mudahale edildi ve durum [COZULDU] olarak kaydedildi.");
                    } else {
                        System.out.println("Bulunamadi veya olay zaten cozulmus!");
                    }
                    break;

                case "4":
                    SistemBirimTestleri.tumTestleriCalistir();
                    break;

                case "0":
                    kayitYoneticisi.olaylariKaydet(merkez.getOlayListesi());
                    System.out.println("Veriler 'guvenlik_olaylari.txt' dosyasina kaydedildi. Cikis yapiliyor...");
                    devam = false;
                    break;

                default:
                    System.out.println("Gecersiz secim!");
            }
        }
        scanner.close();
    }
}