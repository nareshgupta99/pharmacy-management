FROM openjdk:11

RUN mkdir -p /src/app/bin

RUN mkdir -p /src/app

WORKDIR /src/app

COPY . /src/app

RUN javac src/pharmacy/*.java src/misc/*.java src/module/*.java  src/exceptions/*.java -d bin



CMD ["java","-cp","bin","pharmacy.Pharmacy"]