### Syt - Mitschrift 
#### Datum: 11.06.2020
---
#### Thema
WordPress aufsetzen mit Docker compose mithilfe eines .yml files.

**1.docker-compose.yml file schreiben**

```
version: '3.1'
services: 
    wordpress:
        image: wordpress
        container_name: dev_david_hieselmayr
        restart: always
        ports:
            - 8080: 80
        enviroment:
            WORDPRESS_DB_HOST: db
            WORDPRESS_DB_USER: wordpress
            WORDPRESS_DB_PASSWORD: wordpress
            WORDPRESS_DB_NAME: wordpress
        volumes: 
            - wordpress:/var/www/html
    db:
        image: mysql:5.7
        restart: always
        enviroment:
            MYSQL_DATABASE: wordpress
            MYSQL_USER: wordpress
            MYSQL_PASSWORD: wordpress
            MYSQL_RANDOM_ROOT_PASSWORD:'1'
        volumes:
            - db:/var/lib/mysql
volumes:
    wordpress:
    db:
```
**2.Terminal**
```
docker-compose up
```
**3.Erklärung**<br>
**a.**
```
    ports:
        - 8080:80 (Host/Docker intern)
```
> (nach draußen/ in der Sandbox)

**b.**
```
    environment:
        WORDPRESS_DB_HOST: db
        WORDPRESS_DB_USER: wordpress
        WORDPRESS_DB_PASSWORD: wordpress
        WORDPRESS_DB_NAME: wordpress
```
> es gibt manchmal enviroment variablen die festgelegt werden müssen

[Docker Hub wordpress](https://hub.docker.com/_/wordpress/)

**c.**
```
volumes:
    - wordpress:/var/www/html
```
> wird ausgelagert


