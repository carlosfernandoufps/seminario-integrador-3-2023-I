# Runtime

#Imagen básica: el almacén es java y la etiqueta es 17-jdk
FROM eclipse-temurin:17-jdk-focal

# Mantenimiento e información de contacto del espejo actual
MAINTAINER nataliaortiz789.github.io

# Crear un archivo temporal en el directorio / var / lib / docker de la máquina host y vincularlo al directorio / tmp en el contenedor.
VOLUME /tmp

#
WORKDIR /app

# Variable JAR_FILE para almacenar la ruta del archivo JAR
ARG JAR_FILE=docs/*.jar

# Copiar el programa empaquetado en la ubicación especificada en el contenedor
ADD ${JAR_FILE} app.jar

# Comandos que deben ejecutarse después de que se inicia el contenedor
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

# El contenedor expone el puerto 8091
EXPOSE 8091

