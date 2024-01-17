FROM ubuntu:latest
MAINTAINER rahul_vishwakarma1
RUN apt-get update
RUN apt-get -y install curl gnupg
RUN curl -sL https://deb.nodesource.com/setup_14.x  | bash -
RUN apt-get -y install nodejs

# Copy only the necessary files and directories
COPY ./ui /home/ui
# Switch to the UI directory
WORKDIR /home/ui
# Create a non-root user and group, set proper permissions for the working directory
RUN useradd ui

RUN chmod -R ugoa+rwx /home/ui && chown -R ui:0 /home/ui
# Use a separate user for running the application
USER ui

# Expose the application port
EXPOSE 3000

# Install npm dependencies
RUN npm install
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache
# Specify the default command to run the application

#RUN npm run build
#RUN npm install -g serve 
#CMD ["npx","serve","-s","build"]
CMD ["npm", "start"]