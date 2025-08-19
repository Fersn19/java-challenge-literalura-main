
<div align="center">
  <h1 align="center">
     Challenge LiterAlura
    <br />
    <br />
    <a href="https://github.com/Fersn19/java-challenge-literalura">
      <img src="https://github.com/Fersn19/java-challenge-literalura-main/blob/main/src/main/resources/fondo.png" alt="♨️ imagen-LiterAlura ⚙️">
    </a>
  </h1>
</div>

#  Literalura — Challenge

> Desarrollé una aplicación de línea de comandos para un reto de programación. Esta herramienta te permite buscar libros a través de una API, almacenarlos en una base de datos y consultarlos sin conexión a internet.



## 🔧 Instalación y ejecución

### 1. Clona el proyecto
```bash
git clone https://github.com/tu-usuario/literalura.git
cd literalura
```

### 2. Configura tu base de datos PostgreSQL
Crea una base de datos llamada `literalura` y ajusta las credenciales en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecuta el proyecto
```bash
./mvnw spring-boot:run
```

---

