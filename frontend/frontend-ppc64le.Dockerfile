# FROM registry.access.redhat.com/ubi8/ubi:8.4 as js-builder
FROM ppc64le/ubuntu:22.04
MAINTAINER "Rahul Vishwakarma <Rahul.Vishwakarma2@ibm.com>"

ENV PATH=/node-v14.17.6-linux-ppc64le/bin:$PATH

RUN apt-get update -y && \
    apt-get install -y wget git && \
    wget https://nodejs.org/dist/v14.17.6/node-v14.17.6-linux-ppc64le.tar.gz && \
    tar -C / -xzf node-v14.17.6-linux-ppc64le.tar.gz && \
    rm -rf node-v14.17.6-linux-ppc64le.tar.gz && \
    npm install -g yarn

COPY ./ui /home/ui

WORKDIR /home/ui
        
RUN useradd ui
        
RUN chmod -R ugoa+rwx /home/ui && chown -R ui:0 /home/ui
        
USER ui
        
EXPOSE 3000
        
RUN npm install
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache
        
CMD npm run build && npx serve -s build
#CMD ["npm", "start"]