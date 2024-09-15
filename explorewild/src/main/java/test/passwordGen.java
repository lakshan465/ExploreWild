package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class passwordGen {

    static String getHashPwd(String pwd) {
        try {
            MessageDigest md = null;

            md = MessageDigest.getInstance("SHA");

            md.update(pwd.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : rbt) {

                sb.append(String.format("%02x", b));

            }


            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Enter text = ");
        Scanner sc=new Scanner(System.in);

        String s=sc.nextLine();

        System.out.println(getHashPwd(s));


    }

}
