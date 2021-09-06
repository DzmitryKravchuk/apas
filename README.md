# Automatic Postal Analytical System (APAS) api service

* Swagger is available by the path: http://localhost:8080/swagger-ui.html
* Heroku is available by the path: http://


* Start application with docker: $> gradle composeUp


* Connection to dataBase in container:
1) $> docker exec -it {ContainerId} bash 
2) root@...:/# psql -d apas -h localhost -p 5432 -U postgres
3) apas=# \c