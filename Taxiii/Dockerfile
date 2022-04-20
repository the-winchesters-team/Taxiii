FROM maven:3.8.5-openjdk-17
WORKDIR /moroccan-pixels-backend
COPY . .
RUN mvn clean package

CMD java -jar Taxiii-0.0.1-SNAPSHOT.jar