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
integer         = 0|[1-9][0-9]*

/* real */
real            = {integer}\.[0-9]{exponente}
exponente       = E[+-]?{integer}

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
"!="            { return OP; }
":"             { return OP; }
":="            { return OP; }

";"             { return SEMI; }
"."             { return DOT; }

/* identifier */
{identifier}    { return IDENT; }

/* number */
{integer}       { return INT; }
{real}          { return REAL; }

/* string */
\"{CadenaCaracter}+\"        { return CADENA; }
\'{UnCaracter}+\'            { return CADENA; }

/* espacio blanco */
{EspacioBlanco}+ { return BLANCO; }

}

/* error fallback */
.               { return ERROR; }