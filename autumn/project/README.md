# Lineate Sunday School project template

This repo contains the template of the project for Thumbtack(Lineate) Sunday School.
If you are a student of the school - please replace the content of this file with your project info.

## Requirements:
1. Node v12.x (you could try v10)
2. NPM v6.x

## How to install requirements:
1. `npm install`
1. `(cd ./backend && npm install)`
2. `(cd ./frontend && npm install)`
2. `(cd ./frontend && npm run build)`

## Repo contains
1. Backend template: 'backend' folder
2. Frontend template: 'frontend folder
3. Database backups: 'ops' folder

docker-compose file uses postgres as database.
You can change db_user and db_password in docker-compose.yml file.

## How to use Docker:
Run all commands project root folder

### Start containers
`docker-compose up -d`
### Show logs containers
`docker-compose logs -f web`

## How to run app:
1. `docker-compose up -d`
2. Go to http://localhost

## How to watch static:
`cd ./frontend && npm run watch`

# DB commands
## Make a dump
`docker-compose exec db sh -c 'exec pg_dump -U postgres lntsunday > /backup/dump.sql'`

## Restore from the dump
`docker-compose exec db sh -c 'exec psql -U postgres lntsunday < /backup/dump.sql'`

# Useful commands
## Clean all docker containers info
`docker-compose stop && docker-compose down --rmi local --volumes --remove-orphans`

## Styling
(You should pick one of these)
1. CSS
2. SASS
3. CSS-in-JS

## CSS frameworks
(You should pick one of these)
1. Bulma (https://bulma.io/)
2. Bootstrap
3. My own styles
4. Rebass

# Frontend
## Project structure
### components 
Contains all base (global) components of the app. Like 'button', 'table', 'form'
### scenes 
Contains big container components with a lot of logic in them.
Usually each of Route component will be a scene.

Each scene could include its own 'components' folder and even its own 'scenes' folder.
