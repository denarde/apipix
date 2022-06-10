call mvn clean install -DskipTests

docker build -t denarde/api-pix-rest:1.0.0 .

docker compose up -d