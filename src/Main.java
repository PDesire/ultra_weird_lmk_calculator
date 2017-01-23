/*
 * Android Low Memory Killer Script Version 3.0
 * Copyright (C) Tristan Marsell <tristan.marsell@t-online.de>
 * This software is published under GPL v3
 * I TAKE NO WARRANTY/RESPONSIBILITY FOR THIS SOFTWARE
 */
import java.util.*;

/**
 *
 * @author PDesire
 */
public class Main {
    
    //Enable Debug
    public static boolean debug = true;
    public static void pr_debug(String text) {
        if (debug == true)
            System.out.println(text);
    }

    /*
     * totalmemory (Max amount of RAM)
     */
    private static int totalmemory;

    /**
     * @return the totalmemory
     */
    public static int getTotalmemory() {
        return totalmemory;
    }

    /**
     * @param aTotalmemory the totalmemory to set
     * 
     */
    public static void setTotalmemory(int aTotalmemory) {
        totalmemory = aTotalmemory;
    }

    /**
     *
     * @param ramsize
     * @param strength
     */
    public static void preCalculation(
            int ramsize,
            int strength) {
        double LMK1;
        //Calclulate divisor for formula
        double divisor = ramsize / 256;

        //Use "Circle Division" formula
        pr_debug("Calculating with Circle Division Formula...");
        LMK1 = ((Math.sqrt(ramsize) * Math.sqrt(2) / Math.sqrt(3.14)) + (Math.sqrt(3.14) * ramsize) / divisor) / (Math.sqrt(3.14) * 2) / (3.14 * 2);

        //Multiplicate with some important Coefficients
        switch (strength) {
            case 1:
                System.out.println("");
                System.out.println("You have chosen Aggressive");
                LMK1 *= 1.5;
                break;
            case 2:
                System.out.println("");
                System.out.println("You have chosen Balanced");
                LMK1 *= 1.1;
                break;
            case 3:
                System.out.println("");
                System.out.println("You have chosen Multitasking");
                LMK1 /= 1.5;
                break;

            default:
                break;
        }

        //Low Memory Killer value generating
        pr_debug("Generating LMK values...");
        double LMK2 = LMK1 * 2; //Low Memory Killer 2
        double LMK3 = LMK1 * 3; //Low Memory Killer 3
        double LMK4 = LMK1 * 4; //Low Memory Killer 4
        double LMK5 = LMK1 * 5; //Low Memory Killer 5
        double LMK6 = LMK1 * 6; //Low Memory Killer 6 

        //Push values to folding method
        pr_debug("Go to Folding Algorithm...");
        folding(LMK1, LMK2, LMK3, LMK4, LMK5, LMK6, strength);
    }

    /**
     *
     * @param LMK1 1st Low Memory Killer value from preCalculus method
     * @param LMK2 2nd Low Memory Killer value from preCalculus method
     * @param LMK3 3rd Low Memory Killer value from preCalculus method
     * @param LMK4 4th Low Memory Killer value from preCalculus method
     * @param LMK5 5th Low Memory Killer value from preCalculus method
     * @param LMK6 6th Low Memory Killer value from preCalculus method
     * @param strength is the chosen strength from previous main and preCalculus
     * method
     */
    public static void folding(
            double LMK1,
            double LMK2,
            double LMK3,
            double LMK4,
            double LMK5,
            double LMK6,
            int strength) {
        /*
         * here begins the folding algorithm
         */
        double[] folder = new double[4];
        double[] store = {LMK1, LMK2, LMK3, LMK4, LMK5, LMK6};
        double[] LMKfolded = new double[7];
        double cache;
        int i = 0;

        //Folding Algorithm for LMK1-LMK4
        while (i != 5) {
            pr_debug("Calculating with Folding Algorithm attempt " + i + "...");
            //Push values to store array
            folder[0] = store[i];
            folder[1] = store[i + 1];
            folder[2] = 2 / store[i];
            folder[3] = 2 / store[i + 1];

            //Fold Values
            folder[0] /= folder[2];
            folder[1] /= folder[3];
            folder[2] /= folder[3];
            cache = folder[1] / folder[2];
            cache /= LMK1;

            //Divide with some important coefficients
            switch (strength) {
                case 1:
                    LMKfolded[i] = cache / 3.2;
                    break;

                case 2:
                    LMKfolded[i] = cache / 3.5;
                    break;

                case 3:
                    LMKfolded[i] = cache / 4.2;
                    break;
            }
            i++;
        }

        //Don't calculate LMK5 and LMK6 to use them as boost values for LMK
        pr_debug("Setting boost values...");
        LMKfolded[i] = LMK5;
        LMKfolded[i + 1] = LMK6;

        //Push values to postCalculus method
        pr_debug("Go to Cooeficient Algorithm...");
        postCalculation(LMKfolded[0], LMKfolded[1], LMKfolded[2], LMKfolded[3], LMKfolded[4], LMKfolded[5]);
    }

