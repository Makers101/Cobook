sudo chmod 777 ./frontend/run.sh
sudo chmod 777 ./node/run.sh
sudo ./frontend/run.sh
sudo ./node/run.sh
sudo chmod 777 build/libs/cobook-0.0.1-SNAPSHOT.jar
ps -ef | grep cobook | grep -v 'grep' | awk '{print $2}' | xargs sudo kill -9
sudo nohup java -jar build/libs/cobook-0.0.1-SNAPSHOT.jar -Dspring.config.location=/var/lib/jenkins/workspace/application-real.yml