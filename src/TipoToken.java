public enum TipoToken {
    PALABRA_CLAVE,
    IDENTIFICADOR,
    LITERAL_NUMERICO,
    OPERADOR,
    DELIMITADOR
    // P.D: También necesitaremos un tipo para FIN_DE_ARCHIVO (EOF) más adelante para el parser,
    // pero de momento cíñete a lo que pide la práctica léxica.
}