    /**
     *
     * @param LMK1folded 1st folded Low Memory Killer value from folding method
     * @param LMK2folded 2nd folded Low Memory Killer value from folding method
     * @param LMK3folded 3rd folded Low Memory Killer value from folding method
     * @param LMK4folded 4th folded Low Memory Killer value from folding method
     * @param LMK5folded 5th folded Low Memory Killer value from folding method
     * @param LMK6folded 6th folded Low Memory Killer value from folding method
     */
    public static void postCalculation(
            double LMK1folded,
            double LMK2folded,
            double LMK3folded,
            double LMK4folded,
            double LMK5folded,
            double LMK6folded) {
        double LMK1post, LMK2post, LMK3post, LMK4post, LMK5post, LMK6post;
        pr_debug("Calculating with cooeficients...");
        //Coefficient calculating to fix bugs
        double coefficient1 = 2.7;
        double coefficient2 = 1.7;
        LMK1folded *= coefficient1;
        LMK2folded *= coefficient2;

        //Roundup to get integer like values
        pr_debug("Round values...");
        LMK1post = Math.round(LMK1folded * 1) / 1;
        LMK2post = Math.round(LMK2folded * 1) / 1;
        LMK3post = Math.round(LMK3folded * 1) / 1;
        LMK4post = Math.round(LMK4folded * 1) / 1;
        LMK5post = Math.round(LMK5folded * 1) / 1;
        LMK6post = Math.round(LMK6folded * 1) / 1;
        output(LMK1post, LMK2post, LMK3post, LMK4post, LMK5post, LMK6post);
    }

    /**
     *
     * @param LMK1
     * @param LMK2
     * @param LMK3
     * @param LMK4
     * @param LMK5
     * @param LMK6
     */
    public static void output(double LMK1, double LMK2, double LMK3, double LMK4, double LMK5, double LMK6) {

        pr_debug("Convert numbers...");
        //Calculate in Kilobytes and then output
        System.out.println("");
        System.out.println("Your perfect LMK Settings in Kilobytes: ");
        System.out.println(LMK1 * 1024);
        System.out.println(LMK2 * 1024);
        System.out.println(LMK3 * 1024);
        System.out.println(LMK4 * 1024);
        System.out.println(LMK5 * 1024);
        System.out.println(LMK6 * 1024);
        System.out.println("");

        //Output raw values without format conversion
        System.out.println("Your perfect LMK Settings in Megabytes: ");
        System.out.println(LMK1);
        System.out.println(LMK2);
        System.out.println(LMK3);
        System.out.println(LMK4);
        System.out.println(LMK5);
        System.out.println(LMK6);
        //Output fin 
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // Set some info
        System.out.println("ANDROID LOW MEMORY KILLER CALCULATOR");
        System.out.println("");
        System.out.println("I AM NOT RESPONSIBLE FOR ANY DAMAGES ON YOUR PHONE");
        System.out.println("");
        //Activate Scanner
        Scanner sc = new Scanner(System.in);
        System.out.println("Please insert your total RAM of your Phone here: ");
        //Get RAM of phone
        int totalram = sc.nextInt();
        System.out.println("");
        System.out.print("Now set the Strengh of the LMK which you want: ");
        System.out.println("");
        System.out.println("WARNING!!");
        System.out.println("That are the values for the strengh:");
        System.out.println("1 = Aggressive");
        System.out.println("2 = Balanced");
        System.out.println("3 = Multitasking");
        System.out.println("");
        System.out.print("Your Choice: ");
        //Set Strength of LMK
        int power = sc.nextInt();
        //Comes soon for future releases
        setTotalmemory(power);

        //Push values to preCalculus method
        preCalculation(totalram, power);
    }
}
