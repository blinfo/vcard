# Refactoring - vcard

En liten genomgång med refactoring steg för steg.

## V.1.0

Syfte:

Omvandla ett vcard till java-objekt.

Första versionen innehåller två klasser: **Person** entitetsklass som motsvarar innehållet i vcard-filen, samt **VcardReader** som läser innehållet och omvandlar det till Person-objektet.

För att göra det hela enklare läses bara vcard-version 4.0. 

Exempel på vcard-filer finns under "resources".
