## create network 
```
docker network create patient-network 
```
## create patientreportdb 
```
cd patient-report
docker compose up -d
```
## create database-ihm
```
cd database-ihm
docker compose up -d
```
## verification
```
docker ps -a
```