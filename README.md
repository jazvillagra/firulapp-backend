# Firulapp-backend

## Tecnologías

- Java 11 (OpenJDK 11)
- Spring Boot 2.4.2
- Postgres 11
- Maven 3.x

## Setup de ambiente de trabajo

Para instalar el ambiente de desarrollo:

* Clonar el proyecto desde 

* Importar el proyecto Maven: 
  * En IntelliJ IDEA: `Import project > Import project from external model > Maven > Select project > Select JDK > Finish`

* Editar datos del `application.properties`
```
spring.datasource.name=firulapp_db
spring.datasource.url=jdbc:postgresql://<dbConnectionUrl>:<dbport>/<dbname>
spring.datasource.username=<dbUsername>
spring.datasource.password=<dbPassword>
mail.smtp.user=<mail>
mail.smtp.password=<base64EncryptedPassword>
mail.smtp.auth=true
mail.smtp.starttls.enable=true
mail.smtp.host=<maiHost>
mail.smtp.port=<mailPort>
```

* Ejecutar el proyecto
  * Desde el IDE: `Run > Edit configurations > Save > Execute(F8)`
  * Desde la linea de comandos: `mvn spring-boot:run`

## Construcción de jar

Para construir el jar, solo se debe ejecutar en la linea de comandos: `mvn clean install`

## Licencia
<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.
