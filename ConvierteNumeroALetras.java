/**
 *
 * @author Fernando Niño Jaimes
 */
public class ConvierteNumeroALetras {
    private static String[] unidades = {"un", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private static String[] dieces = {"once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "diecicocho", "diecinueve"};
    private static String[] decenas = {"diez", "veinti", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
    private static String[] centenas = {"ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};
    private static String[] unidadesMillones = {"mil", "millón", "mil", "billón", "mil", "trillón",
                                            "mil", "cuatrillón", "mil", "quintillón", "mil", "sextillón",
                                            "mil", "septillón", "mil", "octallón", "mil", "nonallón",
                                            "mil", "decallón"};
    private static String[] millones = {"mil", "millones", "mil", "billones", "mil", "trillones",
                                            "mil", "cuatrillones", "mil", "quintillones", "mil", "sextillones",
                                            "mil", "septillones", "mil", "octallones", "mil", "nonallones",
                                            "mil", "decallones", "mil"};

    public static String ConvierteALetras(String numero){
        numero = numero.trim();
        numero = QuitaCaracterIzq(numero, '0');
        if(numero.length() == 0) numero = "0";
        if((millones.length) * 3 <= (numero.length() - 1)) return "No soportado";
        if(Double.parseDouble(numero) == 0) return "cero";
        return Convierte(numero);
    }
    
    private static String ConvierteDecenas(double numero){
        int num = (int) ((numero % 100));
        int decena = (int) ((numero % 100 - numero % 10) / 10);
        int decimal = (int) ((numero % 10));

        if(num == 20){ //Validar si es 20
            return "veinte";
        }else if(decimal == 0){ //Validar si no tiene unidades
            if(decena == 0) return "";
            return decenas[decena - 1];
        }else if(num >= 11 && num <= 19) {  //Validar si esta entre 11 y 19
            return dieces[num - 11];
        }else{
            if(decena > 0)
                return decenas[decena - 1] + (num >= 21 && num <= 29 ? "" : " y ") + unidades[decimal - 1];
            else
                return unidades[decimal - 1];
        }
    }

    private static String ConvierteCentenas(double numero){
        int num = (int) ((numero % 1000));
        int centena = (int) ((numero % 1000 - numero % 100) / 100);
        int resto = (int) num - centena * 100;
        String letras = "";

        if(num == 100){
            letras =  "cien";
        }else if(centena>0){
            letras = centenas[centena - 1];
        }
        return letras + " " +  ConvierteDecenas(resto);
    }

    private static String QuitaCaracterIzq(String cadena, char car){
        while (cadena.length() > 0){
            if(cadena.charAt(0) == car)
                cadena = cadena.substring(1, cadena.length());
            else
                break;
        }
        return cadena;
    }
    private static String Convierte(String numero){
        if(numero.length() == 0) return "";
        int complemento = (int) (numero.length() - 1) / 3;
        int lenNum = ((numero.length() - 1) % 3) + (complemento >= 0 ? 1 : 0);
        String num = numero.substring(0, lenNum);
        String resta = "";
        if((numero.length() - lenNum) > 0)
            resta = numero.substring(lenNum, numero.length());
        String letras = ConvierteCentenas(Double.parseDouble(num)).trim();
        //Validar si lleva complemento
        if(complemento - 1 >= 0 && (letras.length() > 0 || complemento % 2 == 0)){
            //Validar si debe llevar palabra: un
            if(Double.parseDouble(num) == 1 && numero.length() > 3){
                    return letras.trim() + " " + unidadesMillones[complemento - 1] + " " + Convierte(resta).trim();
            } else{ //validar si se trata de millones
                return letras + " " + millones[complemento - 1] + " " + Convierte(resta).trim();
            }
        } else{
            if((Double.parseDouble(num) % 10 == 1) && Double.parseDouble(num) != 11 && Double.parseDouble(num) != 111 && numero.length() <= 3)
                return letras + "o " + Convierte(resta).trim();
            else
                return letras + " " + Convierte(resta).trim();
        }
    }
}
