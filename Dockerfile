#Etapa 1: Build con gradle 
FROM gradle:8.5-jdk21 AS build
WORKDIR /build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

#Etapa 2: Imagen final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

#Se copia JAR
COPY --from=build /build/build/libs/*.jar app.jar

#Variables por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"

#Usuario no root y Directorio para PDFs 
RUN mkdir -p /app/constancias && adduser -D -u 1001 appuser && chown -R appuser:appuser /app
USER appuser

EXPOSE 8082

#Ejecuta la app
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]