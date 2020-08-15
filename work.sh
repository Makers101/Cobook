sudo ./frontend/run.sh
sudo ./node/run.sh
sudo chmod 777 build/libs/cobook-0.0.1-SNAPSHOT.jar
sudo nohup java -jar build/libs/cobook-0.0.1-SNAPSHOT.jar -Dspring.config.location=/var/lib/jenkins/workspace/application-real.yml