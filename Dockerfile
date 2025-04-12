# Java 17 をベースにした公式イメージ
FROM eclipse-temurin:17-jdk-alpine

# 作業ディレクトリの作成
WORKDIR /app

# Maven Wrapper など含めた全ファイルをコピー
COPY . .

EXPOSE 8080

# Maven Wrapper で依存関係をダウンロードし、アプリをビルド
RUN ./mvnw clean package -DskipTests

# JARファイルの名前（targetフォルダの中）を指定
# Spring Boot Maven Plugin により fat JAR が生成されます
CMD ["java", "-jar", "target/SpringStart10-0.0.1-SNAPSHOT.jar"]
