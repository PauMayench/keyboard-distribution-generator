
#!/bin/bash

# Script per construir i generar documentació per al projecte KEYBOARD DISTRIBUTION GENERATOR

# Construint el projecte
echo "Construint el projecte..."
./gradlew build

# Generant documentació
echo "Generant documentació..."
./gradlew generateJavadoc

# Creant un enllaç simbòlic a l'index.html de la documentació Javadoc
echo "Creant un enllaç simbòlic per a la documentació Javadoc..."
ln -s build/docs/javadoc/index.html javadocs

echo "Fet."
