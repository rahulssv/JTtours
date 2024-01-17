FROM ubuntu:latest
MAINTAINER rahul_vishwakarma1
RUN apt-get update
RUN apt-get -y install curl gnupg
RUN curl -sL https://deb.nodesource.com/setup_14.x  | bash -
RUN apt-get -y install nodejs

COPY ./ui /home/ui

WORKDIR /home/ui

RUN useradd ui

RUN chmod -R ugoa+rwx /home/ui && chown -R ui:0 /home/ui

USER ui

EXPOSE 3000

RUN npm install
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache

RUN npm run build
CMD ["npx","serve","-s","build"]
#CMD ["npm", "start"]