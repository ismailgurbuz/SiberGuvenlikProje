import java.io.*;
import java.util.ArrayList;
import java.util.List;

// TASARIM KALIBI 2: Singleton Pattern
// Kalici veri saklama (File I/O) gorevini tek basina ustlenir (Single Responsibility)
public class VeriKayitYoneticisi {
    private static VeriKayitYoneticisi instance;
    private final String dosyaAdi = "guvenlik_olaylari.txt";

    private VeriKayitYoneticisi() {
    }

    public static synchronized VeriKayitYoneticisi getInstance() {
        if (instance == null) {
            instance = new VeriKayitYoneticisi();
        }
        return instance;
    }

    public void olaylariKaydet(List<GuvenlikOlayi> olaylar) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi))) {
            for (GuvenlikOlayi olay : olaylar) {
                writer.write(olay.dosyaFormatinaCevir());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Dosya kaydetme hatasi: " + e.getMessage());
        }
    }

    public List<GuvenlikOlayi> olaylariYukle() {
        List<GuvenlikOlayi> yuklenenOlaylar = new ArrayList<>();
        File file = new File(dosyaAdi);
        if (!file.exists()) {
            return yuklenenOlaylar;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length == 6) {
                    String tip = parcalar[0];
                    String id = parcalar[1];
                    String hedef = parcalar[2];
                    int kritiklik = Integer.parseInt(parcalar[3]);
                    boolean cozuldu = Boolean.parseBoolean(parcalar[4]);

                    double ekstra = 0;
                    if (tip.equals("ERISIM")) {
                        ekstra = Boolean.parseBoolean(parcalar[5]) ? 1.0 : 0.0;
                    } else {
                        ekstra = Double.parseDouble(parcalar[5]);
                    }

                    GuvenlikOlayi olay = OlayFabrikasi.olayOlustur(tip, id, hedef, kritiklik, ekstra);
                    olay.setCozulduMu(cozuldu);
                    yuklenenOlaylar.add(olay);
                }
            }
        } catch (Exception e) {
            System.out.println("Dosya okuma hatasi: " + e.getMessage());
        }
        return yuklenenOlaylar;
    }
}