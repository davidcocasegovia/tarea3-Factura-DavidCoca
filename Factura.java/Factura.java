import java.util.Scanner;
public class Factura {
    static final double IVA=0.21;
    static final String PROPIETARIO="davidcoca";
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        System.out.println("=== FACTURA de " + PROPIETARIO + " ===");
        System.out.print("Producto: ");
        String producto = sc.nextLine();
        System.out.print("Precio unitario: ");
        double precio = sc.nextDouble();
        System.out.print("Cantidad: ");
        int cantidad = sc.nextInt();
        System.out.print("Es cliente socio (s/n): ");
        boolean socio = sc.next().equalsIgnoreCase("s");
        double base = precio * cantidad;
        double descuento = calcularDescuento(base, socio);
        double baseConDescuento = base - descuento;
        double total = aplicarIVA(baseConDescuento);

        System.out.println("\n--- RESUMEN FACTURA ---");
        System.out.println("Producto: " + producto);
        System.out.printf("Base imponible: %.2f euros%n", base);
        System.out.printf("Descuento aplicado: %.2f euros%n", descuento);
        System.out.printf("Total con IVA: %.2f euros%n", total);
        
        sc.close();
    }
public static double calcularDescuento(double base, boolean socio) {
        double pctDescuento = 0.0;
        if (base > 100) {
            pctDescuento += 0.10;
        }
        if (socio) {
            pctDescuento += 0.05;
        }
        return base * pctDescuento;
    }

    public static double aplicarIVA(double importe) {
        return importe * (1 + IVA);
    }
}