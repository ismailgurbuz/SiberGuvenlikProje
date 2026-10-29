
public class OlayFabrikasi {

    public static GuvenlikOlayi olayOlustur(String tip, String id, String hedef, int kritiklik, double ekstraParametre) {
        if (tip == null) {
            throw new IllegalArgumentException("Olay tipi bos olamaz!");
        }

        switch (tip.toUpperCase()) {
            case "DDOS":
                return new DdosSaldirisi(id, hedef, kritiklik, ekstraParametre);
            case "FIDYE":
                return new FidyeYazilimiSaldirisi(id, hedef, kritiklik, (int) ekstraParametre);
            case "ERISIM":
                boolean adminMi = ekstraParametre >= 1.0;
                return new YetkisizErisimSaldirisi(id, hedef, kritiklik, adminMi);
            default:
                throw new IllegalArgumentException("Gecersiz olay tipi: " + tip);
        }
    }
}