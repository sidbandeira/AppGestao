package com.example.sidnei.appgestao.utilitario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacoes {
    public boolean validarCPF(String cpf) {
        String regex = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }

    public boolean validarFone(String fone) {
        String regex = "^\\(?\\d{2}\\)?[\\s-]?\\d{4}-?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fone);
        return matcher.matches();
    }

    public static boolean validarData(String data) {
        //String regex = "^([0-9]|[0,1,2][0-9]|3[0,1])/([\\d]|1[0,1,2])/\\d{4}$";
        //String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}$";
        String regex = "^(0[1-9]|[12][0-9]|3[01])/.(0[1-9]|1[012])/.(19|20)/d/d";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    public boolean validarNumero(String numero) {
        String regex = "^\\d*[0-9](\\.\\d*[0-9])?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    public boolean validarEndereco(String endereco) {
        String regex = "^(RUA|Rua|R.|AVENIDA|Avenida|AV.|TRAVESSA|Travessa|TRAV.|Trav.) ([a-zA-Z_\\s]+)[, ]+(\\d+)\\s?([-/\\da-zDA-Z\\\\ ]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(endereco);
        return matcher.matches();
    }

    public static boolean validaData(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
