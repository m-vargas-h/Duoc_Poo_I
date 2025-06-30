package com.duoc.exp2_s6;

import java.util.Scanner;
import com.duoc.exp2_s6.servicio.ComicCollectorService;
import com.duoc.exp2_s6.ui.MenuPrincipal;

public class Exp2_S6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ComicCollectorService servicio = new ComicCollectorService();
        new MenuPrincipal(sc, servicio).mostrar();
        sc.close();
    }
}