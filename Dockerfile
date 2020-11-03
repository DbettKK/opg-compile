FROM openjdk:8
WORKDIR /app/
COPY ./* /app/
RUN javac ./com/dbettkk/opg/*.java