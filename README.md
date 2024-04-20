
# KEYBOARD DISTRIBUTION GENERATOR 
### Projecte de Disseny de Teclat - PROP QTardor 2023
Aquest projecte es dedica al desenvolupament de layouts de teclats optimitzats per a l'ús amb un sol dit a partir de texts o freqüències de paraules. Utilitzem algoritmes basats en el mètode QAP (Quadratic Assignment Problem) per optimitzar les disposicions segons la freqüència de paraules en un alfabet específic.


![Keyboard Layout](FONTS/resources/logo33.png "Keyboard Layout")


Aquest projecte està desenvolupat utilitzant Gradle Wrapper (gradlew) per a construir i executar l'aplicació Java.

## Requisits Previs
- Java JDK 11
- Gradle (Gradle Wrapper està inclòs en el projecte)

## Executar l'Aplicació
Per executar l'aplicació t'has de trobar en el directori root (FONTS), utilitza la següent comanda en sistemes Unix/Linux::

```
./KeyLayoutGenerator.sh
```
Si no tens permisos, atorga'ls amb:

```
chmod +x KeyLayoutGenerator.sh
```

Per a sistemes Windows, utilitza:
```
./KeyLayoutGenerator.bat
```

### Alternativament, pots executar directament el fitxer JAR generat amb Java des de qualsevol sistema:

```
java -jar build/libs/ProjectePROPsubgrup33_3-3.0.jar  
```

## Construir el Projecte i Generar Documentació
Per construir el projecte, executa la següent comanda al directori arrel del projecte:

```
./make.sh
```

Si no tens permisos, atorga'ls amb:

```
chmod +x make.sh
```

Per a sistemes Windows, utilitza:

```
chmod +x make.bat
```

Aquesta comanda compila el projecte i genera un fitxer JAR al directori `build/libs`.
També genera la documentació  al directori `build/docs/javadoc`.

Per a accedir a la documentació generada fes doble clic sobre el link generat al directori root FONTS, anomenat 
    
    javadocs

Opcionalment, es pot obrir amb el teu browser preferit executant:

    [browser] javadocs

Addicionalment pots accedir directament a aquest fitxer a 

    build/docs/javadoc/index.html


## Crèdits 
Desenvolupat per 
- Pau Mayench Caro 
- Josep Diaz Sosa
- Marc Expósito Francisco
- Víctor Hernández Barragán 

PROP Grup 33.3 - Q-Tardor 2023 FIB
