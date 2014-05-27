package Model;
import static Model.Token.*;
%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%public
%class PascalLexer
%final
%unicode
%char
%type Token
%caseless

%{
%}


/* main characteres */

finLinea        = \r|\n|\r\n
caracterEntrada = [^\r\n]
EspacioBlanco   = {finLinea}|[ \t\f]

/* identifier */
identifier      = [:jletter:][:jletterdigit:]*

/* integer */
EnteroDecimal         = 0|[1-9][0-9]*

/* floating point literals */
LiteralFlotante = ({FLit1}|{FLit2}|{FLit3}) {Exponente}? [fF]
LiteralDoble = ({FLit1}|{FLit2}|{FLit3}) {Exponente}?

FLit1 = [0-9]+ \. [0-9]*
FLit2 = \. [0-9]+
FLit3 = [0-9]+
Exponente = [eE] [+-]? [0-9]+

/* string and character */
CadenaCaracter = [^\r\n\"\\]
UnCaracter = [^\r\n\'\\]

keyword        = 
"AND"|
"ARRAY"|
"BEGIN"|
"CASE"|
"CONST"|
"DIV"|
"DO"|
"DOWNTO"|
"ELSE"|
"END"|
"FILE"|
"FOR"|
"FUNCTION"|
"GOTO"|
"IF"|
"IN"|
"LABEL"|
"MOD"|
"NIL"|
"NOT"
"OF"|
"OR"|
"PROCEDURE"|
"PROGRAM"|
"RECORD"|
"REPEAT"|
"SET"|
"THEN"|
"TO"|
"TYPE"|
"UNTIL"|
"VAR"|
"WHILE"|
"WITH"

%%
/**
 * LEXICAL RULES:
 */
<YYINITIAL>{

/* keyword */
{keyword}   { return RESERVADA; }

/* operadores */
"*"             { return OP; }
"+"             { return OP; }
".."            { return OP; }
"-"             { return OP; }
"/"             { return OP; }
","             { return OP; }
"("             { return OP; }
")"             { return OP; }
"["             { return OP; }
"]"             { return OP; }
"="             { return OP; }
"<"             { return OP; }
">"             { return OP; }
"<="            { return OP; }
">="            { return OP; }
"<>"            { return OP; }
"!="            { return OP; }
":"             { return OP; }
"=="            { return OP; }
":="            { return OP; }

";"             { return SEMI; }
"."             { return DOT; }

/* identifier */
{identifier}    { return IDENT; }

/* number */
{EnteroDecimal}       { return NUMERO; }
{LiteralFlotante}     { return NUMERO; }
{LiteralDoble}        { return NUMERO; }

/* string */
\"{CadenaCaracter}+\"        { return CADENA; }
\'{UnCaracter}+\'            { return CADENA; }

/* espacio blanco */
{EspacioBlanco}+ { return BLANCO; }

}

/* error fallback */
.               { return ERROR; }
