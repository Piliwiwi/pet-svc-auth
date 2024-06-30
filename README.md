# Pet Service Auth Example

Este repositorio contiene el servicio de autenticación para la aplicación de ejemplo para el registro y seguimiento de la información médica de mascotas.

## Requisitos

- **Java 17**: Asegúrate de tener Java 17 instalado y configurado en tu IDE (IntelliJ IDEA).
- **MongoDB**: Necesitarás crear tu propia base de datos en MongoDB.
- **.env**: Debes crear un archivo `.env` con los valores necesarios para configurar los servicios.

## Configuración del Entorno

1. **Java 17**: Instala Java 17 desde [la página oficial de Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

   - **Configuración en IntelliJ IDEA**:
     1. Abre IntelliJ IDEA y ve a `File > Project Structure`.
     2. En la sección `Project`, selecciona `Project SDK` y haz clic en `Add SDK`.
     3. Selecciona `JDK` y navega hasta el directorio donde instalaste Java 17.
     4. Aplica y guarda los cambios.

2. **MongoDB**: Crea una base de datos en MongoDB para el servicio de autenticación. Puedes hacerlo localmente o usar un servicio como MongoDB Atlas.

3. **Archivo .env**: Crea un archivo `.env` en el directorio raíz del proyecto con el siguiente contenido:

    ```env
    DATABASE_URL=mongodb://localhost:<puerto>/auth_db
    DATABASE_USERNAME=tu_usuario
    DATABASE_PASSWORD=tu_contraseña
    JWT_SECRET=tu_secreto_jwt
    ```

   - Reemplaza `<puerto>` con el puerto correspondiente para tu base de datos MongoDB.

## Servicios Relacionados

Este servicio de autenticación es utilizado por otros servicios que se encuentran en los siguientes repositorios:

- [Servicio MiddleEnd](https://github.com/Piliwiwi/pet-svc-middleend-example)
- [Servicio de Mascotas](https://github.com/Piliwiwi/pet-svc-pets-example)
- Servicio de Vacunas (solo de ejemplo, no existe repositorio)

## Aplicación de Ejemplo

La aplicación móvil de ejemplo que utiliza este servicio se encuentra en el siguiente repositorio:

- [Pet App Example](https://github.com/Piliwiwi/pet-app-example)

## Ejecución

1. Clona este repositorio y navega al directorio del proyecto:

    ```bash
    git clone https://github.com/Piliwiwi/pet-svc-auth-example.git
    cd pet-svc-auth-example
    ```

2. Asegúrate de que la base de datos está corriendo y configurada en tu archivo `.env`.

3. Ejecuta el servicio de autenticación:

    ```bash
    ./gradlew bootRun
    ```

## Contribución

Si deseas contribuir a este proyecto, por favor abre un `issue` o envía un `pull request` con tus cambios.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
