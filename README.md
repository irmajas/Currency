# Currency
EIS atrankos projektas

Lietuvos banko valiutų kursai gali būti nuskaitomi http://www.lb.lt/webservices/fxrates/ pateikiant SOAP request arba tiesiog Get HTTP request.
Nors užklausa suformuota teisingai, tačiau nuolat vyksta perardesavimas i pagrindini lietuvos banko puslapį ir gaunamas kodas 302.
Pateikiant tą užklausą pačiame puslapyje, viskas gerai. Todėl kad greičiau pateikti veikiatį programos variantą, pateiktos užklausos rezultatus įrašiau į failą ir programa dirba su tais duomenimis iš tų failų. 
