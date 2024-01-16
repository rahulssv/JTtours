FROM docker.io/jenkins/jenkins:lts
USER root
COPY $PWD/oc /usr/local/bin/
RUN chmod +x /usr/local/bin/oc
RUN apt-get update -qq \
    && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common 
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
RUN apt-get update  -qq \
    && apt-cache policy docker-ce\
    && apt-get install docker-ce -y
RUN usermod -aG docker jenkins

#$ chown 1000 $PWD/jenkins
#$ cd jenkins/
#$ docker run -d -p 49001:8080 -v /var/run/docker.sock:/var/run/docker.sock -v $PWD/jenkins:/var/jenkins_home:z -t rahul1181/jenkins