package util;

public class pruebaHash {
    public static void main(String[] args) {
        System.out.println("Hash: --"+util.PasswordHasher.hash("admin123")+"--");
        System.out.println("Hash: --"+util.PasswordHasher.hash("admin123")+"--");
        System.out.println("Hash: --"+util.PasswordHasher.hash("admin123")+"--");
    }
}
