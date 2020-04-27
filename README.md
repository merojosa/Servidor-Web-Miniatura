# Servidor Web Miniatura

Para correr el proyecto en IntellJ:

1. Abrir el proyecto con el archivo build.gradle
2. Forma sencilla, buscar el main y correrlo. Así, IntelliJ guarda la configuración sencilla por si se quiere volver a correr. En otros IDEs, de igual manera se tiene que abrir el archivo build.gradle.

Para probar el programa incluyendo la validación del error 404:
1. Desde el navegador entrar a http://localhost:PUERTO/. Para saber el valor de PUERTO hay que fijarse en ServidorWeb.java
2. Desde la terminal con curl. Ejemplo de una solicitud HEAD: curl -X HEAD -i http://localhost:PUERTO/

Para probar la validación del error 406:
curl -H "Accept: image/gif" -i http://localhost:PUERTO/

##### Hecho por Jose  Andrés Mejías Rojas
