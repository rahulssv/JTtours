FROM ubuntu:22.04
MAINTAINER rahul_vishwakarma1

ENV PATH=/node-v14.17.6-linux-x64/bin:$PATH

RUN groupadd -r ui &&\
    useradd -r -g ui ui;

RUN set -eux &&\ 	
    apt-get update &&\  	
    DEBIAN_FRONTEND=noninteractive apt-get full-upgrade -y &&\ 
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends curl gnupg wget pwgen openssl perl xz-utils tzdata gosu &&\
    wget --no-check-certificate https://nodejs.org/dist/v14.17.6/node-v14.17.6-linux-x64.tar.gz  && \
    tar -C / -xzf node-v14.17.6-linux-x64.tar.gz && \
    rm -rf node-v14.17.6-linux-x64.tar.gz && \
    DEBIAN_FRONTEND=noninteractive apt-get remove --purge --auto-remove -y &&\ 
    rm -rf /var/lib/apt/lists/*
    
COPY ./ui /home/ui

WORKDIR /home/ui

RUN chmod -R ugoa+rwx /home/ui && chown -R ui:ui /home/ui

RUN npm install
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache

EXPOSE 3000

CMD npm run build && npx serve -s build
#CMD ["npm", "start"]