FROM registry.access.redhat.com/ubi8/ubi:8.7

MAINTAINER "Rahul Vishwakarma <Rahul.Vishwakarma2@ibm.com>"

RUN yum update -y && \
        yum install -y curl gnupg && \
        export NODE_VERSION=${NODE_VERSION:-16} && \
        curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash && \
        source "$HOME"/.bashrc && \
        echo "installing nodejs $NODE_VERSION" && \
        nvm install "$NODE_VERSION" >/dev/null && \
        nvm use $NODE_VERSION 

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