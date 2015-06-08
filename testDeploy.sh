#!bin/bash

# Move to frontend project and prepare resources
cd ngbp
grunt packageBank
if [ $? -ne 0 ]
	then echo 'Transfering forntend resources failed!'
	return
fi

# Moove to backend project and jar source
cd ../bank
mvn clean package
if [ $? -ne 0 ]
	then echo 'Failed to jar backend project!'
	return
fi

# Transfer packaged project to dev
devdest=clouway@dev.telcong.com:/opt/telcong/incubator/m-bank/
localdest=/home/clouway/demobank/

rsync -v config.properties $localdest
rsync -v target/BrickBank-jar-with-dependencies.jar $localdest
rsync -vr src/main/webapp/ $localdest/frontend/

# Start up newly transferred project
cd $localdest
java -jar BrickBank-jar-with-dependencies.jar config.properties 

cd /home/clouway/workspace/projects/incubator/angularjs/brick-angular-bank
